lass ProductListReqParams {
    static #instance = null;

    constructor(page, category, searchValue) {
        this.page = page;
        this.category = category;
        this.searchValue = searchValue;
    }

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ProductListReqParams(1, "ALL", "");
        }
        return this.#instance;
    }

    getPage() {return this.page;}
    setPage(page) {this.page = page;}
    getCategory() {return this.category;}
    setCategory(category) {this.category = category;}
    getSearchValue() {return this.searchValue;}
    setSearchValue(searchValue) {this.searchValue = searchValue;}

    getObject() {
        return {
            page: this.page,
            category: this.category,
            searchValue: this.searchValue
        };
    }
}

class ProductApi {

    productDataRequest() {
        let responseData = null;

        $.ajax({
            async: false,
            type: "get",
            url: "/api/admin/products",
            data: ProductListReqParams.getInstance().getObject(),
            dataType: "json",
            success: (response) => {
                responseData = response.data;
            },
            error: (error) => {
                console.log(error);
            }
        });

        return responseData;
    }
}

class ProductListService {
    static #instance = null;

    constructor() {
        this.productApi = new ProductApi();
        this.topOptionService = new TopOptionService();
        this.loadProductList();
    }

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ProductListService();
        }
        return this.#instance;
    }

    loadProductList() {
        const responseData = this.productApi.productDataRequest();
        if(this.isSuccessRequestStatus(responseData)) {
            if(responseData.length > 0) {
                this.topOptionService.loadPageMovement(responseData[0].productTotalCount);
                ElementService.getInstance().createProductMst(responseData);
            }
        }
    }

    isSuccessRequestStatus(responseData) {
        return responseData != null;
    }
}

class TopOptionService {
    constructor() {
        this.pageMovement = new PageMovement();
    }

    loadPageMovement(productTotalCount) {
        this.pageMovement.createMoveButtons(productTotalCount);
        this.pageMovement.addEvent();
    }

    addOptioinsEvent() {
        const categorySelectInput = document.querySelector(".category-select .product-input");
        const searchInput = document.querySelector(".product-search .product-input");
        const searchButton = document.querySelector(".search-button");

        const productListReqParams = ProductListReqParams.getInstance();

        categorySelectInput.onchange = () => {
            productListReqParams.setPage(1);
            productListReqParams.setCategory(categorySelectInput.value);
            ProductListService.getInstance().loadProductList();
        }

        searchButton.onclick = () => {
            productListReqParams.setPage(1);
            productListReqParams.setCategory(categorySelectInput.value);
            productListReqParams.setSearchValue(searchInput.value);
            ProductListService.getInstance().loadProductList();
        }

        searchInput.onkeyup = () => {
            if(window.event.keyCode == 13) {
                searchButton.click();
            }
        }
    }

}

class PageMovement {
    pageButtons = document.querySelector(".page-buttons");

    getEndPageNumber(productTotalCount) {
        return (productTotalCount % 10 == 0) ? productTotalCount / 10 : Math.floor(productTotalCount / 10) + 1;
    }

    createMoveButtons(productTotalCount) {
        let nowPage = ProductListReqParams.getInstance().getPage();

        this.pageButtons.innerHTML = "";

        this.createPreButton(nowPage);
        this.createNumberButton(nowPage, productTotalCount);
        this.createPostButton(nowPage, productTotalCount);
    }

    createNumberButton(nowPage, productTotalCount) {
        let startIndex = nowPage % 5 == 0 ? nowPage - 4 : nowPage - (nowPage % 5) + 1;
        let endIndex = startIndex + 4 <= this.getEndPageNumber(productTotalCount) ? startIndex + 4 : this.getEndPageNumber(productTotalCount);

        for(let i = startIndex; i <= endIndex; i++) {
            if(i == this.nowPage) {
                this.pageButtons.innerHTML += `<a href="javascript:void(0)" class="a-selected"><li>${i}</li></a>`;
            }else {
                this.pageButtons.innerHTML += `<a href="javascript:void(0)"><li>${i}</li></a>`;
            }
        }
    }

    createPreButton(nowPage) {
        if(nowPage != 1){
            this.pageButtons.innerHTML = `<a href="javascript:void(0)"><li>&#60;</li></a>`;
        }
    }

