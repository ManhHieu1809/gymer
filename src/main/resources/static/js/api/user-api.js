
    document.addEventListener("DOMContentLoaded", () => {
    // Hàm lấy và hiển thị tên người dùng
    async function fetchAndDisplayUsernames() {
        try {
            // Gửi yêu cầu GET tới API
            const response = await fetch('http://localhost:8080/customer/home/users/user-info1', {
                method: 'GET',
                headers: {
                    'Content-Type': 'application/json'
                },
                credentials: 'include' // Đảm bảo gửi cookie session
            });

            if (response.ok) {
                const data = await response.json(); // Lấy dữ liệu JSON từ phản hồi
                const username = data.username;

                // Hiển thị tên người dùng và ẩn các liên kết login/signup
                document.getElementById("auth-links").style.display = 'none'; // Ẩn "Login" và "Signup"
                document.getElementById("user-info").style.display = 'block'; // Hiển thị tên người dùng
                document.getElementById("username").innerText = username; // Cập nhật tên người dùng
            } else {
                console.error("Error fetching username:", response.statusText);
            }
        } catch (error) {
            console.error("Error fetching username:", error);
        }
    }

    // Kiểm tra khi tải trang và gọi API
    fetchAndDisplayUsernames();
});

