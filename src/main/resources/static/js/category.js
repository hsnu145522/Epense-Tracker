import { createCategory, getCategoriesByUser, updateCategory, deleteCategory } from "./api.js";

const token = localStorage.getItem('token');
const userId = localStorage.getItem('userId');
console.log('Token:', token);
console.log('User ID:', userId);
if (!token || !userId) {
    alert("You must be logged in to create a category.");
    window.location.href = "index.html"; // Redirect to login page
}

document.addEventListener('DOMContentLoaded', async () => {
    const categories = await getCategoriesByUser(userId, token);
    loadCategories(categories);
});

document.getElementById('logoutBtn').addEventListener('click', logout);

// Handle category form submission
document.getElementById("categoryForm").addEventListener("submit", async (e) => {
    e.preventDefault();
    const name = document.getElementById("categoryName").value;
    const type = document.getElementById("categoryType").value;

    if (!name || !type) {
        alert("Please enter valid category name and type");
        return;
    }

    try {
        await createCategory(userId, name, type, token);
        e.target.reset();

        // Close modal if you use one
        const modalEl = document.getElementById("categoryModal");
        const modalInstance = bootstrap.Modal.getInstance(modalEl);
        modalInstance?.hide();

        // Refresh dashboard
        loadCategories();
    } catch (err) {
        alert("Failed to create category: " + err.message);
    }
});

async function loadCategories() {
    try {
        const categories = await getCategoriesByUser(userId, token);
        renderCategories(categories);

    } catch (e) {
        alert('Failed to load categories: ' + e.message);
    }
}

export function renderCategories(categories) {
    const categoryList = document.getElementById('categoryList');
    categoryList.innerHTML = '';

    const createSection = (titleText) => {
        const section = document.createElement('div');
        const title = document.createElement('h5');
        title.textContent = titleText;
        section.appendChild(title);
        return section;
    };

    const incomeSection = createSection('Income Categories');
    const expenseSection = createSection('Expense Categories');

    categories.forEach(cat => {
        const card = document.createElement('div');
        card.className = 'card mb-3 shadow-sm';

        const cardBody = document.createElement('div');
        cardBody.className = 'card-body d-flex justify-content-between align-items-center';

        const info = document.createElement('div');
        info.innerHTML = `<strong>${cat.name}</strong><br><small class="text-muted">${cat.type}</small>`;

        const btnGroup = document.createElement('div');

        const editBtn = document.createElement('button');
        editBtn.className = 'btn btn-sm btn-outline-primary me-2';
        editBtn.innerText = 'Edit';
        editBtn.addEventListener('click', () => handleEditCategory(cat.id, cat.name, cat.type));

        const deleteBtn = document.createElement('button');
        deleteBtn.className = 'btn btn-sm btn-outline-danger';
        deleteBtn.innerText = 'Delete';
        deleteBtn.addEventListener('click', () => handleDeleteCategory(cat.id));

        btnGroup.appendChild(editBtn);
        btnGroup.appendChild(deleteBtn);

        cardBody.appendChild(info);
        cardBody.appendChild(btnGroup);
        card.appendChild(cardBody);

        if (cat.type === 'INCOME') {
            incomeSection.appendChild(card);
        } else {
            expenseSection.appendChild(card);
        }
    });

    categoryList.appendChild(incomeSection);
    categoryList.appendChild(expenseSection);
}

async function handleEditCategory(id, currentName, currentType) {
    const newName = prompt('Edit category name:', currentName);
    if (!newName) return;

    try {
        await updateCategory(id, newName, token);
        loadCategories(); // re-render after update
    } catch (err) {
        alert('Failed to update category: ' + err.message);
    }
}

async function handleDeleteCategory(id) {
    if (!confirm('Are you sure you want to delete this category?')) return;

    try {
        await deleteCategory(id, token);
        alert('Category deleted.');
        loadCategories(); // re-render after delete
    } catch (err) {
        alert('Failed to delete category: ' + err.message);
    }
}



function logout() {
    localStorage.clear();
    window.location.href = 'index.html';
}