## Description du Projet

Le projet repose sur une architecture hybride :

* **Extension Chrome** : un outil intégré qui traduit à la volée le contenu des pages web (texte surligné récupéré automatiquement).  
* **Webservice Java** : un serveur local léger qui reçoit les requêtes, appelle l’API Gemini et renvoie la traduction en darija.

✨ Fonctionnalités principales

* Récupération automatique du texte surligné sur n’importe quelle page web  
* Traduction en darija naturelle et contextuelle (expressions courantes, idiomes)  
* Interface side panel simple : textarea + bouton unique « Traduire »  
* Affichage des traductions ligne par ligne (chaque ligne cliquable pour la copier dans le textarea)  
* Gestion des erreurs utilisateur-friendly (serveur éteint, texte vide, réponse invalide)  
* Authentification basique sécurisée sur le webservice

🛠️ Technologies utilisées

* Java /JAX-RS (webservice REST local)  
* Google Gemini API (traduction IA)  
* JavaScript / HTML5 / CSS3 (interface side panel et content script)  
* Chrome Extension (Manifest V3)  
* Maven (gestion des dépendances Java)  
* Git & GitHub (versioning)  
* Postman / cURL (tests de l’API)

🔐 Sécurité

L’authentification est gérée au niveau backend via un filtre dédié (Basic Auth admin:admin).  
Chaque requête vers l’API Gemini est vérifiée avant traitement, évitant les appels non autorisés.  
Un filtre CORS permet à l’extension Chrome d’accéder au serveur local sans problème.


📺 Démonstration Vidéo  
Vidéo de présentation et test réel de l’extension :  
🔗 [Lien Google Drive](https://docs.google.com/videos/d/1JmeYnSvPqn7P3La0z99kuNBgw0MKHAnX6CrFkaMZmUw/edit?usp=sharing) 