    createPostButton(nowPage, productTotalCount) {
        let maxPage = this.getEndPageNumber(productTotalCount);
        if(nowPage != maxPage){
            this.pageButtons.innerHTML += `<a href="javascript:void(0)"><li>&#62;</li></a>`;
        }
    }

    addEvent() {
        const pageNumbers = this.pageButtons.querySelectorAll("li");
        const productListReqParams = ProductListReqParams.getInstance();

        for(let i = 0; i < pageNumbers.length; i++) {
            pageNumbers[i].onclick = () => {
                let pageNumberText = pageNumbers[i].textContent;

                if(pageNumberText == "<") {
                    productListReqParams.setPage(productListReqParams.getPage() - 1);
                }else if(pageNumberText == ">") {
                    productListReqParams.setPage(productListReqParams.getPage() + 1);
                }else {
                    productListReqParams.setPage(pageNumberText);
                }

                ProductListService.getInstance().loadProductList();
            }
        }
    }
}

class ElementService {
    static #instance = null;
    #productDtl = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ElementService();
        }
        return this.#instance;
    }

    createProductMst(responseData) {
        const listBody = document.querySelector(".list-body");

        listBody.innerHTML = "";

        responseData.forEach((product) => {
            listBody.innerHTML += `
            <tr>
                <td class="product-id">${product.id}</td>
                <td>${product.category}</td>
                <td>${product.name}</td>
                <td>${product.price}<span>???</span></td>
                <td>${product.color}</td>
                <td>${product.size}</td>
                <td><button type="button" class="list-button detail-button"><i class="fa-regular fa-file-lines"></i></button></td>
                <td><button type="button" class="list-button delete-button"><i class="fa-regular fa-trash-can"></i></button></td>
            </tr>
            <tr class="product-detail detail-invisible">

            </tr>
            `;
        });

        this.addProductMstEvent(responseData);
    }

    addProductMstEvent(responseData) {
        const detailButtons = document.querySelectorAll(".detail-button");
        const productDetails = document.querySelectorAll(".product-detail");

        detailButtons.forEach((detailButton, index) => {
            detailButton.onclick = () => {
                this.#productDtl = responseData[index];

                if(productDetails[index].classList.contains("detail-invisible")) {
                    let confirmationOfModification = false;
                    let changeFlag = false;

                    productDetails.forEach((productDetail, index2) => {
                        if(!productDetail.classList.contains("detail-invisible") && index2 != index){
                            confirmationOfModification = true;
                        }
                    });

                    productDetails.forEach((productDetail, index2) => {
                        if(!productDetail.classList.contains("detail-invisible") && index2 != index){
                            changeFlag = confirm("????????? ?????????????????????????");
                            if(changeFlag) {
                                productDetail.classList.add("detail-invisible");
                                productDetail.innerHTML = "";
                                this.createProductDtl(productDetails[index]);
                                productDetails[index].classList.remove("detail-invisible");
                            }
                        }else {
                            if(confirmationOfModification && changeFlag) {
                                this.createProductDtl(productDetails[index]);
                                productDetails[index].classList.remove("detail-invisible");
                            }else if(!confirmationOfModification) {
                                this.createProductDtl(productDetails[index]);
                                productDetails[index].classList.remove("detail-invisible");
                            }
                        }
                    });

                }else{
                    if(confirm("????????? ?????????????????????????")){
                        productDetails[index].classList.add("detail-invisible");
                        productDetails[index].innerHTML = "";
                    }
                }
            }
        });
    }

    createProductDtl(productDetail) {
        // productImgList = productDataList[index].productImgFiles;

        productDetail.innerHTML = `
        <td colspan="8">
            <table class="product-info">
                <tr>
                    <td><input type="text" class="product-input" value="${this.#productDtl.price}" placeholder="??????"></td>
                    <td><input type="text" class="product-input" value="${this.#productDtl.color}" placeholder="??????"></td>
                    <td><input type="text" class="product-input" value="${this.#productDtl.size}" placeholder="?????????"></td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea class="product-input" placeholder="?????? ??????">${this.#productDtl.infoSimple}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea class="product-input" placeholder="?????? ??????">${this.#productDtl.infoDetail}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea class="product-input" placeholder="?????? ??????">${this.#productDtl.infoOption}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea class="product-input" placeholder="?????? ??????">${this.#productDtl.infoManagement}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <textarea class="product-input" placeholder="?????? ??????">${this.#productDtl.infoShipping}</textarea>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <form enctype="multipart/form-data">
                            <div class="product-img-inputs">
                                <label>?????? ?????????</label>
                                <button type="button" class="add-button">??????</button>
                                <input type="file" class="file-input product-invisible" name="file" multiple>
                            </div>
                        </form>
                        <div class="product-images">

                        </div>
                    </td>
                </tr>
                <tr>
                    <td colspan="3">
                        <button type="button" class="black-button update-button">????????????</button>
                    </td>
                </tr>
            </table>
        </td>
        `;

        // loadImageList();
        // addImageFile();
        const productRepository = new ProductRepository(this.#productDtl);
		this.createImgFileService = new ProductImgFileService(productRepository);
        this.createProductDtlImgs(productRepository);
		productImgFileService.addImageFileEvent();

    }

    createProductDtlImgs(productRepository) {

        const productImages = document.querySelector(".product-images");
        productImages.innerHTML = "";

        productRepository.oldImgList.forEach(img => {
            productImages.innerHTML += `
                <div class="img-box">
                    <i class="fa-solid fa-xmark pre-delete"></i>
                    <img class="product-img" src="/image/product/${img.temp_name}">
                </div>
            `;
        });

        productRepository.newImgList.forEach((img) => {
            productImages.innerHTML += `
                <div class="img-box">
                    <i class="fa-solid fa-xmark post-delete"></i>
                    <img class="product-img" src="${img}">
                </div>
            `;
        });

        this.addProductImgDeleteEvent(productRepository);
    }

    addProductImgDeleteEvent(productRepository) {
        const preDeleteButton = document.querySelectorAll(".pre-delete");
        preDeleteButton.forEach((xbutton, index) => {
            xbutton.onclick = () => {
                if(confirm("?????? ???????????? ??????????????????????")) {
                    productRepository.oldImgList.splice(index, 1);
                    this.createProductDtlImgs(productRepository);
                }
            };
        })

        const postDeleteButton = document.querySelectorAll(".post-delete");
        postDeleteButton.forEach((xbutton, index) => {
            xbutton.onclick = () => {
                if(confirm("?????? ???????????? ??????????????????????")) {
                    productRepository.newImgList.splice(index, 1);
                    this.createProductDtlImgs(productRepository);
                }
            };
        })
    }

}

