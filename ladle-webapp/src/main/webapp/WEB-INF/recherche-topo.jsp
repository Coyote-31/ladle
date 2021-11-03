<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Recherche Topo</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp"%>

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
  
  <h1>Recherche de topos</h1>
  <hr>
  
  <%-- FORMULAIRE DE RECHERCHE --%>
  <form method="post" action="recherche-topo">
    
    <div class="row justify-content-center">
    
      <div class="col-sm-7 col-lg">
      
        <%-- Région --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectRegion">Région</label>
          </div>
          <select class="custom-select" id="inputGroupSelectRegion" name="inputGroupSelectRegion">
            <option <c:if test="${selectedRegion == 'all' || empty selectedRegion}">selected</c:if> 
            value="all">Toutes ...</option>
            <c:forEach items="${regions}" var="region">
              <option <c:if test="${selectedRegion == region.regionID}">selected</c:if> 
              value="${region.regionID}">
              ${region.nom} (${region.regionCode})</option>
            </c:forEach>
          </select>
        </div>
        
      </div>
      <div class="col-sm-7 col-lg">
          
        <%-- Pseudo --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="labelInputPseudo">Pseudo</span>
          </div>
          <input id="inputPseudo" name="inputPseudo" type="text" 
          class="form-control" 
          maxlength="30" value="${inputedPseudo}" 
          aria-label="Pseudo" aria-describedby="labelInputPseudo">
        </div>
        
      </div>
      <div class="col-sm-7 col-lg">
    
        <%-- Mots-clés --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="labelInputKeywords">Mots-clés</span>
          </div>
          <input id="inputKeywords" name="inputKeywords" type="text" 
          class="form-control" 
          maxlength="80" value="${inputedKeywords}" 
          aria-label="Mots-clés" aria-describedby="labelInputKeywords">
        </div>
    
      </div>
      
    </div>
    
    <%-- Bouton de validation --%>
    <div class="row">
      <button class="btn btn-outline-primary mx-auto" type="submit" 
          name="submit-btn" value="submit">Valider</button>
    </div>
    
  </form>
  
  <%-- Affichage d'un résultat de recherche vide --%>
  <c:if test="${emptyResult == true}">
    <hr>
    <div class="text-center">
      <strong>Aucun résultat trouvé !</strong><br>
      Veuillez réessayer avec d'autres critères.
    </div>
  </c:if>
  
  <%-- Liste de résultat de la recherche de topo --%>
  <c:if test="${emptyResult == false}">
    <hr>
    <div class="accordion border rounded border-primary" id="accordionTopo">
    
      <c:forEach items="${topos}" var="topo">
      
      <%-- N'affiche pas les topos dont l'utilisateur est le propriétaire  --%>
      <c:if test="${topo.utilisateur.pseudo != utilisateur.pseudo}">
      
        <c:set var="compteur" value="${compteur+1}" scope="page" />
        
          <div class="card">
          
            <div class="card-header" id="heading${compteur}">        
              <h2 class="mb-0 mx-0">
                <button class="btn btn-primary btn-block text-left collapsed ml-0" 
                  type="button" 
                  data-toggle="collapse" data-target="#collapse${compteur}" 
                  aria-expanded="false" aria-controls="collapse${compteur}">
                  <div class="d-flex justify-content-between">
                    <span>${topo.nom}</span> 
                    <span class="text-info">${topo.utilisateur.pseudo}</span> 
                  </div>
                </button>
              </h2>
            </div>
            
            <div id="collapse${compteur}" class="collapse" 
            aria-labelledby="heading${compteur}" data-parent="#accordionTopo">
              
              <div class="card-body">
              
                <div class="row">
                
                  <div class="col-12 col-sm-3 col-lg-2">
                    <strong>Parution</strong>
                  </div>
                  <div class="col-12 col-sm-9 col-lg-10">
                    <fmt:formatDate value="${topo.parutionDate}" 
                    type="both" dateStyle="short" timeStyle="short" />
                  </div>
                  
                  <div class="col-12 col-sm-3 col-lg-2 mt-2">
                    <strong>Région</strong>
                  </div>
                  <div class="col-12 col-sm-9 col-lg-10 mt-2">
                    ${topo.region.nom}
                  </div>
                  
                  <div class="col-12 col-sm-3 col-lg-2 mt-2">
                    <strong>Lieu</strong>
                  </div>
                  <div class="col-12 col-sm-9 col-lg-10 mt-2">
                    ${topo.lieu}
                  </div>
                  
                  <div class="col-12 col-sm-3 col-lg-2 mt-2">
                    <strong>Description</strong>
                  </div>
                  <div class="col-12 col-sm-9 col-lg-10 mt-2">
                    ${topo.description}
                  </div>
                  
                </div>
                
                <%-- Boutons : Demande / Annulation --%>
            
                <c:if test="${!topo.isDemandePret(utilisateur)}">
                  <hr>
                  <div class="row">
                    <button type="button" class="btn btn-success mx-auto" 
                      aria-label="Demande de prêt"
                      onclick="window.location.href = './demande-topo?id=${topo.topoID}'">
                      <i class="fas fa-clipboard pr-2" aria-hidden="true"></i>
                      Faire une demande de prêt
                    </button>
                  </div>
                </c:if>
                
                <c:if test="${topo.isDemandePret(utilisateur)}">
                  <hr>
                  <div class="row">
                    <button type="button" class="btn btn-danger mx-auto" 
                    aria-label="Annuler la demande de prêt"
                    onclick="window.location.href = './annule-demande-topo?topoID=${topo.topoID}'">
                      <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>
                      Annuler la demande de prêt
                    </button>
                  </div>
                </c:if>
                
                
              </div>
            </div>
          
          </div>
        </c:if>
      </c:forEach>
      
    </div>
  </c:if>
  
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>
</body>
</html>