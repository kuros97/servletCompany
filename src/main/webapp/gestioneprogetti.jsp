<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %> 
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>


<h1> Gestione Progetti </h1>
<table>
<tr>
	<td><b>NOME PROGETTO</b></td>
	<td><b>DESCRIZIONE</b></td>
	
</tr>
 
<c:forEach var="list" items="${arraypr}">
    <tr>
    	<td>${list.id}</td>
        <td>${list.nomeProgetto}</td>
        <td>${list.descrizione}</td>
        
        <td><a href="ModificaProgetto?id=${list.id}"><b>modifica</b></a></td>
       
        <td>
            <form action="EliminaProgetto" method="POST">
                <input type="hidden" name="id" value="${list.id}">
                <button type="submit"><b>elimina</b></button>
            </form>
        </td>
    </tr>
</c:forEach>

</table>

<a href="home.jsp">HOME</a>


</body>
</html>