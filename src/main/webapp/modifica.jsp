<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifica Dipendente</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #121212;
            color: #e0e0e0;
            margin: 0;
            padding: 0;
        }
        h1 {
            color: #ffffff;
            text-align: center;
            margin-top: 20px;
        }
        form {
            display: flex;
            flex-direction: column;
            align-items: center;
            margin: 20px auto;
            width: 80%;
            max-width: 800px;
        }
        .form-group {
            display: flex;
            align-items: center;
            margin: 10px 0;
            width: 100%;
        }
        .form-group label {
            width: 20%;
            min-width: 120px;
            margin-right: 10px;
        }
        .form-group input, .form-group select {
            width: 60%;
            padding: 10px;
            border-radius: 5px;
            border: 1px solid #555;
            background-color: #1f1f1f;
            color: white;
        }
        .form-group .current-value {
            width: 20%;
            margin-left: 10px;
            color: #00bcd4;
        }
        button {
            background-color: #00bcd4;
            border: none;
            padding: 10px 20px;
            color: white;
            cursor: pointer;
            border-radius: 5px;
            transition: background-color 0.3s, transform 0.3s;
            margin: 10px;
        }
        button:hover {
            background-color: #0097a7;
            transform: scale(1.05);
        }
    </style>
</head>
<body>

<h1>Modifica Dipendente</h1>
<form method="POST" action="Modifica">
    <!-- Hidden Fields -->
    <input type="hidden" name="accountId" value="${account.id}">
    <input type="hidden" name="idDipendente" value="${account.idDipendente}">

    <div class="form-group">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${account.nome}" placeholder="Inserisci il nome">
        <div class="current-value">(attuale: ${account.nome})</div>
    </div>

    <div class="form-group">
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="${account.cognome}" placeholder="Inserisci il cognome">
        <div class="current-value">(attuale: ${account.cognome})</div>
    </div>

    <div class="form-group">
        <label for="dataNascita">Data di Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" value="${account.dataDiNascita}">
        <div class="current-value">(attuale: ${account.dataDiNascita})</div>
    </div>

    <div class="form-group">
        <label for="cf">Codice Fiscale:</label>
        <input type="text" id="cf" name="cf" value="${account.cf}" readonly>
        <div class="current-value">(non modificabile)</div>
    </div>

    <div class="form-group">
        <label for="stipendio">Stipendio:</label>
        <input type="text" id="stipendio" name="stipendio" value="${account.stipendio}" placeholder="Inserisci lo stipendio">
        <div class="current-value">(attuale: ${account.stipendio})</div>
    </div>

    <div class="form-group">
        <label>Sesso:</label>
        <input type="radio" name="sesso" value="m" ${account.sesso == false ? "checked" : ""}> <label>M</label>
        <input type="radio" name="sesso" value="f" ${account.sesso == true ? "checked" : ""}> <label>F</label>
        <div class="current-value">(attuale: ${account.sesso ? 'F' : 'M'})</div>
    </div>

    <div class="form-group">
        <label for="dataAssunzione">Data di Assunzione:</label>
        <input type="date" id="dataAssunzione" name="dataAssunzione" value="${account.dataDiAssunzione}">
        <div class="current-value">(attuale: ${account.dataDiAssunzione})</div>
    </div>

    <div class="form-group">
        <label for="luogoNascita">Luogo di Nascita:</label>
        <input type="text" id="luogoNascita" name="luogoNascita" value="${account.luogoNascita}" placeholder="Inserisci il luogo di nascita">
        <div class="current-value">(attuale: ${account.luogoNascita})</div>
    </div>

    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="${account.username}" placeholder="Inserisci lo username">
        <div class="current-value">(attuale: ${account.username})</div>
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="${account.email}" placeholder="Inserisci l'email">
        <div class="current-value">(attuale: ${account.email})</div>
    </div>

    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="${account.password}" placeholder="Inserisci la password">
    </div>

    <div class="form-group">
        <label for="idPermesso">Permesso:</label>
        <select id="idPermesso" name="idPermesso">
            <option value="1" ${account.idPermesso == 1 ? "selected" : ""}>Admin</option>
            <option value="2" ${account.idPermesso == 2 ? "selected" : ""}>Guest</option>
        </select>
        <div class="current-value">(attuale: ${account.idPermesso == 1 ? 'Admin' : 'Guest'})</div>
    </div>

    <button type="reset">Cancella</button>
    <button type="submit">Modifica</button>
    <a href="home.jsp">HOME</a>
</form>

</body>
</html>
