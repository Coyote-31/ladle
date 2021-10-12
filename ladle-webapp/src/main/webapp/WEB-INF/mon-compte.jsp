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
<body>
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">

    <h1 class="text-center">Mon Compte</h1>
    
    <%-- Informations sur le compte --%>
    
    <h2>Informations personnelles</h2>
    <hr>

    <c:if test="${not empty utilisateur}">
    
      <div class="card">
      
        <div class="card-body row">
        
          <div class="col-12 col-lg-6 row">
            <div class="col-12 col-md-4">
              <strong>Pseudo</strong>
            </div>
            <div class="col-12 col-md-8">${utilisateur.pseudo}</div>
          </div>
          
          <div class="col-12 col-lg-6 row mt-2 mt-lg-0">
            <div class="col-12 col-md-4">
              <strong>Email</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.email}
            </div>
          </div>
          
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Genre</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.genre}
            </div>
          </div>
                             
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Prénom</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.prenom}
            </div>
          </div>
          
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Nom</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.nom}
            </div>
          </div>
          
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Ville</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.ville.nom}
            </div>
          </div>
          
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Date de création</strong>
            </div>
            <div class="col-12 col-md-8">
              <fmt:formatDate value="${utilisateur.dateCompte}" 
                type="both" dateStyle="medium" timeStyle="short" />
            </div>
          </div>
                    
          <div class="col-12 col-lg-6 row mt-2">
            <div class="col-12 col-md-4">
              <strong>Type</strong>
            </div>
            <div class="col-12 col-md-8">
              ${utilisateur.role == 0 ? "Utilisateur" : ""} ${utilisateur.role == 1 ? "Membre LADLE" : ""}
              ${utilisateur.role == 2 ? "Administrateur" : ""}
            </div>
          </div>
          
        </div>
      </div>
    </c:if>
    
  <%-- Topos du compte --%>
  
  <h2 class="mt-3">Topos</h2>
  <hr>
  
  <div class="card border rounded border-success">
  
    <div class="card-header bg-success">
      <h3 class="mb-0">Possédés (${ownTopos.size()})</h3>
    </div>
    
    <%-- Liste des topos possédés --%>
    <c:if test="${not empty ownTopos}">

      <div class="card-body">
  
        <div class="accordion border rounded border-primary" id="accordionOwnTopo">
  
          <c:forEach items="${ownTopos}" var="topo">
            <c:set var="ownCompteur" value="${ownCompteur+1}" scope="page" />
  
            <div class="card">
  
              <div class="card-header" id="headingOwn${ownCompteur}">
                <h2 class="mb-0 mx-0">
                  <button class="btn btn-primary btn-block text-left collapsed ml-0" type="button" data-toggle="collapse"
                    data-target="#collapseOwn${ownCompteur}" aria-expanded="false"
                    aria-controls="collapseOwn${ownCompteur}"
                  >
                    <div class="d-flex justify-content-between">
                      <span>${topo.nom}</span>
                      <%-- Si topo disponible et non-prêté et <= 0 demandes : affiche le compteur grisé --%>
                      <c:if
                        test="${topo.disponible && empty topo.pretUtilisateur 
                                && topo.demandePretUtilisateurs.size() <= 0}"
                      >
                        <span class="text-muted">Aucune demande</span>
                      </c:if>
                      <%-- Si topo disponible et non-prêté et > 0 demandes : affiche le compteur vert --%>
                      <c:if
                        test="${topo.disponible && empty topo.pretUtilisateur 
                                && topo.demandePretUtilisateurs.size() > 0}"
                      >
                        <span class="text-success">Demande(s): ${topo.demandePretUtilisateurs.size()}</span>
                      </c:if>
                      <%-- Si topo indisponible et non-prêté : affiche indisponible --%>
                      <c:if test="${!topo.disponible && empty topo.pretUtilisateur}">
                        <span class="text-danger">Indisponible</span>
                      </c:if>
                      <%-- Si topo indisponible et prêté : affiche en cours de prêt --%>
                      <c:if test="${!topo.disponible && not empty topo.pretUtilisateur}">
                        <span class="text-warning">En cours de prêt</span>
                      </c:if>
                    </div>
                  </button>
                </h2>
              </div>
  
              <div id="collapseOwn${ownCompteur}" class="collapse" aria-labelledby="headingOwn${ownCompteur}"
                data-parent="#accordionOwnTopo"
              >
                <div class="card-body">
                  Date de création :
                  <fmt:formatDate value="${topo.parutionDate}" type="both" dateStyle="short" timeStyle="short" />
                  <br>
                  Région : ${topo.region.nom}
                  <br>
                  Lieu : ${topo.lieu}
                  <br>
                  Description : ${topo.description}
                  <br>
                  <br>
  
                  <c:if test="${!topo.disponible && not empty topo.pretUtilisateur}">
                    <strong>En cours de prêt :</strong>
                    <br>
                    Pseudo : ${topo.pretUtilisateur.pseudo}
                    <br>
                    Email : ${topo.pretUtilisateur.email}
                    <br>
                    <br>
                    <button type="button" class="btn btn-danger mb-3" aria-label="Annuler le prêt"
                      onclick="window.location.href = './annule-pret-topo?topoID=${topo.topoID}'"
                    >
                      <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>
                      Annuler le prêt
                    </button>
                    <br>
                    <br>
                  </c:if>
  
                  <button type="button" class="btn btn-warning mb-3" aria-label="Edition du topo"
                    onclick="window.location.href = './edition-topo?id=${topo.topoID}'"
                  >
                    <i class="fas fa-edit pr-2" aria-hidden="true"></i>
                    Editer
                  </button>
  
                  <button type="button" class="btn btn-danger mb-3" aria-label="Suppression du topo"
                    onclick="window.location.href = './supprime-topo?id=${topo.topoID}'"
                  >
                    <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>
                    Supprimer
                  </button>
  
                  <%-- Liste des utilisateurs demandeurs --%>
                  <c:if test="${topo.demandePretUtilisateurs.size() > 0 && topo.disponible}">
                    <div class="card">
  
                      <div class="card-header" id="headingDemandes${ownCompteur}">
                        <h2 class="mb-0 mx-0">
                          <button class="btn btn-outline-info btn-block text-left collapsed ml-0" type="button"
                            data-toggle="collapse" data-target="#collapseDemandes${ownCompteur}" aria-expanded="false"
                            aria-controls="collapseDemandes${ownCompteur}"
                          >
                            <div class="d-flex justify-content-between">
                              <span>Demandes de prêt</span>
                              <span class="text-muted">${topo.demandePretUtilisateurs.size()}</span>
                            </div>
                          </button>
                        </h2>
                      </div>
  
                      <div id="collapseDemandes${ownCompteur}" class="collapse"
                        aria-labelledby="headingDemandes${ownCompteur}"
                      >
                        <div class="card-body">
  
                          <ul class="mb-0">
                            <c:forEach items="${topo.demandePretUtilisateurs}" var="askingUser">
  
                              <li>
                                ${askingUser.pseudo}
  
                                <button type="button" class="btn btn-success mb-2" aria-label="Accepter"
                                  onclick="window.location.href = './accepte-demande-topo?topoID=${topo.topoID}&userID=${askingUser.utilisateurID}'"
                                >
                                  <i class="fas fa-check-square" aria-hidden="true"></i>
                                </button>
  
                                <button type="button" class="btn btn-danger mb-2" aria-label="Refuser"
                                  onclick="window.location.href = './refuse-demande-topo?topoID=${topo.topoID}&userID=${askingUser.utilisateurID}'"
                                >
                                  <i class="fas fa-window-close" aria-hidden="true"></i>
                                </button>
  
                              </li>
  
                            </c:forEach>
                          </ul>
  
                        </div>
                      </div>
  
                    </div>
                  </c:if>
                </div>
              </div>
  
            </div>
  
          </c:forEach>
  
        </div>
  
      </div>

    </c:if>
    
    <div class="card-footer">

      <button type="button" class="btn btn-primary ml-0" aria-label="Ajouter un topo"
        onclick="window.location.href = './ajout-topo'">
        <i class="fas fa-plus pr-2" aria-hidden="true"></i>
        Ajouter un topo
      </button>
    
    </div>
    
  </div>
  
  <div class="card border rounded border-danger mt-3">
  
    <div class="card-header bg-danger">
      <h3 class="mb-0">Empruntés (${loanTopos.size()})</h3>
    </div>
  
    <%-- Liste des topos empruntés --%>
    <c:if test="${not empty loanTopos}">
    
      <div class="card-body">
    
        <div class="accordion mb-3" id="accordionLoanTopo">
        
          <c:forEach items="${loanTopos}" var="topo">
          <c:set var="loanCompteur" value="${loanCompteur+1}" scope="page" />
          
            <div class="card">
            
              <div class="card-header" id="headingLoan${loanCompteur}">        
                <h2 class="mb-0 mx-0">
                  <button class="btn btn-outline-primary btn-block text-left collapsed ml-0" 
                    type="button" 
                    data-toggle="collapse" data-target="#collapseLoan${loanCompteur}" 
                    aria-expanded="false" aria-controls="collapseLoan${loanCompteur}">
                    <div class="d-flex justify-content-between">
                      <span>${topo.nom}</span> 
                      <span class="text-info">${topo.utilisateur.pseudo}</span> 
                    </div>
                  </button>
                </h2>
              </div>
              
              <div id="collapseLoan${loanCompteur}" class="collapse" 
                aria-labelledby="headingLoan${loanCompteur}" data-parent="#accordionLoanTopo">
                <div class="card-body">
                  Date de création : 
                    <fmt:formatDate value="${topo.parutionDate}" 
                    type="both" dateStyle = "short" timeStyle = "short"/><br>
                  Région : ${topo.region.nom} <br>
                  Lieu : ${topo.lieu} <br>
                  Description : ${topo.description} <br><br>
                  
                  <strong>Propriétaire :</strong><br>
                  Pseudo : ${topo.utilisateur.pseudo}<br>
                  Email : ${topo.utilisateur.email}<br><br>
                    
                  <button type="button" class="btn btn-danger mb-3" aria-label="Annuler l'emprunt"
                        onclick="window.location.href = './annule-pret-topo?topoID=${topo.topoID}'">
                    <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>Annuler l'emprunt</button>
                  
                </div>
              </div>
            
            </div>
        
          </c:forEach>
      
        </div>
      </div>
    </c:if>
  </div>
  
  <div class="card border rounded border-warning mt-3">
  
    <div class="card-header bg-warning">
      <h3 class="mb-0">Demandés (${demandeTopos.size()})</h3>
    </div>
  
    <%-- Liste des topos demandés --%>
    <c:if test="${not empty demandeTopos}">
    
      <div class="card-body">
      
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
                  Date de création : 
                    <fmt:formatDate value="${topo.parutionDate}" 
                    type="both" dateStyle = "short" timeStyle = "short"/><br>
                  Région : ${topo.region.nom} <br>
                  Lieu : ${topo.lieu} <br>
                  Description : ${topo.description} <br><br>
                    
                  <button type="button" class="btn btn-danger mb-3" aria-label="Annuler la demande de prêt"
                        onclick="window.location.href = './annule-demande-topo?topoID=${topo.topoID}'">
                    <i class="far fa-trash-alt pr-2" aria-hidden="true"></i>Annuler la demande de prêt</button>
                  
                </div>
              </div>
            
            </div>
            
          </c:forEach>
          
        </div>
        
      </div>
    </c:if>
    
  </div>
</div>
  
  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