class ProductRepository {
    oldImgList;
    oldImgDeleteList;
    newImgList;
    newImgFormData;

    constructor(productDtl) {
        this.oldImgList = productDtl.productImgFiles;
        this.oldImgDeleteList = new Array();
        this.newImgList = new Array();
        this.newImgFormData = new FormData();
    }

}


class ProductImgFileService {

	productRepository = null;
	constructor(productRepository) {
		this.productRepository = productRepository;
	}



	addImageFileEvent() {
    const fileAddButton = document.querySelector(".add-button");
    const fileInput = document.querySelector(".file-input");

    fileAddButton.onclick = () => {
        fileInput.click();
    }

    fileInput.onchange = () => {
        const formData = new FormData(document.querySelector("form"));
        let changeFlge = false;

        formData.forEach((value) => {
            if(value.size != 0) {
                this.productRepository.newImgList.push(value);
                changeFlge = true;
            }
        });

        if(changeFlge){
            getImageFiles();
            fileInput.value = null;
        }
    }



	getImageFiles() {

	const newImgList = this.productRepository.newImgList;

    while(newImgList.length != 0) {
        newImgList.pop();
    }

    newImgList.forEach((file, i) => {
        const reader = new FileReader();

        reader.onload = (e) => {
            console.log("????????? ?????? ????????? ???????????? ???????????????.")

            newImgList.push(e.target.result);
            console.log("index: " + i);
            if(i == newImgList.length - 1) {
                console.log("????????? ???????????? ?????? ??????")
                loadImageList();
            }
        }

        setTimeout(() => {reader.readAsDataURL(file);}, i * 100);
    });
}



window.onload = () => {
    new ProductListService();
}