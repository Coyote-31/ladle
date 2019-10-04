<!DOCTYPE html>
<html lang="fr">
<head>
<title>Inscription</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>

<body class="pb-3">
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container">

		<h1>Page d'inscription</h1>

		<!-- inputs d'inscription -->

		<form method="post" action="Inscription">
			<fieldset>
				<legend>Inscription</legend>

				<!-- Pseudo -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-pseudo">Pseudo</span>
					</div>
					<input type="text" id="pseudo" name="pseudo" maxlength="45" class="form-control" placeholder=""
						aria-label="Pseudo" aria-describedby="aria-pseudo">
				</div>

				<div class="row">

					<!-- Genre -->
					<div class="input-group col-md-4 col-lg-3 mb-3">
						<div class="input-group-prepend">
							<label class="input-group-text" for="genre">Genre</label>
						</div>
						<select class="custom-select" id="genre" name="genre">
							<option selected>Choisissez...</option>
							<option value="Madame">Madame</option>
							<option value="Monsieur">Monsieur</option>
						</select>
					</div>

					<!-- Prénom -->
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-prenom">Prénom</span>
						</div>
						<input type="text" id="prenom" name="prenom" maxlength="45" class="form-control" placeholder=""
							aria-label="Prénom" aria-describedby="aria-prenom">
					</div>

					<!-- Nom -->
					<div class="input-group col-md mb-3">
						<div class="input-group-prepend">
							<span class="input-group-text" id="aria-nom">Nom</span>
						</div>
						<input type="text" id="nom" name="nom" maxlength="45" class="form-control" placeholder=""
							aria-label="Nom" aria-describedby="aria-nom">
					</div>

				</div>

				<!-- Mail -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-mail">Mail</span>
					</div>
					<input type="email" id="email" name="email" maxlength="90" class="form-control" placeholder=""
						aria-label="Mail" aria-describedby="aria-mail">
				</div>

				<!-- Ville -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-ville">Ville</span>
					</div>
					<input type="text" id="ville" name="ville" class="form-control" placeholder=""
						aria-label="Ville" aria-describedby="aria-ville">
				</div>

				<!-- Mdp x2 -->
				<div class="input-group mb-3">
					<div class="input-group-prepend">
						<span class="input-group-text" id="aria-mdp">Mot de passe</span>
					</div>
					<input type="password" id="mdp" name="mdp" maxlength="90" class="form-control" placeholder=""
						aria-label="Mot de passe" aria-describedby="aria-mdp"> <input
						type="password" id="mdp2" name="mdp2" maxlength="90" class="form-control" placeholder=""
						aria-label="Retapez le mot de passe" aria-describedby="aria-mdp">
				</div>

				<button type="submit" class="btn btn-primary">Valider</button>
			</fieldset>
		</form>

	</div>

	<%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
