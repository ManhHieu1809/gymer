// Fetch dữ liệu từ API và hiển thị vào bảng
function fetchProgress() {
    fetch('/trainer/home/progress')
        .then(response => response.json())
        .then(data => {
            const tableBody = document.querySelector('#table1 tbody');
            tableBody.innerHTML = ''; // Xóa nội dung cũ trước khi thêm dữ liệu mới

            data.forEach(progress => {
                const row = document.createElement('tr');
                row.innerHTML = `
                    <td>${progress.progressID}</td>
                    <td>${progress.userName }</td>
                    <td>${progress.planName}</td>
                    <td>${progress.progressDate}</td>
                    <td>${progress.achievement}</td>
                    <td>
        <!-- Nút Sửa -->
        <button 
            class="btn btn-warning btn-sm" 
            title="Chỉnh sửa tiến độ" 
            onclick="editPlan(${progress.progressID})"
        >
            Sửa
        </button>
        
        <!-- Nút Xóa -->
        <button 
            class="btn btn-danger btn-sm" 
            title="Xóa tiến độ" 
            onclick="deletePlan(${progress.progressID})"
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
            Swal.fire('Lỗi', 'Không thể tải dữ liệu tiến độ.', 'error');
        });
}

document.addEventListener("DOMContentLoaded", function () {// Populate user table (from previous implementation)
    fetchProgress();     // Populate plan table
});

// Hàm để sửa tiến độ
function editPlan(progressID) {
    // Hiển thị SweetAlert2 với các lựa chọn tiến độ
    Swal.fire({
        title: 'Chỉnh sửa tiến độ',
        input: 'select',
        inputOptions: {
            'COMPLETED': 'Hoàn thành',
            'IN_PROGRESS': 'Đang tiến hành',
            'NOT_STARTED': 'Chưa bắt đầu'
        },
        inputPlaceholder: 'Chọn tiến độ mới',
        showCancelButton: true,
        confirmButtonText: 'Lưu',
        cancelButtonText: 'Hủy',
        inputValidator: (value) => {
            if (!value) {
                return 'Vui lòng chọn tiến độ';
            }
        }
    }).then((result) => {
        // Kiểm tra nếu người dùng xác nhận thay đổi
        if (result.isConfirmed) {
            const selectedProgress = result.value;

            // Gửi yêu cầu PUT để cập nhật tiến độ
            fetch(`/trainer/home/progress/${progressID}/update-achievement?newAchievement=${selectedProgress}`, {
                method: 'PUT',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer your-token-here' // Thêm token nếu cần
                },
                body: JSON.stringify({
                    progressStatus: selectedProgress
                })
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire('Thành công', 'Tiến độ đã được cập nhật', 'success');
                        // Cập nhật lại dữ liệu bảng nếu cần
                        fetchProgress(); // Gọi hàm để tải lại dữ liệu
                    } else {
                        throw new Error('Cập nhật tiến độ không thành công');
                    }
                })
                .catch(error => {
                    Swal.fire('Có lỗi xảy ra', error.message, 'error');
                });
        }
    });
}

// Hàm để xóa tiến độ
function deletePlan(progressID) {
    Swal.fire({
        title: 'Xác nhận xóa',
        text: 'Bạn có chắc chắn muốn xóa tiến độ này?',
        icon: 'warning',
        showCancelButton: true,
        confirmButtonText: 'Xóa',
        cancelButtonText: 'Hủy'
    }).then((result) => {
        if (result.isConfirmed) {
            // Gửi yêu cầu DELETE để xóa tiến độ
            fetch(`/trainer/home/progress/${progressID}`, {
                method: 'DELETE',
                headers: {
                    'Content-Type': 'application/json',
                    'Authorization': 'Bearer your-token-here' // Thêm token nếu cần
                }
            })
                .then(response => {
                    if (response.ok) {
                        Swal.fire('Đã xóa', 'Tiến độ đã bị xóa', 'success');
                        // Cập nhật lại dữ liệu bảng nếu cần
                        fetchProgress(); // Gọi hàm để tải lại dữ liệu
                    } else {
                        throw new Error('Xóa tiến độ không thành công');
                    }
                })
                .catch(error => {
                    Swal.fire('Có lỗi xảy ra', error.message, 'error');
                });
        }
    });
}
