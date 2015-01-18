package com.newssnap.robinroi.newssnap;

import android.util.Log;

/**
 * Created by Robin Onsay on 1/17/2015.
 */
public class Threads extends Thread {
    private String url;
    private String input;
    private int index;
    public Threads(String url, String input){
        this.url = url;
        this.input = input;
    }
    public Threads(String url, String input,int index){
        this.url = url;
        this.input = input;
        this.index = index;
    }

    @Override
    public void run() {
        double count = 0;
        double varianceTotal = 0;
        try {
            if(IsCredible.checkWebsite(url)>-1){

                count= IsCredible.checkWebsite(url);
                varianceTotal = IsCredible.variance;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            count*= IsCredible.results()*0.1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        double scalar = (Math.pow(input.split(" ").length,1.8)/varianceTotal);

        count=scalar*count;

        //System.out.println(IsCredible.checkWebsite("http://en.wikipedia.org/wiki/%22Hello,_world!%22_program"));
        if(input.split(" ").length<4){
          count= count/10;
        }

        Input.count[index] = count;
       Log.i("COUNT_ON" + index, count + "");
    }
}
