const boardListBtn = document.getElementById("board-list");
const updateBtn = document.getElementById("board-update");
const boardRemoveBtn = document.getElementById("board-remove");

function findBoardId() {
  let currentPath = window.location.pathname;
  let pathParts = currentPath.split("/"); // 경로를 '/' 기준으로 나누기
  return pathParts[pathParts.length - 1]; //게시판 pk
}

boardListBtn.addEventListener("click", () => {
  location.href = "/boards";
});

//게시판 수정
updateBtn.addEventListener("click", async (event) => {
  event.preventDefault(); // 기본 동작(페이지 새로고침) 방지

  try {
    const boardId = findBoardId();
    const response = await fetch(
      `/boards/update-check-authorized?boardId=${boardId}`,
      {
        method: "GET",
        headers: {
          "Content-Type": "application/json",
        },
      }
    )
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        if (data.authorized) {
          location.href = `/boards/update?boardId=${boardId}`;
        } else {
          alert("글을 업데이트 할 권한이 없습니다");
        }
      });
  } catch (error) {}
});

//게시판 삭제
boardRemoveBtn.addEventListener("click", async (event) => {
  const boardId = findBoardId();
  try {
    const response = await fetch(`/boards/delete/${boardId}`, {
      method: "POST",
      headers: {
        "Content-Type": "application/json",
      },
    })
      .then((response) => response.json())
      .then((data) => {
        console.log(data);
        //글 삭제 권한 확인 및 리다이랙트 todo
        if (data.success) {
          location.href = "/boards";
        } else {
          alert("글을 삭제할 권한이 없습니다");
        }
      });
  } catch (error) {}
});
