import { css } from '@emotion/react';

function ModifyUserInput({ labelText, name, type }) {
  return (
    <div>
      <label htmlFor={name}>
        <h3 css={labelTextStyle}>{labelText}</h3>
      </label>
      {type === 'textarea' && <textarea css={textareaStyle} />}
      {type !== 'textarea' && <input css={inputStyle} />}
    </div>
  );
}

const inputStyle = css`
  width: 100%;
  height: 40px;
  background-color: #ffffff;
  border-radius: 5px;
  border: 1px solid #d1d1d1;
  margin-bottom: 12px;
`;

const textareaStyle = css`
  width: 100%;
  height: 120px;
  background-color: #ffffff;
  border-radius: 5px;
  border: 1px solid #d1d1d1;
  margin-bottom: 12px;
`;

const labelTextStyle = css`
  font-size: 18px;
  margin-bottom: 12px;
`;

export default ModifyUserInput;
