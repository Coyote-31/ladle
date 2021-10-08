<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Edition de site</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
  
    <%-- Détermine si la classe envoyée est SiteForm --%>
    <c:if test="${site['class'].simpleName == 'Site'}">
      <c:set var="isSiteForm" value="${false}" scope="page"/>
    </c:if>
    <c:if test="${site['class'].simpleName == 'SiteForm'}">
      <c:set var="isSiteForm" value="${true}" scope="page"/>
    </c:if>
    
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

    <h1>Edition du site :</h1>
    <hr>
      
    <%-- Bouton de suppression du site --%>      
    <button type="button" class="btn btn-danger mb-3 ml-0" aria-label="Supprimer le site"
      data-toggle="modal" data-target="#modalDeleteSiteID${site.siteID}">
      <i class="fas fa-trash-alt mr-1" aria-hidden="true"></i> Supprimer le site
    </button>
    
    <div class="row">
    
      <%-- Nom de la ville --%>
      <div class="input-group col-12 col-md-6 mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="villeName">Ville</span>
        </div>
        <input type="text" class="form-control" disabled
          placeholder="${site.ville.nom}" 
          aria-label="Nom de la ville" aria-describedby="villeName">
      </div>
      
      <%-- Dernière mise à jour --%>
      <div class="input-group col-12 col-md-6 mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="dateLastMaj">Mise à jour</span>
        </div>
        <input type="text" class="form-control" disabled
          placeholder=
            "<fmt:formatDate value="${site.dateLastMaj}" 
            type="both" dateStyle = "long" timeStyle = "short"/>" 
          aria-label="Date de la dernière mise à jour" aria-describedby="dateLastMaj">
      </div>
    
    </div>
    
    <%-- Formulaire de modification du site  --%>
    <form id="form" name="form" method="post" action="edition-site">
    
      <%-- Stockage de l'ID du site --%>
      <input name="siteID" type="hidden" value="${site.siteID}">
      
      <%-- Officiel LADLE (si le role est minimum 1 : Membre de l'association ) --%>
      <c:if test="${utilisateur.role >= 1}">
        <div class="input-group mb-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="labelOfficielSite">Officiel</span>
          </div>
          <div class="input-group-text">
            <input id="siteOfficiel" name="siteOfficiel" type="checkbox" 
            value="true" ${site.officiel ? 'checked' : ''}
            aria-label="Officiel LADLE" aria-describedby="labelOfficielSite">
          </div>
        </div>
      </c:if>
      
      <%-- Nom du site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="labelNomSite">Nom</span>
        </div>
        <input id="siteNom" name="siteNom" type="text" 
        class="form-control${isSiteForm && site.nomErr ? ' is-invalid' : ''}" 
        required maxlength="80" value="${site.nom}" 
        aria-label="Nom du site" aria-describedby="labelNomSite">
      </div>
      
      <%-- Descriptif du site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Descriptif</span>
        </div>
        <textarea id="siteDescriptif" name="siteDescriptif" 
        class="form-control${isSiteForm && site.descriptifErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${site.descriptif}</textarea>
      </div>
      
      <%-- Accès au site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Accès</span>
        </div>
        <textarea id="siteAcces" name="siteAcces" 
        class="form-control${isSiteForm && site.accesErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${site.acces}</textarea>
      </div>
      
      <div class="row justify-content-center">
              
        <%-- Bouton d'annulation de l'édition --%>
        <a class="btn btn-secondary ml-0" href="./site?siteID=${site.siteID}">Annuler</a>

        <%-- Bouton d'envoi du formulaire --%>
        <button class="btn btn-primary" type="submit" 
        name="submit-btn" value="submit">Valider</button>
        
      </div>
    
    </form>
              

    <%-- Les secteurs du site --%>
    <div class="card mt-3">
      
      <div class="card-header">
        <h2 class="h3 mb-0">Secteurs</h2>
      </div>
      
      <div class="card-body pb-3 px-3 pt-0">
      
        <%-- Boucle sur toutes les secteurs du site --%>
        <c:forEach items="${site.secteurs}" var="secteur">

          <div class="row no-gutters border mt-3 p-3">
          
            <%-- Nom du secteur --%>
            <div class="col col-lg-auto order-1 mr-3"><strong>${secteur.nom}</strong></div>
  
            <%-- Descriptif du secteur --%>
            <div class="col-12 col-lg order-3">${secteur.descriptif}</div>
  
            <%-- Bouton de suppression du secteur --%>
            <div class="col-auto col-lg-auto order-2 order-lg-4 mb-1 mb-lg-0 row no-gutters align-items-center">
              <button type="button" class="btn btn-danger" 
                aria-label="Supprimer le secteur"
                data-toggle="modal" data-target="#modalDeleteSecteurID${secteur.secteurID}">
                <i class="fas fa-trash-alt" aria-hidden="true"></i>
              </button>
            </div>
            
          </div>              

        </c:forEach>
        
      </div>
          
      <%-- Bouton pour ajouter un nouveau secteur --%>
      <div class="card-footer">
        <button type="button" class="btn btn-success my-auto ml-0" 
          aria-label="Ajouter un secteur" data-toggle="modal" data-target="#modalNewSecteur">
          <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un secteur
        </button>
      </div>
      
    </div>

    
  </div>
  
  <%-- Modal : Ajouter un nouveau secteur --%>
  <div class="modal fade" id="modalNewSecteur" tabindex="-1" aria-labelledby="modalNewSecteurLabel" 
    aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header py-0">
          <h3 class="modal-title my-auto" id="modalNewSecteurLabel">Ajouter un secteur</h3>
          <button type="button" class="close my-auto" data-dismiss="modal" aria-label="Fermer">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form id="newSecteurForm" name="newSecteurForm" method="post" action="ajout-secteur">
          <div class="modal-body">
          
            <%-- Stockage de l'ID du site --%>
            <input name="siteID" type="hidden" value="${site.siteID}">
            
            <%-- Nom du secteur --%>
            <p>Veuillez renseigner le nom du secteur (80 caractères max.) :</p>
            <div class="input-group mb-3">
              <div class="input-group-prepend">
                <span class="input-group-text" id="labelNomSecteur">Nom du secteur</span>
              </div>
              <input id="secteurNom" name="secteurNom" type="text" 
              class="form-control" required maxlength="80" value="" 
              aria-label="Nom du secteur" aria-describedby="labelNomSecteur">
            </div>
            
          </div>
          <div class="modal-footer d-flex justify-content-center">
            <button type="button" class="btn btn-danger ml-0" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-success" value="submit">Valider</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  
  
  <%-- Modal : Supprimer un secteur --%>
  <%-- Boucle sur toutes les secteurs du site --%>
  <c:forEach items="${site.secteurs}" var="secteur">
  
  <div class="modal fade" id="modalDeleteSecteurID${secteur.secteurID}" tabindex="-1" 
  aria-labelledby="modalDeleteSecteurID${secteur.secteurID}Label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header py-0">
          <h3 class="modal-title my-auto" id="modalDeleteSecteurID${secteur.secteurID}Label">
            Supprimer le secteur
          </h3>
          <button type="button" class="close my-auto" data-dismiss="modal" aria-label="Fermer">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form id="deleteSecteurID${secteur.secteurID}Form" name="deleteSecteurID${secteur.secteurID}Form" 
          method="post" action="supprime-secteur">
          <div class="modal-body">
          
            <%-- Stockage de l'ID du site --%>
            <input name="siteID" type="hidden" value="${site.siteID}">
          
            <%-- Stockage de l'ID du secteur --%>
            <input name="secteurID" type="hidden" value="${secteur.secteurID}">
            <p>Le secteur : <strong>${secteur.nom}</strong>, va être supprimé.<br>
              Cette action est définitive.</p>
            <p>Voulez-vous vraiment supprimer ce secteur ?</p>
            
          </div>
          <div class="modal-footer d-flex justify-content-center">
            <button type="button" class="btn btn-secondary ml-0" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-danger" value="submit">Supprimer</button>
          </div>
        </form>
      </div>
    </div>
  </div>
  
  </c:forEach>
  
  <%-- Fenetre modale de suppression du site --%>
  <div class="modal fade" id="modalDeleteSiteID${site.siteID}" tabindex="-1" 
  aria-labelledby="modalDeleteSiteID${site.siteID}Label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header py-0">
          <h3 class="modal-title my-auto" id="modalDeleteSiteID${site.siteID}Label">
            Supprimer le site
          </h3>
          <button type="button" class="close my-auto" data-dismiss="modal" aria-label="Fermer">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form id="deleteSiteID${site.siteID}Form" name="deleteSiteID${site.siteID}Form" 
          method="post" action="supprime-site">
          <div class="modal-body">
          
            <%-- Stockage de l'ID du site --%>
            <input name="siteID" type="hidden" value="${site.siteID}">

            <p>Le site : <strong>${site.nom}</strong>, va être supprimé.<br>
              Cette action est définitive.</p>
            <p>Voulez-vous vraiment supprimer ce site ?</p>
            
          </div>
          <div class="modal-footer d-flex justify-content-center">
            <button type="button" class="btn btn-secondary ml-0" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-danger" value="submit">Supprimer</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>
  
  <script type="text/javascript">
  </script>

</body>
</html>
