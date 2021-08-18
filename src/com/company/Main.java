package com.company;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;


import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;

public class Main {

    public static void main(String[] args) throws IOException {


        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        System.out.println("Введите название статьи на английском");

        String API_CALL_TEMPLATE = "https://en.wikipedia.org/w/api.php?action=opensearch&search=";
        String name = reader.readLine();
        String API_KEY_TEMPLATE = "&limit=1&namespace=0&format=json";

        String urlString = API_CALL_TEMPLATE + name + API_KEY_TEMPLATE;

        URL url = new URL(urlString);

        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        connection.setRequestMethod("GET");
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");


        BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
        String inputLine;

        StringBuffer response = new StringBuffer();

        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }

        in.close();

        String link = response.substring(response.indexOf("[\"\"],[\"")+7, response.indexOf("\"]]"));


        Document doc = Jsoup.connect(link).get();

        Elements h1Elements = doc.getElementsByAttributeValue("class", "firstHeading");
        Elements text = doc.select(".mw-content-ltr p");

        String firstParagraph = text.text();

        System.out.println(h1Elements.text());

        int razm = 190, start = 0, size = firstParagraph.length();;

        while (true){
            if(razm >= size){
                System.out.println(firstParagraph.substring(start, size));
                break;
            }
            System.out.println(firstParagraph.substring(start, razm));
            start += 190;
            razm += 190;
        }

    }
}
