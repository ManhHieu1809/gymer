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
             <td>
                        <!-- Nút sửa -->
                        <a href="/admin/home/user/user-edit/${user.userID}" class="btn btn-primary btn-sm" title="Sửa">
                            <i class="bi bi-pencil-square" style="font-size: 1.5rem;"></i>
                        </a>
                        <!-- Nút xóa -->
                        <button onclick="deleteUser (${user.userID})" class="btn btn-danger btn-sm" title="Xóa">
                            <i class="bi bi-trash" style="font-size: 1.5rem;"></i>
                        </button>
                        
                         <!-- Nút tạo thông báo -->
                    <button onclick="createNotification(${user.userID})" class="btn btn-info btn-sm" title="Tạo Thông Báo">
                        <i class="bi bi-bell" style="font-size: 1.5rem;"></i>
                    </button>
                    </td>
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
            <td>
                        <!-- Nút sửa -->
                        <a href="/admin/home/product/product-edit/${product.productID}" class="btn btn-primary btn-sm" title="Sửa">
                            <i class="bi bi-pencil-square" style="font-size: 1.5rem;"></i>
                        </a>
                        <!-- Nút xóa -->
                        <button onclick="deleteProduct (${product.productID})" class="btn btn-danger btn-sm" title="Xóa">
                            <i class="bi bi-trash" style="font-size: 1.5rem;"></i>
                        </button>
                    </td>
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
    fetch("/admin/home/orders") // Replace with your backend API path
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
            <td>
                <!-- Nút sửa trạng thái -->
                <button onclick="editOrderStatus(${order.orderID}, '${order.statuss}')" class="btn btn-primary btn-sm" title="Sửa">
                    <i class="bi bi-pencil-square" style="font-size: 1.5rem;"></i>
                </button>
            
                <!-- Nút xóa -->
                <button onclick="deleteOrder(${order.orderID})" class="btn btn-danger btn-sm" title="Xóa">
                    <i class="bi bi-trash" style="font-size: 1.5rem;"></i>
                </button>
            
            </td>
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

// Hàm chỉnh sửa trạng thái đơn hàng
function editOrderStatus(orderID, currentStatus) {
    // Hiển thị SweetAlert2 với bảng thay đổi trạng thái
    Swal.fire({
        title: 'Chỉnh sửa trạng thái đơn hàng',
        html: `
            <label for="status">Trạng thái mới:</label>
            <select id="status" class="form-select" required>
                <option value="Pending" ${currentStatus === 'Pending' ? 'selected' : ''}>Pending</option>
                <option value="Shipped" ${currentStatus === 'Shipped' ? 'selected' : ''}>Shipped</option>
                <option value="Delivered" ${currentStatus === 'Delivered' ? 'selected' : ''}>Delivered</option>
                <option value="Cancelled" ${currentStatus === 'Cancelled' ? 'selected' : ''}>Cancelled</option>
            </select>
        `,
        showCancelButton: true,
        confirmButtonText: 'Cập nhật',
        cancelButtonText: 'Hủy',
        preConfirm: () => {
            const newStatus = document.getElementById("status").value;

            // Gửi yêu cầu PUT tới backend để cập nhật trạng thái đơn hàng
            return fetch(`/admin/home/orders/${orderID}/status?newStatus=${newStatus}`, { // Đổi endpoint này thành API của bạn
                method: "PUT",
                headers: {"Content-Type": "application/json"},
                body: JSON.stringify({statuss: newStatus})
            })
                .then(response => {
                    if (!response.ok) {
                        throw new Error("Không thể cập nhật trạng thái");
                    }
                    return response.json();
                })
                .catch(error => {
                    Swal.showValidationMessage(`Cập nhật thất bại: ${error}`);
                });
        }
    }).then(result => {
        if (result.isConfirmed) {
            Swal.fire("Thành công!", "Trạng thái đơn hàng đã được cập nhật.", "success");
            fetchOrders(); // Tải lại danh sách đơn hàng
        }
    });
}


