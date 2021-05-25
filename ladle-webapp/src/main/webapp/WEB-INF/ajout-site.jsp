<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Ajouter un site</title>
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
    
    <h1>Formulaire de création d'un nouveau site</h1>
    
    <%-- Formulaire de sélection d'une ville et du nom du site --%>
    <form method="post" action="ajout-site">
    
    <p>1. Choisir une ville :</p>
    
      <%-- Ville --%>
      <div class="row">
        <div class="col">
          <div class="input-group mb-3">
  
            <div class="input-group-prepend">
              <label class="input-group-text" for="inputTextVille">Ville</label>
            </div>
            <input type="text" class="form-control${villeNameError ? ' is-invalid' : ''}" 
              id="inputTextVille" name="inputTextVille"
              value="${ not empty inputedVille ? inputedVille : '' }">
            <div class="input-group-append">
              <button class="btn btn-outline-secondary ml-0" type="submit" 
                id="btn-searchVille" name="btn-searchVille" value="btn-searchVille"
                aria-label="Recherche ville">
                <i class="fas fa-search" aria-hidden="true"></i>
              </button>
            </div>
          </div>
          
        </div>
  
        <div class="col">
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <label class="input-group-text" for="inputGroupSelectVille">Sélection</label>
            </div>
            <select class="custom-select${villeSelectError ? ' is-invalid' : ''}" 
            ${villeSelectHighlight ? ' autofocus="autofocus" ' : ''} 
            id="inputGroupSelectVille" name="inputGroupSelectVille"
              <c:if test="${empty villes}">disabled</c:if>>
              <option <c:if test="${empty selectedVille}">selected</c:if> 
              value="">...</option>
              <c:forEach items="${villes}" var="ville">
                <option <c:if test="${selectedVille == ville.villeID}">selected</c:if> 
                value="${ville.villeID}">${ville.cp} - ${ville.nom}</option>
              </c:forEach>
            </select>
          </div>
        </div>
      </div>
      
      <p>2. Choisir le nom du site (80 caractères max.) :</p>
      
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="labelNomSite">Nom du site</span>
        </div>
        <input id="siteNom" name="siteNom" type="text" 
        class="form-control${siteNameError ? ' is-invalid' : ''}" 
        maxlength="80" value="${inputedSiteName}" 
        aria-label="Nom du site" aria-describedby="labelNomSite">
      </div>
    
      <%-- Btn de validation du formulaire --%>
      <div class="row justify-content-center">
        <button class="btn btn-outline-primary" type="submit" 
          id="btn-submit" name="btn-submit" value="btn-submit">Valider</button>
      </div>
    
    </form>

  </div>
 

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>