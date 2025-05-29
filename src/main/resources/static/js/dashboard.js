import { renderAccounts } from './account.js';
import { getAccountsByUser, getTransactionsByAccount } from './api.js';

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
    } catch (e) {
        alert('Failed to load dashboard: ' + e.message);
    }
}

function renderTransactions(transactions) {
    const list = document.getElementById('transactionList');
    list.innerHTML = '';
    transactions.forEach(tx => {
        const li = document.createElement('li');
        li.className = 'list-group-item';
        li.innerText = `${tx.timestamp} - ${tx.category.name} - $${tx.amount} (${tx.description || ''})`;
        list.appendChild(li);
    });
}

function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}