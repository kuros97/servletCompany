<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="ISO-8859-1">
    <title>Web</title>
</head>
<body>
    <div class="container">
        <div class="logout">
            <a href="Logout">Logout</a>
        </div>
        <h1>Welcome <c:out value="${account.username}"/>!</h1>
        <ul>
            <c:if test="${role == 'admin'}">
                <li><a href="Inserimento">Gestione Dipendenti</a></li>
                <li><a href="inserimento.jsp">Aggiungi Nuovo Dipendente</a></li>
                <li><a href="InserimentoProgetto">Gestione Progetti</a></li>
                <li><a href="inserimentoprogetto.jsp">Aggiungi Nuovo Progetto</a></li>
                <li><a href="AssociaDipendenteProgetto">Aggiungi o Rimuovi Dipendenti dai Progetti</a></li>
            </c:if>
            <li><a href="DipendentiProgetti">Visualizza Tutti i Dipendenti e i Progetti Associati</a></li>
            <c:if test="${role == 'guest'}">
            	<li><a href="LoadGuestProfile">Visualizza il tuo profilo</a></li>
                <li><a href="UpdateGuestProfile">Modifica il tuo profilo</a></li>
            </c:if>
        </ul>
    </div>
</body>
</html>
