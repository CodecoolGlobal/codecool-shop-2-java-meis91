
let errorLink = document.querySelector("#show-error")

let toggle = button => {
    let errorP = document.querySelector("#error-p")
    let hidden = errorP.getAttribute("hidden");

    if (hidden) {
        errorP.removeAttribute("hidden");
        button.innerText = "HIDE ERROR";
    } else {
        errorP.setAttribute("hidden", "hidden");
        button.innerText = "SHOW ERROR";
    }
}

errorLink.addEventListener("click", toggle)