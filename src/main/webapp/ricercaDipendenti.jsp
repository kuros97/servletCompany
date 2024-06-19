<%@ page language="java" contentType="text/html; charset=ISO-8859-1"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" type="text/css" href="css/style.css">
<meta charset="UTF-8">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Ricerca Dipendenti</title>
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

table {
	width: 100%;
	border-collapse: collapse;
	margin-top: 20px;
}

th, td {
	border: 1px solid #333;
	padding: 10px;
	text-align: left;
}

th {
	background-color: #1f1f1f;
}

tr:hover {
	background-color: #333;
}

a, button {
	text-decoration: none;
	background-color: #00bcd4;
	color: white;
	padding: 10px 20px;
	border: none;
	cursor: pointer;
	border-radius: 5px;
	transition: background-color 0.3s, transform 0.3s;
}

a:hover, button:hover {
	background-color: #0097a7;
	transform: scale(1.05);
}

form {
	display: flex;
	justify-content: center;
	align-items: center;
	flex-wrap: wrap;
	margin: 20px 0;
}

form div {
	margin: 10px;
}

input, select {
	padding: 10px;
	border-radius: 5px;
	border: 1px solid #555;
	background-color: #1f1f1f;
	color: white;
	margin: 5px;
}

.pagination {
	text-align: center;
	margin-top: 20px;
}

.pagination a {
	color: #00bcd4;
	padding: 8px 16px;
	text-decoration: none;
	border: 1px solid #333;
	margin: 0 4px;
	border-radius: 5px;
	transition: background-color 0.3s, transform 0.3s;
}

.pagination a.active {
	background-color: #00bcd4;
	color: white;
	border: 1px solid #00bcd4;
}

.pagination a:hover:not(.active) {
	background-color: #333;
	transform: scale(1.05);
}
</style>
</head>
<body>
	<h1>Ricerca Dipendenti</h1>

	<!-- Form di ricerca -->
	<form action="Inserimento" method="get">
		<input type="hidden" name="action" value="search">
		<div>
			<label for="cf">Codice Fiscale:</label> <input type="text" id="cf"
				name="cf">
		</div>
		<div>
			<label for="nome">Nome:</label> <input type="text" id="nome"
				name="nome">
		</div>
		<div>
			<label for="cognome">Cognome:</label> <input type="text" id="cognome"
				name="cognome">
		</div>
		<div>
			<label for="luogoNascita">Luogo di Nascita:</label> <input
				type="text" id="luogoNascita" name="luogoNascita">
		</div>
		<div>
			<label for="stipendioMax">Stipendio Massimo:</label> <input
				type="text" id="stipendioMax" name="stipendioMax">
		</div>
		<div>
			<label for="permesso">Permesso:</label> <select id="permesso"
				name="permesso">
				<option value="1">Admin</option>
				<option value="2">Guest</option>
			</select>
		</div>
		<div>
			<label for="pageSize">Elementi per pagina:</label> <input type="text"
				id="pageSize" name="pageSize" value="10">
		</div>
		<div>
			<button type="submit">Ricerca</button>
		</div>
	</form>

	<table>
		<thead>
			<tr>
				<th>ID</th>
				<th>NOME</th>
				<th>COGNOME</th>
				<th>DATA DI NASCITA</th>
				<th>CODICE FISCALE</th>
				<th>STIPENDIO</th>
				<th>SESSO</th>
				<th>DATA DI ASSUNZIONE</th>
				<th>LUOGO DI NASCITA</th>
				<th>USERNAME</th>
				<th>EMAIL</th>
				<th>PERMESSO</th>
				<th>MODIFICA</th>
				<th>ELIMINA</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="list" items="${accounts}">
				<tr>
					<td>${list.id}</td>
					<td>${list.nome}</td>
					<td>${list.cognome}</td>
					<td>${list.dataDiNascita}</td>
					<td>${list.cf}</td>
					<td>${list.stipendio}</td>
					<td>${list.sesso ? 'F' : 'M'}</td>
					<td>${list.dataDiAssunzione}</td>
					<td>${list.luogoNascita}</td>
					<td>${list.username}</td>
					<td>${list.email}</td>
					<td>${list.idPermesso == 1 ? 'Admin' : 'Guest'}</td>
					<td><a href="Modifica?cf=${list.cf}">Modifica</a></td>
					<td><a href="Elimina?cf=${list.cf}">Elimina</a></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

	<div class="pagination">
		<c:if test="${totalPages > 1}">
			<nav aria-label="Page navigation">
				<ul class="pagination">
					<c:forEach begin="1" end="${totalPages}" var="i">
						<li class="page-item ${i == currentPage ? 'active' : ''}"><a
							class="page-link"
							href="Inserimento?action=search&page=${i}&pageSize=${pageSize}&cf=${param.cf}&nome=${param.nome}&cognome=${param.cognome}&luogoNascita=${param.luogoNascita}&stipendioMax=${param.stipendioMax}&permesso=${param.permesso}">${i}</a>
						</li>
					</c:forEach>
				</ul>
			</nav>
		</c:if>
	</div>

	<div style="text-align: center; margin: 20px;">
		<a href="home.jsp">HOME</a>
	</div>
</body>
</html>

