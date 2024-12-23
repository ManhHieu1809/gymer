function fetchUsers1() {
    fetch("/admin/home/users")
        .then(response => response.json())
        .then(data => {
            const userTableBody = document.querySelector("#userList1 tbody");
            userTableBody.innerHTML = "";
            data.forEach((user, index) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                    <td>${user.fullName}</td>
                    <td>${user.email}</td>
                    <td>${user.age}</td>
                    <td>${user.gender}</td>
                    <td>${user.height.toFixed(2)}</td>
                    <td>${user.weight.toFixed(2)}</td>
                    <td>
                        <div class="actions">
                            <button onclick="openCreateNutritionPlanModal(${user.userID})" class="btn btn-success btn-sm" title="Thêm kế hoạch dinh dưỡng">
                                <i class="bi bi-plus-circle" style="font-size: 1.5rem;"></i>
                            </button>
                            <button onclick="createNotification(${user.userID})" class="btn btn-info btn-sm" title="Tạo Thông Báo">
                                <i class="bi bi-bell" style="font-size: 1.5rem;"></i>
                            </button>
                            <!-- Nút xem chủ đề -->
                            <button onclick="viewUserTopics(${user.userID})" class="btn btn-primary btn-sm" title="Xem Chủ Đề">
                                <i class="bi bi-eye" style="font-size: 1.5rem;"></i>
                            </button>
                        </div>
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
}


document.addEventListener("DOMContentLoaded", function () {
    fetchUsers1();    // Populate user table (from previous implementation)
    // Gọi hàm fetchPlans để tải dữ liệu khi trang được tải

});

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

function openCreateNutritionPlanModal(userID) {
    // Hiển thị cửa sổ nhập thông tin với SweetAlert2
    Swal.fire({
        title: 'Tạo kế hoạch dinh dưỡng',
        html: `
            <input id="nameNutritionPlan" class="swal2-input" placeholder="Tên kế hoạch">
            <input id="descriptions" class="swal2-input" placeholder="Mô tả">
            <input id="calo" type="number" step="0.1" class="swal2-input" placeholder="Calo (kcal)">
        `,
        showCancelButton: true,
        confirmButtonText: 'Tạo',
        cancelButtonText: 'Hủy',
        preConfirm: () => {
            const nameNutritionPlan = document.getElementById('nameNutritionPlan').value;
            const descriptions = document.getElementById('descriptions').value;
            const calo = parseFloat(document.getElementById('calo').value);

            if (!nameNutritionPlan || !descriptions || isNaN(calo)) {
                Swal.showValidationMessage('Vui lòng điền đầy đủ thông tin!');
                return null;
            }

            return { nameNutritionPlan, descriptions, calo };
        }
    }).then((result) => {
        if (result.isConfirmed) {
            const data = {
                ...result.value,
                userID: userID
            };

            // Gọi Fetch API để tạo kế hoạch dinh dưỡng
            fetch('/trainer/home/nutrition-plans', {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json'
                },
                body: JSON.stringify(data)
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire('Thành công', 'Kế hoạch dinh dưỡng đã được tạo!', 'success');
                    } else {
                        return response.json().then(err => {
                            throw new Error(err.message || 'Đã xảy ra lỗi.');
                        });
                    }
                })
                .catch(error => {
                    Swal.fire('Lỗi', error.message, 'error');
                });
        }
    });
}

function viewUserTopics(userID) {
    fetch(`/trainer/home/topics/user/${userID}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);  // Kiểm tra dữ liệu trả về
            if (data && Array.isArray(data) && data.length > 0) {
                const topicsList = data.map(topic => {
                    return `
                        <li>
                            <button onclick="viewTopicDetails(${topic.topicID}, ${userID})" class="btn btn-info btn-sm" title="Xem Chi Tiết">
                                Xem Chi Tiết
                            </button>
                        </li>
                    `;
                }).join('');

                const modalContent = `
                    <h5>Chủ Đề Của Người Dùng</h5>
                    <ul>
                        ${topicsList}
                    </ul>
                `;

                // Hiển thị danh sách chủ đề
                Swal.fire({
                    title: 'Các Chủ Đề Của Người Dùng',
                    html: modalContent,
                    icon: 'info',
                    confirmButtonText: 'Đóng'


                });
            } else {
                Swal.fire('Không tìm thấy chủ đề', 'Người dùng này chưa có chủ đề nào.', 'warning');
            }
        })
        .catch(error => {
            console.error('Error fetching user topics:', error);
            Swal.fire('Có lỗi xảy ra', 'Không thể lấy thông tin chủ đề.', 'error');
        });
}


function viewTopicDetails(topicID, userID) {
    // Lấy thông tin chi tiết của chủ đề từ API
    fetch(`/trainer/home/topics/${topicID}`)
        .then(response => response.json())
        .then(data => {
            console.log(data);  // Kiểm tra dữ liệu trả về

            if (data && data.topicName && data.descriptions) {
                const topicDetailContent = `
                    <h5>Chi Tiết Chủ Đề</h5>
                    <p><strong>Tên Chủ Đề:</strong> ${data.topicName}</p>
                    <p><strong>Mô Tả:</strong> ${data.descriptions}</p>
                    <button onclick="viewUserTopics(${userID})" class="btn btn-secondary">Trở Về</button>
                `;

                // Hiển thị chi tiết chủ đề
                Swal.fire({
                    title: 'Chi Tiết Chủ Đề',
                    html: topicDetailContent,
                    icon: 'info',
                    showConfirmButton: false // Tắt nút "OK"
                });
            } else {
                Swal.fire('Không tìm thấy chủ đề', 'Thông tin chi tiết chủ đề không hợp lệ.', 'warning');
            }
        })
        .catch(error => {
            console.error('Error fetching topic details:', error);
            Swal.fire('Có lỗi xảy ra', 'Không thể lấy thông tin chi tiết chủ đề.', 'error');
        });
}

async function fetchAndDisplayUsernames() {
    try {
        // Gửi yêu cầu GET tới API
        const response = await fetch('http://localhost:8080/trainer/home/users/user-info', {
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

// async function fetchAndDisplayUsernames() {
//     try {
//         // Gửi yêu cầu GET tới API
//         const response = await fetch('http://localhost:8080/trainer/home/users/user-info', {
//             method: 'GET',
//             headers: {
//                 'Content-Type': 'application/json'
//             },
//             credentials: 'include' // Đảm bảo gửi cookie session
//         });
//
//         if (response.ok) {
//             const data = await response.json(); // Lấy dữ liệu JSON từ phản hồi
//
//             // Kiểm tra xem dữ liệu có chứa username không
//             if (data && data.username) {
//                 const username = data.username;
//
//                 // Cập nhật username cho thẻ mong muốn
//                 const usernameElement = document.getElementById('username');  // Giả sử bạn có thẻ có id="username"
//                 if (usernameElement) {
//                     usernameElement.innerText = username; // Cập nhật tên người dùng
//                 }
//             } else {
//                 console.error("Username not found in the response");
//             }
//         } else {
//             console.error("Error fetching username:", response.statusText);
//         }
//     } catch (error) {
//         console.error("Error fetching username:", error);
//     }
// }
//
// // Gọi hàm khi trang được tải
// window.onload = fetchAndDisplayUsernames;
//
