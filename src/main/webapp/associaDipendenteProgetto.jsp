<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
    <meta charset="UTF-8">
    <title>Associa Dipendente a Progetto</title>

</head>
<body>
    <div class="container">
        <h1>Associa Dipendente a Progetto</h1>
        <form action="AssociaDipendenteProgetto" method="post">
            <label for="idDipendente">Seleziona Dipendente:</label>
            <select id="idDipendente" name="idDipendente">
                <c:forEach var="dipendente" items="${dipendenti}">
                    <option value="${dipendente.id}">${dipendente.nome} ${dipendente.cognome}</option>
                </c:forEach>
            </select>
            <br>
            <label for="idProgetto">Seleziona Progetto:</label>
            <select id="idProgetto" name="idProgetto">
                <c:forEach var="progetto" items="${progetti}">
                    <option value="${progetto.id}">${progetto.nomeProgetto}</option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="Associa">
        </form>

        <h1>Dissocia Dipendente da Progetto</h1>
        <form action="DissociaDipendenteProgetto" method="post">
            <label for="idDipendente">Seleziona Dipendente:</label>
            <select id="idDipendente" name="idDipendente">
                <c:forEach var="dipendente" items="${dipendenti}">
                    <option value="${dipendente.id}">${dipendente.nome} ${dipendente.cognome}</option>
                </c:forEach>
            </select>
            <br>
            <label for="idProgetto">Seleziona Progetto:</label>
            <select id="idProgetto" name="idProgetto">
                <c:forEach var="progetto" items="${progetti}">
                    <option value="${progetto.id}">${progetto.nomeProgetto}</option>
                </c:forEach>
            </select>
            <br>
            <input type="submit" value="Dissocia">
        </form>

        <p style="color:red">${errorMessage}</p>
        <p style="color:green">${successMessage}</p>
        <a href="home.jsp">HOME</a>
    </div>
</body>
</html>
