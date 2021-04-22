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