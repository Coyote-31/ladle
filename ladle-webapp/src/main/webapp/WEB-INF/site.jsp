<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Site</title>
<link rel="icon" href="favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

    <h1>Site :</h1>
    ${site.nom} <br>
    ${site.officiel} <br>
    <fmt:formatDate value="${site.dateLastMaj}" type="date" /> <br>
    ${site.descriptif} <br>
    ${site.ville.nom} <br>
    ${site.acces} <br>
    
    <ul>
      <c:forEach items="${site.secteurs}" var="secteur">
        <li>${secteur.nom} : ${secteur.descriptif}</li>
      </c:forEach>
    </ul>

  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>