// URL API Backend
const API_BASE_URL = 'http://localhost:8080/admin/home/users/create';

// Thêm người dùng mới
document.getElementById('addUserForm').addEventListener('submit', async (event) => {
    event.preventDefault();

    // Thu thập dữ liệu từ form
    const user = {
        username: document.getElementById('username').value,
        password: document.getElementById('password').value,
        email: document.getElementById('email').value,
        fullName: document.getElementById('fullName').value,
        age: document.getElementById('age').value,
        gender: document.getElementById('gender').value,
        height: parseFloat(document.getElementById('height').value), // Parse số nguyên
        weight: parseFloat(document.getElementById('weight').value), // Parse số nguyên
        roles: [document.getElementById('roles').value] // Enum dưới dạng chuỗi
    };

    try {
        // Gửi request POST tới API
        const response = await fetch(API_BASE_URL, {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(user),
        });

        if (response.ok) {
            alert('Người dùng đã được thêm thành công!');
            document.getElementById('addUserForm').reset(); // Xóa dữ liệu form sau khi thêm thành công
        } else {
            const errorData = await response.json();
            console.error('Lỗi khi thêm user:', errorData);
            alert('Thêm người dùng thất bại! Hãy kiểm tra lại.');
        }
    } catch (error) {
        console.error('Lỗi kết nối tới API:', error);
        alert('Không thể kết nối đến server.');
    }
});