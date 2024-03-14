const logoutBtn = document.getElementById("logoutBtn");

logoutBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  try {
    const response = await fetch("/members/logout", {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data.success) {
          location.href = "/";
        }
      });
  } catch (error) {
    alert(error);
  }
});
