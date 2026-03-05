document.getElementById("loginForm").addEventListener("submit", async function (e) {
    e.preventDefault();

    const userCredentials = {
        username: document.getElementById("username").value,
        password: document.getElementById("password").value
    };

    const response = await fetch("http://localhost:8080/api/login", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(userCredentials)
    });

    if (!response.ok) {
        localStorage.removeItem("token");
        alert("Login failed");
        return;
    }

    const data = await response.json();

    console.log("New token:", data.token);

    localStorage.setItem("token", data.token);
    localStorage.setItem("roles", JSON.stringify(data.roles));

    if (data.roles.includes("ROLE_ADMIN")) {
        window.location.replace("/index.html");
    }
});