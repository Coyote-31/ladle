<!DOCTYPE html>
<html lang="fr">
<head>
<title>Inscription</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>

<body class="pb-3">
	<%@ include file="/WEB-INF/parts/header.jsp"%>

	<div class="container">

		<h1>Inscription</h1>

		<!-- inputs d'inscription -->

		<form>

			<!-- Pseudo -->
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="pseudo">Pseudo</span>
				</div>
				<input type="text" class="form-control" placeholder=""
					aria-label="Pseudo" aria-describedby="pseudo">
			</div>

 <div class="row">

			<!-- Genre -->
			<div class="input-group col-md-4 col-lg-3 mb-3">
				<div class="input-group-prepend">
					<label class="input-group-text" for="genre">Genre</label>
				</div>
				<select class="custom-select" id="genre">
					<option selected>Choisissez...</option>
					<option value="Madame">Madame</option>
					<option value="Monsieur">Monsieur</option>
				</select>
			</div>

			<!-- Prénom -->
			<div class="input-group col-md mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="prenom">Prénom</span>
				</div>
				<input type="text" class="form-control" placeholder=""
					aria-label="Prénom" aria-describedby="prenom">
			</div>

			<!-- Nom -->
			<div class="input-group col-md mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="nom">Nom</span>
				</div>
				<input type="text" class="form-control" placeholder=""
					aria-label="Nom" aria-describedby="nom">
			</div>

</div>

			<!-- Mail -->
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="mail">Mail</span>
				</div>
				<input type="email" class="form-control" placeholder=""
					aria-label="Mail" aria-describedby="mail">
			</div>

			<!-- Ville -->
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="ville">Ville</span>
				</div>
				<input type="text" class="form-control" placeholder=""
					aria-label="Ville" aria-describedby="ville">
			</div>

			<!-- Mdp x2 -->
			<div class="input-group mb-3">
				<div class="input-group-prepend">
					<span class="input-group-text" id="mdp">Mot de passe</span>
				</div>
				<input type="password" class="form-control" placeholder=""
					aria-label="Mot de passe" aria-describedby="mdp"> <input
					type="password" class="form-control" placeholder=""
					aria-label="Retapez le mot de passe" aria-describedby="mdp">
			</div>

			<button type="submit" class="btn btn-primary">Valider</button>
		</form>

	</div>

	<%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
