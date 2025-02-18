package com.team_60.Mocco.alarm.service;

import com.team_60.Mocco.alarm.entity.Alarm;
import com.team_60.Mocco.alarm.repository.AlarmRepository;
import com.team_60.Mocco.exception.businessLogic.BusinessLogicException;
import com.team_60.Mocco.exception.businessLogic.ExceptionCode;
import com.team_60.Mocco.helper.sse.SseService;
import com.team_60.Mocco.member.entity.Member;
import com.team_60.Mocco.member.service.MemberService;
import com.team_60.Mocco.study.entity.Study;
import com.team_60.Mocco.study_member.entity.StudyMember;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class AlarmServiceImpl implements AlarmService{

    private final AlarmRepository alarmRepository;
    private final MemberService memberService;
    private final SseService sseService;

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public SseEmitter subscribeAlarm(long memberId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        SseEmitter sseEmitter = sseService.subscribeAlarm(findMember);

        List<Alarm> findAlarms = alarmRepository.findByMember(findMember);
        sseService.publishAlarmToEmitter(findMember, findAlarms, sseEmitter);
        return sseEmitter;
    }

    @Override
    @Transactional(readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public void unsubscribeAlarm(String subscribeId) {
        sseService.unsubscribeAlarm(subscribeId);
    }

    @Override
    @Transactional(readOnly = true)
    public List<Alarm> findAlarmsByMemberId(long memberId) {

        Member findMember = memberService.findVerifiedMember(memberId);
        return alarmRepository.findByMember(findMember);
    }

    @Override
    public void deleteAlarm(long alarmId) {
        Alarm findAlarm = findVerifiedAlarm(alarmId);
        alarmRepository.delete(findAlarm);
    }

    @Override
    public void deleteAlarmsByMemberId(long memberId) {
        Member findMember = memberService.findVerifiedMember(memberId);
        alarmRepository.deleteAllByMember(findMember);
    }

    @Override
    public void createAlarmWhenStudyOpen(Study study) {

        for (StudyMember studyMember : study.getStudyMemberList()){
            Member member = studyMember.getMember();
            Alarm alarm = createNewAlarm(study, member, Alarm.AlarmType.STUDY_OPEN);
            alarm.setContent(study.getTeamName() + "가 오늘부터 시작되었습니다.");
            Alarm createAlarm = alarmRepository.save(alarm);
            sseService.publishAlarm(member, createAlarm);
        }
    }

    @Override
    public Alarm createAlarmWhenStudyNotOpen(Study study) {
        Alarm alarm = createNewAlarm(null, study.getTeamLeader(), Alarm.AlarmType.STUDY_NOT_OPEN);
        alarm.setContent(study.getTeamName() + "가 최소 인원이 충족되지 않아 개설되지 않았습니다.");
        Alarm createAlarm = alarmRepository.save(alarm);
        sseService.publishAlarm(study.getTeamLeader(), createAlarm);
        return createAlarm;
    }

    @Override
    public Alarm createAlarmWhenProposalApprove(Study study, Member member) {
        Alarm alarm = createNewAlarm(study, member, Alarm.AlarmType.STUDY_ENTER);
        alarm.setContent(study.getTeamName() + "에 초대되셨습니다.");
        Alarm createAlarm = alarmRepository.save(alarm);
        sseService.publishAlarm(member, createAlarm);
        return createAlarm;
    }

    public Alarm findVerifiedAlarm(long alarmId){
        Optional<Alarm> optionalAlarm = alarmRepository.findById(alarmId);
        Alarm findAlarm = optionalAlarm.orElseThrow( () ->
                new BusinessLogicException(ExceptionCode.ALARM_NOT_FOUND));
        return findAlarm;
    }

    private Alarm createNewAlarm(Study study, Member member, Alarm.AlarmType alarmType) {
        Alarm alarm = new Alarm();
        alarm.setAlarmType(alarmType);
        alarm.setStudy(study);
        alarm.setMember(member);

        return alarm;
    }
}
