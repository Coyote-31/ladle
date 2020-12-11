<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Secteur</title>
<link rel="icon" href="favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

    <h1>Secteur :</h1>
    
    ${secteur.nom} <br>
    <fmt:formatDate value="${secteur.dateLastMaj}" type="date" /> <br>
    ${secteur.descriptif} <br>
    ${secteur.site.ville.nom} <br>
    ${secteur.acces} <br>
    <img class="img-fluid my-3" src="data:image/jpg;base64,${secteur.plan}" 
    width="${secteurPlanWidth}" height="${secteurPlanHeight}"
    alt="Plan du secteur.">
    
    <table class="table">
        <caption>voies</caption>
        <thead class="thead-dark">
          <tr>
            <th scope="col">Numéro</th>
            <th scope="col">Cotation</th>
            <th scope="col">Nom</th>
            <th scope="col">Hauteur</th>
            <th scope="col">Dégaines</th>
            <th scope="col">remarque</th>
          </tr>
        </thead>
        <c:forEach items="${secteur.voies}" var="voie">
          <tr>
            <th scope="row">${voie.numero}</th>
            <td>${voie.cotation}</td>
            <td>${voie.nom}</td>
            <td>${voie.hauteur}</td>
            <td>${voie.degaine}</td>
            <td>${voie.remarque}</td>
          </tr>
        </c:forEach>
      </table>
    
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
