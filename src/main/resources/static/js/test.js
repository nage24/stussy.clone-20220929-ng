//  전역 메소드 -> 객체 내에 메소드 만들고 바로 참조하는 방식. 자신을 호출한 시점! 의 전역 변수를 참조함 

const user1 = {
    username: "짱구",
    printUsername: () => {
        console.log(this.username);
    }
}

// 익명함수

const user2 = {
    username: "짱구",
    printUsername: function() { // 익명함수 -> 메소드 내의 값을 참조하게 됨. 
        console.log(this.username);
        const testPrint = () => {
            console.log("testprint: " + this.username); // 객체내 property 참조 ! 
        }
        testPrint(); // 객체 내에서 정의되고, 객체 내에서 메소드 호출이 일어남. ( user 안에서의 username을 참조함. )
    }
}


var username = "test";    // 전역 변수
let username2 = "test2";  // 지역 변수? 
user1.printUsername(); // -> undefined / test

user2.printUsername(); 