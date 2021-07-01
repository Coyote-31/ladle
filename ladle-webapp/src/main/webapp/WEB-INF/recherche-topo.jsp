<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
  
  <h1>Recherche de topos</h1>
  
  <%-- FORMULAIRE DE RECHERCHE --%>
  <form method="post" action="recherche-topo">
    
    <div class="row">
      <div class="col">
      
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
    </div>
    <div class="row">
      <div class="col">
      
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
      <div class="col">
    
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
    <div class="row mb-3">
      <button class="btn btn-outline-primary mx-auto" type="submit" 
          name="submit-btn" value="submit">Valider</button>
    </div>
    
  </form>
  
  <%-- Affichage d'un résultat de recherche vide --%>
  <c:if test="${emptyResult}">
    <div class="pb-1 mb-3 bg-primary"></div>
    <div class="text-center">
      <strong>Aucun résultat trouvé !</strong><br>
      Veuillez réessayer avec d'autres critères.
    </div>
  </c:if>
  
  <%-- Liste de résultat de la recherche de topo --%>
  <c:if test="${not emptyResult}">
    <div class="pb-1 mb-3 bg-primary"></div>
    
    <div class="accordion" id="accordionTopo">
    
      <c:forEach items="${topos}" var="topo">
      <c:set var="compteur" value="${compteur+1}" scope="page" />
      
        <div class="card">
        
          <div class="card-header" id="heading${compteur}">        
            <h2 class="mb-0 mx-0">
              <button class="btn btn-outline-primary btn-block text-left collapsed ml-0" 
                type="button" 
                data-toggle="collapse" data-target="#collapse${compteur}" 
                aria-expanded="false" aria-controls="collapse${compteur}">
                <div class="d-flex justify-content-between">
                  <span>${topo.nom}</span> 
                  <span class="text-muted">${topo.utilisateur.pseudo}</span> 
                </div>
              </button>
            </h2>
          </div>
          
          <div id="collapse${compteur}" class="collapse" 
            aria-labelledby="heading${compteur}" data-parent="#accordionTopo">
            <div class="card-body">
              Lieu : ${topo.lieu} <br>
              ${topo.description}
            </div>
          </div>
        
        </div>
        
      </c:forEach>
      
    </div>
  </c:if>
  
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>
</body>
</html>