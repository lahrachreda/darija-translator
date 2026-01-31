<?php
// Configuration de l'API Java
$apiUrl = "http://localhost:8081/api/translator/translate";
$username = "admin"; 
$password = "admin";

// Récupération du texte
$textToTranslate = $_POST['text'];

// Préparation des données JSON
$data = json_encode(["text" => $textToTranslate]);

// Configuration de la requête HTTP (sans cURL)
$options = [
    "http" => [
        "header"  => "Content-Type: application/json\r\n" .
                     "Authorization: Basic " . base64_encode("$username:$password") . "\r\n",
        "method"  => "POST",
        "content" => $data,
        "ignore_errors" => true // Pour récupérer le contenu même en cas d'erreur 401/500
    ]
];

// Création du contexte
$context  = stream_context_create($options);

// Exécution de la requête
$response = file_get_contents($apiUrl, false, $context);

// Analyse de la réponse
if ($response === FALSE) {
    echo "<p style='color:red'>Erreur de connexion au serveur Java.</p>";
} else {
    // On vérifie les en-têtes pour voir si c'est un succès (200 OK)
    $responseHeaders = $http_response_header[0];
    
    if (strpos($responseHeaders, "200 OK") !== false) {
        $jsonResult = json_decode($response, true);
        if (isset($jsonResult['translation'])) {
            echo "<strong>" . htmlspecialchars($jsonResult['translation']) . "</strong>";
        } else {
            echo "Réponse reçue mais pas de traduction trouvée.";
        }
    } elseif (strpos($responseHeaders, "401") !== false) {
         echo "<p style='color:red'>Erreur 401 : Mot de passe ou identifiant incorrect.</p>";
    } else {
         echo "<p style='color:red'>Erreur serveur : $responseHeaders</p>";
    }
}
?>
