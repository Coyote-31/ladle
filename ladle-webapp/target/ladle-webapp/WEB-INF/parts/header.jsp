<nav id="header-navbar"
	class="navbar navbar-expand-lg navbar-dark mb-3 sticky-top">
	<a class="navbar-brand" href="./"> <img
		src="/docs/4.3/assets/brand/bootstrap-solid.svg" width="30"
		height="30" class="d-inline-block align-top" alt="" /> Les Amis De
		L'Escalade
	</a>
	<button class="navbar-toggler" type="button" data-toggle="collapse"
		data-target="#navbarColor02" aria-controls="navbarColor02"
		aria-expanded="false" aria-label="Toggle navigation">
		<span class="navbar-toggler-icon"></span>
	</button>

	<div class="collapse navbar-collapse mt-3 mt-lg-0" id="navbarColor02">
		<ul class="navbar-nav mr-auto">
			<li class="nav-item active"><a class="nav-link" href="./">Accueil<span
					class="sr-only">(current)</span>
			</a></li>
			<li class="nav-item"><a class="nav-link" href="Inscription">Inscription</a></li>
			<li class="nav-item"><a class="nav-link" href="Connexion">Connexion</a></li>
			<li class="nav-item"><a class="nav-link" href="#">À propos</a></li>
		</ul>
		
		<!-- Button trigger modal -->
		<button type="button" class="btn btn-primary" data-toggle="modal" data-target="#exampleModalCenter">
		  Launch demo modal
		</button>
		
		<!-- Modal connexion -->
		<div class="modal fade" id="exampleModalCenter" tabindex="-1" role="dialog" aria-labelledby="exampleModalCenterTitle" aria-hidden="true">
		  <div class="modal-dialog modal-dialog-centered" role="document">
		    <div class="modal-content">
		      <div class="modal-header">
		        <h5 class="modal-title" id="exampleModalLongTitle">Modal title</h5>
		        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
		          <span aria-hidden="true">&times;</span>
		        </button>
		      </div>
		      <div class="modal-body">
		        
						<form>
							<div class="form-row">
							
								<!-- Input Email -->
								<div class="col-12 col-sm">
									<div class="form-group">  	
								    <label for="headerInputEmail">Email</label>
								    <input type="email" class="form-control form-control-sm" id="headerInputEmail" placeholder="Entrez votre mail">
									</div>
								</div>
								
								<!-- Input Password -->
								<div class="col-12 col-sm">
								  <div class="form-group">
								    <label for="HeaderInputPassword">Password</label>
								    <input type="password" class="form-control form-control-sm" id="HeaderInputPassword" placeholder="Mot de passe...">
								  </div>
							  </div>
							  
							</div>			
							<div class="form-row">
							 
							 	<!-- Input checkBox -->
							 	<div class="col">
								  <div class="form-group form-check">
								    <input type="checkbox" class="form-check-input" id="HeaderCheckStayConnected">
								    <label class="form-check-label" for="HeaderCheckStayConnected">Rester connecté</label>
								  </div>
								</div>
							  
							  <!-- Bouton Envoyé -->
							  <div class="col">
							  	<button type="submit" class="btn btn-primary">Envoyer</button>
							  </div>
							  
							</div>
						</form>

		      </div>
		      <div class="modal-footer">
		        <button type="button" class="btn btn-secondary" data-dismiss="modal">Close</button>
		        <button type="button" class="btn btn-primary">Save changes</button>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- OLD Header form connexion -->
		
		
	</div>
		
</nav>
