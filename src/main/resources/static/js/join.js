const joinBtn = document.getElementById("joinBtn");

joinBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  const username = document.getElementById("joinUsername").value;
  const password = document.querySelector("#joinPassword").value;

  try {
    const response = await fetch("/members/join", {
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
          alert("회원가입에 성공하였습니다");
          const signInButton = document.getElementById("signIn");
          signInButton.click();
        } else {
          alert("아이디나 비밀번호가 올바른 형태가 아닙니다");
        }
      });
  } catch (error) {
    alert(error);
  }
});
