const addToCartBtn = document.getElementsByClassName("add-to-cart-btn" );
const cartAmount = document.getElementsByClassName("cart-amount");

const shoppingBasket = JSON.parse(localStorage.getItem("cart")) || [];


function addProductToLocalStorage(evt) {
    let productId = evt.target.getAttribute("data-product-id");
    let product = {id: productId,amount: 1}
    let search = shoppingBasket.find((product) => product.id === productId);
    if(search === undefined){
        shoppingBasket.push(product);
    } else {
        search.amount += 1;
    }
    localStorage.setItem("cart", JSON.stringify(shoppingBasket));
   /* cartAmount.innerHTML = calculation();*/
}

/*let calculation = () =>{
    return shoppingBasket.map((x) => x.amount).reduce((x, y) => x + y, 0);
}*/

for(let i = 0; i < addToCartBtn.length; i++){
    addToCartBtn.item(i).addEventListener("click", evt => {
        evt.preventDefault();
        addProductToLocalStorage(evt);
    })
}

/*cartBtn.addEventListener("click", evt => {
    evt.preventDefault();
    //addProductToLocalStorage(evt);
})*/

/*
calculation();*/
