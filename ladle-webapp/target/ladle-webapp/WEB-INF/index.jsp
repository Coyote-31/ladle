<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Accueil</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body>
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container ladle-bg-main">
	
		<h2>Hello World !</h2>

		<p>
			<c:out value="JSTL fonctionne ! =)"></c:out>
		</p>

		<c:if test="${not empty myList}">
			<table class="table">
				<caption>Table de test des régions</caption>
				<thead class="thead-dark">
					<tr>
						<th scope="col">ID Région</th>
						<th scope="col">Nom</th>
						<th scope="col">Soundex</th>
					</tr>
				</thead>
				<c:forEach items="${myList}" var="element">
					<tr>
						<th scope="row">${element.regionID}</th>
						<td>${element.nom}</td>
						<td>${element.soundex}</td>
					</tr>
				</c:forEach>
			</table>
		</c:if>
	</div>
	
	<%@ include file="/WEB-INF/parts/footer.jsp"%>
	
</body>
</html>
