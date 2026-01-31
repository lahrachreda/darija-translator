package com.translator;

import jakarta.ws.rs.container.ContainerRequestContext;
import jakarta.ws.rs.container.ContainerRequestFilter;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.ext.Provider;
import java.io.IOException;
import java.util.Base64;
import java.util.List;
import java.util.StringTokenizer;

@Provider // Indique à JAX-RS que c'est un composant automatique
public class AuthFilter implements ContainerRequestFilter {

    private static final String AUTHORIZATION_HEADER_KEY = "Authorization";
    private static final String AUTHORIZATION_HEADER_PREFIX = "Basic ";

    // Pour l'exemple, login = admin, password = admin
    // Dans un vrai projet, utilisez une base de données !
    private static final String SECURE_USERNAME = "admin";
    private static final String SECURE_PASSWORD = "admin";

    @Override
    public void filter(ContainerRequestContext requestContext) throws IOException {
        List<String> authHeader = requestContext.getHeaders().get(AUTHORIZATION_HEADER_KEY);

        if (authHeader != null && !authHeader.isEmpty()) {
            String authToken = authHeader.get(0);
            if (authToken.startsWith(AUTHORIZATION_HEADER_PREFIX)) {
                String decodedString = new String(Base64.getDecoder().decode(authToken.substring(AUTHORIZATION_HEADER_PREFIX.length())));
                StringTokenizer tokenizer = new StringTokenizer(decodedString, ":");
                String username = tokenizer.nextToken();
                String password = tokenizer.nextToken();

                if (SECURE_USERNAME.equals(username) && SECURE_PASSWORD.equals(password)) {
                    return; // Authentification réussie, on laisse passer
                }
            }
        }

        // Si on arrive ici, l'accès est refusé
        Response unauthorizedStatus = Response
                .status(Response.Status.UNAUTHORIZED)
                .entity("{\"error\": \"Accès refusé. Veuillez vérifier vos identifiants.\"}")
                .build();

        requestContext.abortWith(unauthorizedStatus);
    }
}