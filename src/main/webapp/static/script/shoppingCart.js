const addToCartBtn = document.getElementsByClassName("add-to-cart-btn" );
const cartAmount = document.getElementsByClassName("cart-amount");
const cartBtn = document.getElementById("shopping-cart-link");
const basket = JSON.parse(localStorage.getItem("cart")) || [];

//let basket = [];

function addProductToLocalStorage(evt) {
    let productId = evt.target.getAttribute("data-product-id");
    let product = {id: productId,amount: 1}
    let search = basket.find((product) => product.id === productId);
    if(search === undefined){
        basket.push(product);
    } else {
        search.amount += 1;
    }
    localStorage.setItem("cart", JSON.stringify(basket));
    console.log(cartAmount)
    cartAmount.innerHTML = calculation();
}

let calculation = () =>{
    return basket.map((x) => x.amount).reduce((x, y) => x + y, 0);
}

async function getProduct(productId){
    let html = ""
    let params = new URLSearchParams();
    params.set("id", productId);
    let response = await fetch("json/" + "?" + params.toString());
    let result = await response.json();
    console.log(result)
    localStorage.setItem("cart", JSON.stringify(result));

}

for(let i = 0; i < addToCartBtn.length; i++){
    addToCartBtn.item(i).addEventListener("click", evt => {
        evt.preventDefault();
        addProductToLocalStorage(evt);
    })
}

cartBtn.addEventListener("click", evt => {
    //evt.preventDefault();
    addProductToLocalStorage(evt);
})