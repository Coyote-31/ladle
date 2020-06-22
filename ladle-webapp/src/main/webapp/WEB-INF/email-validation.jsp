<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Validation de l'email</title>
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body class="pb-3">
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container">

    <h1>Page de validation du mail.</h1>

    <%-- Validation du SHA réussie --%>
    <c:if test="${emailValide}">
      <p>Bravo ! L'addresse du compte est validée.</p>
    </c:if>

    <%-- Erreur de validation --%>
    <c:if test="${emailValide == false}">
      <p>Erreur ! Le lien de validation est expiré ou invalide.</p>
    </c:if>
    
    <%-- Erreur de login (redirection si le compte n'est pas validé) --%>
    
    <c:if test="${errorEmailSHAOnLogin}">
      <p>Erreur ! Votre compte n'est pas encore validé. <br>
      Regardez vos mails pour terminer votre inscription !</p>
    </c:if>

  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
