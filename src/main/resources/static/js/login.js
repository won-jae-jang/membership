const loginBtn = document.getElementById("loginBtn");
const LONGIN_FAIL_CODE = "loginFail"; //로그인에 실패한 경우
const LOGIN_BAD_REQUEST_CODE = "USER-EX"; //사용자가 올바르지 않는 데이터를 전송

loginBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  const username = document.getElementById("loginUsername").value;
  const password = document.getElementById("loginPassword").value;

  try {
    const response = await fetch("/members/login", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ username, password }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data.success) {
          location.href = "/";
        } else if (data.code === LONGIN_FAIL_CODE) {
          alert("로그인 실패");
        } else if (data.code === LOGIN_BAD_REQUEST_CODE) {
          alert("아이디나 비밀번호가 올바르지 않는 형식입니다.");
        }
      });
  } catch (error) {
    alert(error);
  }
});
