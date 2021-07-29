<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
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
        
          <div class="card-header" id="headingOwn${ownCompteur}">        
            <h2 class="mb-0 mx-0">
              <button class="btn btn-outline-primary btn-block text-left collapsed ml-0" 
                type="button" 
                data-toggle="collapse" data-target="#collapseOwn${ownCompteur}" 
                aria-expanded="false" aria-controls="collapseOwn${ownCompteur}">
                <div class="d-flex justify-content-between">
                  <span>${topo.nom}</span> 
                  <span class="text-muted">${topo.utilisateur.pseudo}</span> 
                </div>
              </button>
            </h2>
          </div>
          
          <div id="collapseOwn${ownCompteur}" class="collapse" 
            aria-labelledby="headingOwn${ownCompteur}" data-parent="#accordionOwnTopo">
            <div class="card-body">
              Date de création : <fmt:formatDate value="${topo.parutionDate}" type="date" /> <br>
              Région : ${topo.region.nom} <br>
              Lieu : ${topo.lieu} <br>
              Description : ${topo.description} <br><br>
              
              <button type="button" class="btn btn-warning mb-3" aria-label="Edition du topo"
                    onclick="window.location.href = './edition-topo?id=${topo.topoID}'">
                <i class="fas fa-edit pr-2" aria-hidden="true"></i>Editer</button>
                
              <button type="button" class="btn btn-danger mb-3" aria-label="Suppression du site"
                    onclick="window.location.href = './suppression-topo?id=${topo.topoID}'">
                <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>Supprimer</button>
                
              <%-- Liste des utilisateurs demandeurs --%>
              
                <div class="card">
                
                  <div class="card-header" id="headingDemandes${ownCompteur}">        
                    <h2 class="mb-0 mx-0">
                      <button class="btn btn-outline-info btn-block text-left collapsed ml-0" 
                        type="button" 
                        data-toggle="collapse" data-target="#collapseDemandes${ownCompteur}" 
                        aria-expanded="false" aria-controls="collapseDemandes${ownCompteur}">
                        <div class="d-flex justify-content-between">
                          <span>Demandes de prêt</span> 
                          <span class="text-muted">${topo.demandePretUtilisateurs.size()}</span> 
                        </div>
                      </button>
                    </h2>
                  </div>
                  
                  <div id="collapseDemandes${ownCompteur}" class="collapse" 
                    aria-labelledby="headingDemandes${ownCompteur}">
                    <div class="card-body">
                    
                    <ul class="mb-0">
                      <c:forEach items="${topo.demandePretUtilisateurs}" var="askingUser">
                      
                      <li> ${askingUser.pseudo} 
                      
                      <button type="button" class="btn btn-success mb-2" aria-label="Accepter"
                        onclick="window.location.href = './#?topoID=${topo.topoID}&userID=${askingUser.utilisateurID}'">
                        <i class="fas fa-check-square" aria-hidden="true"></i></button>
                        
                      <button type="button" class="btn btn-danger mb-2" aria-label="Refuser"
                        onclick="window.location.href = './#?topoID=${topo.topoID}&userID=${askingUser.utilisateurID}'">
                        <i class="fas fa-window-close" aria-hidden="true"></i></button>
                      
                      </li>
                      
                      </c:forEach>
                    </ul>
                    
                    </div>
                  </div>
                
                </div>
              
            </div>
          </div>
        
        </div>
        
      </c:forEach>
      
    </div>
  </c:if>
  
  <button type="button" class="btn btn-primary mb-3" aria-label="Edition du site"
        onclick="window.location.href = './ajout-topo'">
    <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un topo
  </button>
  
  <h3>Empruntés</h3>
  
  <h3>Demandés</h3>
  
  <%-- Liste des topos demandés --%>
  <c:if test="${not empty demandeTopos}">
    
    <div class="accordion mb-3" id="accordionDemandeTopo">
    
      <c:forEach items="${demandeTopos}" var="topo">
      <c:set var="demandeCompteur" value="${demandeCompteur+1}" scope="page" />
      
        <div class="card">
        
          <div class="card-header" id="headingDemande${demandeCompteur}">        
            <h2 class="mb-0 mx-0">
              <button class="btn btn-outline-primary btn-block text-left collapsed ml-0" 
                type="button" 
                data-toggle="collapse" data-target="#collapseDemande${demandeCompteur}" 
                aria-expanded="false" aria-controls="collapseDemande${demandeCompteur}">
                <div class="d-flex justify-content-between">
                  <span>${topo.nom}</span> 
                  <span class="text-muted">${topo.utilisateur.pseudo}</span> 
                </div>
              </button>
            </h2>
          </div>
          
          <div id="collapseDemande${demandeCompteur}" class="collapse" 
            aria-labelledby="headingDemande${demandeCompteur}" data-parent="#accordionDemandeTopo">
            <div class="card-body">
              Date de création : <fmt:formatDate value="${topo.parutionDate}" type="date" /> <br>
              Région : ${topo.region.nom} <br>
              Lieu : ${topo.lieu} <br>
              Description : ${topo.description} <br><br>
                
              <button type="button" class="btn btn-danger mb-3" aria-label="Annuler la demande de prêt"
                    onclick="window.location.href = './#?id=${topo.topoID}'">
                <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>Annuler la demande de prêt</button>
              
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
