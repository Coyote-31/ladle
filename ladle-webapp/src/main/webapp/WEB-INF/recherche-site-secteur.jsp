<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Recherche Site/Secteur</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">

    <%-- BARRE DE RECHERCHE --%>
    <form method="post" action="recherche-site-secteur">
      <fieldset>
        <legend>Recherche de Sites &amp; Secteurs :</legend>

        <%-- Région --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectRegion">Région</label>
          </div>
          <select class="custom-select" id="inputGroupSelectRegion" name="inputGroupSelectRegion"
          onchange="this.form.formChangeOn.value = 'region'; this.form.submit();">
            <option <c:if test="${selectedRegion == 'all' || empty selectedRegion}">selected</c:if> 
            value="all">Toutes ...</option>
            <c:forEach items="${regions}" var="region">
              <option <c:if test="${selectedRegion == region.regionCode}">selected</c:if> 
              value="${region.regionCode}">
              ${region.nom} (${region.regionCode})</option>
            </c:forEach>
          </select>
        </div>

        <%-- Département --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectDepartement">Département</label>
          </div>
          <select class="custom-select" id="inputGroupSelectDepartement" name="inputGroupSelectDepartement" 
          onchange="this.form.formChangeOn.value = 'departement'; this.form.submit();">
            <option <c:if test="${selectedDepartement == 'all' || empty selectedDepartement}">selected</c:if> 
            value="all">Tous ...</option>
              <c:forEach items="${departements}" var="departement">
                <option <c:if test="${selectedDepartement == departement.departementCode}">selected</c:if> 
                value="${departement.departementCode}">
                ${departement.nom} (${departement.departementCode})</option>
              </c:forEach>
          </select>
        </div>

        <%-- Code Postal --%>
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="code-postal">Code postal</span>
          </div>
          <input type="text" class="form-control" id="inputCodePostal" name="inputCodePostal" 
          placeholder="ex. 31000" 
          maxlength="5" pattern="[0-9][0-9][0-9][0-9][0-9]" 
          oninvalid="setCustomValidity('Code postal invalide ! Il doit contenir exactement 5 chiffres (ex. 31000).')" 
          oninput="setCustomValidity('')"
          aria-label="Code Postal" aria-describedby="code-postal">
        </div>

        <%-- Ville --%>

        <%-- Cotation --%>

        <%-- Nombre de Secteurs --%>

        <%-- Officiel LADLE --%>
        
        <%-- Variables pour les maj du formulaire --%>
        
        <input type="hidden" name="formChangeOn" value="">

        <button class="btn btn-outline-primary" type="submit" 
        name="submit-btn" value="submit">Valider</button>
        
      </fieldset>
    </form>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>
</body>
</html>