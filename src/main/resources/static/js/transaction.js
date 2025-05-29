import { createTransaction, getCategoriesByUser } from "./api.js";
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
        alert('Transaction created!');
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
        li.className = 'list-group-item';
        li.innerText = `${tx.timestamp} - ${tx.category.name} - $${tx.amount} (${tx.description || ''})`;
        list.appendChild(li);
    });
}