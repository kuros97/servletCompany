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
            justify-content: space-between;
            width: 100%;
            margin-bottom: 15px;
        }
        .form-group label {
            width: 30%;
        }
        .form-group input, .form-group select {
            width: 65%;
            padding: 10px;
            background-color: #333333;
            border: none;
            border-radius: 5px;
            color: #ffffff;
        }
        .form-group input[type="radio"] {
            width: auto;
            margin-left: 10px;
        }
        .form-group .current-value {
            width: 30%;
            text-align: right;
            color: #888888;
        }
        button, a {
            width: 40%;
            padding: 10px;
            background-color: #4caf50;
            border: none;
            border-radius: 5px;
            color: #ffffff;
            font-size: 1em;
            cursor: pointer;
            margin: 10px;
            text-decoration: none;
            text-align: center;
        }
        button:hover, a:hover {
            background-color: #45a049;
        }
        button[type="reset"] {
            background-color: #f44336;
        }
        button[type="reset"]:hover {
            background-color: #e53935;
        }
    </style>
</head>
<body>

<h1>Modifica il tuo profilo</h1>

<form action="UpdateGuestProfile" method="post">
    <input type="hidden" id="accountId" name="accountId" value="${accountDTO.id}">
    <input type="hidden" id="idDipendente" name="idDipendente" value="${accountDTO.idDipendente}">

    <div class="form-group">
        <label for="nome">Nome:</label>
        <input type="text" id="nome" name="nome" value="${accountDTO.nome}" placeholder="Inserisci il nome">
        <div class="current-value">(attuale: ${accountDTO.nome})</div>
    </div>

    <div class="form-group">
        <label for="cognome">Cognome:</label>
        <input type="text" id="cognome" name="cognome" value="${accountDTO.cognome}" placeholder="Inserisci il cognome">
        <div class="current-value">(attuale: ${accountDTO.cognome})</div>
    </div>

    <div class="form-group">
        <label for="dataNascita">Data di Nascita:</label>
        <input type="date" id="dataNascita" name="dataNascita" value="${accountDTO.dataDiNascita}">
        <div class="current-value">(attuale: ${accountDTO.dataDiNascita})</div>
    </div>

    <div class="form-group">
        <label for="cf">Codice Fiscale:</label>
        <input type="text" id="cf" name="cf" value="${accountDTO.cf}" readonly>
        <div class="current-value">(attuale: ${accountDTO.cf} non modificabile)</div>
    </div>

    <div class="form-group">
        <label for="stipendio">Stipendio:</label>
        <input type="text" id="stipendio" name="stipendio" value="${accountDTO.stipendio}" readonly>
        <div class="current-value">(attuale: ${accountDTO.stipendio} non modificabile)</div>
    </div>

    <div class="form-group">
        <label>Sesso:</label>
        <input type="radio" name="sesso" value="m" ${accountDTO.sesso == false ? "checked" : ""}> <label>M</label>
        <input type="radio" name="sesso" value="f" ${accountDTO.sesso == true ? "checked" : ""}> <label>F</label>
        <div class="current-value">(attuale: ${accountDTO.sesso ? 'F' : 'M'})</div>
    </div>

    <div class="form-group">
        <label for="dataAssunzione">Data di Assunzione:</label>
        <input type="date" id="dataAssunzione" name="dataAssunzione" value="${accountDTO.dataDiAssunzione}">
        <div class="current-value">(attuale: ${accountDTO.dataDiAssunzione})</div>
    </div>

    <div class="form-group">
        <label for="luogoNascita">Luogo di Nascita:</label>
        <input type="text" id="luogoNascita" name="luogoNascita" value="${accountDTO.luogoNascita}" placeholder="Inserisci il luogo di nascita">
        <div class="current-value">(attuale: ${accountDTO.luogoNascita})</div>
    </div>

    <div class="form-group">
        <label for="username">Username:</label>
        <input type="text" id="username" name="username" value="${accountDTO.username}" placeholder="Inserisci lo username">
        <div class="current-value">(attuale: ${accountDTO.username})</div>
    </div>

    <div class="form-group">
        <label for="email">Email:</label>
        <input type="text" id="email" name="email" value="${accountDTO.email}" placeholder="Inserisci l'email">
        <div class="current-value">(attuale: ${accountDTO.email})</div>
    </div>

    <div class="form-group">
        <label for="password">Password:</label>
        <input type="password" id="password" name="password" value="${accountDTO.password}" placeholder="Inserisci la password">
    </div>

    <button type="reset">Cancella</button>
    <button type="submit">Modifica</button>
    <a href="home.jsp">HOME</a>
</form>


</body>
</html>
