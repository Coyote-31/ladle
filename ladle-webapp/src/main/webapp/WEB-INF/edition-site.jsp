<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Edition de site</title>
<link rel="icon" href="favicon.ico">
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
    
    <p>${site.ville.nom} - <fmt:formatDate value="${site.dateLastMaj}" type="date" /> </p><br>
    
    <%-- Formulaire de modification du site  --%>
    <form id="form" name="form" method="post" action="edition-site">
    
      <%-- Stockage de l'ID du site --%>
      <input id="siteID" name="siteID" type="hidden" value="${site.siteID}">
      
      <%-- Nom du site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text" id="labelNomSite">Nom du site</span>
        </div>
        <input id="siteNom" name="siteNom" type="text" 
        class="form-control${isSiteForm && site.nomErr ? ' is-invalid' : ''}" 
        required maxlength="80" value="${site.nom}" 
        aria-label="Nom du site" aria-describedby="labelNomSite">
      </div>
      
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
      
      <%-- Descriptif du site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Descriptif du site</span>
        </div>
        <textarea id="siteDescriptif" name="siteDescriptif" 
        class="form-control${isSiteForm && site.descriptifErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${site.descriptif}</textarea>
      </div>
      
      <%-- Accès au site --%>
      <div class="input-group mb-3">
        <div class="input-group-prepend">
          <span class="input-group-text">Accès au site</span>
        </div>
        <textarea id="siteAcces" name="siteAcces" 
        class="form-control${isSiteForm && site.accesErr ? ' is-invalid' : ''}" 
        maxlength="2000"
        aria-label="Zone de texte">${site.acces}</textarea>
      </div>
              

      <%-- Table avec les secteurs du site --%>
      <table class="table table-bordered" id="tableSecteur">
          <caption hidden=true>secteurs</caption>
          <thead class="thead-dark">
            <tr>
              <th id="labelSecteurNom" scope="col">Nom</th>
              <th id="labelSecteurDescriptif" scope="col">Descriptif</th>
              <th id="labelSecteurSupprimer" scope="col">Supprimer</th>
            </tr>
          </thead>
          
          <%-- Boucle sur toutes les secteurs du site --%>
          <c:forEach items="${site.secteurs}" var="secteur">
          
            <%-- Ligne du tableau représentant un secteur --%>
            
            <tr>
              <%-- Nom du secteur --%>
              <td>
                <p>${secteur.nom}</p>
              </td>
              <%-- Descriptif du secteur --%>
              <td>
                <p>${secteur.descriptif}</p>
              </td>
              
              <%-- Bouton de suppression du secteur --%>
              <td>
                <button type="button" class="btn btn-danger my-auto" aria-label="Supprimer le secteur"
                  onclick="">
                  <i class="fas fa-trash-alt" aria-hidden="true"></i>
                </button>              
              </td>
            </tr>
          </c:forEach>
        
        </table>
        
        <%-- Bouton d'envoi du formulaire --%>
        <div class="row justify-content-center">
            <button class="btn btn-primary" type="submit" 
            name="submit-btn" value="submit">Valider</button>
        </div>
      
      </form>
    
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp" %>
  
  <script type="text/javascript">
  </script>

</body>
</html>
