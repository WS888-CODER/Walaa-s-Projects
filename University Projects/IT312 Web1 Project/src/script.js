/* JavaScript toggle function for fly-in */
function toggleNav() {
    var overlay = document.getElementById("overlayNav");
    var darkBackground = document.getElementById("darkBackground");
    var body = document.body;

    if (overlay.style.width === "250px") {
        overlay.style.width = "0";
        overlay.style.display = "none";
        darkBackground.style.display = "none";
        body.classList.remove("no-scroll"); /* Re-enable scrolling */
    } else {
        overlay.style.display = "block";
        overlay.style.width = "250px";
        darkBackground.style.display = "block"; /* Show dark background */
        body.classList.add("no-scroll"); /* Disable scrolling */
    }
}

// Display the start date of the week
function displayWeekDate() {
    const today = new Date();
    const dayOfWeek = today.getDay();
    const startOfWeek = new Date(today);
    startOfWeek.setDate(today.getDate() - dayOfWeek + 1);
    document.getElementById("weekDate").innerText = `${startOfWeek.toDateString()}`; // Display only the date
}

// Show hidden offers
function showMoreOffers() {
    const hiddenOffers = document.querySelectorAll(".offer-raghad:nth-child(n+3)"); // Select offers starting from the 3rd
    hiddenOffers.forEach(offer => offer.style.display = "block");
    document.getElementById("moreButton").style.display = "none"; // Hide the More button after clicking
}

// Show review details on hover
document.querySelectorAll(".review-raghad").forEach(review => {
    review.addEventListener("mouseover", () => review.querySelector(".review-details").style.display = "block");
    review.addEventListener("mouseout", () => review.querySelector(".review-details").style.display = "none");
});

window.onload = displayWeekDate;




