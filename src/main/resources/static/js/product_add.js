const fileAddButton = document.querySelector(".add-button");
const fileInput = document.querySelector(".file-input")

let productImageFiles = new Array();


fileAddButton.onclick = () => {
    fileInput.click();
}

fileInput.onchange = () => {
    const formData = new FormData().document.querySelector("form");

    formData.forEach((value)  => {
        if(value.size != 0) {
            productImageFiles.push(value);
            console.log(productImageFiles);

            getImagePreview();
        }
        // console.log("value: " + value);
        // console.log("key: " + key);
    });
}

function getImagePreview() {
// img 정보 배열
    const productImages = document.querySelector(".product-images");


    const reader = new FileReader();
    reader.onload = (e) => {
        productImages.innerHTML = "
                <div class="img-box">
                    <i class="fa-solid fa-xmark"></i>
                    <img class="product-img" src="${e.target.result}">
                </div>
                ";

            const deleteButton = document.querySelectorAll(".fa-xmark");
            deleteButton.forEach((xbutton, index) => {
                xbutton.onclick = () => {
                    if(confirm("상품 이미지를 지우시겠습니까?")) {
                        productImageFiles.splice(index, 1);
                        getImagePreview();
                    }
                };
            });
    }


    productImages.forEach(file => {
        reader.readAsDataURL(file);
    }



    }
}