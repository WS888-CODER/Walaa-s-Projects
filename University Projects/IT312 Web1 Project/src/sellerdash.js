document.addEventListener("DOMContentLoaded", function () {
    const productsZuhd = JSON.parse(localStorage.getItem("products-zuhd")) || [];
    const productListContainer = document.querySelector(".product-list-container");
    const noProductsMessage = document.querySelector(".no-products-message");

    if (productsZuhd.length > 0) {
        if (noProductsMessage) {
            noProductsMessage.style.display = "none";
        }

        productsZuhd.forEach(productZuhd => {
            const productCard = document.createElement("div");
            productCard.className = "product-card"; // Apply the CSS class for styling

            // Populate the product card with product data, including the image
            productCard.innerHTML = `
                <img src="${productZuhd.image || 'images/default-product.png'}" alt="${productZuhd.name}" class="product-image">
                <div class="details">
                    <h4>${productZuhd.name}</h4>
                    <p><strong>Category:</strong> ${productZuhd.category}</p>
                    <p><strong>Description:</strong> ${productZuhd.description}</p>
                </div>
                <div class="price-quantity">
                    <p><strong>Price:</strong> ${productZuhd.price} SR</p>
                    <p><strong>Quantity:</strong> ${productZuhd.quantity}</p>
                </div>
            `;

            productListContainer.appendChild(productCard);
        });
    } else {
        if (noProductsMessage) {
            noProductsMessage.style.display = "block";
        }
    }
});
