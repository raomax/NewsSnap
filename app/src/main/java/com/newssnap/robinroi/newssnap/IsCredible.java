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
import java.util.Arrays;

//import javax.swing.text.Document;

/**
 *
 * @author Roi
 */
public class IsCredible {

    private static final int MAX_WORD_SPACE = 100;
    public static String input;
    public static String[] inputWords;
    public static int variance;
    private static String stopWords = "a,able,about,across,after,all,almost,also,am,among,an,and" +
            ",any,are,as,at,be,because,been,but,by,can,cannot,could,dear,did,do,does,either,else," +
            "ever,every,for,from,get,got,had,has,have,he,her,hers,him,his,how,however,i,if,in,into," +
            "is,it,its,just,least,let,like,likely,may,me,might,most,must,my,neither,no,nor,of," +
            "off,often,on,only,or,other,our,own,rather,said,say,says,she,should,since,so,some,than" +
            ",that,the,their,them,then,there,these,they,this,tis,to,too,twas,us,wants,was,we,were," +
            "what,when,where,which,while,who,whom,why,will,with,would,yet,you,your";
    private static String[]stopWrds = stopWords.split(",");
//http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=hello+world

    static URL searchURL;

    public static String[] getWebsites() throws Exception {

        String google = "http://ajax.googleapis.com/ajax/services/search/web?v=1.0&q=";

        String search = input;
        input =input.trim();
        input = removeStopWords(input);

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
    public static int calcVariance(ArrayList<Integer> positions){
        int cred = 0;
        int wordDistance = 0;
        int length = positions.size();
        for(int i=0;i<length;i++){
            if(i==length-1){
                wordDistance+=0;
            }else if(positions.get(i+1)-positions.get(i)>MAX_WORD_SPACE){
                wordDistance+=0;
                length--;
            }else {
                wordDistance+= Math.abs(positions.get(i+1)-positions.get(i));
            }
        }
        double mean= wordDistance/length;
        Log.i("CREDIBILITY_RAW_WORD_DISTANCE_AVERAGE",mean+"");
        cred = (int)(mean);

        cred= (int)Math.pow(cred, .5);
        cred = (int)Math.max(cred,.001);
        Log.i("CREDIBILITY_CALCVARIANCE METHOD",cred+"");
        return cred;
    }

    public static int checkWebsite(String website) throws Exception {
        int rating = 5;
        try {
            String url = website;
                if (url.contains("org") || url.contains("edu")) {
                    rating *= 3;
                } else if (url.contains(".gov")) {
                    rating *= 5;
            }
            /*Document text = Jsoup.connect(url).get();
             String info = text.toString();*/
            String[] words = websiteDatatoStringArray(website);

            ArrayList websiteWords = new ArrayList(Arrays.asList(words));
            ArrayList wordPositions = new ArrayList<Integer>();
            for (int i = 0; i < inputWords.length; i++) {
                for (int j = 0; j < websiteWords.size(); j++) {
                    if (inputWords[i].equalsIgnoreCase(websiteWords.get(j).toString())) {
                        websiteWords.remove(j);
                        wordPositions.add(j);
                    }
                }
            }
            //System.out.println(Arrays.toString(positions));
            variance = calcVariance(wordPositions);

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
        textContents = textContents.trim();
        textContents = removeStopWords(textContents);
        //System.out.println(textContents);
        inputWords = input.split(" ");
        return textContents.split(" ");
    }

//    private static ArrayList<String> findMatchedWords(String[] website){
//        ArrayList matchedWords = new ArrayList<String>();
//        return null;
//    }
    public static String removeStopWords(String input) {
        for(String stop:stopWrds){
                input = input.replace((" "+stop)+" ", "");
            }
        return input;
    }
}