//* 
document.addEventListener("DOMContentLoaded", function () {
// --- Code for Add Product Page ---
const formZuhd = document.querySelector(".product-form-zuhd");
const productNameInputZuhd = document.getElementById("product-name-zuhd");
const productPriceInputZuhd = document.getElementById("product-price-zuhd");
const productCategoryInputZuhd = document.getElementById("product-category-zuhd");
const productQuantityInputZuhd = document.getElementById("product-quantity-zuhd");
const productDescriptionInputZuhd = document.getElementById("product-description-zuhd");
const productPhotoInputZuhd = document.getElementById("product-photo-zuhd");

if (formZuhd) {
    formZuhd.addEventListener("submit", function (eventZuhd) {
        eventZuhd.preventDefault();

        // Form validation
        if (
            !productNameInputZuhd.value ||
            !productPriceInputZuhd.value ||
            !productCategoryInputZuhd.value ||
            !productQuantityInputZuhd.value ||
            !productDescriptionInputZuhd.value ||
            !productPhotoInputZuhd.files[0] // Ensure an image is uploaded
        ) {
            alert("Please fill in all the fields, including the product photo.");
            return;
        }

        // Name validation: Cannot start with a number
        if (/^\d/.test(productNameInputZuhd.value)) {
            alert("Product name cannot start with a number.");
            return;
        }

        // Check if price and quantity are numbers
        if (isNaN(productPriceInputZuhd.value) || productPriceInputZuhd.value <= 0) {
            alert("Please enter a valid price.");
            return;
        }
        if (isNaN(productQuantityInputZuhd.value) || productQuantityInputZuhd.value <= 0) {
            alert("Please enter a valid quantity.");
            return;
        }

        // Process the uploaded image
        const readerZuhd = new FileReader();
        readerZuhd.onload = function (eZuhd) {
            const newProductZuhd = {
                name: productNameInputZuhd.value,
                price: parseFloat(productPriceInputZuhd.value).toFixed(2),
                category: productCategoryInputZuhd.value,
                quantity: parseInt(productQuantityInputZuhd.value, 10),
                description: productDescriptionInputZuhd.value,
                image: eZuhd.target.result // Save the image as a Base64 string
            };

            // Store product information in local storage
            let productsZuhd = JSON.parse(localStorage.getItem("products-zuhd")) || [];
            productsZuhd.push(newProductZuhd);
            localStorage.setItem("products-zuhd", JSON.stringify(productsZuhd));

            // Alert success
            alert(`Product "${newProductZuhd.name}" has been added successfully.`);

            // Clear the form
            formZuhd.reset();
        };
        readerZuhd.readAsDataURL(productPhotoInputZuhd.files[0]);
    });
}

    // Sort functionality
    const sortDropdownZuhd = document.getElementById("sort-zuhd");
    const productContainerZuhd = document.querySelector(".product-container-zuhd");

    if (sortDropdownZuhd && productContainerZuhd) {
        sortDropdownZuhd.addEventListener("change", function () {
            const productsZuhd = Array.from(productContainerZuhd.children);
            const sortTypeZuhd = sortDropdownZuhd.value;

            productsZuhd.sort((a, b) => {
                const priceA = parseFloat(a.querySelector(".product-price-zuhd").textContent.replace("SR", ""));
                const priceB = parseFloat(b.querySelector(".product-price-zuhd").textContent.replace("SR", ""));
                const nameA = a.querySelector(".product-name-zuhd").textContent.toLowerCase();
                const nameB = b.querySelector(".product-name-zuhd").textContent.toLowerCase();

                if (sortTypeZuhd === "low-to-high") return priceA - priceB;
                if (sortTypeZuhd === "high-to-low") return priceB - priceA;
                if (sortTypeZuhd === "a-to-z") return nameA.localeCompare(nameB);
                if (sortTypeZuhd === "z-to-a") return nameB.localeCompare(nameA);
            });

            productContainerZuhd.innerHTML = ""; // Clear current product list
            productsZuhd.forEach((product) => productContainerZuhd.appendChild(product));
        });
    }

    // Add to cart functionality
    const addToCartButtonsZuhd = document.querySelectorAll(".add-to-cart-zuhd");
    const cartZuhd = JSON.parse(localStorage.getItem("cart-zuhd")) || [];

    addToCartButtonsZuhd.forEach((button) => {
        button.addEventListener("click", () => {
            const productZuhd = button.closest(".product-item-zuhd");
            const nameZuhd = productZuhd.querySelector(".product-name-zuhd").textContent;
            const priceZuhd = parseFloat(productZuhd.querySelector(".product-price-zuhd").textContent.replace("SR", ""));
            const imageZuhd = productZuhd.querySelector("img").src;

            const existingItemZuhd = cartZuhd.find((item) => item.name === nameZuhd);
            if (existingItemZuhd) {
                existingItemZuhd.quantity += 1;
            } else {
                cartZuhd.push({ name: nameZuhd, price: priceZuhd, image: imageZuhd, quantity: 1 });
            }

            localStorage.setItem("cart-zuhd", JSON.stringify(cartZuhd));
            window.location.href = "cart_page.html"; // Redirect to cart page
        });
    });

    // Cart page functionality
    if (window.location.pathname.includes("cart_page.html")) {
        const cartItemsZuhd = JSON.parse(localStorage.getItem("cart-zuhd")) || [];
        const cartPageContainerZuhd = document.querySelector("#cart-page");

        cartItemsZuhd.forEach((item, index) => {
            const cartItemHTMLZuhd = `
                <div class="cart-item" data-price="${item.price}">
                    <img src="${item.image}" alt="${item.name}" class="cart-item-image">
                    <div class="cart-item-details">
                        <h3>${item.name}</h3>
                        <p>Price: ${item.price.toFixed(2)} SR</p>
                        <label for="quantity${index + 1}">Quantity:</label>
                        <input type="number" id="quantity${index + 1}" name="quantity${index + 1}" value="${item.quantity}" min="1">
                        <button class="update-quantity" data-index="${index}">Update</button>
                        <button class="delete-item" data-index="${index}">Remove</button>
                    </div>
                </div>
            `;
            cartPageContainerZuhd.insertAdjacentHTML("beforeend", cartItemHTMLZuhd);
        });

        // Cart page logic
        const clearCartButton = document.querySelector(".clear-cart");
        const checkoutButton = document.querySelector(".checkout");
        const cartTotalElement = document.createElement("div");
        cartTotalElement.className = "cart-total";
        cartPageContainerZuhd.insertAdjacentElement("beforeend", cartTotalElement);

        const updateQuantityButtons = document.querySelectorAll(".update-quantity");
        const deleteItemButtons = document.querySelectorAll(".delete-item");

        if (clearCartButton && checkoutButton && cartTotalElement) {
            const updateCartTotal = () => {
                console.log("Updating cart total...");
                const cartItems = document.querySelectorAll(".cart-item");
                let total = 0;

                cartItems.forEach((item) => {
                    const price = parseFloat(item.getAttribute("data-price"));
                    const quantityInput = item.querySelector("input[type='number']");
                    const quantity = quantityInput ? parseInt(quantityInput.value, 10) : 0;
                    total += price * quantity;
                });

                cartTotalElement.textContent = `Total: ${total.toFixed(2)} SR`;
            };

            clearCartButton.addEventListener("click", () => {
                console.log("Clear Cart button clicked.");
                document.querySelectorAll(".cart-item").forEach((item) => item.remove());
                localStorage.removeItem("cart-zuhd");
                updateCartTotal();
            });

            checkoutButton.addEventListener("click", () => {
                const total = cartTotalElement.textContent;
                console.log("Checkout clicked. Total:", total);
                alert(`Thank you for shopping! ${total}`);
                window.location.href = "eval.html";
            });

            updateQuantityButtons.forEach((button) => {
    button.addEventListener("click", (e) => {
        console.log("Quantity updated.");
        const cartItem = e.target.closest(".cart-item");
        if (cartItem) {
            const quantityInput = cartItem.querySelector("input[type='number']");
            const index = Array.from(updateQuantityButtons).indexOf(button); // Correct index
            const quantity = parseInt(quantityInput.value, 10);

            // Update the localStorage data if applicable
            const cartItems = JSON.parse(localStorage.getItem("cart-zuhd")) || [];
            if (cartItems[index]) {
                cartItems[index].quantity = quantity;
                localStorage.setItem("cart-zuhd", JSON.stringify(cartItems));
            }

            // Recalculate and update the cart total
            updateCartTotal();
        }
    });
});


            deleteItemButtons.forEach((button) => {
                button.addEventListener("click", (e) => {
                    const cartItem = e.target.closest(".cart-item");
                    if (cartItem) {
                        console.log("Removing item:", cartItem);
                        const index = button.getAttribute("data-index");
                        cartItemsZuhd.splice(index, 1);
                        localStorage.setItem("cart-zuhd", JSON.stringify(cartItemsZuhd));
                        cartItem.remove();
                        updateCartTotal();
                    }
                });
            });

            // Initialize cart total on page load
            updateCartTotal();
        } else {
            console.log("Cart-specific elements not found on this page.");
        }
    }
});
