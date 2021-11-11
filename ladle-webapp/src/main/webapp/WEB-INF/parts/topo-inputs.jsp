<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<div class="row">
  <div class="col-12 col-md-6">
  
    <%-- Région --%>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <label class="input-group-text" for="inputGroupSelectRegion">Région</label>
      </div>
      <select class="custom-select${topoRegionError ? ' is-invalid' : ''}" required
        id="inputGroupSelectRegion" name="inputGroupSelectRegion">
        <option <c:if test="${empty selectedRegion}">selected</c:if> 
        value="">...</option>
        <c:forEach items="${regions}" var="region">
          <option <c:if test="${selectedRegion == region.regionID}">selected</c:if> 
          value="${region.regionID}">
          ${region.nom} (${region.regionCode})</option>
        </c:forEach>
      </select>
    </div>
  
  </div>
  <div class="col-12 col-md-6">
  
    <%-- Lieu --%>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="labelLieu">Lieu</span>
      </div>
      <input id="inputLieu" name="inputLieu" type="text" 
      class="form-control${topoLieuError ? ' is-invalid' : ''}" 
      maxlength="80" value="${inputedTopoLieu}" 
      aria-label="Lieu" aria-describedby="labelLieu">
    </div>
  
  </div>
</div>
<div class="row">
  <div class="col-12 col-md-6">

    <%-- Nom --%>
    <div class="input-group mb-3">
      <div class="input-group-prepend">
        <span class="input-group-text" id="labelNom">Nom</span>
      </div>
      <input id="inputNom" name="inputNom" type="text" required
      class="form-control${topoNomError ? ' is-invalid' : ''}" 
      maxlength="80" value="${inputedTopoNom}" 
      aria-label="Nom" aria-describedby="labelNom">
    </div>

  </div>
  <div class="col-12 col-md-6 d-flex align-items-center mb-3">

    <%-- Disponibilité --%>
    <div class="input-group">
      <div class="custom-control custom-switch">
        <input id="checkboxDispo" name="checkboxDispo" type="checkbox" 
          class="custom-control-input"${checkedboxDispo ? " checked" : ""}>
        <label class="custom-control-label d-inline" for="checkboxDispo">Disponible</label>
      </div>
    </div>
  
  </div>
</div>

<div class="">
  <%-- Description --%>
  <label for="textareaDescription">
    <strong>Description :</strong>
  </label>
  <textarea id="textareaDescription" name="textareaDescription"
  class="form-control${topoDescriptionError ? ' is-invalid' : ''}" 
  maxlength="2000"
  aria-label="Description du topo">${textedareaDescription}</textarea>
</div>

<hr>

<%-- Btn de validation du formulaire --%>
<div class="row justify-content-center">
  <button class="btn btn-outline-primary" type="submit" 
    id="btn-submit" name="btn-submit" value="btn-submit">Valider</button>
</div>
