const writeBtn = document.getElementById("writeBtn");
const SERVER_INTERNAL_ERROR = "EX";

writeBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  const title = document.getElementById("title").value;
  const content = document.getElementById("content").value;

  if (title === "" || content === "") {
    alert("제목이나 본문은 필수로 입력해야 합니다");
    return;
  }

  const response = await fetch("/boards/write", {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
    },
    body: JSON.stringify({ title, content }),
  })
    .then((response) => response.json())
    .then((data) => {
      console.log(data);
      if (data.success) {
        location.href = `/boards/${data.boardId}`;
      } else if (data.code === SERVER_INTERNAL_ERROR) {
        alert("서버 내부 오류가 발생했습니다.");
      }
    });
});
