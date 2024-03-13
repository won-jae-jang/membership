const writeBtn = document.getElementById("writeBtn");
const logoutBtn = document.getElementById("logoutBtn");

writeBtn.addEventListener("click", () => {
  location.href = "/boards/write";
});

logoutBtn.addEventListener("click", async (event) => {
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
  } catch (error) {}
});
