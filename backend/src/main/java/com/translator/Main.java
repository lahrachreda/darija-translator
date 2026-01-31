package com.translator;

import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import java.io.IOException;
import java.net.URI;

public class Main {

    // Port fixe et URL de base (change ici si besoin)
    public static final String BASE_URI = "http://localhost:8081/api/";

    public static HttpServer startServer() {
        final ResourceConfig rc = new ResourceConfig()
                .packages("com.translator")           // scanne toutes les classes du package
                .register(CORSFilter.class)           // toujours actif (Chrome extension)
                // .register(AuthFilter.class)        // ← commente cette ligne pour tester sans auth
                ;

        return GrizzlyHttpServerFactory.createHttpServer(URI.create(BASE_URI), rc);
    }

    public static void main(String[] args) throws IOException {
        final HttpServer server = startServer();
        System.out.println("Serveur démarré sur : " + BASE_URI);
        System.out.println("WADL : " + BASE_URI + "application.wadl");
        System.out.println("Test GET : " + BASE_URI + "translate?text=hello");
        System.out.println("Appuyez sur Entrée pour arrêter...");
        System.in.read();
        server.shutdownNow();
    }
}