<!DOCTYPE html>
<html lang="it">
<head>
<meta charset="ISO-8859-1">
<title>Registrazione Dipendente</title>
<style>
  body {
    font-family: 'Arial', sans-serif;
    background-color: #f4f4f4;
    margin: 0;
    padding: 0;
  }

  .container {
    max-width: 600px;
    margin: 20px auto;
    padding: 20px;
    background-color: #fff;
    box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  }

  .form-title {
    color: #333;
    text-align: center;
    margin-bottom: 30px;
  }

  form {
    display: flex;
    flex-direction: column;
  }

  input[type=text],
  input[type=date],
  input[type=email],
  input[type=password],
  select {
    padding: 10px;
    margin-bottom: 15px;
    border: 1px solid #ddd;
    border-radius: 4px;
  }

  input[type=radio] {
    margin-right: 5px;
  }

  label {
    margin-right: 20px;
  }

  button {
    padding: 10px;
    border: none;
    border-radius: 4px;
    background-color: #5cb85c;
    color: white;
    cursor: pointer;
    margin-top: 10px;
  }

  button[type=reset] {
    background-color: #f0ad4e;
  }
</style>
</head>
<body>

<div class="container">
  <h2 class="form-title">Inserimento Nuovo Dipendente</h2>
  <form method="POST" action="Inserimento">
    <input type="text" name="nome" placeholder="Nome" required>
    <input type="text" name="cognome" placeholder="Cognome" required>
    <input type="date" name="dataNascita" placeholder="Data di Nascita" required>
    <input type="text" name="cf" placeholder="Codice Fiscale" required>
    <input type="text" name="stipendio" placeholder="Stipendio" required>
    <div>
      <input type="radio" name="sesso" value="m" id="maschio" required>
      <label for="maschio">M</label>
      <input type="radio" name="sesso" value="f" id="femmina">
      <label for="femmina">F</label>
    </div>
    <input type="date" name="dataAssunzione" placeholder="Data Assunzione" required>
    <input type="text" name="luogoNascita" placeholder="Luogo di Nascita" required>
    <input type="text" name="username" placeholder="Username" required>
    <input type="email" name="email" placeholder="Email" required>
    <input type="password" name="password" placeholder="Password" required>
    <select name="permesso" required>
      <option value="" disabled selected>Tipo di Permesso</option>
      <option value="1">Admin</option>
      <option value="2">Guest</option>
    </select>
    <button type="reset">Cancella</button>
    <button type="submit">Invia</button>
    <a href="home.jsp">HOME</a>
  </form>
</div>

</body>
</html>
