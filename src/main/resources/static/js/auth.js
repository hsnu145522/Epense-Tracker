import { loginUser, registerUser } from './api.js';

document.addEventListener("DOMContentLoaded", () => {
    const loginForm = document.getElementById("loginForm");
    const registerForm = document.getElementById("registerForm");

    if (loginForm) {
        loginForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("username").value;
            const password = document.getElementById("password").value;

            try {
                const data = await loginUser(username, password);
                localStorage.setItem("token", data.token);
                localStorage.setItem("userId", data.userId);
                localStorage.setItem("username", data.username);
                window.location.href = "dashboard.html";
            } catch (err) {
                alert("Login failed: " + err.message);
            }
        });
    }

    if (registerForm) {
        registerForm.addEventListener("submit", async (e) => {
            e.preventDefault();
            const username = document.getElementById("username").value;
            const email = document.getElementById("email").value;
            const password = document.getElementById("password").value;

            try {
                await registerUser(username, email, password);
                alert("Registration successful! You can now login.");
                window.location.href = "index.html";
            } catch (err) {
                alert("Registration failed: " + err.message);
            }
        });
    }
});