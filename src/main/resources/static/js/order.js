class ImportApi {
    static #instance = null;

    static getInstance() {
        if(this.#instance == null) {
            this.#instance = new ImportApi();
        }
        return this.#instance;
    }

    IMP = null;

    // 결제
    importPayParams = {
            pg: "kakaopay",
            pay_method: "card",
            merchant_uid: "product-" + new Date().getTime(),
            name: "pingmyping",
            amount: 1,
            buyer_email: "gildong@gmail.com",
            buyer_name: "전갱이",
            buyer_tel: "010-4242-4242",
            buyer_addr: "부산광역시 동래구 온천동",
            buyer_postcode: "01181"
    };


    imgInfo =  { 
        impUid: null,
        restApiKey: null,
        restApiSecret: null
    }

    constructor() {
        this.IMP = window.IMP;
        this.impInfo.impUid = "imp75586747";
        this.impInfo.restApiKey = "2566467358377736";
        this.impInfo.restApiSecret = "KPwEXsw7aaYkqzw7OvxjtgYJ2LStyvYHi3zrG07oTUV1piSUztVuhibxWgfJaLiSnnZoYhqeVPjKnKar";

        this.IMP.init(this.impInfo.impUid);
    }

    requestPay() {
        this.IMP.request_pay(this.importPayParams, this.responsePay);
    }

    requestImpAccessToken() {
            const accessToken = null;

            return accessToken;
    }


    requestPayDetails() {
    }

    requestPay(resp) {
        if(resp.success) {

            $.ajax({
                async: false,
                type: "post",
                url: "https://api.iamport.kr/users/getToken",
                beforeSend: function (xhr) {
                    xhr.setRequestHeader("Content-Type", "application/json");
                },
                data: JSON.stringify({
                    imp_key: "2566467358377736",
                    imp_secret: "KPwEXsw7aaYkqzw7OvxjtgYJ2LStyvYHi3zrG07oTUV1piSUztVuhibxWgfJaLiSnnZoYhqeVPjKnKar"
                }),
                dataType: "json",
                success: (response) => {
                    console.log(response);
                    accessToken = response;
                },
                error: (error) => {
                    console.log(error);
                }
            });

            alert("결제 성공!");

        }else {
            alert("결제 실패!");
        }
    }
}




class Order {
    constructor() {
        this.addPaymentButtonEvent();
    }

    addPaymentButtonEvent() {
        const paymentButton = document.querySelector(".payment-button");
        paymentButton.onclick = () => {
            ImportApi.getInstance().requestPay();
        }
    }
}

/* onload 는 한번만 ! 스크립트는 한번에 읽혀지므로 한 군데에서 호출해주어도 됨 ! */
window.onload = () => {
    AddressApi.getInstance().addAddressButtonEvent();
    new Order();
}


