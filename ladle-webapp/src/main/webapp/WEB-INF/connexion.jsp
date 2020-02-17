<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html lang="fr">
<head>
<title>Connexion</title>
<%@ include file="/WEB-INF/parts/meta.jsp"%>
</head>
<body class="pb-3">
  <%@ include file="/WEB-INF/parts/header.jsp"%>

  <div class="container">

    <h1>Connexion :</h1>
    <p>Connecté : ${ isLoginValid? oui : non }<br>
       ${ user.pseudo }
    </p>
  </div>

  <%@ include file="/WEB-INF/parts/footer.jsp"%>

</body>
</html>
