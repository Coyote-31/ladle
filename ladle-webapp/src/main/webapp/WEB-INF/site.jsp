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
      <h1>Site :</h1>
      <c:if test="${isLoginValid}">
        <c:if test="${!site.officiel || site.officiel && utilisateur.role >= 1}">
          <button type="button" class="btn btn-secondary my-auto" aria-label="Edition du site"
          onclick="window.location.href = './edition-site?siteID=${site.siteID}'">
            <i class="fas fa-edit pr-2" aria-hidden="true"></i>Edition
          </button>
        </c:if>
      </c:if>
    </div>
    
  <h2>${site.nom}</h2><br>
  Ville : ${site.ville.nom} <br>
  Mis à jour : <fmt:formatDate value="${site.dateLastMaj}" type="date" /> <br>
  Officiel : ${site.officiel ? 'Oui' : 'Non'} <br>
  Descriptif : ${site.descriptif} <br>
  Accès : ${site.acces} <br>
  
  
  <%-- Avec filtrage de recherche --%>
  <c:if test="${not empty listFilterSecteursID}">
    <div class="form-check my-3">
      <input class="form-check-input" type="checkbox" value="" checked="checked" 
        onClick="filterSecteurs()" onKeyDown="filterSecteurs()" id="checkFilter">
      <label class="form-check-label" for="checkFilter">
        Filtrer selon la recherche.
      </label>
    </div>
    
    <ul>
      <c:forEach items="${site.secteurs}" var="secteur">
        <li class="liSecteur<c:forEach items="${listFilterSecteursID}" var="filterID">
            ${filterID == secteur.secteurID ? ' secteurResult' : ''}
          </c:forEach>"
            ${fn:contains(listFilterSecteursID, secteur.secteurID) ? '' : 'hidden'}
        >
          <a href="./secteur?secteurID=${secteur.secteurID}">${secteur.nom}</a> : ${secteur.descriptif}
        </li>
      </c:forEach>
    </ul>
  </c:if>
  
  <%-- Sans filtrage de recherche --%>
  <c:if test="${empty listFilterSecteursID}">
    <ul>
      <c:forEach items="${site.secteurs}" var="secteur">
        <li class="liSecteur">
          <a href="./secteur?secteurID=${secteur.secteurID}">${secteur.nom}</a> : ${secteur.descriptif}
        </li>
      </c:forEach>
    </ul>
  </c:if>   
    
  <%-- Bouton pour ajouter un nouveau secteur --%>
  <div class="row mb-3">
    <button type="button" class="btn btn-secondary my-auto" 
      aria-label="Ajouter un secteur" data-toggle="modal" data-target="#modalNewSecteur">
      <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un secteur
    </button>
  </div>
  
  <%-- Section commentaires --%>
  <div class="container ladle-bg-main bg-secondary pb-0">
    <p>Commentaires (${commentaires.size()}) :</p>
    <c:forEach items="${commentaires}" var="commentaire">
    <div class="card mb-3">
      <div class="card-header d-flex justify-content-between">
        <div><strong>${commentaire.utilisateur.pseudo}</strong></div>
        <div>
          <fmt:formatDate value="${commentaire.dateCreation}" type="date" />
          
          <form name="deleteForm" method="post" action="site" style="display: inline">
            <%-- input hidden site ID commentaire ID --%>
            <input name="siteID" type="hidden" value="${site.siteID}">
            <input name="commentaireID" type="hidden" value="${commentaire.commentaireID}">
            <%-- Bouton de suppression du site --%>      
            <button class="btn btn-danger my-auto" aria-label="Supprimer le commentaire"
              type="submit" value="delete">
              <i class="fas fa-trash-alt" aria-hidden="true"></i>
            </button>
          </form>
          
        </div>
      </div>
      <div class="card-body">
        ${commentaire.contenu}
      </div>
    </div>
    </c:forEach>
    
    <%-- Bouton pour ajouter un commentaire --%>
    <div id="displayCommentButton" class="row mb-3" 
      ${empty errorListCommentaire ? '' : 'style="display:none"'}>
      <button type="button" class="btn btn-primary my-auto" aria-label="Ajouter un commentaire"
      onclick="displayCommentForm();">
        <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un commentaire
      </button>
    </div>
    
    <%-- Formulaire d'ajout de commentaire --%>
    <form id="commentForm" name="commentForm" method="post" action="site" 
      ${empty errorListCommentaire ? 'style="display:none"' : ''}>
      <div class="card mb-3">
        <div class="card-header d-flex justify-content-between">
          <div><strong>${utilisateur.pseudo}</strong></div>
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
        <%-- Bouton d'envoi du formulaire --%>
        <div class="row justify-content-center">
            <button class="btn btn-primary mb-3" type="submit" 
            name="submit-btn" value="submit">Valider</button>
        </div>
      </div>
    </form>
    
  </div>

  </div>
  
  <%-- Modal : Ajouter un nouveau secteur --%>
  <div class="modal fade" id="modalNewSecteur" tabindex="-1" aria-labelledby="modalNewSecteurLabel" 
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalNewSecteurLabel">Ajouter un secteur</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Fermer">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form id="newSecteurForm" name="newSecteurForm" method="post" action="ajout-secteur">
          <div class="modal-body">
          
            <%-- Stockage de l'ID du site --%>
            <input id="siteID" name="siteID" type="hidden" value="${site.siteID}">
            
            <%-- Nom du secteur --%>
            <p>Veuillez renseigner le nom du secteur (80 caractères max.)</p>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="labelNomSecteur">Nom du secteur</span>
              </div>
              <input id="secteurNom" name="secteurNom" type="text" 
              class="form-control" required maxlength="80" value="" 
              aria-label="Nom du secteur" aria-describedby="labelNomSecteur">
            </div>
            
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-primary" value="submit">Valider</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  
  <script type="text/javascript">
  
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
    
    function displayCommentForm() {
        document.getElementById("displayCommentButton").style.display="none";
        document.getElementById("commentForm").style.display="block";
    }
  
   </script>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>