<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Site</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

  <div class="d-flex justify-content-between mb-3">
  
    <h1 class="mb-0">Site :</h1>
    
    <%-- Affiche le btn d'édition si l'utilisateur est connecté --%>
    <c:if test="${isLoginValid}">
      <%-- Affiche le btn d'édition d'un site officel si l'utilisateur a les droits --%>
      <c:if test="${!site.officiel || site.officiel && utilisateur.role >= 1}">
        <button type="button" class="btn btn-warning my-auto" aria-label="Edition du site"
        onclick="window.location.href = './edition-site?siteID=${site.siteID}'">
          <i class="fas fa-edit pr-2" aria-hidden="true"></i>Edition
        </button>
      </c:if>
    </c:if>
    
  </div>
  
  <hr>
  
  <div class="card border-primary">
  
    <div class="card-header">
  
      <div class="row justify-content-start">
        <div class="col-auto order-2">
          <h2 class="mb-0">${site.nom}</h2>
        </div>
        <div class="col-12 col-lg-auto order-1 order-lg-3 mb-2">
          <c:if test="${site.officiel}">
            <span class="badge badge-success">
              Officiel : Les amis de l’escalade
            </span>
          </c:if>
        </div>  
      </div>
    
    </div>
    
    <div class="card-body">
    
      <div class="row justify-content-md-between">
    
        <div class="col-12 col-md-auto">
          <strong>Ville :</strong> ${site.ville.nom}
        </div>
        
        <div class="col-12 col-md-auto">
          <strong>Mis à jour :</strong>
          <fmt:formatDate value="${site.dateLastMaj}" 
            type="both" dateStyle = "medium" timeStyle="short"/>
        </div>
        
      </div>
      
      <hr>
      
      <div class="row justify-content-start">
        <div class="col-12 col-md-2"><strong>Descriptif :</strong></div>
        <div class="col-12 col-md">${site.descriptif}</div>
      </div>
      
      <div class="row justify-content-start mt-2">
        <div class="col-12 col-md-2"><strong>Accès :</strong></div>
        <div class="col-12 col-md">${site.acces}</div>
      </div>
        
    </div>
    
    <div class="card-footer">
    
      <h3>Secteurs :</h3>
    
      <%-- Avec filtrage de recherche --%>
      <c:if test="${not empty listFilterSecteursID}">
        <div class="form-check">
          <input class="form-check-input" type="checkbox" value="" checked="checked" 
            onClick="filterSecteurs()" onKeyDown="filterSecteurs()" id="checkFilter">
          <label class="form-check-label" for="checkFilter">
            Filtrer selon la recherche.
          </label>
        </div>
        
        <c:forEach items="${site.secteurs}" var="secteur">
          <div 
            class="border m-2 py-2 row
            liSecteur${fn:contains(listFilterSecteursID, secteur.secteurID) ? ' secteurResult' : ''}"
            ${fn:contains(listFilterSecteursID, secteur.secteurID) ? '' : ' hidden'}
          >
            <div class="col-12 col-lg-auto">
              <a href="./secteur?secteurID=${secteur.secteurID}">
                <strong>
                  ${secteur.nom} :
                </strong>
              </a>
            </div>
            <div class="col-12 col-lg">
              ${secteur.descriptif}
            </div>
          </div>
        </c:forEach>

      </c:if>
      
      <%-- Sans filtrage de recherche --%>
      <c:if test="${empty listFilterSecteursID}">

        <c:forEach items="${site.secteurs}" var="secteur">
          <div class="border m-2 py-2 row liSecteur">
            <div class="col-12 col-lg-auto">
              <a href="./secteur?secteurID=${secteur.secteurID}">
                <strong>
                  ${secteur.nom} :
                </strong>
              </a>
            </div>
            <div class="col-12 col-lg">
              ${secteur.descriptif}
            </div>
          </div>
        </c:forEach>

      </c:if>   
        
      <%-- Bouton pour ajouter un nouveau secteur --%>
      <c:if test="${isLoginValid}">   
        <div class="row p-2">
          <button type="button" class="btn btn-success" 
            aria-label="Ajouter un secteur" data-toggle="modal" data-target="#modalNewSecteur">
            <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un secteur
          </button>
        </div>
      </c:if>
      
    </div>
    
  </div>
  
  <%-- Section commentaires --%>
  <div class="card border-dark rounded mt-3">
    
    <%-- Comments : card-header --%>
    <div class="card-header bg-dark">
      <h4 class="mb-0">Commentaires (${commentaires.size()}) :</h4>
    </div>
    
    <%-- Comments : card-body --%>
    <div class="card-body p-3">

      <%-- Si l'utilisateur est connecté --%>
      <c:if test="${isLoginValid}">
      
        <%-- Bouton pour ajouter un commentaire --%>
        <div id="displayCommentButton" class="d-flex justify-content-center" 
          ${empty errorListCommentaire ? '' : 'style="display:none !important;"'}>
          <button type="button" class="btn btn-primary my-auto ml-0" aria-label="Ajouter un commentaire"
          onclick="displayCommentForm();">
            <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un commentaire
          </button>
        </div>
      
        <%-- Formulaire d'ajout de commentaire --%>
        <form id="commentForm" name="commentForm" method="post" action="site" 
          ${empty errorListCommentaire ? 'style="display:none !important;"' : ''}>
          <div class="card">
            <div class="card-header header-rounded-debug">
              <div class="d-flex justify-content-between">
                <strong>${utilisateur.pseudo}</strong>
              </div>
            </div>
            <div class="card-body">
            <%-- Liste des erreurs de commentaire --%>
            <c:if test="${not empty errorListCommentaire}">
              <c:forEach items="${errorListCommentaire}" var="error">
                <p class="text-danger">${error}</p>
              </c:forEach>
            </c:if>
              <textarea id="commentFormText" name="commentFormText"
                class="form-control" required minlength="1" maxlength="2000"
                aria-label="Zone de texte du commentaire">${inputedCommentaire}</textarea>
            </div>
            <%-- Stockage de l'ID du site --%>
            <input name="siteID" type="hidden" value="${site.siteID}">
            
            <div class="row justify-content-center mb-3">
            
              <%-- Bouton d'annulation d'ajout de commentaire --%>
              <button class="btn btn-danger ml-0" 
                type="button" aria-label="Annule le commentaire"
                onclick="hideCommentForm();">Annuler</button>
                
              <%-- Bouton d'envoi du formulaire --%>
              <button class="btn btn-primary" type="submit" 
                name="submit-btn" value="submit">Valider</button>
                
            </div>
          </div>
        </form>
        
      </c:if>
      
      <%-- Liste des commentaires --%>
      <c:forEach items="${commentaires}" var="commentaire">
      
        <div class="card mt-3">
        
          <div class="card-header header-rounded-debug d-flex flex-column flex-md-row justify-content-between">
          
            <div class="flex-fill my-auto">
              <strong>${commentaire.utilisateur.pseudo}</strong>
            </div>
            
            <div class="my-auto">
              <fmt:formatDate value="${commentaire.dateCreation}" 
              type="date" dateStyle="short"/>
              -
              <fmt:formatDate value="${commentaire.dateCreation}" 
              type="time" timeStyle="short"/>
            </div>
            
          </div>
          <div class="card-body">
            
            <%-- Bouton d'affichage du formulaire de modification du commentaire --%>
            <c:if test="${utilisateur.role >= 1}"> 
            
              <div class="float-right d-flex">
                 
                <div>
                  <button class="btn btn-warning pr-2" aria-label="Modifier le commentaire" 
                   type="button" onclick="displayUpdateCommentForm(${commentaire.commentaireID});">
                    <i class="fas fa-edit" aria-hidden="true"></i>
                  </button>
                </div>
                
                <%-- Formulaire de suppression d'un commentaire --%>
                <form name="deleteForm" method="post" action="site">
                  <%-- input hidden site ID commentaire ID --%>
                  <input name="siteID" type="hidden" value="${site.siteID}">
                  <input name="commentaireID" type="hidden" value="${commentaire.commentaireID}">
                  <%-- Bouton de suppression du commentaire --%>      
                  <button class="btn btn-danger" aria-label="Supprimer le commentaire"
                    type="submit" name="delete-btn" value="delete">
                    <i class="fas fa-trash-alt" aria-hidden="true"></i>
                  </button>
                </form>
                
              </div>

            </c:if>
            
            <%-- Contenu du commentaire --%>
            <div id="contentCommentID${commentaire.commentaireID}" 
              class="d-inline contentComment"
              ${not empty errorListUpdatedCommentaire 
                && not empty updatedCommentaireID 
                && updatedCommentaireID == commentaire.commentaireID 
                ? 'style="display:none !important;"' : ''}>
              ${commentaire.contenu}
            </div>
            
            <%-- Formulaire pour la modification du commentaire --%>
            <c:if test="${utilisateur.role >= 1}">
              <div id="updateCommentID${commentaire.commentaireID}" class="updateComment"
                ${not empty errorListUpdatedCommentaire 
                  && not empty updatedCommentaireID 
                  && updatedCommentaireID == commentaire.commentaireID ? '' : 'style="display:none !important;"'}>
                <%-- Liste des erreurs de modification de commentaire --%>
                <c:if test="${not empty errorListUpdatedCommentaire 
                  && commentaire.commentaireID == updatedCommentaireID}">
                  <c:forEach items="${errorListUpdatedCommentaire}" var="error">
                    <p class="text-danger">${error}</p>
                  </c:forEach>
                </c:if>
                <form name="updateFormCommentID${commentaire.commentaireID}" method="post" action="site">
                  <input name="siteID" type="hidden" value="${commentaire.site.siteID}">
                  <input name="commentaireID" type="hidden" value="${commentaire.commentaireID}">
                  <textarea name="updateFormCommentTextarea"
                      class="form-control" required minlength="1" maxlength="2000"
                      aria-label="Zone de texte du commentaire"
                      >${not empty errorListUpdatedCommentaire 
                        && not empty updatedCommentaireID 
                        && updatedCommentaireID == commentaire.commentaireID ? 
                        updatedCommentaireTextarea : commentaire.contenu}</textarea>
                  <%-- Bouton de validation de modification du commentaire --%>
                  <div class="row justify-content-center">      
                    <button class="btn btn-danger mt-3 ml-0" type="button"
                      aria-label="Annuler la modification du commentaire"
                      onclick="cancelUpdateCommentForm(${commentaire.commentaireID});">
                      Annuler
                    </button>
                    <button class="btn btn-primary mt-3" type="submit"
                      name="submit-update-btn" value="update" 
                      aria-label="Valider la modification du commentaire">
                      Valider
                    </button>
                  </div>
                </form>
              </div>
            </c:if>
          
          </div>
          
        </div>
        
      </c:forEach>
      
    </div>    
  </div>

  </div>
  
  <%-- Modal : Ajouter un nouveau secteur --%>
  <c:if test="${isLoginValid}"> 
    <div class="modal fade" id="modalNewSecteur" tabindex="-1" aria-labelledby="modalNewSecteurLabel" 
      aria-hidden="true">
      <div class="modal-dialog modal-dialog-centered">
        <div class="modal-content">
          <div class="modal-header py-0">
            <h2 class="modal-title h3 my-auto" id="modalNewSecteurLabel">Ajouter un secteur</h2>
            <button type="button" class="close my-auto" data-dismiss="modal" aria-label="Fermer">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
          <form id="newSecteurForm" name="newSecteurForm" method="post" action="ajout-secteur">
            <div class="modal-body">
            
              <%-- Stockage de l'ID du site --%>
              <input id="siteID" name="siteID" type="hidden" value="${site.siteID}">
              
              <%-- Nom du secteur --%>
              <p>Veuillez renseigner le nom du secteur (80 caractères max.) :</p>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <span class="input-group-text" id="labelNomSecteur">Nom</span>
                </div>
                <input id="secteurNom" name="secteurNom" type="text" 
                class="form-control" required maxlength="80" value="" 
                aria-label="Nom du secteur" aria-describedby="labelNomSecteur">
              </div>
              
            </div>
            <div class="modal-footer justify-content-center">
              <button type="button" class="btn btn-danger ml-0" data-dismiss="modal">Annuler</button>
              <button type="submit" class="btn btn-success" value="submit">Valider</button>
            </div>
          </form>
        </div>
      </div>
    </div>
  </c:if>
  
  <script type="text/javascript">
  
  <%-- Fonction de filtre des secteurs depuis la recherche --%>
    function filterSecteurs() {
        const liSecteurs = document.getElementsByClassName('liSecteur');
        
        for(const liSecteur of liSecteurs){
            
            if (!liSecteur.classList.contains('secteurResult')){
                
                if (checkFilter.checked == true){
                    liSecteur.hidden = true;
                } else {
                    liSecteur.hidden = false;
                }
            }
        }
     }
    
    <%-- Fonction d'affichage du formulaire d'un nouveau commentaire --%>
    function displayCommentForm() {
        document.getElementById("displayCommentButton").style.setProperty("display", "none", "important");
        document.getElementById("commentForm").style.setProperty("display", "block", "important");
        
        <%-- Reinitialisation du display global --%>
        let contentComments = Array.from(document.getElementsByClassName('contentComment'));
        contentComments.forEach(function(item, index, array) {
            item.style.setProperty("display", "flex", "important");
        });
        
        let updateComments = Array.from(document.getElementsByClassName('updateComment'));
        updateComments.forEach(function(item, index, array) {
            item.style.setProperty("display", "none", "important");
        });
    }
    
    <%-- Fonction pour cacher le formulaire d'un nouveau commentaire --%>
    function hideCommentForm() {
        document.getElementById("displayCommentButton").style.setProperty("display", "flex", "important");
        document.getElementById("commentForm").style.setProperty("display", "none", "important");
        document.getElementById("commentFormText").value = "";
    }
    
    <%-- Fonction d'affichage du formulaire de modification d'un commentaire --%>
    function displayUpdateCommentForm(id) {
        
        <%-- Reinitialisation du display global --%>
        let contentComments = Array.from(document.getElementsByClassName('contentComment'));
        contentComments.forEach(function(item, index, array) {
            item.style.setProperty("display", "flex", "important");
        });
        
        let updateComments = Array.from(document.getElementsByClassName('updateComment'));
        updateComments.forEach(function(item, index, array) {
            item.style.setProperty("display", "none", "important");
        });
        
        
        <%-- Gestion des elements spécifiques --%>
        document.getElementById("contentCommentID" + id).style.setProperty("display", "none", "important");
        document.getElementById("updateCommentID" + id).style.setProperty("display", "block", "important");
        
        hideCommentForm();
    }
    
    <%-- Fonction d'annulation de l'affichage du formulaire de modification d'un commentaire --%>
    function cancelUpdateCommentForm(id) {
        document.getElementById("contentCommentID" + id).style.setProperty("display", "flex", "important");
        document.getElementById("updateCommentID" + id).style.setProperty("display", "none", "important");
    }

   </script>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>