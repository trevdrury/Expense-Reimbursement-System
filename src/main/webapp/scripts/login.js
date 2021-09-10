window.onload = async function () {
  // check session
  const sessionResponse = await fetch(`${domain}/api/check-session`);
  const sessionData = await sessionResponse.json();

  console.log(sessionData);

  if (sessionData.data) {
    window.location = `${domain}/dashboard`;
  }
};

let loginForm = document.getElementById("login-form");

loginForm.onsubmit = async (e) => {
  e.preventDefault();

  let username = document.getElementById("username").value;
  let password = document.getElementById("password").value;
  let response;

  if ((username.length == 0) | (password.length == 0)) {
    alert("You must enter a username and password");
    return false;
  } else {
    response = await fetch(`${domain}/api/login`, {
      method: "POST",
      body: JSON.stringify({
        username: username,
        password: password,
      }),
    });
  }

  let responseData = await response.json();

  if (responseData.success) {
    window.location =
      `${domain}/dashboard?role=${responseData.data.userRoleId}` +
      `&uName=${responseData.data.username}&id=${responseData.data.userId}`;
  }
};
