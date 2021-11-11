<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Editer un topo</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
  
    <%-- Liste les erreurs du formulaire --%>
    <c:if test="${not empty errorList}">
      <div class="alert alert-danger" role="alert">
        <h1 class="alert-heading">Erreur(s):</h1>
          <hr>
          <ul>
          <c:forEach items="${errorList}" var="error">
            <li><p class="mb-0">${error}</p>
          </c:forEach>
        </ul>
      </div>
    </c:if>
    
    <h1>Formulaire d'édition du topo</h1>
    <hr>
    
    <%-- Formulaire d'édition du topo --%>
    <form method="post" action="edition-topo">
    
      <%-- Stockage de l'ID du topo --%>
      <input name="topoID" type="hidden" value="${topoID}">
    
      <%@ include file="/WEB-INF/parts/topo-inputs.jsp" %>
    
    </form>

  </div>
 

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>