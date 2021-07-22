<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Mon Compte</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body class="pb-3">
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">

    <h1 class="text-center">Mon Compte</h1>
    
    <%-- Informations sur le compte --%>
    
    <h2>Informations personnelles</h2>

    <c:if test="${not empty utilisateur}">
      <div class="row">
        <div class="col">
          <table class="table">
            <tr>
              <th scope="row">Pseudo</th>
              <td>${utilisateur.pseudo}</td>
            </tr>
            <tr>
              <th scope="row">Genre</th>
              <td>${utilisateur.genre}</td>
            </tr>
            <tr>
              <th scope="row">Nom</th>
              <td>${utilisateur.nom}</td>
            </tr>
            <tr>
              <th scope="row">Prénom</th>
              <td>${utilisateur.prenom}</td>
            </tr>
          </table>
        </div>
        
        <div class="col">
          <table class="table">
            <tr>
              <th scope="row">Ville</th>
              <td>${utilisateur.ville.nom}</td>
            </tr>
            <tr>
              <th scope="row">Email</th>
              <td>${utilisateur.email}</td>
            </tr>
            <tr>
              <th scope="row">Type</th>
              <td>
                ${utilisateur.role == 0 ? "Utilisateur" : ""}
                ${utilisateur.role == 1 ? "Membre LADLE" : ""}
                ${utilisateur.role == 2 ? "Administrateur" : ""}
              </td>
            </tr>
            <tr>
              <th scope="row">Date de création</th>
              <td><fmt:formatDate value="${utilisateur.dateCompte}" type="date" /></td>
            </tr>
          </table>
        </div>
      </div>
    </c:if>
    
  <%-- Topos du compte --%>
  
  <h2>Topos</h2>
  
  <h3>Possédés</h3>
  
  <%-- Liste des topos possédés --%>
  <c:if test="${not empty ownTopos}">
    
    <div class="accordion mb-3" id="accordionOwnTopo">
    
      <c:forEach items="${ownTopos}" var="topo">
      <c:set var="ownCompteur" value="${ownCompteur+1}" scope="page" />
      
        <div class="card">
        
          <div class="card-header" id="heading${ownCompteur}">        
            <h2 class="mb-0 mx-0">
              <button class="btn btn-outline-primary btn-block text-left collapsed ml-0" 
                type="button" 
                data-toggle="collapse" data-target="#collapse${ownCompteur}" 
                aria-expanded="false" aria-controls="collapse${ownCompteur}">
                <div class="d-flex justify-content-between">
                  <span>${topo.nom}</span> 
                  <span class="text-muted">${topo.utilisateur.pseudo}</span> 
                </div>
              </button>
            </h2>
          </div>
          
          <div id="collapse${ownCompteur}" class="collapse" 
            aria-labelledby="heading${ownCompteur}" data-parent="#accordionOwnTopo">
            <div class="card-body">
              Lieu : ${topo.lieu} <br>
              ${topo.description}
            </div>
          </div>
        
        </div>
        
      </c:forEach>
      
    </div>
  </c:if>
  
  <button type="button" class="btn btn-secondary mb-3" aria-label="Edition du site"
        onclick="window.location.href = './ajout-topo'">
    <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un topo
  </button>
  
  <h3>Empruntés</h3>
  
  <h3>Demandés</h3>
 
    
  </div>
  
  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