// Hiển thị chi tiết đơn hàng
function viewOrderDetails(orderIndex) {
    const details = window.orderDetails[orderIndex];

    // Chuẩn bị nội dung hiển thị
    const detailsHTML = details.map(detail => `
        <p><b>Sản phẩm:</b> ${detail.product.productName}</p>
        <p><b>Số lượng:</b> ${detail.quantity}</p>
        <p><b>Đơn giá:</b> ${detail.product.price.toFixed(2)} VNĐ</p>
        <hr>
    `).join("");

    // Hiển thị với SweetAlert2
    Swal.fire({
        title: 'Chi tiết đơn hàng',
        html: detailsHTML, // Dùng HTML để hiển thị đẹp
        icon: 'info',
        confirmButtonText: 'Đóng',
        customClass: {
            popup: 'swal-wide' // Nếu muốn tùy chỉnh độ rộng
        }
    });
}

// Fetch all data on page load
document.addEventListener("DOMContentLoaded", function () {
    fetchUsers();    // Populate user table (from previous implementation)
    fetchGoods();    // Populate goods table (from previous implementation)
    fetchOrders();
    fetchAndDisplayUsernames();
});

async function deleteUser(userId) {
    if (confirm("Bạn có chắc chắn muốn xóa người dùng này?")) {
        try {
            const response = await fetch(`/admin/home/users/${userId}`, {
                method: 'DELETE',
            });

            if (response.ok) {
                alert('Người dùng đã được xóa thành công!');
                fetchUsers(); // Gọi lại hàm fetchUsers để làm mới danh sách người dùng
            } else {
                const errorData = await response.json();
                console.error('Lỗi khi xóa user:', errorData);
                alert('Xóa người dùng thất bại! Hãy kiểm tra lại.');
            }
        } catch (error) {
            console.error('Lỗi kết nối tới API:', error);
            alert('Không thể kết nối đến server.');
        }
    }
}

// Hàm xóa sản phẩm
function deleteProduct(productId) {
    if (confirm("Bạn có chắc chắn muốn xóa sản phẩm này?")) {
        // Gửi yêu cầu DELETE đến server để xóa sản phẩm
        fetch(`/admin/home/products/${productId}`, {
            method: 'DELETE',
            headers: {
                'Content-Type': 'application/json',
                'Accept': 'application/json'
            }
        })
            .then(response => {
                if (response.ok) {
                    if (response.status === 204) {
                        // Nếu trạng thái là 204, tức là xóa thành công mà không có dữ liệu trả về
                        // Xóa dòng sản phẩm khỏi bảng
                        const rowToDelete = document.querySelector(`tr[data-id='${productId}']`);
                        if (rowToDelete) {
                            rowToDelete.remove();
                        }
                        alert("Sản phẩm đã được xóa thành công.");
                        location.reload();
                    } else {
                        // Nếu không phải 204, xử lý theo trường hợp khác nếu cần
                        alert("Đã có lỗi xảy ra khi xóa sản phẩm.");
                    }
                } else {
                    throw new Error('Không thể xóa sản phẩm');
                }
            })
            .catch(error => {
                // Nếu có lỗi trong quá trình gửi yêu cầu
                console.error('Error:', error);
                alert("Đã có lỗi xảy ra. Vui lòng thử lại.");
            });
    }
}

