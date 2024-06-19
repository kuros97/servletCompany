<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<form method="POST" action="InserimentoProgetto">
<input type = "text" name = "nomeProgetto" placeholder = "Inserisci il nome del progetto" value= "${pr.nomeProgetto}"> 
<input type = "text" name = "descrizione" placeholder = "Inserisci la descrizione del progetto"> 
<br>

<button type="submit">Invia</button>


</form>


</body>
</html>