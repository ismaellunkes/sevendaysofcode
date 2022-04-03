package org.ismaellunkes;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.net.URI;
import java.net.URISyntaxException;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Main {

    private static final Pattern TITLE = Pattern.compile("title");
    private static final Pattern FULLTITLE = Pattern.compile("fullTitle");
    private static final Pattern IMAGE = Pattern.compile("\"image\":");
    private static final Pattern CREW = Pattern.compile("\"crew\":");

    public static void main(String[] args) throws URISyntaxException, JsonProcessingException {

        HttpRequest httpRequest = HttpRequest.newBuilder()
                .uri(new URI("https://imdb-api.com/pt/API/Top250Movies/k_6siwg9d6"))
                .GET()
                .build();

        HttpClient client = HttpClient.newHttpClient();

        String textoConsulta = client.sendAsync(httpRequest, HttpResponse.BodyHandlers.ofString())
                .thenApply(HttpResponse::body)
                .join();

        String dados = textoConsulta.substring(textoConsulta.indexOf("[") + 1, textoConsulta.lastIndexOf("]"));

        // Tratando o string com recursos nativos da linguagem Java
        Matcher inicioTitle = TITLE.matcher(dados);
        Matcher fimTitle = FULLTITLE.matcher(dados);
        Matcher inicioImage = IMAGE.matcher(dados);
        Matcher fimImage = CREW.matcher(dados);
        List<String>titles = new ArrayList<>();
        List<String>urlImages = new ArrayList<>();
        System.out.println("-- FILMES ---");
        int indice = 0;
        while (inicioTitle.find() && fimTitle.find()) {
            indice++;
            titles.add(indice+". " + dados.substring(inicioTitle.end() + 3, fimTitle.start() - 3));
        }
        titles.stream()
                .forEach(title -> System.out.println(title));

        System.out.println("\n");
        System.out.println("-- LINKS ---");
        indice = 0;
        while (inicioImage.find() && fimImage.find()) {
            indice++;
            urlImages.add(indice+". " + dados.substring(inicioImage.end() + 1, fimImage.start() - 2));
        }

        urlImages.stream()
                .forEach(urlImage -> System.out.println(urlImage));

        //Tratando o JSON e convertando para Objeto com a biblioteca GSON
        System.out.println();
        System.out.println("-- JSON TO OBJECT com GSON ---");
        String[] moviesArray = dados.split("[{]");
        List<Movie> movies = new ArrayList<>();
        Gson gson = new Gson();
        for (int i = 1; i < moviesArray.length; i++) {
            movies.add(gson.fromJson("{"+moviesArray[i].replace("},","}"), Movie.class));
        }
        movies.stream().forEach(movie -> System.out.println("\n"+movie));

       //Converter JSON usando Jackson
        System.out.println();
        System.out.println("-- JSON TO OBJECT com Jackson ---");
        List<Movie> moviesJackson = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();
        for (int i = 1; i < moviesArray.length; i++) {
            moviesJackson.add(objectMapper.readValue("{"+moviesArray[i].replace("},","}"), Movie.class));
        }
        moviesJackson.stream().forEach(movie -> System.out.println("\n"+movie));

        //Modelo JSON
        /*{
            "id": "tt0111161",
                "rank": "1",
                "title": "The Shawshank Redemption",
                "fullTitle": "The Shawshank Redemption (1994)",
                "year": "1994",
                "image": "https://imdb-api.com/images/original/MV5BMDFkYTc0MGEtZmNhMC00ZDIzLWFmNTEtODM1ZmRlYWMwMWFmXkEyXkFqcGdeQXVyMTMxODk2OTU@._V1_Ratio0.6716_AL_.jpg",
                "crew": "Frank Darabont (dir.), Tim Robbins, Morgan Freeman",
                "imDbRating": "9.2",
                "imDbRatingCount": "2568069"
        }*/

    }
}
