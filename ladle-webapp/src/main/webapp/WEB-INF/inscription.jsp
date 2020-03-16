<%@ page language="java" contentType="text/html; charset=UTF-8"	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html> 
<html lang="fr">
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<%-- Fontawesome --%>
<script src="https://kit.fontawesome.com/9371740617.js" crossorigin="anonymous"></script>
<%-- Inclusion des metas --%>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>

<body class="pb-3">
  
	<%-- Inclusion du bandeau de menu --%>
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container ladle-bg-main">

		<h1>Page d'inscription</h1>

		<%-- inputs d'inscription --%>

		<form method="post" action="inscription">
			<fieldset>
				<legend>Inscription</legend>

				<%-- ====== --%>
				<%-- Pseudo --%>
				<%-- ====== --%>
				
				<%--  --%>
				
				
				<%-- Message d'erreur : Le champ pseudo est vide --%>
				<c:if test="${validationList['pseudoEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de pseudo !</strong> Ce champ ne peut pas être
						vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le pseudo existe déjà --%>
				<c:if test="${validationList['pseudoExist'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de pseudo !</strong> Le pseudo existe déjà.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le pseudo fait plus de 30 caractères --%>
				<c:if test="${validationList['pseudoLength'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de pseudo !</strong> 30 caractères maximum.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
        
        <%-- Message d'erreur : Le pseudo ressemble à un email --%>
        <c:if test="${validationList['pseudoNotEmail'] == 0}">
          <div class="alert alert-danger alert-dismissible fade show"
            role="alert">
            <strong>Erreur de pseudo !</strong> Le pseudo ne doit pas être un email.
            <button type="button" class="close" data-dismiss="alert"
              aria-label="Close">
              <span aria-hidden="true">&times;</span>
            </button>
          </div>
        </c:if>

				<%-- formulaire pseudo --%>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-pseudo">Pseudo</span>
					</div>
					<input type="text" id="pseudo" name="pseudo" maxlength="30"
						class="form-control
            <c:if test="${validationList['pseudo'] == 1}"> is-valid</c:if>
            <c:if test="${validationList['pseudo'] == 0}"> is-invalid</c:if>"
						<c:if test="${validationList['pseudo'] == 1}"> value="${user.pseudo}"</c:if>
						placeholder='' required aria-label="Pseudo"
						aria-describedby="aria-pseudo">
				</div>

				<%-- ----- --%>
				<%-- Genre --%>
				<%-- ----- --%>

				<%-- Message d'erreur : Le champ genre est vide --%>
				<c:if test="${validationList['genreEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de genre !</strong> Ce champ ne peut pas être vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le genre est invalide ("Madame","Monsieur") --%>
				<c:if
					test="${validationList['genreValid'] == 0 && validationList['genreEmpty'] == 1}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de genre !</strong> Veuillez sélectionner Madame ou
						Monsieur.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- ------ --%>
				<%-- Prénom --%>
				<%-- ------ --%>

				<%-- Message d'erreur : Le champ prénom est vide --%>
				<c:if test="${validationList['prenomEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de prénom !</strong> Ce champ ne peut pas être
						vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le prénom fait plus de 40 caractères --%>
				<c:if test="${validationList['prenomLength'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de prénom !</strong> 40 caractères maximum.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- --- --%>
				<%-- Nom --%>
				<%-- --- --%>

				<%-- Message d'erreur : Le champ nom est vide --%>
				<c:if test="${validationList['nomEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de nom !</strong> Ce champ ne peut pas être vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le nom fait plus de 40 caractères --%>
				<c:if test="${validationList['nomLength'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de nom !</strong> 40 caractères maximum.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<div class="row">

					<%-- ===== --%>
					<%-- Genre --%>
					<%-- ===== --%>

					<%-- formulaire genre --%>
					<div class="input-group col-md-4 col-lg-3 mb-3">
						<div class="input-group-prepend">
							<label class="input-group-text" for="genre">Genre</label>
						</div>
						<select class="custom-select<c:if 
						test="${validationList['genre'] == 1}"> is-valid</c:if><c:if	
						test="${validationList['genre'] == 0}"> is-invalid</c:if>"
						id="genre" name="genre" required>
							<option value="" 
							<c:if test="${validationList['genre'] != 1}"> selected</c:if>
							>Choisissez...</option>
							<option value="Madame" 
								<c:if test="${validationList['genre'] == 1 
														&& user.genre == 'Madame'}"> selected</c:if>
							>Madame</option>
							<option value="Monsieur"
								<c:if test="${validationList['genre'] == 1 
														&& user.genre == 'Monsieur'}"> selected</c:if>
							>Monsieur</option>
						</select>
					</div>
					
					<%-- ====== --%>
					<%-- Prénom --%>
					<%-- ====== --%>

					<%-- formulaire prenom --%>
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-prenom">Prénom</span>
						</div>
						<input type="text" id="prenom" name="prenom" maxlength="40"
							class="form-control<c:if 
						test="${validationList['prenom'] == 1}"> is-valid</c:if><c:if	
						test="${validationList['prenom'] == 0}"> is-invalid</c:if>"
						<c:if test="${validationList['prenom'] == 1}"> value="${user.prenom}"</c:if> 
						placeholder="" required aria-label="Prénom" aria-describedby="aria-prenom">
					</div>

					<%-- === --%>
					<%-- Nom --%>
					<%-- === --%>

					<%-- formulaire nom --%>
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-nom">Nom</span>
						</div>
						<input type="text" id="nom" name="nom" maxlength="40"
							class="form-control<c:if 
						test="${validationList['nom'] == 1}"> is-valid</c:if><c:if	
						test="${validationList['nom'] == 0}"> is-invalid</c:if>"
						<c:if test="${validationList['nom'] == 1}"> value="${user.nom}"</c:if> 
						placeholder="" required aria-label="Nom" aria-describedby="aria-nom">
					</div>

				</div>

				<%-- ===== --%>
				<%-- Email --%>
				<%-- ===== --%>

				<%-- Message d'erreur : Le mail existe déjà --%>
				<c:if test="${validationList['emailExist'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur d'email !</strong> L'adresse mail existe déjà.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Le mail est invalide --%>
				<c:if test="${validationList['emailValid'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur d'email !</strong> Le mail est invalide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
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
						class="form-control<c:if 
						test="${validationList['email'] == 1}"> is-valid</c:if><c:if	
						test="${validationList['email'] == 0}"> is-invalid</c:if>"
						<c:if test="${validationList['email'] == 1}"> value="${user.email}"</c:if> 
						placeholder="" required aria-label="eMail"	aria-describedby="aria-email">
				</div>

				<%-- ===== --%>
				<%-- Ville --%>
				<%-- ===== --%>

				<%-- formulaire ville --%>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-ville">Ville</span>
					</div>
					<input type="text" id="ville" name="ville" class="form-control"
						placeholder="" aria-label="Ville" aria-describedby="aria-ville">
				</div>

				<%-- ====== --%>
				<%-- Mdp x2 --%>
				<%-- ====== --%>

				<%-- Message d'erreur : Le mdp n'a pas la bonne taille (8 à 40 chars) --%>
				<c:if test="${validationList['mdpLength'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de mot de passe !</strong> Doit contenir entre 8 et
						40 caratères.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- Message d'erreur : Les mdp ne sont pas identiques --%>
				<c:if test="${validationList['mdpEquals'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de mot de passe !</strong> Les mots de passe
						doivent être identiques.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<%-- formulaire mdp 2 inputs --%>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-mdp">Mot de passe</span>
					</div>
					<input type="password" id="mdp" name="mdp" pattern=".{8,40}"
						title="De 8 à 40 caratères." class="form-control<c:if 
						test="${validationList['mdpLength'] == 1}"> is-valid</c:if><c:if	
						test="${validationList['mdpLength'] == 0}"> is-invalid</c:if>"
						placeholder="" required aria-label="Mot de passe" aria-describedby="aria-mdp">
						<div class="input-group-append">
        			<button id="btnMdp" class="btn btn-outline-warning" type="button" onClick="passwordShowHide(this)">
        				<i id="pass-status" class="far fa-eye-slash" aria-hidden="true"></i>
        			</button>
						</div>
				</div>
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-mdp">Confirmation</span>
					</div>
					<input type="password" id="mdp2" name="mdp2" pattern=".{8,40}"
						title="De 8 à 40 caratères." class="form-control
            <c:if	test="${validationList['mdp'] == 0}"> is-invalid</c:if>"
						placeholder="" required aria-label="Confirmez le mdp" aria-describedby="aria-mdp">
						<div class="input-group-append">
        			<button id="btnMdp2" class="btn btn-outline-warning" type="button" onClick="passwordShowHide(this)">
        				<i id="pass-status2" class="far fa-eye-slash" aria-hidden="true"></i>
        			</button>
						</div>
				</div>
				
				<%-- Script hide/show des champs de mdp --%>
				<script>
					function passwordShowHide(elem) {
						
						if(elem.id == 'btnMdp'){
							  var passwordInput = document.getElementById('mdp');
							  var passStatus = document.getElementById('pass-status');
							  
						} else {
							  var passwordInput = document.getElementById('mdp2');
							  var passStatus = document.getElementById('pass-status2');
						}

					  if (passwordInput.type == 'password'){
					    passwordInput.type='text';
					    passStatus.className='far fa-eye';
					    
					  }
					  else{
					    passwordInput.type='password';
					    passStatus.className='far fa-eye-slash';
					  }
					}
				</script>
				
				<%-- Script de vérification du mot de passe (mdp == mdp2) --%>
				<script>
					var mdp = document.getElementById("mdp")
					  , mdp2 = document.getElementById("mdp2");
	
					function validatePassword(){
					  if(mdp.value != mdp2.value) {
					    mdp2.setCustomValidity("Les mots de passe ne sont pas identiques");
					  } else {
					    mdp2.setCustomValidity('');
					  }
					}
	
					mdp.onchange = validatePassword;
					mdp2.onkeyup = validatePassword;
				</script>

				<%-- Bouton de validation du formulaire --%>
				<button type="submit" class="btn btn-primary">Valider</button>
			</fieldset>
		</form>

	</div>

	<%-- Inclusion du bas de page --%>
	<%@ include file="/WEB-INF/parts/footer.jsp" %>

</body>
</html>
