/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package javaapplication3;

import java.util.Scanner;

/**
 *
 * @author Roi
 */
public class Input {
    public static void main(String[] args) throws Exception{
        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        long beginTime = System.currentTimeMillis();
        
        IsCredible.input = input;
        IsCredible.removePronouns();
        String[] websites = IsCredible.getWebsites();
        
        double count = 0;
        double total =0;
        double varianceTotal = 0;
        for(String website:websites){
            //System.out.println(website);
            if(IsCredible.checkWebsite(website)>-1){
            
                count+=IsCredible.checkWebsite(website);
            total++;
            varianceTotal+=IsCredible.variance;
            }
        }
        count /=total;
        varianceTotal/=total;
        
        count*=IsCredible.results();
        double scalar = (100*Math.pow(input.split(" ").length,1.4)/varianceTotal);
        
        count=scalar*count;
        
        //System.out.println(IsCredible.checkWebsite("http://en.wikipedia.org/wiki/%22Hello,_world!%22_program"));
        if(input.split(" ").length<4){
            count = count/10;
        }
        System.out.println(count);
        count = Math.max(0, Math.min(count, 10));
        System.out.println(count);
        //System.out.println("this took "+ (System.currentTimeMillis()-beginTime)/1000);
        //System.out.println(varianceTotal);
    }
}
