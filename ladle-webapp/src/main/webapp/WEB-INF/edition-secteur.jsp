<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Edition de secteur</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp" %>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp" %>

  <div class="container ladle-bg-main">
  
    <%-- Détermine si la classe envoyée est SecteurForm --%>
    <c:if test="${secteur['class'].simpleName == 'Secteur'}">
      <c:set var="isSecteurForm" value="${false}" scope="page"/>
    </c:if>
    <c:if test="${secteur['class'].simpleName == 'SecteurForm'}">
      <c:set var="isSecteurForm" value="${true}" scope="page"/>
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

    <h1>Edition du secteur :</h1>
    <hr>
    
    <%-- Bouton de suppression du secteur --%>      
    <button type="button" class="btn btn-danger mb-3 ml-0" aria-label="Supprimer le secteur"
      data-toggle="modal" data-target="#modalDeleteSecteurID${secteur.secteurID}">
      <i class="fas fa-trash-alt" aria-hidden="true"></i> Supprimer le secteur
    </button>  
    
    <div class="row">
    
      <%-- Nom du site --%>
      <div class="input-group col-12 col-md-6 col-xl-4 mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="siteName">Site</span>
        </div>
        <input type="text" class="form-control" disabled
          placeholder="${secteur.site.nom}" 
          aria-label="Nom du site" aria-describedby="siteName">
      </div>
    
      <%-- Nom de la ville --%>
      <div class="input-group col-12 col-md-6 col-xl-4 mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="villeName">Ville</span>
        </div>
        <input type="text" class="form-control" disabled
          placeholder="${secteur.site.ville.nom}" 
          aria-label="Nom de la ville" aria-describedby="villeName">
      </div>
      
      <%-- Dernière mise à jour --%>
      <div class="input-group col-12 col-md-6 col-xl-4 mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="dateLastMaj">Mise à jour</span>
        </div>
        <input type="text" class="form-control" disabled
          placeholder=
            "<fmt:formatDate value="${secteur.dateLastMaj}" 
            type="both" dateStyle = "long" timeStyle = "short"/>" 
          aria-label="Date de la dernière mise à jour" aria-describedby="dateLastMaj">
      </div>
    
    </div>      
      
    
    <%-- Formulaire de modification du secteur  --%>
    <form id="form" name="form" method="post" action="edition-secteur" enctype="multipart/form-data">
    
      <%-- Stockage de l'ID du secteur --%>
      <input name="secteurID" type="hidden" value="${secteur.secteurID}">
      
      <%-- Nom du secteur --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="labelNomSecteur">Nom</span>
        </div>
        <input id="secteurNom" name="secteurNom" type="text" 
        class="form-control${isSecteurForm && secteur.nomErr ? ' is-invalid' : ''}" 
        required maxlength="80" value="${secteur.nom}" 
        aria-label="Nom du secteur" aria-describedby="labelNomSecteur">
      </div>
      
      <%-- Descriptif du secteur --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Descriptif</span>
        </div>
        <textarea id="secteurDescriptif" name="secteurDescriptif" 
        class="form-control${isSecteurForm && secteur.descriptifErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${secteur.descriptif}</textarea>
      </div>
      
      <%-- Accès au secteur --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Accès</span>
        </div>
        <textarea id="secteurAcces" name="secteurAcces" 
        class="form-control${isSecteurForm && secteur.accesErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${secteur.acces}</textarea>
      </div>
      
      <hr>
      
      <div class="card${isSecteurForm && secteur.planErr ? ' border-danger' : ''}">
      
        <div class="card-header">
          <h2 class="mb-0">
            <label class="mb-0" for="secteurPlan">
              Plan du secteur
            </label>
            <c:if test="${isSecteurForm && secteur.planErr}">
              <i class="fas fa-exclamation-triangle text-danger ml-2" aria-hidden="true"></i>
            </c:if>
          </h2>
        </div>
      
        <%-- Plan du secteur --%>
        <c:if test="${not empty secteur.planBase64}">
          <div class="card-body${isSecteurForm && secteur.planErr ? ' border-top border-danger' : ''}">
            <div class="text-center">
              <img class="img-thumbnail" src="data:image/jpg;base64,${secteur.planBase64}" 
                width="${secteurPlanWidth}" height="${secteurPlanHeight}"
                alt="Plan du secteur.">
            </div>
          </div>
        </c:if>
     
        <div class="card-footer${isSecteurForm && secteur.planErr ? ' border-danger' : ''}">
     
          <div class="row justify-content-between">
            <div class="col-12 col-md-auto my-auto">
              <input type="file" 
              class="form-control-file" 
              id="secteurPlan" name="secteurPlan">
            </div>
    
            <input type="hidden" id="supprimePlan" name="supprimePlan" value="false">
          
            <div class="col-12 col-md-auto mt-2 mt-md-0">
              <button type="button" class="btn btn-danger ml-0" aria-label="Supprimer le plan"
                onclick="deletePlan()">
                <i class="fas fa-trash-alt mr-2" aria-hidden="true"></i>
                Supprimer le plan
              </button>
            </div>
          </div>
        
        </div>
      
      </div>

      <hr>
      
      <div class="card">
      
        <div class="card-header">
          <h2 id="tableTitle" class="mb-0">
            Voies du secteur :
          </h2>
        </div>
        
        <div class="card-body table-responsive">

          <%-- Table avec les voies du secteur --%>
          <table id="tableVoie" 
            class="table table-hover table-light table-lg-small text-center mb-0" 
            aria-describedby="tableTitle">
            
            <thead class="thead-dark">
              <tr>
                <th id="labelNumVoie" scope="col">Numéro</th>
                <th id="labelCotationVoie" scope="col">Cotation</th>
                <th id="labelNomVoie" scope="col">Nom</th>
                <th id="labelHauteurVoie" scope="col">Hauteur(m)</th>
                <th id="labelDegaineVoie" scope="col">Dégaines</th>
                <th id="labelRemarqueVoie" scope="col" class="th-remarque">Remarques</th>
                <th id="labelSupprimerVoie" scope="col">Supprimer</th>
                <th id="labelIdVoie" scope="col" class="p-0"></th>
              </tr>
            </thead>
            
            <%-- Ajout de la variable d'itération --%>
            <c:set var="voieIteration" value="0" />
              
            <%-- Boucle sur toutes les voies du secteur --%>
            <c:forEach items="${secteur.voies}" var="voie">
            
              <%-- Incrémente la variable d'itération --%>
              <c:set var="voieIteration" value="${voieIteration + 1}" />
            
              <%-- Ligne du tableau représentant une voie --%>
              <%-- IMPORTANT : Si modifié changer aussi le javascript --%>
              <tr>
                <%-- Numéro de la voie --%>
                <td>
                  <input id="numVoie${voieIteration}" name="numVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.numeroErr ? ' is-invalid' : ''}" 
                    type="text" required value="${voie.numero}" 
                    required maxlength="6" pattern="[1-9][0-9]{0,2}(bis|ter)?"
                    oninvalid="this.setCustomValidity('Le numéro va de 1 à 999 et peut être suivi de bis ou ter. Ex: 42 ou 42bis')"
                    onchange="this.setCustomValidity('')"
                    aria-label="Numéro de la voie" aria-describedby="labelNumVoie">
                </td>
                <%-- Cotation de la voie --%>
                <td>
                  <input id="cotationVoie${voieIteration}" name="cotationVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.cotationErr ? ' is-invalid' : ''}" 
                    type="text" value="${voie.cotation}" 
                    maxlength="3" pattern="[3-9]([abc][\+]?)?"
                    oninvalid="this.setCustomValidity('La cotation va de 3 à 9 et peut être suivie de la lettre a, b ou c suivit ou non de +. Ex: 4 ou 4b+')"
                    onchange="this.setCustomValidity('')"
                    aria-label="Cotation de la voie" aria-describedby="labelCotationVoie">
                </td>
                <%-- Nom de la voie --%>
                <td>
                  <input id="nomVoie${voieIteration}" name="nomVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.nomErr ? ' is-invalid' : ''} w-auto" 
                    type="text" value="${voie.nom}" 
                    maxlength="45" aria-label="Nom de la voie" aria-describedby="labelNomVoie">
                </td>
                <%-- Hauteur de la voie --%>
                <td>
                  <input id="hauteurVoie${voieIteration}" name="hauteurVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.hauteurErr ? ' is-invalid' : ''}" 
                    type="number" value="${voie.hauteur}" 
                    maxlength="3" pattern="[1-9][0-9]{0,2}" min="1" max="999"
                    oninvalid="this.setCustomValidity('La hauteur en mètres va de 1 à 999. Ex: 42')"
                    onchange="this.setCustomValidity('')"
                    aria-label="Hauteur de la voie" aria-describedby="labelHauteurVoie">
                </td>
                <%-- Nombre de dégaines de la voie --%>
                <td>
                  <input id="degaineVoie${voieIteration}" name="degaineVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.degaineErr ? ' is-invalid' : ''}" 
                    type="number" value="${voie.degaine}" 
                    maxlength="2" pattern="([0-9])|([1-9][0-9])" min="0" max="99"
                    oninvalid="this.setCustomValidity('Le nombre de dégaines va de 0 à 99. Ex: 12')"
                    onchange="this.setCustomValidity('')"
                    aria-label="Nombre de dégaines de la voie" aria-describedby="labelDegaineVoie">
                </td>
                <%-- Remarque sur la voie --%>
                <td>
                  <textarea id="remarqueVoie${voieIteration}" name="remarqueVoie${voieIteration}" 
                    class="form-control${isSecteurForm && voie.remarqueErr ? ' is-invalid' : ''} textarea-small" 
                    maxlength="255" 
                    aria-label="Remarques sur la voie" aria-describedby="labelRemarqueVoie"
                  >${voie.remarque}</textarea>
                </td>
                
                <%-- Bouton de suppression de la voie --%>
                <td>
                  <button type="button" class="btn btn-danger ml-0" 
                    aria-label="Supprimer la voie"
                    onclick="deleteRow(this)">
                    <i class="fas fa-trash-alt" aria-hidden="true"></i>
                  </button>              
                </td>
                <td class="p-0">
                  <%-- ID de la voie en input caché --%>
                  <input id="voieID${voieIteration}" name="voieID${voieIteration}"
                    type="hidden" value="${voie.voieID}">
                 </td>
              </tr>
            </c:forEach>
          
          </table>
          
        </div>
        
        <div class="card-footer">
        
          <%-- Bouton pour ajouter une nouvelle voie --%>
          <div class="row">
            <button type="button" class="btn btn-success my-auto" 
              aria-label="Ajouter une voie"
              onclick="addNewRow()">
              <i class="fas fa-plus mr-2" aria-hidden="true"></i>
              Ajouter une voie
            </button>
          </div>
          
        </div>
        
      </div>
      
      <%-- Input hidden du nombre de voies TODO retirer ?? --%>
      <input id="nombreDeVoies" name="nombreDeVoies" 
        type="hidden" required value="${voieIteration}">
      
      <%-- Bouton d'envoi du formulaire --%>
      <div class="row mt-3 justify-content-center">
              
        <%-- Bouton d'annulation de l'édition --%>
        <a class="btn btn-secondary ml-0" href="./secteur?secteurID=${secteur.secteurID}">Annuler</a>

        <%-- Bouton d'envoi du formulaire --%>
        <button class="btn btn-primary" type="submit" 
        name="submit-btn" value="submit">Valider</button>
        
      </div>
    
    </form>
    
  </div>
  
  <%-- Fenetre modale de suppression du secteur --%>
  <div class="modal fade" id="modalDeleteSecteurID${secteur.secteurID}" tabindex="-1" 
  aria-labelledby="modalDeleteSecteurID${secteur.secteurID}Label" aria-hidden="true">
    <div class="modal-dialog modal-dialog-centered">
      <div class="modal-content">
        <div class="modal-header">
          <h5 class="modal-title" id="modalDeleteSecteurID${secteur.secteurID}Label">
          Supprimer le secteur</h5>
          <button type="button" class="close" data-dismiss="modal" aria-label="Fermer">
            <span aria-hidden="true">&times;</span>
          </button>
        </div>
        <form id="deleteSecteurID${secteur.secteurID}Form" name="deleteSecteurID${secteur.secteurID}Form" 
          method="post" action="supprime-secteur">
          <div class="modal-body">
          
            <%-- Stockage de l'ID du site --%>
            <input name="siteID" type="hidden" value="${secteur.site.siteID}">
          
            <%-- Stockage de l'ID du secteur --%>
            <input name="secteurID" type="hidden" value="${secteur.secteurID}">
            <p>Le secteur : <strong>${secteur.nom}</strong>, va être supprimé. Cette action est définitive.</p>
            <p>Voulez-vous vraiment supprimer ce secteur ?</p>
            
          </div>
          <div class="modal-footer">
            <button type="button" class="btn btn-secondary" data-dismiss="modal">Annuler</button>
            <button type="submit" class="btn btn-primary" value="submit">Supprimer</button>
          </div>
        </form>
      </div>
    </div>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>
  
  <script type="text/javascript">
    <%-- Test de la taille et de l'extension de l'image --%>
    const uploadPlan = document.getElementById("secteurPlan");
  
    uploadPlan.onchange = function() {
        const maxAllowedSize = 5 * 1024 * 1024;
        const validExtensions = ['png','jpg','jpeg'];
        const uploadPlanExt = event.target.files[0].type.replace(/(.*)\//g, '');
        
        if(this.files[0].size > maxAllowedSize){
           alert("Le plan ne doit pas dépasser 5 Mo !");
           this.value = "";
        };
        
        if(!validExtensions.includes(uploadPlanExt)){
            alert("Le plan doit être de type png, jpg ou jpeg !");
            this.value = "";
         };
    };
    
    <%-- Bouton de suppression du plan --%>
    function deletePlan() {
        document.getElementById("secteurPlan").value = '';
        document.getElementById("supprimePlan").value = 'true';
        document.forms["form"].submit();
    };
    
    <%-- Fonction de suppression d'une voie --%>
    function deleteRow(btn) {
        var row = btn.parentNode.parentNode;
        row.parentNode.removeChild(row);
    };
    
    <%-- Fonction d'ajout d'une nouvelle voie --%>
    function addNewRow() {
      
      var nombreDeVoies = document.getElementById("nombreDeVoies").value;
      document.getElementById("nombreDeVoies").value = ++nombreDeVoies;
        
      var table = document.getElementById("tableVoie");
      
      var row = table.insertRow();
      
      var cellNumero = row.insertCell(0);
      var cellCotation = row.insertCell(1);
      var cellNom = row.insertCell(2);
      var cellHauteur = row.insertCell(3);
      var cellDegaine = row.insertCell(4);
      var cellRemarque = row.insertCell(5);
      var cellSupprimer = row.insertCell(6);
      var cellVoieID = row.insertCell(7);
      
      row.cells[7].classList.add('p-0');
      
      cellNumero.innerHTML = 
          "<input id=\"numVoie" + nombreDeVoies + "\" name=\"numVoie" + nombreDeVoies + "\" class=\"form-control\""
          + "type=\"text\" required value=\"\"" 
          + "required maxlength=\"6\" pattern=\"[1-9][0-9]{0,2}(bis|ter)?\""
          + "oninvalid=\"this.setCustomValidity('Le numéro va de 1 à 999 et peut être suivi de bis ou ter. Ex: 42 ou 42bis')\""
          + "onchange=\"this.setCustomValidity('')\""
          + "aria-label=\"Numéro de la voie\" aria-describedby=\"labelNumVoie\">";
          
      cellCotation.innerHTML = 
          "<input id=\"cotationVoie" + nombreDeVoies + "\" name=\"cotationVoie" + nombreDeVoies + "\"" 
          + "class=\"form-control\"" 
          + "type=\"text\" value=\"\"" 
          + "maxlength=\"3\" pattern=\"[3-9]([abc]\\+?)?\""
          + "oninvalid=\"this.setCustomValidity('La cotation va de 3 à 9 et peut être suivie de la lettre a, b ou c suivit ou non de +. Ex: 4 ou 4b+')\""
          + "onchange=\"this.setCustomValidity('')\""
          + "aria-label=\"Cotation de la voie\" aria-describedby=\"labelCotationVoie\">";
          
      cellNom.innerHTML = 
          "<input id=\"nomVoie" + nombreDeVoies + "\" name=\"nomVoie" + nombreDeVoies + "\"" 
          + "class=\"form-control w-auto\"" 
          + "type=\"text\" value=\"\"" 
          + "maxlength=\"45\" aria-label=\"Nom de la voie\" aria-describedby=\"labelNomVoie\">";
      
      cellHauteur.innerHTML = 
          "<input id=\"hauteurVoie" + nombreDeVoies + "\" name=\"hauteurVoie" + nombreDeVoies + "\"" 
          + "class=\"form-control\"" 
          + "type=\"number\" value=\"\"" 
          + "maxlength=\"3\" pattern=\"[1-9][0-9]{0,2}\" min=\"1\" max=\"999\""
          + "oninvalid=\"this.setCustomValidity('La hauteur en mètres va de 1 à 999. Ex: 42')\""
          + "onchange=\"this.setCustomValidity('')\""
          + "aria-label=\"Hauteur de la voie\" aria-describedby=\"labelHauteurVoie\">";
      
      cellDegaine.innerHTML = 
          "<input id=\"degaineVoie" + nombreDeVoies + "\" name=\"degaineVoie" + nombreDeVoies + "\"" 
          + "class=\"form-control\"" 
          + "type=\"number\" value=\"\"" 
          + "maxlength=\"2\" pattern=\"([0-9])|([1-9][0-9])\" min=\"0\" max=\"99\""
          + "oninvalid=\"this.setCustomValidity('Le nombre de dégaines va de 0 à 99. Ex: 12')\""
          + "onchange=\"this.setCustomValidity('')\""
          + "aria-label=\"Nombre de dégaines de la voie\" aria-describedby=\"labelDegaineVoie\">";
      
      cellRemarque.innerHTML = 
          "<textarea id=\"remarqueVoie" + nombreDeVoies + "\" name=\"remarqueVoie" + nombreDeVoies + "\"" 
          + "class=\"form-control textarea-small\"" 
          + "maxlength=\"255\"" 
          + "aria-label=\"Remarques sur la voie\" aria-describedby=\"labelRemarqueVoie\">"
          + "</textarea>";
      
      cellSupprimer.innerHTML = 
          "<button type=\"button\" class=\"btn btn-danger ml-0\" aria-label=\"Supprimer la voie\""
          + "onclick=\"deleteRow(this)\">"
          + "<i class=\"fas fa-trash-alt\" aria-hidden=\"true\"></i>"
          + "</button>";
      
      cellVoieID.innerHTML =
          "<input id=\"voieID" + nombreDeVoies + "\" name=\"voieID" + nombreDeVoies + "\"" 
          + "type=\"hidden\" value=\"\">";
          
    }
    
  </script>

</body>
</html>
