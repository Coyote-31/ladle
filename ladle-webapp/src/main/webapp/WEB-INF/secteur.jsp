<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Secteur</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
  
  <div>
    Site : <a href="./site?siteID=${secteur.site.siteID}">${secteur.site.nom}</a>
  </div>

    <div class="d-flex justify-content-between mb-3">
      <h1>Secteur :</h1>
      
      <%-- Affiche le btn d'édition si l'utilisateur est connecté --%>
      <c:if test="${isLoginValid}">
        <button type="button" class="btn btn-secondary my-auto" aria-label="Edition du secteur"
        onclick="window.location.href = './edition-secteur?secteurID=${secteur.secteurID}'">
          <i class="fas fa-edit pr-2" aria-hidden="true"></i>Edition
        </button>
      </c:if>
    </div>
    
    <h2>${secteur.nom}</h2><br>
    Ville : ${secteur.site.ville.nom} <br>
    Mis à jour : <fmt:formatDate value="${secteur.dateLastMaj}" type="date" /> <br>
    Descriptif : ${secteur.descriptif} <br>
    Accès : ${secteur.acces} <br>
    
    <c:if test="${not empty secteur.planBase64}">
      <img class="img-fluid my-3" src="data:image/jpg;base64,${secteur.planBase64}" 
      width="${secteurPlanWidth}" height="${secteurPlanHeight}"
      alt="Plan du secteur.">
    </c:if>
    
    <table class="table">
        <caption>voies</caption>
        <thead class="thead-dark">
          <tr>
            <th scope="col">Numéro</th>
            <th scope="col">Cotation</th>
            <th scope="col">Nom</th>
            <th scope="col">Hauteur (m)</th>
            <th scope="col">Dégaines</th>
            <th scope="col">Remarques</th>
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
