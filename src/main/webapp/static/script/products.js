const categoryFilters = document.getElementsByClassName("dropdown-item category");
const supplierFilters = document.getElementsByClassName("dropdown-item supplier");
const categoryLinks = document.getElementsByClassName("card-text category-link");
const supplierLinks = document.getElementsByClassName("card-text supplier-link");
const productsContainer = document.getElementById("products");
const containerName = document.getElementById("container-name");

let filterName;




function filterByCategory(evt) {
    let categoryId = evt.target.getAttribute("data-category-id");
    let category = "category";
    getProducts(category, categoryId);
}

function filterBySupplier(evt) {
    let categoryId = evt.target.getAttribute("data-supplier_id");
    let category = "supplier";
    getProducts(category, categoryId);
}

async function getProducts(searchType, searchId){
    let html = ""
    let params = new URLSearchParams();
    params.set("type", searchType);
    params.set("id", searchId);
    let response = await fetch("json/" + "?" + params.toString());
    let result = await response.json();
    await setFilterName(searchType, result);
    for(let product of result){
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
    categoryAndSupplierCardLinks();
}

function setFilterName(searchType, products) {
    if (searchType == "category") {
        filterName = products[0].productCategory.name;
    } else if (searchType == "supplier") {
        filterName = products[0].supplier.name;
    }
    containerName.innerText = filterName;
}

function categoryAndSupplierCardLinks(){
    for (let i = 0; i < categoryLinks.length; i++){
        categoryLinks.item(i).addEventListener("click", evt =>{
            evt.preventDefault();
            //console.log(categoryLinks.item(i))
            filterByCategory(evt);
        })
    }
    for (let i = 0; i < supplierLinks.length; i++){
        supplierLinks.item(i).addEventListener("click", evt =>{
            evt.preventDefault();
            filterBySupplier(evt);
        })
    }
}


for (let i = 0; i < categoryFilters.length; i++) {
    categoryFilters.item(i).addEventListener("click", evt =>{
        evt.preventDefault();
        filterByCategory(evt);
    })
}
for (let i = 0; i < supplierFilters.length; i++) {
    supplierFilters.item(i).addEventListener("click", evt =>{
        evt.preventDefault();
        filterBySupplier(evt);
    })
}

categoryAndSupplierCardLinks();




