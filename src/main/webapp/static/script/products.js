const categoryFilters = document.getElementsByClassName("dropdown-item category");
const supplierFilters = document.getElementsByClassName("dropdown-item supplier");
const productsContainer = document.getElementById("products");
const containerName = document.getElementById("container-name");
let filterName;


for (let i = 0; i < categoryFilters.length; i++) {
    categoryFilters.item(i).addEventListener("click", evt =>{
        let categoryId = evt.target.getAttribute("data-category-id");
        let category = "category";
        getProducts(category, categoryId);
    })
}

for (let i = 0; i < supplierFilters.length; i++) {
    supplierFilters.item(i).addEventListener("click", evt =>{
        let categoryId = evt.target.getAttribute("data-supplier_id");
        let category = "supplier";
        getProducts(category, categoryId);
    })
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
                        <p style="display:inline"> Category: </p><a th:href="#" class="card-text" role="button">${product.productCategory.name}</a><br>
                        <p style="display:inline"> Supplier: </p><a th:href="#" class="card-text" role="button">${product.supplier.name} </a>
                    </div>
                    <div class="card-body">
                        <div class="card-text">
                            <p class="lead">${product.defaultPrice} USD</p>
                        </div>
                        <div class="card-text">
                            <a class="btn btn-success" th:href="@{/(cart=${product.id})}">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>
        `
    }
    productsContainer.innerHTML = html;
}

function setFilterName(searchType, products) {
    if (searchType == "category") {
        filterName = products[0].productCategory.name;
    } else if (searchType == "supplier") {
        filterName = products[0].supplier.name;
    }
    containerName.innerText = filterName;
}
