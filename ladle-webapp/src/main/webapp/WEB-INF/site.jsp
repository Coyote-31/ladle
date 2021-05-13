<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<%@ taglib uri = "http://java.sun.com/jsp/jstl/functions" prefix = "fn" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Site</title>
<link rel="icon" href="favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">

    <div class="d-flex justify-content-between mb-3">
      <h1>Site :</h1>
      <c:if test="${!site.officiel || site.officiel && utilisateur.role >= 1}">
        <button type="button" class="btn btn-secondary my-auto" aria-label="Edition du site"
        onclick="window.location.href = './edition-site?siteID=${site.siteID}'">
          <i class="fas fa-edit pr-2" aria-hidden="true"></i>Edition
        </button>
      </c:if>
    </div>
    
    <h2>${site.nom}</h2><br>
    ${site.officiel} <br>
    <fmt:formatDate value="${site.dateLastMaj}" type="date" /> <br>
    ${site.descriptif} <br>
    ${site.ville.nom} <br>
    ${site.acces} <br>
    
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
    
  <%-- Bouton pour ajouter un nouveau secteur --%>
  <div class="row">
    <button type="button" class="btn btn-secondary my-auto" 
      aria-label="Ajouter un secteur" data-toggle="modal" data-target="#modalNewSecteur">
      <i class="fas fa-plus pr-2" aria-hidden="true"></i>Ajouter un secteur
    </button>
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
  
   </script>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>