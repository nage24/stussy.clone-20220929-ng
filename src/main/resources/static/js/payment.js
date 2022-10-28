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
            merchant_uid: "ORD20180131-0000011",
            name: "노르웨이 회전 의자",
            amount: 64900,
            buyer_email: "gildong@gmail.com",
            buyer_name: "홍길동",
            buyer_tel: "010-4242-4242",
            buyer_addr: "서울특별시 강남구 신사동",
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
        this.IMP.request_pay(this.#importPayParams, this.responsePay);
    }

    requestPay(resp) {
        if(resp.success) {
            alert("결제 성공!");
        }else {
            alert("결제 실패!");
        }
    }


    requestimpAccessToken() {
        const accessToken = null;

        $.ajax({
            async: false,
            type: "post", 
            url: "",
            contentType: "application/json",
            data: JSON.stringify({
                imp_key: this.impInfo.restApiKey,
                imp_secret: this.impInfo.restApiSecret
            }),
            


        })
    }
}




class Order {


    addPaymentButtonEvent() {
        const paymentButton = document.querySelector(".payment-button");
        paymentButton.onclick = () => {
            ImportApi.getInstance().requestPay();
        }
    }

}



