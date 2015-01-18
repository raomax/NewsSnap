/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.newssnap.robinroi.newssnap;

import android.util.Log;

import com.google.gson.Gson;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;

//import javax.swing.text.Document;

/**
 *
 * @author Roi
 */
public class IsCredible {

    public static String input;
    public static String[] inputWords;
//http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=hello+world

    public static void removePronouns() {
        input = input.replace(" a ", "");
        input = input.replace(" the ", "");
        input = input.replace(" it ", "");
        input = input.replace(" he ", "");
        input = input.replace(" she ", "");
        
    }
    static URL searchURL;

    public static String[] getWebsites() throws Exception {

        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";

        String search = input;
        //System.out.println(search);
        String charset = "UTF-8";

        URL url = new URL(google + URLEncoder.encode(search, charset));
        searchURL = url;
        Reader reader = new InputStreamReader(url.openStream(), charset);
        com.newssnap.robinroi.newssnap.GoogleResults results = new Gson().fromJson(reader,
                com.newssnap.robinroi.newssnap.GoogleResults.class);

        // Show title and URL of 1st result.
        //System.out.println(results.getResponseData().getResults().get(0).getTitle());
        //System.out.println(results.getResponseData().getResults().get(0).getUrl());
        String[] websites = new String[4];

        for (int i = 0; i < 4; i++) {
            try{
            websites[i] = results.getResponseData().getResults().get(i).getUrl();
            }
            catch(Exception E){
                websites[i]="";
            }
        }

        return websites;

    }

    /*public static int getResultsCount(final String query) throws IOException {
     final URL url = new URL("https://www.google.com/search?q=" + URLEncoder.encode(query, "UTF-8"));
     final URLConnection connection = url.openConnection();
     connection.setConnectTimeout(60000);
     connection.setReadTimeout(60000);
     connection.addRequestProperty("User-Agent", "Mozilla/5.0");
     final Scanner reader = new Scanner(connection.getInputStream(), "UTF-8");
     while(reader.hasNextLine()){
     final String line = reader.nextLine();
     if(!line.contains("<div id=\"resultStats\">"))
     continue;
     try{
     return Integer.parseInt(line.split("<div id=\"resultStats\">")[1].split("<")[0].replaceAll("[^\\d]", ""));
     }finally{
     reader.close();
     }
     }
     reader.close();
     return 0;
     }*/
    public static double results() throws Exception {
        Document text = Jsoup.connect(searchURL.toString()).get();
        String info = text.toString();
        //System.out.println(info);
        Log.w("SEARCHES",searchURL.toString());
        int beginIndex = info.indexOf("resultCount\":") + "resultCount\":".length() + 1;
        int endIndex = info.indexOf("\"", beginIndex);
        String results = (info.substring(beginIndex, endIndex));
        //System.out.println(info);
        results = results.replace(",", "");
        Log.w("RESULTS",results);
        return Math.min(Math.log(Double.parseDouble(results)) / 5, 1.6);
        //System.out.println(results);
    }
    public static int calcVariance(int[] positions){
        int sum = 0;
        for(int num: positions){
            sum+= num;
        }
        double mean= sum/positions.length;
        sum=0;
        for(int num:positions){
          sum+=Math.pow((num-mean), 2);
        }
        sum/=(positions.length-1);
        sum = (int)Math.pow(sum, .5);
        return (int)sum;
    }
    public static int variance;
    public static int checkWebsite(String website) throws Exception {
        int rating = 5;
        try {
            String url = website;
                if (url.contains("org") || url.contains("edu")) {
                    rating *= 1.3;
                } else if (url.contains(".gov")) {
                    rating *= 1.4;
            }
            /*Document text = Jsoup.connect(url).get();
             String info = text.toString();*/
            String[] words = websiteDatatoStringArray(website);
            int[] positions = new int[inputWords.length];
            for (int i = 0; i < inputWords.length; i++) {
                for (int j = 0; j < words.length; j++) {
                    if (inputWords[i].equalsIgnoreCase(words[j])) {
                        positions[i] = j;
                    }
                }
            }
            //System.out.println(Arrays.toString(positions));
            variance = calcVariance(positions);

            int total = 0;
            int count = words.length;
            for (String word : words) {
                total += word.length();
            }
            double avgWordLength = total / count;
            //System.out.println(avgWordLength);
            if (avgWordLength > 5) {
                rating *= 1.2;
            } else {
                rating /= 1.2;
            }
            return rating;
        } catch (Exception e) {
            return -1;
        }
    }

    private static String[] websiteDatatoStringArray(String website) throws IOException {
        Document doc = Jsoup.connect(website).get();
        String textContents = doc.text().toString();
        //System.out.println(textContents);
        inputWords = input.split(" ");
        return textContents.split(" ");
    }

    private static ArrayList<String> findMatchedWords(String[] website){
        ArrayList matchedWords = new ArrayList<String>();
        return null;
    }
}
