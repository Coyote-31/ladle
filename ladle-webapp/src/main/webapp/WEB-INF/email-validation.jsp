<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Validation de l'email</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

    <h1>Validation du mail</h1>
    <hr>

    <%-- Validation du SHA réussie --%>
    <c:if test="${emailValide}">
      <p class="text-success">Bravo ! L'addresse du compte est validée.</p>
    </c:if>

    <%-- Erreur de validation --%>
    <c:if test="${emailValide == false}">
      <p class="text-danger">Erreur ! Le lien de validation est expiré ou invalide.</p>
    </c:if>

  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
