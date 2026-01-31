package com.translator;

import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/translate")
public class TranslatorResource {

    private final GeminiService geminiService = new GeminiService();

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response translate(TranslationRequest request) {
        if (request == null || request.text == null || request.text.trim().isEmpty()) {
            return Response.status(400)
                    .entity(new ErrorResponse("Texte requis"))
                    .build();
        }

        try {
            String translation = geminiService.translateToDarija(request.text);
            return Response.ok(new TranslationResponse(translation)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(500)
                    .entity(new ErrorResponse("Erreur interne : " + e.getMessage()))
                    .build();
        }
    }

    // Classes JSON simples
    public static class TranslationRequest {
        public String text;
    }

    public static class TranslationResponse {
        public String translation;
        public TranslationResponse(String translation) { this.translation = translation; }
    }

    public static class ErrorResponse {
        public String error;
        public ErrorResponse(String error) { this.error = error; }
    }
}