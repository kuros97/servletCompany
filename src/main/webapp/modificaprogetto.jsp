<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Modifica Progetto</title>
</head>
<body>

<h1>Modifica Progetto</h1>
<form method="POST" action="ModificaProgetto">
	<input type="text" name="id" value="${progetto.id}" readonly>
    <br>
    <input type="text" name="nomeProgetto" value="${progetto.nomeProgetto}" placeholder="Inserisci il nome del progetto">
    <br>
    <input type="text" name="descrizione" value="${progetto.descrizione}" placeholder="Inserisci la descrizione">
    <br>
    
    <button type="reset">Cancella</button>
    <button type="submit">Modifica</button>
</form>

</body>
</html>
