const cartBtn = document.getElementsByClassName("add-to-cart-btn" );
const cartAmount = document.getElementsByClassName("cart-amount");

function addItemToCart(evt) {
    //console.log(evt.target.parentNode)
    let productId = evt.target.getAttribute("data-product-id");

    localStorage.setItem("cart", productId);
    let shoppingBasket = JSON.parse(localStorage.getItem("cart")) || [];
    console.log("basketr")
    console.log(shoppingBasket.length);
    cartAmount.innerText = shoppingBasket.length;
    addProductToCart(productId)
}

async function addProductToCart(productId){
    let html = ""
    let params = new URLSearchParams();
    params.set("id", productId);
    let response = await fetch("json/" + "?" + params.toString());
    let result = await response.json();
    console.log(result)
    localStorage.setItem("cart", JSON.stringify(result));
    //
    /*for(let product of result){
        html += `
                <div class="col col-sm-12 col-md-6 col-lg-4">
                <div class="card">
                    <img class="" src="/static/img/product_${product.id}.jpg" alt="" />
                    <div class="card-header">
                        <h4 class="card-title">${product.name}</h4>
                        <p class="card-text">${product.productCategory.name} </p>
                        <p class="card-text">${product.description}</p>
                        <p style="display:inline"> Category: </p>
                            <a class="card-text category-link" role="button" href="/category=${product.productCategory.id}" data-category-id=${product.productCategory.id}>${product.productCategory.name}</a><br>
                        <p style="display:inline"> Supplier: </p>
                            <a class="card-text supplier-link" role="button" href="/supplier=${product.supplier.id}" data-supplier_id=${product.supplier.id}>${product.supplier.name}</a>
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <p class="lead">${product.defaultPrice} USD</p>
                        </div>
                        <div class="card-text">
                            <a class="btn btn-success" href="/cart=${product.id}">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>
        `
    }
    productsContainer.innerHTML = html;
    categoryAndSupplierCardLinks();*/
}

for(let i = 0; i < cartBtn.length; i++){
    cartBtn.item(i).addEventListener("click", evt => {
        evt.preventDefault();
        addItemToCart(evt);
    })
}