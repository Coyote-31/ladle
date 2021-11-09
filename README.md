# LADLE : Les amis de l'escalade.

[![Quality gate](https://sonarcloud.io/api/project_badges/quality_gate?project=Coyote-31_ladle)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle)

## ✨ Présentation :

Ce projet a pour but la création d'un site web responsive et sécurisé en Java EE.
Pour une association : "*Les amis de l'escalade*".
Afin de mettre en avant cette discipline et les endroits où la pratiquer dans l'hexagone.

- [Voir le projet sur GitHub](https://github.com/Coyote-31/ladle)

> **IMPORTANT:**
> Ce projet fait partie du cursus de formation "Développeur J2EE" de [OpenClassroom](https://openclassrooms.com/).
> Les informations qu'il comporte ne doivent pas être utilisées dans le cadre d'une pratique réelle de l'escalade.
> Les informations présentées ici ne servent qu'à illustrer le projet pour le rendre le plus proche possible de la réalité.

## 📱 Application :

L'application est un site web développé en **Java EE** sur l'**IDE Eclipse**.
Elle est packagée via **[Maven](https://maven.apache.org/index.html)** dans un fichier `.war` pour être déployée sur un serveur **[Apache TomEE v9.0](https://tomee.apache.org/)**.

#### Aperçu de l'application :

![Aperçu de l'application](https://i.imgur.com/s7O8kg1.jpg)

## 🗃 Base de données :

La base de données est développée avec **MySQL v8.0**.
Elle est intégrée à l'application via des **classes JPA** et l'**ORM Hibernate**

#### Diagramme de l'architecture de la base de données :

![Diagramme de l'architecture de la base de données](https://i.imgur.com/4SuegYZ.jpg)

## 🛠 Déploiement :

Pour déployer correctement l'application veuillez suivre ces étapes :

#### 1. Base de données :

La création de la base de données se fait grâce à l'utilisation d'un des script SQL qui se trouve dans [`ladle/DB_dump`](https://github.com/Coyote-31/ladle/tree/master/DB_dump) :

- [`ladle_db_DumpCreateWithData`](https://github.com/Coyote-31/ladle/blob/master/DB_dump/ladle_db_DumpCreateWithData_1.0_20211109.sql) :
Création de la **base de données** *ladle_db* avec toutes les **tables**, les **données minimales** (Régions/Villes/...) et un jeu de **données de démonstration**.

- [`ladle_db_DumpWithData`](https://github.com/Coyote-31/ladle/blob/master/DB_dump/ladle_db_DumpWithData_1.0_20211109.sql) :
Création des **tables**, insertion des **données minimales** (Régions/Villes/...) et d'un jeu de **données de démonstration**.

- [`ladle_db_DumpCleanCreate`](https://github.com/Coyote-31/ladle/blob/master/DB_dump/ladle_db_DumpCleanCreate_1.0_20211103.sql) :
Création de la **base de données** *ladle_db* avec toutes les **tables** et les **données minimales** (Régions/Villes/...).

- [`ladle_db_DumpClean`](https://github.com/Coyote-31/ladle/blob/master/DB_dump/ladle_db_DumpClean_1.0_20211103.sql) :
Création des **tables**, insertion des **données minimales** (Régions/Villes/...).

#### 2. Données de connexion à la BDD :

Lorsque la base de données est prête.
Pour faire la connexion entre la BDD et l'application il faut changer les données du fichier [`resources.xml`](https://github.com/Coyote-31/ladle/blob/master/ladle-webapp/src/main/webapp/WEB-INF/resources.xml) du module *ladle-webapp* :
- `jdbcUrl` : Mettre l'URL du serveur de BDD.
- `userName` : Mettre le nom d'utilisateur du serveur de BDD.
- `password` : Mettre le mot de passe du serveur de BDD.

#### 3. Données d'envoi de mail :

Pour que l'application puisse envoyer les mails pour la confirmation d'inscription des utilisateurs, il faut changer les données de connexion à la boite mail.

Pour cela il faut modifier le fichier [`mail-resources_EXAMPLE.xml`](https://github.com/Coyote-31/ladle/blob/master/ladle-service/src/main/java/org/ladle/service/mail-resources_EXAMPLE.xml) du module *ladle-service* :

- Renommer le fichier `mail-resources_EXAMPLE.xml` en `mail-resources.xml` ou créer une copie avec ce nom.

- Modifier les données de `mailUsername`, `mailPassword` et `siteURL` avec les bonnes valeurs.

#### 4. Packaging Maven :

Pour compiler et packager l'application dans un fichier `.war`, il faut utiliser le goal [`install`](https://maven.apache.org/plugins/maven-install-plugin/) de Maven avec la commande `mvn install`. Le fichier se créé alors dans le dossier *target* du module *ladle-webapp*.
> On peut aussi utiliser le goal [`deploy`](https://maven.apache.org/plugins/maven-deploy-plugin/) mais cela demande de configurer le fichier POM, ce que je ne développerai pas ici.

#### 5. Serveur d'application :

La dernière étape est de mettre en ligne le serveur **[Apache TomEE v9.0](https://tomee.apache.org/)**.

Lorsque ce dernier est en ligne, dans l'interface du serveur Apache cliquer sur le bouton `Manager App`.
Après s'être identifier trouver la partie `Deployer > Fichier WAR à déployer` et y déployer le fichier `.war` précédemment généré à l'étape 4.

Bravo ! L'application web est maintenant 100% fonctionnelle !

## ✅ Qualité du code :

| [![Bugs](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=bugs)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Security Rating](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=security_rating)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) |
| :----------- | :----------- | :----------- |
| [![Lines of Code](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=ncloc)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=code_smells)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Maintainability Rating](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) |
| [![Duplicated Lines (%)](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Technical Debt](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=sqale_index)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) | [![Reliability Rating](https://sonarcloud.io/api/project_badges/measure?project=Coyote-31_ladle&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=Coyote-31_ladle) |
