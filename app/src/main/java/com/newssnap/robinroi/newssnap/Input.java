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
    public static String[] websites;
    public static double [] count = new double[4];
    public static double sum = 0;
    public static void main(String input) throws Exception{IsCredible.input = input;

       websites = IsCredible.getWebsites();
        Threads t1 = new Threads(websites[0],input,0);
        Threads t2 = new Threads(websites[1],input,1);
        Threads t3 = new Threads(websites[2],input,2);
        Threads t4 = new Threads(websites[3],input,3);
        t1.start();
        t2.start();
        t3.start();
        t4.start();
        t1.join();
        t2.join();
        t3.join();
        t4.join();

        int length = count.length;
        for(double i:count){
            if(Double.isNaN(i)){
                length--;
            }else if(length ==0){
                sum = -1;
            }else
                sum+=i;
        }
        Log.w("COUNT_SUM", sum+"");
        Log.w("COUNT_LENGTH", length+"");
        sum/=length;
        sum *=15;
        sum = Math.max(Math.min(10,sum),0);



    }
}
