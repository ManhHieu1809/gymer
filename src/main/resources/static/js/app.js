function filterTable(searchBoxId, tableId) {
    const input = document.getElementById(searchBoxId);
    const filter = input.value.toLowerCase();
    const table = document.getElementById(tableId);
    const rows = table.getElementsByTagName("tr");

    for (let i = 1; i < rows.length; i++) { // Skip the header row
        const cells = rows[i].getElementsByTagName("td");
        let match = false;

        for (let j = 0; j < cells.length; j++) {
            if (cells[j].textContent.toLowerCase().includes(filter)) {
                match = true;
                break;
            }
        }

        rows[i].style.display = match ? "" : "none";
    }
}

function deleteCheckedRows(tableId) {
    const table = document.querySelector(`#${tableId} tbody`);
    if (!table) return; // Exit if the table is not found

    const checkboxes = table.querySelectorAll('.checkbox:checked');
    const idsToDelete = Array.from(checkboxes).map(checkbox => checkbox.dataset.id);

    if (idsToDelete.length === 0) {
        alert("No rows selected for deletion.");
        return;
    }

    // Send the IDs to the backend for deletion
    fetch("/your-api-path/deleteUsers", {
        method: "DELETE",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify({ userIDs: idsToDelete })
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to delete users");
            }
            return response.json();
        })
        .then(() => {
            // Remove rows from the DOM if deletion is successful
            checkboxes.forEach(checkbox => {
                const row = checkbox.closest("tr");
                table.removeChild(row);
            });
            alert("Selected rows deleted successfully.");
        })
        .catch(error => {
            console.error("Error deleting rows:", error);
            alert("An error occurred while deleting rows.");
        });
}


var index = 0; // Initialize a global index variable

function addGoods() {
    var inputname = document.getElementById("inputname2").value;
    var inputquantity = document.getElementById("inputquantity2").value;
    var inputdescription = document.getElementById("inputdecription2").value; // Fixed spelling
    var inputprice = document.getElementById("inputprice2").value;

    // Create a product object to send to the server
    const product = {
        productName: inputname,
        descriptions: inputdescription,
        price: parseFloat(inputprice),
        quantity: parseInt(inputquantity, 10)
    };
    // Add event listener for "Select All" checkbox
    document.querySelector("#goods-selectAll").addEventListener("change", function () {
        const checkboxes = document.querySelectorAll(".checkbox");
        checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
    });
    // Send the data to the backend API
    fetch("/your-api-path/addProduct", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(product)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to save product");
            }
            return response.json();
        })
        .then(savedProduct => {
            // Add the new product to the table in the UI
            var rows = `<tr>
                        <td><input type="checkbox" class="checkbox" data-id="${savedProduct.productID}" /></td>
                        <td>${savedProduct.productID}</td>
                        <td>${savedProduct.productName}</td>
                        <td>${savedProduct.descriptions}</td>
                        <td>${savedProduct.price.toFixed(2)}</td>
                        <td>${savedProduct.quantity}</td>
                    </tr>`;
            document.querySelector("#table2 tbody").insertAdjacentHTML("beforeend", rows);
        })
        .catch(error => console.error("Error adding product:", error));

    return false; // Prevent form submission
}


function addUser() {
    var inputname = document.getElementById("inputname1").value;
    var inputpassword = document.getElementById("inputpassword1").value;
    var inputemail = document.getElementById("inputemail1").value;
    var inputfullname = document.getElementById("inputfullname1").value;
    var inputage = document.getElementById("inputage1").value;
    var inputgender = document.getElementById("inputgender1").value;
    var inputheight = document.getElementById("inputheight1").value;
    var inputweight = document.getElementById("inputweight1").value;
    var inputrole = document.getElementById("inputrole1").value;

    // Create a user object to send to the server
    const user = {
        username: inputname,
        password: inputpassword,
        email: inputemail,
        fullName: inputfullname,
        age: parseInt(inputage, 10),
        gender: inputgender,
        height: parseFloat(inputheight),
        weight: parseFloat(inputweight),
        roles: inputrole
    };
    // Add event listener for "Select All" checkbox
    document.querySelector("#user-selectAll").addEventListener("change", function () {
        const checkboxes = document.querySelectorAll(".checkbox");
        checkboxes.forEach(checkbox => (checkbox.checked = this.checked));
    });
    // Send the data to the backend API
    fetch("/admin/home/users", {
        method: "POST",
        headers: {
            "Content-Type": "application/json"
        },
        body: JSON.stringify(user)
    })
        .then(response => {
            if (!response.ok) {
                throw new Error("Failed to save user");
            }
            return response.json();
        })
        .then(savedUser => {
            // Add the new user to the table in the UI
            var rows = `<tr>
                        <td><input type="checkbox" class="checkbox" data-id="${savedUser.userID}" /></td>
                        <td>${savedUser.userID}</td>
                        <td>${savedUser.username}</td>
                        <td>${savedUser.email}</td>
                        <td>${savedUser.fullName}</td>
                        <td>${savedUser.age}</td>
                        <td>${savedUser.gender}</td>
                        <td>${savedUser.height.toFixed(2)}</td>
                        <td>${savedUser.weight.toFixed(2)}</td>
                        <td>${savedUser.roles}</td>
                    </tr>`;
            document.querySelector("#table1 tbody").insertAdjacentHTML("beforeend", rows);
        })
        .catch(error => console.error("Error adding user:", error));

    return false; // Prevent form submission
}
