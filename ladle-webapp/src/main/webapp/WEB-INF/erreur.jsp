<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Erreur</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
    <div class="alert alert-danger" role="alert">
      <h1 class="alert-heading">Erreur(s):</h1>
        <hr>
        <ul>
        <c:forEach items="${errorList}" var="error">
          <li><p class="mb-0">${error}</p>
        </c:forEach>
      </ul>
    </div>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
