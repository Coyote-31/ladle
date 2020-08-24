<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Recherche Site/Secteur</title>
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

    <h1>Résultat de la recherche :</h1>

      <c:if test="${not empty regions}">
      <table class="table">
        <caption>Table de test des régions</caption>
        <thead class="thead-dark">
          <tr>
            <th scope="col">ID Région</th>
            <th scope="col">Code</th>
            <th scope="col">Nom</th>
            <th scope="col">Soundex</th>
            <th scope="col">1st Dep</th>
          </tr>
        </thead>
        <c:forEach items="${regions}" var="region">
          <tr>
            <th scope="row">${region.regionID}</th>
            <td>${region.regionCode}</td>
            <td>${region.nom}</td>
            <td>${region.soundex}</td>
            <td>${region.departements[0].nom}</td>
          </tr>
        </c:forEach>
      </table>
    </c:if>
  
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>