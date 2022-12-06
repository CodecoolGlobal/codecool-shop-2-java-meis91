const checkOutBtn = document.getElementById("checkoutBtn");
const modal = document.getElementById("checkoutModal");
const span = document.getElementsByClassName("close")[0];
const userRegistration = document.getElementById("user-registration")
const checkoutOrPayBtn = document.getElementById("checkout-or-pay");

checkOutBtn.addEventListener("click", function (){
    modal.style.display = "block";
})

userRegistration.addEventListener("submit", function (evt) {
    evt.preventDefault();
    console.log(checkoutOrPayBtn.innerHTML)
    checkoutOrPayBtn.innerHTML = `
    <h2>Registration successful</h2>
    <h3>To continue with payment, press the button below</h3>
    <a class="btn btn-success" id="payBtn" href="/payment" >Pay</a>
    `
    modal.style.display = "none";
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