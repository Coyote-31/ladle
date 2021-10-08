<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<link rel="icon" href="images/favicon/favicon.ico">
<%-- Fontawesome --%>
<script src="https://kit.fontawesome.com/9371740617.js" crossorigin="anonymous" type="text/javascript"></script>
<%-- Inclusion des metas --%>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>

<body>

  <%-- Inclusion du bandeau de menu --%>
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container ladle-bg-main">
  
    <%-- Affichage de la validation de l'inscription --%>
    <c:if test="${isInscriptionValid}">
      <div class="card">
        <div class="card-header header-rounded-debug">
          Bienvenue ${user.pseudo} !
        </div>
        
        <div class="card-body">
          <p>
            Vous êtes désormais inscrit !
          </p>
          <p class="text-danger mb-0">
            IMPORTANT ! L'inscription doit être finalisée en cliquant sur le lien de validation 
            dans le mail envoyé à l'adresse suivante : ${user.email}
          </p>
        </div>
      
      </div>
    </c:if>
    
    <%-- Affichage du formulaire d'inscription --%>
    <c:if test="${!isInscriptionValid}">
      <h1>Page d'inscription</h1>
      <hr>

    <%-- Erreur interne --%>
    <c:if test="${internalError}">
      <div class="alert alert-danger alert-dismissible fade show" role="alert">
        <strong>Erreur serveur !</strong> Une erreur interne au serveur empêche de terminer 
        l'inscription. veuillez essayer de nouveau ultérieurement ou contacter un administrateur.
        <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
          aria-hidden="true">&times;</span></button>
      </div>
    </c:if>

    <%-- inputs d'inscription --%>

    <form method="post" action="inscription">
    
      <div class="row">

        <%-- ====== --%>
        <%-- Pseudo --%>
        <%-- ====== --%>
        
        <div class="col-12 col-sm-8 col-md-7 col-lg-5">
  
          <%-- Message d'erreur : Le champ pseudo est vide --%>
          <c:if test="${validationList['pseudoEmpty'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de pseudo !</strong> Ce champ ne peut pas être vide.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            </div>
          </c:if>
    
          <%-- Message d'erreur : Le pseudo existe déjà --%>
          <c:if test="${validationList['pseudoExist'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de pseudo !</strong> Le pseudo existe déjà.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            </div>
          </c:if>
    
          <%-- Message d'erreur : Le pseudo fait plus de 30 caractères --%>
          <c:if test="${validationList['pseudoLength'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de pseudo !</strong> 30 caractères maximum.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            </div>
          </c:if>
    
          <%-- Message d'erreur : Le pseudo ressemble à un email --%>
          <c:if test="${validationList['pseudoNotEmail'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de pseudo !</strong> Le pseudo ne doit pas être un email.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close"><span
                aria-hidden="true">&times;</span></button>
            </div>
          </c:if>
    
          <%-- formulaire pseudo --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-pseudo">Pseudo</span>
            </div>
            <input type="text" id="pseudo" name="pseudo" maxlength="30"
              class='form-control
              <c:if test="${validationList['pseudo'] == 1}"> is-valid</c:if>
              <c:if test="${validationList['pseudo'] == 0}"> is-invalid</c:if>'
              value="${user.pseudo}" placeholder='' required aria-label="Pseudo" aria-describedby="aria-pseudo">
          </div>
          
        </div>
        
      </div>
      <div class="row">
  
        <%-- ----- --%>
        <%-- Genre --%>
        <%-- ----- --%>
        
        <div class="col-12 col-lg-3">
        
          <div class="row">
          
            <div class="col-12 col-sm-6 col-md-4 col-lg-12">
    
              <%-- Message d'erreur : Le champ genre est vide --%>
              <c:if test="${validationList['genreEmpty'] == 0}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                  <strong>Erreur de genre !</strong>
                  Ce champ ne peut pas être vide.
                  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
              </c:if>
    
              <%-- Message d'erreur : Le genre est invalide ("Madame","Monsieur") --%>
              <c:if test="${validationList['genreValid'] == 0 && validationList['genreEmpty'] == 1}">
                <div class="alert alert-danger alert-dismissible fade show" role="alert">
                  <strong>Erreur de genre !</strong>
                  Veuillez sélectionner Madame ou Monsieur.
                  <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                    <span aria-hidden="true">&times;</span>
                  </button>
                </div>
              </c:if>
    
              <%-- formulaire genre --%>
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <label class="input-group-text" for="genre">Genre</label>
                </div>
                <select
                  class='custom-select<c:if 
              test="${validationList['genre'] == 1}"> is-valid</c:if><c:if  
              test="${validationList['genre'] == 0}"> is-invalid</c:if>'
                  id="genre" name="genre" required
                >
                  <option value="" <c:if test="${user.genre != 'Madame' && user.genre != 'Monsieur' }"> selected</c:if>>
                  Choisissez...</option>
                  <option value="Madame" <c:if test="${user.genre == 'Madame'}"> selected</c:if>>
                  Madame</option>
                  <option value="Monsieur" <c:if test="${user.genre == 'Monsieur'}"> selected</c:if>>
                  Monsieur</option>
                </select>
              </div>
              
            </div>
            
          </div>
          
        </div>
        
        <%-- ------ --%>
        <%-- Prénom --%>
        <%-- ------ --%>
        
        <div class="col-12 col-sm-8 col-md-7 col-lg">

          <%-- Message d'erreur : Le champ prénom est vide --%>
          <c:if test="${validationList['prenomEmpty'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de prénom !</strong>
              Ce champ ne peut pas être vide.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- Message d'erreur : Le prénom fait plus de 40 caractères --%>
          <c:if test="${validationList['prenomLength'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de prénom !</strong>
              40 caractères maximum.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- formulaire prenom --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-prenom">Prénom</span>
            </div>
            <input type="text" id="prenom" name="prenom" maxlength="40"
              class='form-control<c:if 
          test="${validationList['prenom'] == 1}"> is-valid</c:if><c:if 
          test="${validationList['prenom'] == 0}"> is-invalid</c:if>'
              value="${user.prenom}" placeholder="" required aria-label="Prénom" aria-describedby="aria-prenom"
            >
          </div>
          
        </div>
        
        <%-- --- --%>
        <%-- Nom --%>
        <%-- --- --%>
        
        <div class="col-12 col-sm-8 col-md-7 col-lg">

          <%-- Message d'erreur : Le champ nom est vide --%>
          <c:if test="${validationList['nomEmpty'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de nom !</strong>
              Ce champ ne peut pas être vide.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- Message d'erreur : Le nom fait plus de 40 caractères --%>
          <c:if test="${validationList['nomLength'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de nom !</strong>
              40 caractères maximum.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>


          <%-- formulaire nom --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-nom">Nom</span>
            </div>
            <input type="text" id="nom" name="nom" maxlength="40"
              class="form-control
            <c:if test="${validationList['nom'] == 1}"> is-valid</c:if>
            <c:if test="${validationList['nom'] == 0}"> is-invalid</c:if>"
              value="${user.nom}" placeholder="" required aria-label="Nom" aria-describedby="aria-nom"
            >
          </div>

        </div>

      </div>
      
      <hr class="mt-0">
      
      <div class="row">

        <%-- ===== --%>
        <%-- Ville --%>
        <%-- ===== --%>

        <div class="col-12 col-sm-6 col-md-5 col-lg-3">
        
          <%-- Message d'erreur : Le CP est invalide --%>
          <c:if test="${validationList['cpValid'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de Code Postal !</strong>
              Le CP est invalide.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- input CP --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="code-postal">Code postal</span>
            </div>
            <input type="text"
              class="form-control${
              validationList['cp'] == 1 || cpIsValid == true ? ' is-valid' : '' 
              }${ validationList['cp'] == 0 || cpIsValid == false ? ' is-invalid' : '' }"
              id="cp" name="cp" placeholder="ex. 31000" <c:if test="${not empty user.cp}">value="${user.cp}"</c:if>
              maxlength="5" pattern="[0-9][0-9][0-9][0-9][0-9]" oninput="codePostalSender(this.form);"
              aria-label="Code Postal" aria-describedby="code-postal"
            >
          </div>

        </div>
        <div class="col-12 col-sm-8 col-md-7 col-lg-5">

          <%-- Message d'erreur : La ville n'est pas sélectionné invalide --%>
          <c:if test="${validationList['villeId'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur !</strong>
              Veuillez sélectionner une ville dans la liste.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- select ville --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <label class="input-group-text" for="ville">Ville</label>
            </div>
            <select
              class="custom-select${
               validationList['villeId'] == 1 ? ' is-valid' : '' 
               }${ validationList['villeId'] == 0 ? ' is-invalid' : '' }"
              ${ villeSelectHighlight ? ' autofocus="autofocus" ' : ''} id="ville" name="ville"
              <c:if test="${empty villes}">disabled</c:if>
            >
              <option <c:if test="${empty user.villeID}">selected</c:if> value="0">Sélection ...</option>
              <c:forEach items="${villes}" var="ville">
                <option <c:if test="${user.villeID == ville.villeID}">selected</c:if> 
                  value="${ville.villeID}">${ville.cp} - ${ville.nom}
                </option>
              </c:forEach>
            </select>
          </div>

        </div>

      </div>
      
      <hr class="mt-0">
      
      <div class="row">

        <%-- ===== --%>
        <%-- Email --%>
        <%-- ===== --%>
        
        <div class="col-12 col-md-8 col-lg-5 col-xl-4">

          <%-- Message d'erreur : Le mail existe déjà --%>
          <c:if test="${validationList['emailExist'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur d'email !</strong>
              L'adresse mail existe déjà.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- Message d'erreur : Le mail est invalide --%>
          <c:if test="${validationList['emailValid'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur d'email !</strong>
              Le mail est invalide.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- formulaire email --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-email">Email</span>
            </div>
            <input type="email" id="email" name="email" maxlength="90"
              class='form-control<c:if 
                test="${validationList['email'] == 1}"> is-valid</c:if><c:if  
                test="${validationList['email'] == 0}"> is-invalid</c:if>'
              value="${user.email}" placeholder="" required aria-label="eMail" aria-describedby="aria-email"
            >
          </div>

        </div>

        <%-- ====== --%>
        <%-- Mdp x2 --%>
        <%-- ====== --%>
        
        <div class="col-12 col-md-8 col-lg-5 col-xl-4">

          <%-- Message d'erreur : Le mdp n'a pas la bonne taille (8 à 40 chars) --%>
          <c:if test="${validationList['mdpLength'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de mot de passe !</strong>
              Doit contenir entre 8 et 40 caratères.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- Message d'erreur : Les mdp ne sont pas identiques --%>
          <c:if test="${validationList['mdpEquals'] == 0}">
            <div class="alert alert-danger alert-dismissible fade show" role="alert">
              <strong>Erreur de mot de passe !</strong>
              Les mots de passe doivent être identiques.
              <button type="button" class="close" data-dismiss="alert" aria-label="Close">
                <span aria-hidden="true">&times;</span>
              </button>
            </div>
          </c:if>

          <%-- formulaire mdp 2 inputs --%>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-mdp">Mot de passe</span>
            </div>
            <input type="password" id="mdp" name="mdp" pattern=".{8,40}" title="De 8 à 40 caratères."
              class='form-control<c:if 
          test="${validationList['mdpLength'] == 1}"> is-valid</c:if><c:if  
          test="${validationList['mdpLength'] == 0}"> is-invalid</c:if>'
              placeholder="" required aria-label="Mot de passe" aria-describedby="aria-mdp"
            >
            <div class="input-group-append">
              <button id="btnMdp" class="btn btn-outline-warning ml-0" 
                type="button" onClick="passwordShowHide(this)">
                <i id="pass-status" class="far fa-eye-slash" aria-hidden="true"></i>
              </button>
            </div>
          </div>
          <div class="input-group mb-3">
            <div class="input-group-prepend">
              <span class="input-group-text" id="aria-mdp">Confirmation</span>
            </div>
            <input type="password" id="mdp2" name="mdp2" pattern=".{8,40}" title="De 8 à 40 caratères."
              class='form-control
          <c:if test="${validationList['mdp'] == 0}"> is-invalid</c:if>'
              placeholder="" required aria-label="Confirmez le mdp" aria-describedby="aria-mdp"
            >
            <div class="input-group-append">
              <button id="btnMdp2" class="btn btn-outline-warning ml-0" 
                type="button" onClick="passwordShowHide(this)">
                <i id="pass-status2" class="far fa-eye-slash" aria-hidden="true"></i>
              </button>
            </div>
          </div>

        </div>

      </div>

      <%-- Variables pour les maj du formulaire --%>
      <input type="hidden" name="formChangeOn" value="">
      
      <hr class="mt-0">
      
      <div class="d-flex justify-content-center">
        <%-- Bouton de validation du formulaire --%>
        <button type="submit" class="btn btn-primary ml-0">Valider</button>
      </div>

    </form>
      
    </c:if>
    
  </div>
  
  <script type="text/javascript">
    <%-- Script de recherche de la ville depuis le cp --%>
    function codePostalSender(myForm) {
      if (myForm.cp.value.length == 5) {
        if (myForm.cp.checkValidity()) {
            myForm.formChangeOn.value = "code-postal";
            myForm.submit();
        } else {
          myForm.cp.setCustomValidity(
            'Code postal invalide ! Il doit contenir exactement 5 chiffres (ex. 31000).');
          myForm.cp.reportValidity();
          myForm.cp.classList.remove('is-valid');
          myForm.cp.classList.add('is-invalid');
        }
      } else {
          myForm.cp.setCustomValidity('');
          myForm.cp.classList.remove('is-valid');
          myForm.cp.classList.remove('is-invalid');
      }
    }
  
    <%-- Script hide/show des champs de mdp --%>
    function passwordShowHide(elem) {

        if (elem.id == 'btnMdp') {
            var passwordInput = document
                    .getElementById('mdp');
            var passStatus = document
                    .getElementById('pass-status');

        } else {
            var passwordInput = document
                    .getElementById('mdp2');
            var passStatus = document
                    .getElementById('pass-status2');
        }

        if (passwordInput.type == 'password') {
            passwordInput.type = 'text';
            passStatus.className = 'far fa-eye';

        } else {
            passwordInput.type = 'password';
            passStatus.className = 'far fa-eye-slash';
        }
    }

    <%-- Script de vérification du mot de passe (mdp == mdp2) --%>

    var mdp = document.getElementById("mdp"), mdp2 = document
            .getElementById("mdp2");

    function validatePassword() {
        if (mdp.value != mdp2.value) {
            mdp2
                    .setCustomValidity("Les mots de passe ne sont pas identiques !");
        } else {
            mdp2.setCustomValidity('');
        }
    }

    mdp.onchange = validatePassword;
    mdp2.onkeyup = validatePassword;
  </script>

  <%-- Inclusion du bas de page --%>
  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
