import { atom } from 'recoil';
import { recoilPersist } from 'recoil-persist';

const { persistAtom } = recoilPersist();

export const userInfoState = atom({
  key: 'userInfoState',
  default: [],
  effects_UNSTABLE: [persistAtom],
});

export const singleStudyState = atom({
  key: 'singleStudyState',
  default: {},
  effects_UNSTABLE: [persistAtom],
});
