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

    <div class="d-flex justify-content-between mb-3">
      
      <h1 class="mb-0">Secteur :</h1>
      
      <%-- Affiche le btn d'édition si l'utilisateur est connecté --%>
      <c:if test="${isLoginValid}">
        <button type="button" class="btn btn-warning my-auto" aria-label="Edition du secteur"
        onclick="window.location.href = './edition-secteur?secteurID=${secteur.secteurID}'">
          <i class="fas fa-edit pr-2" aria-hidden="true"></i>Edition
        </button>
      </c:if>
      
    </div>
    
    <hr>
    
    <div class="card border-primary">
    
      <div class="card-header">
    
        <h2 class="mb-0">${secteur.nom}</h2>

      </div> 
      <div class="card-body">
      
        <div class="row justify-content-md-between">
        
          <div class="col-12 col-lg-auto">
            <strong>Site :</strong>
            <a href="./site?siteID=${secteur.site.siteID}">${secteur.site.nom}</a>
          </div>
          
          <div class="col-12 col-lg-auto">
            <strong>Ville :</strong>
            ${secteur.site.ville.nom}
          </div>
          
          <div class="col-12 col-lg-auto">
            <strong>Mis à jour :</strong>
            <fmt:formatDate value="${secteur.dateLastMaj}" 
              type="both" dateStyle = "medium" timeStyle = "short"/>
          </div>
            
        </div>
        
        <hr>
        
        <div class="row justify-content-start">
          <div class="col-12 col-md-2"><strong>Descriptif :</strong></div>
          <div class="col-12 col-md">${secteur.descriptif}</div>
        </div>
        
        <div class="row justify-content-start mt-2">
          <div class="col-12 col-md-2"><strong>Accès :</strong></div>
          <div class="col-12 col-md">${secteur.acces}</div>
        </div>
    
        <c:if test="${not empty secteur.planBase64}">
          <hr>
          <div class="text-center">
            <img class="img-thumbnail" 
              src="data:image/jpg;base64,${secteur.planBase64}" 
              width="${secteurPlanWidth}" height="${secteurPlanHeight}"
              alt="Plan du secteur.">
          </div>
        </c:if>
      
      </div>
      <div class="card-footer">
      
        <h3 id="voiesTitle">Voies du secteur :</h3>
        
        <c:if test="${empty secteur.voies}">
          <p>
            <strong>Il n'y a aucune voie pour ce secteur...</strong><br>
            Pensez à éditer le secteur pour rajouter des voies !
          </p>
        </c:if>
        
        <c:if test="${!empty secteur.voies}">
          <div class="card table-responsive border-top-0 mb-2">
          
            <table class="table table-hover table-md-small text-center mb-0" 
              aria-describedby="voiesTitle">
  
              <thead>
                <tr>
                  <th scope="col">Numéro</th>
                  <th scope="col">Cotation</th>
                  <th scope="col" class="th-nom">Nom</th>
                  <th scope="col">Hauteur</th>
                  <th scope="col">Dégaines</th>
                  <th scope="col" class="text-left th-remarque">Remarques</th>
                </tr>
              </thead>
  
              <c:forEach items="${secteur.voies}" var="voie">
                <tr>
                  <th class="pl-2" scope="row">
                    ${voie.numero}
                  </th>
                  <td class="text-center">
                    ${voie.cotation}
                  </td>
                  <td>${voie.nom}</td>
                  <td>${voie.hauteur} m</td>
                  <td>${voie.degaine}</td>
                  <td class="text-left">
                    ${voie.remarque}
                  </td>
                </tr>
              </c:forEach>
  
            </table>
            
          </div>
        </c:if>
      
      </div>
        
    </div>
    
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
