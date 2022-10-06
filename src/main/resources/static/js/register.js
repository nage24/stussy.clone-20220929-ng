const registerButton = document.querySelector(".login-button");
const registerInputs = document.querySelector(".register-input");

for(let i = 0; i < registerInputs.length; i++) {
    registerInputs[i].onkeyup = () => {
        if(window.event.keyCode == 13){
            if(index != 3) {
                registerInputs[i].focus(index + 1);
            }else {
                registerButton.click();
            }
    }
}



registerInputs.forEach((input, index) => {
    input.onkeyup = () => {

        }

    }

})






registerButton.onclick = () => {
    const registerInputs = document.querySelectorAll(".login-input");


        let registerInfo = {
            lastName: registerInputs[0].value,
            firstName: registerInputs[1].value,
            email: registerInputs[2].value,
            password: registerInputs[3].value
        }



        $.ajax({
            async: false,
            type: "post",
            url: "/api/account/register",
            contentType: "application/json",
            data: JSON.stringify(registerInfo),
            dataType: "json",
            success: (response) => {
                console.log(response);
                location.replace("/account/login");
            },
            error: (error) => {
                console.log(error);
                validationError(error.responseJSON.data); // error.responseJSON 의 data
            }
        });

}

function validationError(error) { // error 객체  value 만 뽑아서  forEach 돌리는 거
    const accountErrors = document.querySelector(".account-errors");
    const accountErrorList = accountErrors.querySelector("ul");

    const errorValues = Object.values(error); // Entries ; (key , value)

    accountErrorList.innerHTML = ""; // 초기화

    errorValues.forEach((value) => {
        accountErrorList.innerHTML = `
                    <li>${value}</li>
        `;
    })

    accountErrors.classList.remove("account-invisible");
}