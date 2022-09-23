import { css } from '@emotion/react';
import { useState } from 'react';
import ProposalTab from './ProposalTab/ProposalTab';
import QuestionTab from './QuestionTab/QuestionTab';

function TabSection() {
  const [currentTab, SetCurrentTab] = useState(0);

  const tabList = [
    { name: '질답 게시판', content: <QuestionTab /> },
    { name: '신청 게시판', content: <ProposalTab /> },
  ];

  const selectTabHandler = (idx) => {
    SetCurrentTab(idx);
  };

  return (
    <div css={container}>
      <ul css={tab_title}>
        {tabList.map((el, idx) => (
          <li
            key={idx}
            role="presentation"
            className={currentTab === idx ? 'tab focused' : 'tab not_focused'}
            onClick={() => selectTabHandler(idx)}
          >
            {el.name}
          </li>
        ))}
      </ul>
      <div css={tab_container}>{tabList[currentTab].content}</div>
    </div>
  );
}

export default TabSection;

const container = css`
  padding-bottom: 100px;
`;

const tab_title = css`
  display: flex;
  font-size: 17px;
  font-weight: 500;

  .tab {
    padding: 17px;
    border: 1px solid #d1d1d1;
    border-bottom: none;
    border-radius: 10px;
    border-bottom-left-radius: 0px;
    border-bottom-right-radius: 0px;
    cursor: pointer;
    color: #5b5d61;
    transition: all 0.1s linear;
    margin-bottom: -2px;
  }
  .tab:hover,
  .focused {
    color: #0b6ff2;
    /* border-bottom: 3px solid #0b6ff2; */
    border-top: 10px solid #0b6ff2;
    padding-top: 8px;
    border-bottom: 2px solid #fff;
  }
`;

const tab_container = css`
  display: flex;
  flex-direction: column;
  align-items: center;
  border: 1px solid #d1d1d1;
  border-radius: 15px;
  border-top-left-radius: 0px;
  padding: 40px;
`;
