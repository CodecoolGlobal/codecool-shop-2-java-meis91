const checkOutBtn = document.getElementById("checkoutBtn");
const modal = document.getElementById("checkoutModal");
const span = document.getElementsByClassName("close")[0];


checkOutBtn.addEventListener("click", function (){
    modal.style.display = "block";
})


// When the user clicks on <span> (x), close the modal
span.onclick = function() {
    modal.style.display = "none";
}

// When the user clicks anywhere outside of the modal, close it
window.onclick = function(event) {
    if (event.target == modal) {
        modal.style.display = "none";
    }
}