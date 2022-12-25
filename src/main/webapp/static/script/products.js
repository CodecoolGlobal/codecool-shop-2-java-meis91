const categoryFilters = document.getElementsByClassName("dropdown-item category");
const supplierFilter = document.getElementsByClassName("dropdown-item supplier");


for (let i = 0; i < categoryFilters.length; i++) {
    categoryFilters.item(i).addEventListener("click", evt =>{
        let categoryId = evt.target.getAttribute("data-category-id");
        let category = "category";
        console.log(getProducts(category, categoryId))

    })
}


async function getProducts(searchType, searchId){
    let html = ""
    let params = new URLSearchParams();
    params.set("type", searchType);
    params.set("id", searchId);
    let response = await fetch("json/" + "?" + params.toString());
    console.log("hi")
    let result = await response.json();
    console.log(result);

}
/**/