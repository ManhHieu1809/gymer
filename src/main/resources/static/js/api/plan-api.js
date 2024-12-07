
// Fetch dữ liệu từ API và hiển thị vào bảng
function fetchPlans() {
    fetch('/trainer/home/workout-plans')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#table1 tbody');
            tableBody.innerHTML = ''; // Xóa nội dung cũ trước khi thêm dữ liệu mới

            data.forEach(plan => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${plan.planID}</td>
                    <td>${plan.planName || 'Không có tên kế hoạch'}</td>
                    <td>${plan.descriptions || 'Không có mô tả'}</td>
                    <td>${plan.duration || 'Không có thời gian'}</td>
                    <td>
                        <button 
                            class="btn btn-info btn-sm" 
                            onclick="viewCustomerDetails(${plan.planID})"
                        >
                            Xem Chi Tiết
                        </button>
                    </td>
                    <td>
        <!-- Nút Sửa -->
        <button 
            class="btn btn-warning btn-sm" 
            title="Chỉnh sửa kế hoạch" 
            onclick="editPlan(${plan.planID})"
        >
            Sửa
        </button>
        
        <!-- Nút Xóa -->
        <button 
            class="btn btn-danger btn-sm" 
            title="Xóa kế hoạch" 
            onclick="deletePlan(${plan.planID})"
        >
            Xóa
        </button>
    </td>
                `;
                tableBody.appendChild(row);
            });
        })
        .catch(error => {
            console.error('Error fetching plans:', error);
            Swal.fire('Lỗi', 'Không thể tải dữ liệu kế hoạch luyện tập.', 'error');
        });
}

// Hàm để mở cửa sổ chi tiết khách hàng
function viewCustomerDetails(planID) {
    fetch(`/trainer/home/workouts/plan/${planID}`)
        .then(response => response.json())
        .then(workouts => {
            if (Array.isArray(workouts) && workouts.length > 0) {
                // Tạo danh sách HTML từ các bài tập
                const workoutDetails = workouts.map(workout => `
                    <div style="margin-bottom: 15px; padding: 10px; border: 1px solid #ddd; border-radius: 5px;">
                        <p><strong>Loại:</strong> ${workout.category || 'Không có dữ liệu'}</p>
                        <p><strong>Tên:</strong> ${workout.workoutName || 'Không có dữ liệu'}</p>
                        <p><strong>Hướng dẫn:</strong> ${workout.instructions || 'Không có dữ liệu'}</p>
                    </div>
                `).join('');

                // Hiển thị danh sách bài tập trong SweetAlert2
                Swal.fire({
                    title: 'Thông Tin Bài Tập',
                    html: workoutDetails,
                    icon: 'info',
                    confirmButtonText: 'Đóng'
                });
            } else {
                Swal.fire('Thông Báo', 'Không có bài tập nào liên quan đến kế hoạch này.', 'info');
            }
        })
        .catch(error => {
            console.error('Error fetching workout details:', error);
            Swal.fire('Lỗi', 'Không thể tải thông tin bài tập.', 'error');
        });
}

function editPlan(planID) {
    // Hiển thị cửa sổ nhập thông tin sửa kế hoạch
    Swal.fire({
        title: 'Sửa Kế Hoạch Luyện Tập',
        html: `
            <input type="text" id="editPlanName" class="swal2-input" placeholder="Tên Kế Hoạch">
            <textarea id="editDescriptions" class="swal2-textarea" placeholder="Mô Tả"></textarea>
            <input type="text" id="editDuration" class="swal2-input" placeholder="Thời Gian (VD: 30 ngày)">
        `,
        focusConfirm: false,
        showCancelButton: true,
        confirmButtonText: 'Lưu',
        cancelButtonText: 'Hủy',
        preConfirm: () => {
            const planName = document.getElementById('editPlanName').value.trim();
            const descriptions = document.getElementById('editDescriptions').value.trim();
            const duration = document.getElementById('editDuration').value.trim();

            if (!planName || !descriptions || !duration) {
                Swal.showValidationMessage('Vui lòng điền đầy đủ thông tin');
                return false;
            }

            // Gửi yêu cầu cập nhật kế hoạch
            return fetch(`/trainer/home/workout-plans/${planID}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer your-token-here' // Thêm token nếu cần
                },
                body: JSON.stringify({
                    planName: planName,
                    descriptions: descriptions,
                    duration: duration
                })
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => {
                            throw new Error(err.message || 'Cập nhật thất bại');
                        });
                    }
                    return response.json();
                })
                .catch(error => {
                    Swal.showValidationMessage(`Lỗi: ${error.message}`);
                });
        }
    }).then(result => {
        if (result.isConfirmed) {
            Swal.fire('Cập nhật thành công!', '', 'success').then(() => {
                fetchPlans(); // Reload bảng kế hoạch sau khi sửa
            });
        }
    });
}

function deletePlan(planID) {
    Swal.fire({
        title: 'Xác nhận xóa',
        text: 'Bạn có chắc chắn muốn xóa kế hoạch này không?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy',
        confirmButtonColor: '#d33',
        cancelButtonColor: '#3085d6'
    }).then(result => {
        if (result.isConfirmed) {
            // Gửi yêu cầu xóa kế hoạch
            fetch(`/trainer/home/workout-plans/${planID}`, {
                method: 'DELETE',
                headers: {
                    'Authorization': 'Bearer your-token-here' // Thêm token nếu cần
                }
            })
                .then(response => {
                    if (!response.ok) {
                        return response.json().then(err => {
                            throw new Error(err.message || 'Xóa thất bại');
                        });
                    }
                    Swal.fire('Xóa thành công!', '', 'success').then(() => {
                        fetchPlans(); // Reload bảng kế hoạch sau khi xóa
                    });
                })
                .catch(error => {
                    Swal.fire('Lỗi', `Không thể xóa kế hoạch: ${error.message}`, 'error');
                });
        }
    });
}


document.addEventListener("DOMContentLoaded", function () {// Populate user table (from previous implementation)
    fetchPlans();     // Populate plan table
});