import { createAccount, deleteAccount, updateAccount } from "./api.js";
import { loadDashboard } from "./dashboard.js";

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');

// Add Account
document.getElementById("accountForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("accountName").value;
    const balance = parseFloat(document.getElementById("initialBalance").value);

    if (!name || balance < 0) {
        alert("Please enter valid account name and balance");
        return;
    }

    try {
        await createAccount(userId, name, balance, token);
        e.target.reset();
        // Close the modal
        const modalElement = document.getElementById("accountModal");
        const modalInstance = bootstrap.Modal.getInstance(modalElement);
        modalInstance.hide();
        loadDashboard();
    } catch (err) {
        alert(err.message);
    }
});

// Load Accounts with Edit/Delete
export async function renderAccounts(accounts) {
    const accountList = document.getElementById('accountList');
    accountList.innerHTML = '';

    accounts.forEach(acc => {
        const li = document.createElement('li');
        li.className = 'list-group-item d-flex justify-content-between align-items-center';

        const span = document.createElement('span');
        span.innerText = `${acc.name}: $${acc.balance}`;

        const btnGroup = document.createElement('span');

        const editBtn = document.createElement('button');
        editBtn.className = 'btn btn-sm btn-primary me-2';
        editBtn.innerText = 'Edit';
        editBtn.addEventListener('click', () => handleEditAccount(acc.id, acc.name, acc.balance));

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'btn btn-sm btn-danger';
        deleteBtn.innerText = 'Delete';
        deleteBtn.addEventListener('click', () => handleDeleteAccount(acc.id));

        btnGroup.appendChild(editBtn);
        btnGroup.appendChild(deleteBtn);

        li.appendChild(span);
        li.appendChild(btnGroup);
        accountList.appendChild(li);
    });
}

async function handleDeleteAccount(accountId) {
    if (!confirm('Are you sure you want to delete this account?')) return;
    try {
        await deleteAccount(accountId, token);
        loadDashboard();
    } catch (e) {
        alert('Failed to delete account: ' + e.message);
    }
}

async function handleEditAccount(id, name, balance) {
    const newName = prompt("Edit account name:", name);
    const newBalance = prompt("Edit account balance:", balance);
    if (newName && parseFloat(newBalance) >= 0) {
        updateAccount(id, newName, parseFloat(newBalance), token)
            .then(loadDashboard)
            .catch(err => alert("Failed to update account: " + err.message));
    }
}