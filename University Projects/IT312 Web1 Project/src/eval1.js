const productSelect = document.getElementById('products');
const selectedProduct = document.getElementById('selected-product');
const productDescription = document.querySelector('#product-description');
const productPrice = document.querySelector('#product-price');
const productImage = document.getElementById('product-image');

const productDetails = {
  product1: {
    name: "Eco-Friendly Orange Bottle",
    description: "A sustainable water bottle perfect for everyday hydration.",
    price: "50 SR",
  },
  product2: {
    name: "Reusable Coffee Cup",
    description: "Take your coffee on the go with this eco-friendly coffee cup.",
    price: "30 SR",
  },
  product3: {
    name: "Insulated Lunch Bag",
    description: "Keep your meals fresh with this stylish insulated lunch bag.",
    price: "60 SR",
  },
  product4: {
    name: "Organic Cotton Tote",
    description: "Carry your essentials in this durable, eco-friendly tote bag.",
    price: "40 SR",
  }
};

const viewProductButton = document.getElementById('view-product-button');

viewProductButton.addEventListener('click', function(event) {
  event.preventDefault();
  const selectedProductValue = productSelect.value;
  const selectedProductDetails = productDetails[selectedProductValue];
  selectedProduct.textContent = `Selected Product: ${selectedProductDetails.name}`;
  productDescription.textContent = selectedProductDetails.description;
  productPrice.textContent = `Total Price: ${selectedProductDetails.price}`;
  productImage.src = selectedProductDetails.imageUrl;
  alert('Product details updated without page reload!');
});

const submitButton = document.getElementById('submitButton');

submitButton.addEventListener('click', function(event) {
  event.preventDefault();
  const orderSelect = document.getElementById('previous-orders');
  const selectedOrder = orderSelect.value;
  const ratingStars = document.querySelectorAll('.rating input');
  const selectedRating = Array.from(ratingStars).find(star => star.checked);

  if (!selectedOrder || !selectedRating) {
    alert('Please select a previous order and choose a rating.');
    return;
  }

  // Get the selected product key from the dropdown
  const selectedProductValue = productSelect.value;

  // Get the rating from the selected radio button
  let productNumber = selectedRating.id.charAt(1); // Default is based on the rating star number

  switch (selectedRating.id) {
    case "r1":
      productNumber = 5;
      break;
    case "r2":
      productNumber = 4;
      break;
    case "r3":
      productNumber = 3; // Added case for rating 3
      break;
    case "r4":
      productNumber = 2;
      break;
    case "r5":
      productNumber = 1;
      break;
    default:
      break;
  }

  const userRating = productNumber;

  // Only print the product number in the alert
  alert(`Thank you for your feedback! Your rating for product#${selectedProductValue.slice(-1)} is ${userRating}`);
  window.location.href = 'home.html';
});
