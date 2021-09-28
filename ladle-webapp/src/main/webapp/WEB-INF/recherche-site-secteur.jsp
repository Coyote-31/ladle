<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Recherche Site/Secteur</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body>
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">
  
    <h1>Recherche de Sites &amp; Secteurs :</h1>
    <hr>

    <%-- BARRE DE RECHERCHE --%>
    <form method="post" action="recherche-site-secteur">

      <div class="form-row">
      
        <%-- Région --%>
        <div class="input-group mb-3 col-lg-6">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectRegion">Région</label>
          </div>
          <select class="custom-select" id="inputGroupSelectRegion" name="inputGroupSelectRegion"
          oninput="this.form.formChangeOn.value = 'region'; this.form.submit();">
            <option <c:if test="${selectedRegion == 'all' || empty selectedRegion}">selected</c:if> 
            value="all">Toutes ...</option>
            <c:forEach items="${regions}" var="region">
              <option <c:if test="${selectedRegion == region.regionCode}">selected</c:if> 
              value="${region.regionCode}">
              ${region.nom} (${region.regionCode})</option>
            </c:forEach>
          </select>
        </div>
  
        <%-- Département --%>
        <div class="input-group mb-3 col-lg-6">
          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectDepartement">Département</label>
          </div>
          <select class="custom-select" id="inputGroupSelectDepartement" name="inputGroupSelectDepartement" 
          oninput="this.form.formChangeOn.value = 'departement'; this.form.submit();">
            <option <c:if test="${selectedDepartement == 'all' || empty selectedDepartement}">selected</c:if> 
            value="all">Tous ...</option>
              <c:forEach items="${departements}" var="departement">
                <option <c:if test="${selectedDepartement == departement.departementCode}">selected</c:if> 
                value="${departement.departementCode}">
                ${departement.departementCode} - ${departement.nom}</option>
              </c:forEach>
          </select>
        </div>
        
        <div class="col-12">
          <hr class="mt-0">
        </div>
        
        <p id="selectVilleHelp" class="form-text text-muted col-12">
            <strong>Sélection</strong> d'une ville à partir de son code postal ou de son nom :
        </p>
        
        <%-- Code Postal --%>
        <div class="input-group mb-3 col-sm-5 col-md-4 col-lg-3">
          <div class="input-group-prepend">
            <span class="input-group-text" id="code-postal">Code postal</span>
          </div>
          <input type="text" 
          class="form-control${
          cpIsValid == true ? ' is-valid' : '' 
          }${ cpIsValid == false ? ' is-invalid' : '' }"
          id="inputCodePostal" name="inputCodePostal" 
          placeholder="ex. 31000"
          <c:if test="${not empty inputedCodePostal}">value="${inputedCodePostal}"</c:if>
          maxlength="5" pattern="[0-9][0-9][0-9][0-9][0-9]"
          oninput="codePostalSender(this.form);"
          aria-label="Code Postal" aria-describedby="code-postal">
        </div>

        <%-- Ville : Recherche --%>
        <div class="input-group mb-3 col-sm">

          <div class="input-group-prepend">
            <label class="input-group-text" for="inputTextVille">Ville</label>
          </div>
          <input type="text" class="form-control${
            villeNameError ? ' is-invalid' : '' }" id="inputTextVille" name="inputTextVille"
            value="${ not empty inputedVille ? inputedVille : '' }"
            onchange="this.form.formChangeOn.value = 'searchVille';">
          <div class="input-group-append">
            <button class="btn btn-outline-secondary ml-0" type="button" 
              id="btnSearchVille" name="btnSearchVille" aria-label="Recherche ville"
              onclick="this.form.formChangeOn.value = 'searchVille'; this.form.submit();">
              <i class="fas fa-search" aria-hidden="true"></i>
            </button>
          </div>
          
        </div>
 
        <%-- Ville : Select --%>
        <div class="input-group mb-3 col-lg">

          <div class="input-group-prepend">
            <label class="input-group-text" for="inputGroupSelectVille">Sélection</label>
          </div>
          <select class="custom-select" ${ villeSelectHighlight ? ' autofocus="autofocus" ' : ''} 
          id="inputGroupSelectVille" name="inputGroupSelectVille"
          aria-describedby="selectVilleHelp"
          oninput="this.form.formChangeOn.value = 'selectVille'; this.form.submit();"
            <c:if test="${empty villes}">disabled</c:if>>
            <option <c:if test="${empty selectedVille}">selected</c:if> 
            value="all">Toutes ...</option>
            <c:forEach items="${villes}" var="ville">
              <option <c:if test="${selectedVille == ville.villeID}"
              >selected</c:if> value="${ville.villeID}">${ville.cp} - ${ville.nom}</option>
            </c:forEach>
          </select>
          
        </div>
        
        <div class="col-12">
          <hr class="mt-0">
        </div>
  
        <%-- Cotation --%>
        <div class="input-group mb-3 col-lg-6 row no-gutters">
          
          <div class="col-12 col-sm-8">
          
           <div class="d-flex flex-row">
            
              <div class="input-group-prepend">
                <span class="input-group-text no-radius-right">
                  Cotation
                </span>
              </div>
              
              <select class="custom-select no-radius-left no-radius-right-sm no-border-right-sm" 
              id="inputGroupSelectCotaEqual" 
              name="inputGroupSelectCotaEqual" 
              aria-label="Signe du comparateur de cotation">
                <option ${empty selectedCotaEqual or selectedCotaEqual == "all" ? "selected":""}
                value="all">Indifférent ...</option>
                <option ${selectedCotaEqual == "supEq" ? "selected":""}
                value="supEq">Supérieure ou égale à :</option>
                <option ${selectedCotaEqual == "infEq" ? "selected":""}
                value="infEq">Inférieur ou égale à :</option>
                <option ${selectedCotaEqual == "equal" ? "selected":""}
                value="equal">Égale à :</option>
              </select>
              
            </div>
            
          </div>
          <div class="col-6 col-sm">
          
            <select class="custom-select no-radius-right no-radius-left-sm border-right-0" 
            id="inputGroupSelectCotaNum"  
            name="inputGroupSelectCotaNum" 
            aria-label="Comparateur de cotation : nombre">
              <option ${empty selectedCotaNum or selectedCotaNum == "3" ? "selected":""}
                value="3">3</option>
              <option ${selectedCotaNum == "4" ? "selected":""}
                value="4">4</option>
              <option ${selectedCotaNum == "5" ? "selected":""}
                value="5">5</option>
              <option ${selectedCotaNum == "6" ? "selected":""}
                value="6">6</option>
              <option ${selectedCotaNum == "7" ? "selected":""}
                value="7">7</option>
              <option ${selectedCotaNum == "8" ? "selected":""}
                value="8">8</option>
              <option ${selectedCotaNum == "9" ? "selected":""}
                value="9">9</option>
            </select>
          
          </div>
          <div class="col-6 col-sm">
              
            <select class="custom-select no-radius-left" 
            id="inputGroupSelectCotaChar" 
            name="inputGroupSelectCotaChar" 
            aria-label="Comparateur de cotation : lettre">
              <option ${empty selectedCotaChar or selectedCotaChar == "a" ? "selected":""}
                value="a">a</option>
              <option ${selectedCotaChar == "a+" ? "selected":""}
                value="a+">a+</option>
              <option ${selectedCotaChar == "b"  ? "selected":""}
                value="b">b</option>
              <option ${selectedCotaChar == "b+" ? "selected":""}
                value="b+">b+</option>
              <option ${selectedCotaChar == "c"  ? "selected":""}
                value="c">c</option>
              <option ${selectedCotaChar == "c+" ? "selected":""}
                value="c+">c+</option>
            </select>
            
          </div>
          
        </div>
  
        <%-- Nombre de Secteurs --%>
        <div class="input-group mb-3 col-lg-6 row no-gutters">
          
          <div class="col-12 col-sm-9">
          
            <div class="d-flex flex-row">
          
              <div class="input-group-prepend">
                <span class="input-group-text no-radius-right">Nbr de secteurs</span>
              </div>
              <select class="custom-select no-radius-left no-radius-right-sm no-border-right-sm" 
              id="inputGroupSelectSectEqual" 
              name="inputGroupSelectSectEqual" 
              aria-label="Signe du comparateur du nombre de secteurs">
                <option ${empty selectedSectEqual or selectedSectEqual == "supEq" ? "selected":""}
                value="supEq">Supérieure ou égale à :</option>
                <option ${selectedSectEqual == "infEq" ? "selected":""}
                value="infEq">Inférieur ou égale à :</option>
                <option ${selectedSectEqual == "equal" ? "selected":""}
                value="equal">Égale à :</option>
              </select>
              
            </div>
          
          </div>
          <div class="col-12 col-sm-3">
          
            <select class="custom-select no-radius-left-sm" 
            id="inputGroupSelectSectNum"  
            name="inputGroupSelectSectNum" 
            aria-label="Comparateur du nombre de secteurs : nombre">
              <option ${empty selectedSectNum or selectedSectNum == "0" ? "selected":""}
                value="0">0</option>
              <option ${selectedSectNum == "1" ? "selected":""}
                value="1">1</option>
              <option ${selectedSectNum == "2" ? "selected":""}
                value="2">2</option>
              <option ${selectedSectNum == "3" ? "selected":""}
                value="3">3</option>
              <option ${selectedSectNum == "4" ? "selected":""}
                value="4">4</option>
              <option ${selectedSectNum == "5" ? "selected":""}
                value="5">5</option>
              <option ${selectedSectNum == "6" ? "selected":""}
                value="6">6</option>
              <option ${selectedSectNum == "7" ? "selected":""}
                value="7">7</option>
              <option ${selectedSectNum == "8" ? "selected":""}
                value="8">8</option>
              <option ${selectedSectNum == "9" ? "selected":""}
                value="9">9</option>
            </select>
          
          </div>
          
        </div>
  
        <%-- Site d'escalade Officiel --%>
        
        <div class="input-group mb-3 col-lg-4 mx-auto">
          <div class="input-group-prepend">
            <span class="input-group-text" id="">Site officiel</span>
          </div>
          <select class="custom-select w-auto" 
          id="inputGroupSelectOfficiel" 
          name="inputGroupSelectOfficiel" 
          aria-label="Définit si le site doit être officiel">
            <option ${empty selectedOfficiel or selectedOfficiel == "all" ? "selected":""}
            value="all">Indifférent...</option>
            <option ${selectedOfficiel == "yes" ? "selected":""}
            value="yes">Oui</option>
            <option ${selectedOfficiel == "no" ? "selected":""}
            value="no">Non</option>
          </select>
        </div>
        
      </div>
      
      <%-- Variables pour les maj du formulaire --%>
      
      <input type="hidden" name="formChangeOn" value="">
      
      <hr class="mt-0">
      
      <div class="row">
        <button class="btn btn-outline-primary mx-auto" type="submit" 
        name="submit-btn" value="submit">
          Valider
        </button>
      </div>
        
    </form>
  </div>
 
  <%-- Affichage des résultats de la recherche --%>
  <%-- 
        Avec en index : 0 = Region
                        1 = Departement
                        2 = Ville
                        3 = Site
                        4 = Secteur
  --%>
  <c:if test="${not empty searchResultSecteurs}">
    <div class="container ladle-bg-main">
    
      <h1>Résultat de la recherche :</h1>
      
      <%-- Boucle des Régions --%>
      <c:forEach items="${searchResultRegions}" var="searchResultRegion">
      <div class="container ladle-bg-main">
        <h2>${searchResultRegion.nom} (${searchResultRegion.regionCode})</h2>
      
        <%-- Boucle des Départements --%>
        <c:forEach items="${searchResultDepartements}" var="searchResultDepartement">
          <c:if test="${searchResultRegion.regionCode == searchResultDepartement.region.regionCode}">
            <div class="container ladle-bg-main">
              <h3>${searchResultDepartement.nom} (${searchResultDepartement.departementCode})</h3>
          
          <%-- Boucle des Villes --%>
          <c:forEach items="${searchResultVilles}" var="searchResultVille">
            <c:if test="${searchResultDepartement.departementCode == searchResultVille.departement.departementCode}">
              <div class="container ladle-bg-main">
                <h4>${searchResultVille.nom} (${searchResultVille.cp})</h4>
            
            <%-- Boucle des Sites --%>
            <c:forEach items="${searchResultSites}" var="searchResultSite">
              <c:if test="${searchResultVille.villeID == searchResultSite.ville.villeID}">
                <div class="container ladle-bg-main">
                  <h5><a href="./site?siteID=${searchResultSite.siteID}<c:forEach 
                    items="${searchResultSecteurs}" var="searchResultSecteur">
                    ${searchResultSite.siteID == searchResultSecteur.site.siteID ? 
                    '&secteursID='+=searchResultSecteur.secteurID:''}
                  </c:forEach>">
                  ${searchResultSite.nom} (${searchResultSite.officiel})</a></h5>
              
      
              <%-- Boucle des Secteurs --%>
              <c:forEach items="${searchResultSecteurs}" var="searchResultSecteur">
                <c:if test="${searchResultSite.siteID == searchResultSecteur.site.siteID}">
                  <div class="container ladle-bg-main">
                    <table>
                      <tr>
                        <th scope="row"><a href="./secteur?secteurID=${searchResultSecteur.secteurID}">
                          ${searchResultSecteur.nom}
                        </a></th>
                        <td>${searchResultSecteur.descriptif}</td>
                        <td>ID = ${searchResultSecteur.secteurID}</td>
                      </tr>
                    </table>
                  </div>
                </c:if>
               </c:forEach>
              </div>
             </c:if>
            </c:forEach>
           </div> 
          </c:if>
         </c:forEach>
        </div>
       </c:if>
      </c:forEach>
      </div>
      </c:forEach>
      </div>
  </c:if>
  
  <%-- Affichage si le résultat de la recherche est vide --%>
  <c:if test="${searchResultEmpty}">
    <div class="container ladle-bg-main text-center">
      <p class="mb-0"><strong>La recherche n'a retourné aucun résultat.</strong><br>
      Veuillez essayer avec d'autres critères.</p>
    </div>
  </c:if>

  <script type="text/javascript">

  function codePostalSender(myForm) {
    if (myForm.inputCodePostal.value.length == 5) {
      if (myForm.inputCodePostal.checkValidity()) {
          myForm.formChangeOn.value = "code-postal";
          myForm.submit();
      } else {
        myForm.inputCodePostal.setCustomValidity(
          'Code postal invalide ! Il doit contenir exactement 5 chiffres (ex. 31000).');
        myForm.inputCodePostal.reportValidity();
        myForm.inputCodePostal.classList.remove('is-valid');
        myForm.inputCodePostal.classList.add('is-invalid');
      }
    } else {
        myForm.inputCodePostal.setCustomValidity('');
        myForm.inputCodePostal.classList.remove('is-valid');
        myForm.inputCodePostal.classList.remove('is-invalid');
    }
  }
  </script>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>
</body>
</html>