
let page = 1;

window.onload = () => {

}

function getList() {
    
    $.ajax({
        async: false, 
        type: "get", 
        url: "/api/admin/products",
        data: {
            pageNumber: page,
            category: "",
            searchTest: ""
        }, 
        dataType: "json", 
        success: (response) => {
            console.log(response);
        },
        error: (error) => {
            console.log(error);
        }

    });
}