function deleteOrder(orderID) {
    // Hiển thị cửa sổ xác nhận SweetAlert2
    Swal.fire({
        title: 'Bạn có chắc chắn muốn xóa đơn hàng này?',
        text: "Đây là hành động không thể hoàn tác!",
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Có, xóa!',
        cancelButtonText: 'Hủy bỏ',
        reverseButtons: true // Đổi vị trí của nút "Hủy bỏ" và "Xóa"
    }).then((result) => {
        if (result.isConfirmed) {
            // Gọi API DELETE để xóa đơn hàng
            fetch(`/admin/home/orders/${orderID}`, {
                method: "DELETE", // Sử dụng phương thức DELETE để xóa
                headers: {
                    "Content-Type": "application/json"
                }
            })
                .then(response => {
                    console.log("Response Status:", response.status); // Kiểm tra mã trạng thái của phản hồi

                    // Kiểm tra mã trạng thái 200 hoặc 204
                    if (response.status === 200 || response.status === 204) {
                        // Không cần gọi response.json() nếu không có dữ liệu trả về (204)
                        return response.status; // Trả về mã trạng thái
                    } else {
                        throw new Error(`Failed to delete order. Status code: ${response.status}`);
                    }
                })
                .then(status => {
                    if (status === 200 || status === 204) {
                        // Thông báo thành công khi xóa
                        Swal.fire(
                            'Đã xóa!',
                            'Đơn hàng đã được xóa thành công.',
                            'success'
                        );

                        // Cập nhật lại giao diện sau khi xóa
                        const row = document.querySelector(`#orderList table tbody tr[data-id='${orderID}']`);
                        if (row) {
                            row.remove(); // Xóa dòng này khỏi bảng
                        }
                        fetchOrders();

                    }
                })
                .catch(error => {
                    console.error("Error deleting order:", error);
                    Swal.fire(
                        'Lỗi!',
                        `Có lỗi khi xóa đơn hàng: ${error.message}`,
                        'error'
                    );
                });
        }
    });
}

// Hàm tạo thông báo cho người dùng
function createNotification(userID) {
    Swal.fire({
        title: 'Nhập nội dung thông báo',
        input: 'textarea',
        inputPlaceholder: 'Nhập nội dung thông báo...',
        showCancelButton: true,
        confirmButtonText: 'Gửi Thông Báo',
        cancelButtonText: 'Hủy',
        preConfirm: (content) => {
            if (!content) {
                Swal.showValidationMessage('Vui lòng nhập nội dung thông báo');
            }
            return content;
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const notificationContent = result.value;

            // Gửi yêu cầu tạo thông báo qua API
            fetch('/admin/home/notifications', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                },
                body: JSON.stringify({
                    content: notificationContent, // Nội dung thông báo
                    userID: userID,               // ID của người dùng nhận thông báo
                }),
            })
                .then(response => response.json())
                .then(data => {
                    if (data && data.content) {
                        Swal.fire({
                            title: 'Thông Báo Đã Gửi',
                            html: `<strong>Thông báo:</strong> ${data.content}<br><strong>ID Người Nhận:</strong> ${data.userID}`,
                            icon: 'success',
                            confirmButtonText: 'OK'
                        });
                    } else {
                        Swal.fire({
                            title: 'Lỗi',
                            text: 'Không thể gửi thông báo.',
                            icon: 'error',
                            confirmButtonText: 'OK'
                        });
                    }
                })
                .catch(error => {
                    console.error('Error sending notification:', error);
                    Swal.fire({
                        title: 'Lỗi',
                        text: 'Có lỗi xảy ra khi gửi thông báo.',
                        icon: 'error',
                        confirmButtonText: 'OK'
                    });
                });
        }
    });
}
async function fetchAndDisplayUsernames() {
    try {
    // Gửi yêu cầu GET tới API
    const response = await fetch('http://localhost:8080/admin/home/users/user-info1', {
    method: 'GET',
    headers: {
    'Content-Type': 'application/json'
},
    credentials: 'include' // Đảm bảo gửi cookie session
});

    if (response.ok) {
    const data = await response.json(); // Lấy dữ liệu JSON từ phản hồi

    // Danh sách các ID của thẻ cần cập nhật
    const usernameElements = ["username1", "username2", "username3"];

    // Cập nhật nội dung cho tất cả các thẻ
    usernameElements.forEach(id => {
    const element = document.getElementById(id);
    if (element) {
    element.innerText = data.username; // Cập nhật tên người dùng
}
});
} else {
    console.error("Error fetching username:", response.statusText);
}
} catch (error) {
    console.error("Error fetching username:", error);
}
}

    // Gọi hàm khi trang được tải
    window.onload = fetchAndDisplayUsernames;

