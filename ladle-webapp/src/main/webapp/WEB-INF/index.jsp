<!DOCTYPE html>
<html lang="fr">
<head>
<title>Accueil</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body class="pb-3">
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container">
	
		<h2>Hello World !</h2>

		<p>
			Server Version:
			<%=application.getServerInfo()%><br> Servlet Version:
			<%=application.getMajorVersion()%>.<%=application.getMinorVersion()%>
			JSP Version:
			<%=JspFactory.getDefaultFactory().getEngineInfo().getSpecificationVersion()%>
			<br>
		</p>

		<p>
			<c:out value="JSTL fonctionne ! =)"></c:out>
		</p>

		<c:if test="${not empty myList}">
			<table class="table">
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
