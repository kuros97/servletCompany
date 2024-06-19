<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="ISO-8859-1">
    <title>Login</title>
</head>
<body>
    <h1>Login</h1>
    <form action="Login" method="post">
        <label for="usernameOrEmail">Username or Email:</label>
        <input type="text" id="usernameOrEmail" name="usernameOrEmail" required><br>
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" required><br>
        <input type="submit" value="Login">
    </form>
    <p style="color:red">${errorMessage}</p>
</body>
</html>
