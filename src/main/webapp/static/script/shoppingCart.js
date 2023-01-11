const title = document.getElementById("container-name")
const cartContainer = document.getElementById("products");
const cartBtn = document.getElementsByClassName("bi bi-cart4");
const plusBtn = document.getElementsByClassName("btn btn-success btn-number");
const inputAmount = document.getElementsByClassName("form-control input-number");
const minusBtn = document.getElementsByClassName("btn btn-danger btn-number");
const total = document.getElementById("total-sum")

let totalSum = 0;

async function getCart(){
    title.innerHTML = "Your Cart"
    let shoppingCart = JSON.parse(localStorage.getItem("cart")) || [];
    let html = ""
    let params = new URLSearchParams();
    params.set("cart", JSON.stringify(shoppingCart))
    let response = await fetch("json/" + "?" + params.toString());
    let result = await response.json();
    for(let product of result){
        let amount = 0;
        for(let item of shoppingCart){
            if(item.id == product.id){
                amount = item.amount;
            }
        }
        let sum = amount * product.defaultPrice;
        totalSum += sum;
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
                            <p class="lead">Price: ${product.defaultPrice} USD</p>
                         
                        </div>
                        <div class="amount" id="${product.id}">
                            </p><div class="input-group">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-danger btn-number"  data-type="${product.id}" data-field="quant[2]">
                                    <span class="glyphicon glyphicon-minus"></span>
                                  </button>
                              </span>
                              <input type="text" name="quant[2]" class="form-control input-number" value="${amount}" min="1" max="100">
                              <span class="input-group-btn">
                                  <button type="button" class="btn btn-success btn-number" data-type="${product.id}" data-field="quant[2]">
                                      <span class="glyphicon glyphicon-plus"></span>
                                  </button>
                              </span>
                          </div>
                      </div>
                      <div class="product-sum">
                            <p class="lead">Sum: ${sum} USD</p>
                        </div>
                    </div>
                </div>
            </div>
            
        `
    }
    productsContainer.innerHTML = html;
    await setEventsForCardConfig()
    await setTotalPrice()
    await checkoutListener()
}

async function submitCart(){
    try {
        const response = await fetch('json/post', {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json'
            },
            body: JSON.stringify({
                // your expected POST request payload goes here
                title: "cart",
                body: localStorage.getItem("cart")
            })
        });
        console.log(response )
        const data = await response.json();
        console.log(data);
    } catch(error) {
        console.log(error)
    }
}


function decrease(evt){
    let shoppingCart = JSON.parse(localStorage.getItem("cart")) || [];
    let productId = evt.target.getAttribute("data-type");
    let search = shoppingCart.find((product) => product.id === productId);
    search.amount -= 1;
    if(search.amount <= 0){
        shoppingCart.pop(productId)
        console.log("product removed")
    }
    localStorage.setItem("cart", JSON.stringify(shoppingCart));
    getCart()
}


function increase(evt){
    let shoppingCart = JSON.parse(localStorage.getItem("cart")) || [];
    let productId = evt.target.getAttribute("data-type");
    let search = shoppingCart.find((product) => product.id === productId);
    search.amount += 1;
    localStorage.setItem("cart", JSON.stringify(shoppingCart));
    getCart()
}

function setTotalPrice(){
    let totalHtml =  `
            <div class="total card-text">
                <p><h3>Total Price: ${totalSum}</h3></p>
                <div class="pay-now-btn" id = "checkout-or-pay" >
                <a class="btn btn-success" id="checkoutBtn" >Confirm Order</a>
            </div>
            </div>
        `
    total.innerHTML = totalHtml;
}


function setEventsForCardConfig(){
    for(let btn of plusBtn){
        btn.addEventListener("click", evt =>{
            increase(evt)
        } )
    }
    for(let btn of minusBtn){
        btn.addEventListener("click", evt =>{
            decrease(evt)
        } )
    }
}

cartBtn[0].addEventListener("click",
    evt => {
       getCart()
    })

function checkoutListener() {
    const checkOutBtn = document.getElementById("checkoutBtn");
    const modal = document.getElementById("checkoutModal");
    const span = document.getElementsByClassName("close")[0];
    const submit = document.getElementById("user-registration-btn")

    submit.addEventListener("click", evt => {
        console.log("submiiit")
        submitCart()
    })

    checkOutBtn.addEventListener("click", function () {
        modal.style.display = "block";
    })

    span.onclick = function () {
        modal.style.display = "none";
    }

    window.onclick = function (event) {
        if (event.target == modal) {
            modal.style.display = "none";
        }
    }
}
