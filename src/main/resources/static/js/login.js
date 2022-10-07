const registerButton = document.querySelectorAll(".login-button")[0];
const loginButton = document.querySelectorAll(".login-button")[1];

registerGoButton.onclick = () => {
    location.href = "/account/register";
}

loginButton.onclick = () => {
    const loginForm = document.querySelector("form");
    // loginForm.submit();
}
