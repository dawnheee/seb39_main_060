import { css } from '@emotion/react';

function ProposaTab() {
  return (
    <div
      css={css`
        display: flex;
        justify-content: center;
        align-items: center;
        padding: 40px;

        input {
          width: 900px;
          height: 40px;
          border: 1px solid #d1d1d1;
          border-radius: 5px;
        }

        button {
          margin-left: 60px;
          width: 120px;
          height: 40px;
          font-size: 20px;
          font-weight: 500;
          color: #ffffff;
          border: none;
          background-color: #0b6ff2;
          border-radius: 8px;
        }
      `}
    >
      <input type="text" placeholder="신청을 위한 한 마디를 적어주세요" />
      <button>등록</button>
    </div>
  );
}

export default ProposaTab;
