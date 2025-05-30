const API_BASE_URL = "http://localhost:8080";

// === Auth ===
export async function loginUser(username, password) {
  const response = await fetch(`${API_BASE_URL}/api/auth/login`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, password })
  });
  if (!response.ok) throw new Error("Login failed");
  return await response.json();
}

export async function registerUser(username, email, password) {
  const response = await fetch(`${API_BASE_URL}/api/auth/register`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ username, email, password })
  });
  if (!response.ok) throw new Error("Registration failed");
  return await response.json();
}

// === Account ===
// Fetch accounts by user ID
export async function getAccountsByUser(userId, token) {
  const response = await fetch(`${API_BASE_URL}/api/accounts/user/${userId}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  if (!response.ok) throw new Error("Failed to fetch accounts");
  return await response.json();
}

// Create a new account
export async function createAccount(userId, name, initialBalance, token) {
  const response = await fetch(`${API_BASE_URL}/api/accounts/user/${userId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
    body: JSON.stringify({
      name: name,
      initialBalance: initialBalance
    })
  });

  if (!response.ok) {
    throw new Error("Failed to create account");
  }

  return await response.json();
}

// Update an account
export async function updateAccount(accountId, name, balance, token) {
  const response = await fetch(`${API_BASE_URL}/api/accounts/${accountId}`, {
    method: "PUT",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`,
    },
    body: JSON.stringify({
      name: name,
      balance: balance
    })
  });

  if (!response.ok) {
    throw new Error("Failed to update account");
  }

  return await response.json();
}

// Delete an account
export async function deleteAccount(accountId, token) {
  const response = await fetch(`${API_BASE_URL}/api/accounts/${accountId}`, {
    method: "DELETE",
    headers: { Authorization: `Bearer ${token}` }
  });
  if (!response.ok) throw new Error("Failed to delete account");
}

// === Category ===
export async function getCategoriesByUser(userId, token) {
  const response = await fetch(`${API_BASE_URL}/api/categories/user/${userId}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  if (!response.ok) throw new Error("Failed to fetch categories");
  return await response.json();
}

export async function createCategory(userId, name, type, token) {
  const response = await fetch(`${API_BASE_URL}/api/categories/user/${userId}`, {
    method: "POST",
    headers: {
      "Content-Type": "application/json",
      "Authorization": `Bearer ${token}`
    },
    body: JSON.stringify({
      name: name,
      type: type
    })
  });

  if (!response.ok) {
    throw new Error("Failed to create category");
  }

  return await response.json();
}

// Update a category by ID
export async function updateCategory(id, name, token) {
  const response = await fetch(`/api/categories/${id}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`,
    },
    body: JSON.stringify({ name }),
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Failed to update category');
  }

  return response.json();
}

// Delete a category by ID
export async function deleteCategory(id, token) {
  const response = await fetch(`/api/categories/${id}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`,
    },
  });

  if (!response.ok) {
    const errorData = await response.json();
    throw new Error(errorData.message || 'Failed to delete category');
  }

  return true; // or response.json() if your API returns JSON on delete
}

// === Transaction ===
export async function getTransactionsByAccount(accountId, token) {
  const response = await fetch(`${API_BASE_URL}/api/transactions/account/${accountId}`, {
    headers: { Authorization: `Bearer ${token}` }
  });
  if (!response.ok) throw new Error("Failed to fetch transactions");
  return await response.json();
}

export async function createTransaction(categoryId, accountId, amount, description, token) {
  const response = await fetch(`${API_BASE_URL}/api/transactions`, {
    method: 'POST',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({
      categoryId,
      accountId,
      amount,
      description
    })
  });

  if (!response.ok) {
    const error = await response.json();
    throw new Error(error.message || 'Failed to create transaction');
  }

  return response.json();
}

export async function updateTransaction(transactionId, newAmount, token) {
  const res = await fetch(`${API_BASE_URL}/api/transactions/${transactionId}`, {
    method: 'PUT',
    headers: {
      'Content-Type': 'application/json',
      'Authorization': `Bearer ${token}`
    },
    body: JSON.stringify({ newAmount })
  });
  if (!res.ok) throw new Error('Failed to update transaction');
  return await res.json();
}

export async function deleteTransaction(transactionId, token) {
  const res = await fetch(`${API_BASE_URL}/api/transactions/${transactionId}`, {
    method: 'DELETE',
    headers: {
      'Authorization': `Bearer ${token}`
    }
  });
  if (!res.ok) throw new Error('Failed to delete transaction');
}