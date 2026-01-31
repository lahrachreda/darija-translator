package com.translator;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import org.json.JSONArray;
import org.json.JSONObject;

public class GeminiService {

    // TA CLE API (vérifie qu’elle est valide sur https://aistudio.google.com/app/apikey)
    private static final String API_KEY = "AIzaSyBS2SPohlHY6_hBQ7_mTzd-Y9Wgj0Sbb7U";

    // Modèle qui marche en janvier 2026
    private static final String MODEL = "gemini-2.5-flash";  // ← change ici si besoin
    private static final String API_URL = "https://generativelanguage.googleapis.com/v1/models/" + MODEL + ":generateContent?key=" + API_KEY;

    public String translateToDarija(String englishText) throws Exception {
        if (englishText == null || englishText.trim().isEmpty()) {
            throw new IllegalArgumentException("Texte vide");
        }

        // Prompt plus précis pour du darija authentique
        String prompt = "Traduire uniquement en darija marocain parlé (style Casablanca/Rabat), naturel, sans ajouter aucun commentaire ni anglais, juste la traduction : " + englishText;

        JSONObject part = new JSONObject().put("text", prompt);
        JSONObject content = new JSONObject().put("parts", new JSONArray().put(part));
        JSONObject requestBody = new JSONObject().put("contents", new JSONArray().put(content));

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(API_URL))
                .header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.ofString(requestBody.toString()))
                .build();

        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        System.out.println("Gemini statut : " + response.statusCode());
        System.out.println("Gemini réponse complète : " + response.body());

        if (response.statusCode() != 200) {
            throw new RuntimeException("Gemini error " + response.statusCode() + " → " + response.body());
        }

        JSONObject json = new JSONObject(response.body());

        if (!json.has("candidates") || json.getJSONArray("candidates").length() == 0) {
            throw new RuntimeException("Aucune réponse candidate de Gemini");
        }

        return json.getJSONArray("candidates")
                .getJSONObject(0)
                .getJSONObject("content")
                .getJSONArray("parts")
                .getJSONObject(0)
                .getString("text")
                .trim();
    }
}