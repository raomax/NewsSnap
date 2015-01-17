/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package com.newssnap.robinroi.newssnap;

import android.util.Log;

/**
 *
 * @author Roi
 */
public class Input {
    public static void main(String input) throws Exception{

        long beginTime = System.currentTimeMillis();

        com.newssnap.robinroi.newssnap.IsCredible.input = input;
        com.newssnap.robinroi.newssnap.IsCredible.removePronouns();
        String[] websites = com.newssnap.robinroi.newssnap.IsCredible.getWebsites();
        
        double count = 0;
        double total =0;
        double varianceTotal = 0;
        for(String website:websites){
            //System.out.println(website);
            if(com.newssnap.robinroi.newssnap.IsCredible.checkWebsite(website)>-1){
            
                count+= com.newssnap.robinroi.newssnap.IsCredible.checkWebsite(website);
            total++;
            varianceTotal+= com.newssnap.robinroi.newssnap.IsCredible.variance;
            }
        }
        count /=total;
        varianceTotal/=total;
        
        count*= com.newssnap.robinroi.newssnap.IsCredible.results();
        double scalar = (100*Math.pow(input.split(" ").length,1.4)/varianceTotal);
        
        count=scalar*count;
        
        //System.out.println(IsCredible.checkWebsite("http://en.wikipedia.org/wiki/%22Hello,_world!%22_program"));
        if(input.split(" ").length<4){
            count = count/10;
        }
        Log.w("COUNT",""+count);
        count = Math.max(0, Math.min(count, 10));
        Log.w("COUNT",""+count);
        //System.out.println("this took "+ (System.currentTimeMillis()-beginTime)/1000);
        //System.out.println(varianceTotal);
    }
}
