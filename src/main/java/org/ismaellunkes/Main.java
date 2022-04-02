package org.ismaellunkes;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class Main {

    public static void main(String[] args) throws URISyntaxException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                                    .uri(new URI("https://imdb-api.com/en/API/SearchMovie/k_6siwg9d6/titanic"))
                                    .GET()
                                    .build();

        HttpClient client = HttpClient.newHttpClient();

        client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .thenAccept(System.out::println)
                .join();


    }

}
