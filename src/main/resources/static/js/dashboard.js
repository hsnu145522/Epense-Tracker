import { renderAccounts } from './account.js';
import { renderTransactions } from './transaction.js';
import { getAccountsByUser, getTransactionsByAccount, getCategoriesByUser } from './api.js';

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');
const username = localStorage.getItem('username');
console.log('Token:', token);
console.log('User ID:', userId);
if (!token || !userId || !username) {
    alert("You must be logged in to view the dashboard.");
    window.location.href = "index.html"; // Redirect to login page
}

// Refresh
document.addEventListener('DOMContentLoaded', () => {
    document.getElementById('username').innerText = username;
    loadDashboard();
});

// Logout button
document.getElementById('logoutBtn').addEventListener('click', logout);

export async function loadDashboard() {
    try {
        const accounts = await getAccountsByUser(userId, token);
        renderAccounts(accounts);

        const allTransactions = [];
        for (const acc of accounts) {
            const txs = await getTransactionsByAccount(acc.id, token);
            allTransactions.push(...txs);
        }
        renderTransactions(allTransactions);
        // Update account display in Transcation modal
        const accountSelect = document.getElementById('transactionAccount');
        accountSelect.innerHTML = '<option value="">Select Account</option>';
        accounts.forEach(acc => {
            const option = document.createElement('option');
            option.value = acc.id;
            option.textContent = `${acc.name} ($${acc.balance.toFixed(2)})`;
            accountSelect.appendChild(option);
        });
        // Initially disable category select until type chosen
        const categorySelect = document.getElementById('transactionCategory');
        categorySelect.disabled = true;

    } catch (e) {
        alert('Failed to load dashboard: ' + e.message);
    }
}


function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}