import { createTransaction, getCategoriesByUser, deleteTransaction, updateTransaction } from "./api.js";
import { loadDashboard } from "./dashboard.js";

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');

const typeSelect = document.getElementById('transactionType');
typeSelect.addEventListener('change', async () => {
    const categories = await getCategoriesByUser(userId, token);
    const categorySelect = document.getElementById('transactionCategory');
    const selectedType = typeSelect.value;
    categorySelect.innerHTML = '<option value="">Select Category</option>';

    if (!selectedType) {
        categorySelect.disabled = true;
        return;
    }

    const filteredCategories = categories.filter(cat => cat.type === selectedType);

    filteredCategories.forEach(cat => {
        const option = document.createElement('option');
        option.value = cat.id;
        option.textContent = cat.name;
        categorySelect.appendChild(option);
    });

    categorySelect.disabled = false;
});

document.getElementById('transactionForm').addEventListener('submit', async (e) => {
    e.preventDefault();
    const accountSelect = document.getElementById('transactionAccount');
    const categorySelect = document.getElementById('transactionCategory');
    const accountId = accountSelect.value;
    const categoryId = categorySelect.value;
    const amount = parseFloat(document.getElementById('transactionAmount').value);
    const description = document.getElementById('transactionDescription').value;

    if (!accountId || !categoryId || amount <= 0) {
        alert('Please fill all required fields with valid data');
        return;
    }

    try {
        await createTransaction(categoryId, accountId, amount, description, token);
        e.target.reset();

        // Close modal (Bootstrap 5)
        const modalEl = document.getElementById('transactionModal');
        const modal = bootstrap.Modal.getInstance(modalEl);
        modal.hide();

        // Optionally refresh dashboard or transaction list here
        loadDashboard();
    } catch (err) {
        alert('Failed to create transaction: ' + err.message);
    }
});

export async function renderTransactions(transactions) {
    const list = document.getElementById('transactionList');
    list.innerHTML = '';
    transactions.forEach(tx => {
        const li = document.createElement('li');
        li.className = 'list-group-item d-flex justify-content-between align-items-center';

        const info = document.createElement('div');
        info.innerText = `${tx.timestamp} - ${tx.category.name} - $${tx.amount} (${tx.description || ''})`;

        const btnGroup = document.createElement('div');

        const editBtn = document.createElement('button');
        editBtn.className = 'btn btn-sm btn-outline-primary me-2';
        editBtn.innerText = 'Edit';
        editBtn.addEventListener('click', () => handleEditTransaction(tx));

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'btn btn-sm btn-outline-danger';
        deleteBtn.innerText = 'Delete';
        deleteBtn.addEventListener('click', () => handleDeleteTransaction(tx.id));

        btnGroup.appendChild(editBtn);
        btnGroup.appendChild(deleteBtn);

        li.appendChild(info);
        li.appendChild(btnGroup);
        list.appendChild(li);
    });
}

async function handleDeleteTransaction(transactionId) {
    if (!confirm('Are you sure you want to delete this transaction?')) return;

    try {
        await deleteTransaction(transactionId, token);
        loadDashboard();
    } catch (err) {
        alert('Failed to delete: ' + err.message);
    }
}

async function handleEditTransaction(tx) {
    const newAmount = prompt('Edit ammount:', tx.amount);
    if (isNaN(newAmount) || newAmount <= 0) {
        alert('Invalid amount');
        return;
    }

    try {
        await updateTransaction(tx.id, newAmount, token);
        loadDashboard(); // re-render after update
    } catch (err) {
        alert('Failed to update category: ' + err.message);
    }
}
