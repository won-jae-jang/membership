const commentBtn = document.getElementById("submit");

// const currentPath = window.location.pathname;
// const pathParts = currentPath.split("/"); // 경로를 '/' 기준으로 나누기
// const boardId = pathParts[pathParts.length - 1]; //게시판 pk

function findBoardId() {
  let currentPath = window.location.pathname;
  let pathParts = currentPath.split("/"); // 경로를 '/' 기준으로 나누기
  return pathParts[pathParts.length - 1]; //게시판 pk
}

//댓글 입력
commentBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  const content = document.getElementById("comment-input").value;
  if (content === "") {
    alert("댓글을 입력해주세요");
    return;
  }

  try {
    const boardId = findBoardId();
    console.log(`/comments/${boardId}`);
    console.log("보낼 데이터: " + content);
    const response = await fetch(`/comments/${boardId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
      body: JSON.stringify({ content }),
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        //댓글 디비 및 화면 반영 todo
        if (data.success) {
          location.href = `/boards/${boardId}`;
        }
      });
  } catch (error) {}
});
