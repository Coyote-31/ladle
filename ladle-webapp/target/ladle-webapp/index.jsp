<!DOCTYPE html>
<html lang="fr">
<head>
<title>Accueil</title>
<%@ include file="parts/meta.jsp"%>
</head>
<body>
	<%@ include file="parts/header.jsp"%>

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

	<%@ include file="parts/footer.jsp"%>
</body>
</html>
