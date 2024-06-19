<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="UTF-8">
    <title>Dipendenti e Progetti</title>
</head>
<body>
    <h2>Dipendenti Associati ai Progetti</h2>
    <table border="1">
        <tr>
            <th>Nome Dipendente</th>
            <th>Cognome Dipendente</th>
            <th>Nome Progetto</th>
        </tr>
        <c:forEach var="working" items="${workings}">
            <tr>
                <td>${working.nomeDipendente}</td>
                <td>${working.cognomeDipendente}</td>
                <td>${working.nomeProgetto}</td>
            </tr>
        </c:forEach>
    </table>
    <a href="guestMenu.jsp">HOME</a>
</body>
</html>
