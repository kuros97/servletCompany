<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Elimina Dipendente</title>
</head>
<body>
    <h2>Conferma eliminazione dipendente</h2>
    <form action="Elimina" method="post">
        <input type="hidden" name="cf" value="${cf}"/>
        <p>Sei sicuro di voler eliminare il dipendente con codice fiscale: ${cf}?</p>
        <button type="submit">Conferma</button>
        <a href="home.jsp">Annulla</a>
    </form>
</body>
</html>
