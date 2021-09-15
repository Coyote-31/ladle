<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Accueil</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
  
    <%-- Logo LADLE & présentation de l'association --%>
    <div class="card mb-3">
      <div class="row no-gutters">
      
        <div class="col-md-4 my-auto">
          <img src="images/ladle_logo.png" width="300" height="168"
            class="card-img-top img-fluid mx-auto d-block w-auto p-3" 
            alt="Logo de l'association LADLE : Les amis de l'escalade." />
        </div>
        
        <div class="col-md-8">
          <div class="card-body h-100">
            <blockquote class="blockquote h-100 my-md-4">
              <p>Bonjour, nous sommes une association qui réunit les passionnés 
                d’escalade dans toute la France.</p>
              <footer class="blockquote-footer">Association LADLE : <br>
                <cite title="Source Title">Les Amis De L'Escalade</cite>
              </footer>
            </blockquote>
          </div>
        </div>
        
      </div>
    </div>
    
    <%-- Titre de l'actualité --%>
    <div class="card mt-4">
      <h1 id="actuCard" class="bg-white my-3 mx-4">Les 3 derniers secteurs actualisés :</h1>
    </div>
      
    <%-- Affiche les secteurs --%>
    <c:forEach items="${secteurs}" var="secteur">
    
      <div class="card border-primary mt-4">
      
        <%-- En-tête du secteur --%>
        <div class="card-header bg-primary text-white row mx-0">
          <h2 class="my-auto col">${secteur.nom}</h2>
          <div class="my-auto col-lg-auto">${secteur.site.ville.nom}</div>
          <div class="my-auto col-lg-auto text-lg-center">
            Mis à jour :<br>
            <fmt:formatDate value="${secteur.dateLastMaj}" 
            type="date" dateStyle="short"/>
            à <fmt:formatDate value="${secteur.dateLastMaj}" 
            type="time" timeStyle="short"/>
          </div>
        </div>
        
        <%-- Image du plan du secteur --%>
        <c:if test="${not empty secteur.planBase64}">
        
          <div class="border-bottom border-primary text-center">
            <img class="img-fluid mx-auto" 
            src="data:image/jpg;base64,${secteur.planBase64}" 
            width="${secteur.planWidth}" height="${secteur.planHeight}"
            alt="Plan du secteur.">
          </div>
          
        </c:if>
        
        <%-- Informations sur le secteur --%>
        <div class="card-body">
          
          <%-- Descriptif --%>
          <div class="row">     
            <div class="col-md-2 my-2 text-center text-md-right">
              <strong>Descriptif :</strong>
            </div>            
            <div class="col-md my-2">
              ${secteur.descriptif}
            </div>          
          </div>
          
          <%-- Accès --%>
          <div class="row">
            <div class="col-md-2 my-2 text-center text-md-right">
              <strong>Accès :</strong>
            </div>
            <div class="col-md my-2">
              ${secteur.acces}
            </div>
          </div>
          
          <%-- Bouton vers la page d'affichage du secteur --%>
          <div class="d-flex justify-content-center mt-3">
            <a href="./secteur?secteurID=${secteur.secteurID}" 
            class="btn btn-primary ml-0">Voir</a>
          </div>

        </div>
      </div>
    
    </c:forEach>
      
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
