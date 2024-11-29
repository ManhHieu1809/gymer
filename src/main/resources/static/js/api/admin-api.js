// Fetch and populate user accounts
function fetchUsers() {
    fetch("/admin/home/users")
        .then(response => response.json())
        .then(data => {
            const userTableBody = document.querySelector("#userList tbody");
            userTableBody.innerHTML = "";
            data.forEach((user, index) => {
                const row = document.createElement("tr");
                row.innerHTML = `
            <td><input type="checkbox" class="checkbox" data-id="${user.userID}" /></td>
            <td>${user.userID}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.fullName}</td>
            <td>${user.age}</td>
            <td>${user.gender}</td>
            <td>${user.height.toFixed(2)}</td>
            <td>${user.weight.toFixed(2)}</td>
            <td>${user.roles}</td>
          `;
                userTableBody.appendChild(row);
            });
            // Add event listener for "Select All" checkbox
            document.querySelector("#user-selectAll").addEventListener("change", function () {
                const checkboxes = document.querySelectorAll(".checkbox");
                checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
            });
        })
        .catch(error => console.error("Error fetching users:", error));
////////////////////////////////////
}

// Fetch and populate goods table
function fetchGoods() {
    fetch("/admin/home/products") // Replace with the actual backend API path
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch goods");
            }
            return response.json();
        })
        .then(data => {
            const goodsTableBody = document.querySelector("#goodsList table tbody");
            goodsTableBody.innerHTML = ""; // Clear existing rows

            data.forEach((product, index) => {
                const row = document.createElement("tr");
                row.innerHTML = `
            <td><input type="checkbox" class="checkbox" data-id="${product.productID}" /></td>
            <td>${product.productID}</td>
            <td>${product.productName}</td>
            <td>${product.descriptions}</td>
            <td>${product.price.toFixed(2)}</td>
            <td>${product.quantity}</td>
          `;
                goodsTableBody.appendChild(row);
            });
            // Add event listener for "Select All" checkbox
            document.querySelector("#goods-selectAll").addEventListener("change", function () {
                const checkboxes = document.querySelectorAll(".checkbox");
                checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
            });
        })
        .catch(error => console.error("Error fetching goods:", error));
}

function fetchOrders() {
    fetch("/your-api-path/orders") // Replace with your backend API path
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to fetch orders");
            }
            return response.json();
        })
        .then(data => {
            const ordersTableBody = document.querySelector("#orderList table tbody");
            ordersTableBody.innerHTML = ""; // Clear existing rows

            data.forEach((order, index) => {
                const row = document.createElement("tr");
                row.innerHTML = `
            <td><input type="checkbox" class="checkbox" data-id="${order.orderID}" /></td>
            <td>${order.orderID}</td>
            <td>${new Date(order.orderDate).toLocaleDateString()}</td>
            <td>${order.totalPrice.toFixed(2)}</td>
            <td>${order.statuss}</td>
            <td>${order.user.fullName} (${order.user.username})</td>
            <td><button onclick="viewOrderDetails(${index})">View Details</button></td>
          `;
                ordersTableBody.appendChild(row);

                // Store order details for quick access
                window.orderDetails = window.orderDetails || [];
                window.orderDetails[index] = order.orderDetails;
            });

            // Add event listener for "Select All" checkbox
            document.querySelector("#order-selectAll").addEventListener("change", function () {
                const checkboxes = document.querySelectorAll(".checkbox");
                checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
            });
        })
        .catch(error => console.error("Error fetching orders:", error));
}

// View details of an order
function viewOrderDetails(orderIndex) {
    const details = window.orderDetails[orderIndex];
    const detailsText = details.map(detail => `
      Product: ${detail.product.productName}, 
      Quantity: ${detail.quantity}, 
      Price: ${detail.product.price.toFixed(2)}
    `).join("\n");

    alert(`Order Details:\n${detailsText}`);
}

// Fetch all data on page load
document.addEventListener("DOMContentLoaded", function () {
    fetchUsers();    // Populate user table (from previous implementation)
    fetchGoods();    // Populate goods table (from previous implementation)
    fetchOrders();

});

