<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<meta charset="utf-8" />
<title>Inscription</title>
<!-- Inclusion des metas -->
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>

<body class="pb-3">

	<!-- Inclusion du bandeau de menu -->
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container">

		<h1>Page d'inscription</h1>

		<!-- inputs d'inscription -->

		<form method="post" action="Inscription">
			<fieldset>
				<legend>Inscription</legend>

				<!-- ====== -->
				<!-- Pseudo -->
				<!-- ====== -->

				<!-- Message d'erreur : Le champ pseudo est vide -->
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

				<!-- Message d'erreur : Le pseudo existe déjà -->
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

				<!-- Message d'erreur : Le pseudo fait plus de 30 caractères -->
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

				<!-- formulaire pseudo -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-pseudo">Pseudo</span>
					</div>
					<input type="text" id="pseudo" name="pseudo" maxlength="30"
						class="form-control" placeholder="" required aria-label="Pseudo"
						aria-describedby="aria-pseudo">
				</div>

				<!-- ================================================ -->
				<!-- Liste d'erreurs de la ligne Genre / Prénom / Nom -->
				<!-- ================================================ -->
			
				<!-- ----- -->
				<!-- Genre -->
				<!-- ----- -->
			
				<!-- Message d'erreur : Le champ genre est vide -->
				<c:if test="${validationList['genreEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de genre !</strong> Ce champ ne peut pas être
						vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>

				<!-- Message d'erreur : Le genre est invalide ("Madame","Monsieur") -->
				<c:if test="${validationList['genreValid'] == 0 && validationList['genreEmpty'] == 1}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de genre !</strong> Veuillez sélectionner Madame
						ou Monsieur.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				
				<!-- ------ -->
				<!-- Prénom -->
				<!-- ------ -->
				
				<!-- Message d'erreur : Le champ prénom est vide -->
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
				
				<!-- Message d'erreur : Le prénom fait plus de 40 caractères -->
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
								
				<!-- --- -->
				<!-- Nom -->
				<!-- --- -->
				
				<!-- Message d'erreur : Le champ nom est vide -->
				<c:if test="${validationList['nomEmpty'] == 0}">
					<div class="alert alert-danger alert-dismissible fade show"
						role="alert">
						<strong>Erreur de nom !</strong> Ce champ ne peut pas être
						vide.
						<button type="button" class="close" data-dismiss="alert"
							aria-label="Close">
							<span aria-hidden="true">&times;</span>
						</button>
					</div>
				</c:if>
				
				<!-- Message d'erreur : Le nom fait plus de 40 caractères -->
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

					<!-- ===== -->
					<!-- Genre -->
					<!-- ===== -->

					<!-- formulaire genre -->
					<div class="input-group col-md-4 col-lg-3 mb-3">
						<div class="input-group-prepend">
							<label class="input-group-text" for="genre">Genre</label>
						</div>
						<select class="custom-select" id="genre" name="genre" required>
							<option value="" selected>Choisissez...</option>
							<option value="Madame">Madame</option>
							<option value="Monsieur">Monsieur</option>
						</select>
					</div>

					<!-- ====== -->
					<!-- Prénom -->
					<!-- ====== -->

					<!-- formulaire prenom -->
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-prenom">Prénom</span>
						</div>
						<input type="text" id="prenom" name="prenom" maxlength="40"
							class="form-control" placeholder="" required aria-label="Prénom"
							aria-describedby="aria-prenom">
					</div>

					<!-- === -->
					<!-- Nom -->
					<!-- === -->

					<!-- formulaire nom -->
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-nom">Nom</span>
						</div>
						<input type="text" id="nom" name="nom" maxlength="40"
							class="form-control" placeholder="" required aria-label="Nom"
							aria-describedby="aria-nom">
					</div>

				</div>

				<!-- ===== -->
				<!-- Email -->
				<!-- ===== -->

				<!-- Message d'erreur : Le mail existe déjà -->
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
				
				<!-- Message d'erreur : Le mail est invalide -->
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
				
				<!-- formulaire email -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-email">Email</span>
					</div>
					<input type="email" id="email" name="email" maxlength="90"
						class="form-control" placeholder="" required aria-label="eMail"
						aria-describedby="aria-email">
				</div>

				<!-- ===== -->
				<!-- Ville -->
				<!-- ===== -->

				<!-- formulaire ville -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-ville">Ville</span>
					</div>
					<input type="text" id="ville" name="ville" class="form-control"
						placeholder="" aria-label="Ville" aria-describedby="aria-ville">
				</div>

				<!-- ====== -->
				<!-- Mdp x2 -->
				<!-- ====== -->
				
				<!-- formulaire email 2 inputs -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-mdp">Mot de passe</span>
					</div>
					<input type="password" id="mdp" name="mdp" maxlength="90"
						class="form-control" placeholder="" required
						aria-label="Mot de passe" aria-describedby="aria-mdp">
					<input type="password" id="mdp2" name="mdp2" maxlength="90"
						class="form-control" placeholder="" required
						aria-label="Retapez le mot de passe" aria-describedby="aria-mdp">
				</div>

				<!-- Bouton de validation du formulaire -->
				<button type="submit" class="btn btn-primary">Valider</button>
			</fieldset>
		</form>

	</div>

	<!-- Inclusion du bas de page -->
	<%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
