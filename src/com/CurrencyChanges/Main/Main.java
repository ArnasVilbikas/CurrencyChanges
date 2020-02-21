package com.CurrencyChanges.Main;

import com.CurrencyChanges.currencyInfo.currencyInfo;

import java.io.*;
import java.net.URL;
import java.util.*;



public class Main {

    public final static String csv_file_period_start= "C:/temporaryFolder/currency_stream_period_START.csv";
    public final static String csv_file_period_end= "C:/temporaryFolder/currency_stream_period_END.csv";


    public static ArrayList<currencyInfo> reader(String csv_file){

        char cvsSplitBy = ';';
        String line;
        String tempDouble;
        BufferedReader bufferedReader = null;
        ArrayList<currencyInfo> arrayList = new ArrayList<currencyInfo>();

        try {
            bufferedReader = new BufferedReader(new FileReader(csv_file));
            bufferedReader.readLine();
            while ((line = bufferedReader.readLine()) != null) {

                // use ';' as separator
                String[] store = line.split(String.valueOf(cvsSplitBy));


                //remove "" from end and beggining of the double number (which is a string data type at the time)
                tempDouble = store[2].substring(1,store[2].length()-1);
                arrayList.add(new currencyInfo(store[0],store[1],Double.parseDouble(tempDouble.replace(",", ".")),store[3]));

            }
        } catch(IOException e) {
            e.printStackTrace();
        } finally {
            if (bufferedReader != null) {
                try {
                    bufferedReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return arrayList;
    }


    public static void main(String[] args) {
        String url = "https://www.lb.lt/lt/currency/daylyexport/?csv=1&class=Eu&type=day&date_day=";
        ArrayList<currencyInfo> arrayListPeriodStart = new ArrayList<currencyInfo>();
        ArrayList<currencyInfo> arrayListPeriodEnd = new ArrayList<currencyInfo>();
        String urlPeriodBegin;
        String urlPeriodEnd;

        Scanner obj = new Scanner(System.in);


        System.out.println("Enter the period start (format 'yyyy-MM-dd'):");
        urlPeriodBegin = url + obj.nextLine();
        downloadFromURL(urlPeriodBegin, csv_file_period_start);

        System.out.println("Enter the period end (format 'yyyy-MM-dd'):");
        urlPeriodEnd = url + obj.nextLine();
        downloadFromURL(urlPeriodEnd, csv_file_period_end);

        arrayListPeriodEnd = reader(csv_file_period_end);
        for (currencyInfo str : arrayListPeriodEnd) {
            System.out.println(str);
        }

        arrayListPeriodStart = reader(csv_file_period_start);
        for (currencyInfo str : arrayListPeriodStart) {
            System.out.println(str);
        }
    }


    public static void downloadFromURL(String url, String csv_file){
        try {
            downloadUsingStream(url, csv_file);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private static void downloadUsingStream(String urlStr, String file) throws IOException{
        URL url = new URL(urlStr);
        BufferedInputStream bis = new BufferedInputStream(url.openStream());
        byte[] buffer = new byte[1024];
        int count=0;

        if((count=bis.read(buffer,0,1024)) > 62){// > 62 in case the database is not updated on that specific day (meaning downloaded csv file only has headers and no actual currency data)
            FileOutputStream fis = new FileOutputStream(file);
            fis.write(buffer,0,count);
            while((count = bis.read(buffer,0,1024)) != -1){ //-1 to decide that file has ended
                fis.write(buffer, 0, count);
            }
            fis.close();
        }else{// in case of empty csv file from url - we change url's date by 1 day
            urlChange(urlStr, file);
        }

        bis.close();
    }

    private static void urlChange(String url, String file){// in case of empty csv file from url - we change url's date by 1 day (if first day of month then month-1 day=31 and so on)

        String dayStr;
        String monthStr;
        String yearStr;
        String oldStr;
        String newStr;

        int temporalIntDay;
        int temporalIntMonth;
        int temporalIntYear;

        dayStr = url.substring(url.length()-2);
        monthStr = url.substring(url.length()-5,url.length()-3);
        yearStr = url.substring(url.length()-10,url.length()-6);
        oldStr = url.substring(url.length()-10);

        temporalIntDay = Integer.parseInt(dayStr);
        temporalIntMonth = Integer.parseInt(monthStr);
        temporalIntYear = Integer.parseInt(yearStr);

        if(temporalIntDay != 1){
            temporalIntDay = temporalIntDay-1;
        }else{

            if(temporalIntMonth != 1){
                temporalIntMonth = temporalIntMonth-1;
                temporalIntDay = 31;
            }else{
                temporalIntYear = temporalIntYear-1;
                temporalIntMonth = 12;
                temporalIntDay = 31;
            }
        }
        if(temporalIntDay<10){//in case the day is below 10 then we add '0' in front of it (so its 01'st month and not just 1'st)
            dayStr = "0" + Integer.toString(temporalIntDay);
            if(temporalIntMonth<10){//same logic if both day and month variables are below 10
                monthStr = "0" + Integer.toString(temporalIntMonth);
                newStr = Integer.toString(temporalIntYear) + "-" + monthStr + "-" + dayStr;
            }else{//if just day and not month is below 10
                newStr = Integer.toString(temporalIntYear) + "-" + Integer.toString(temporalIntMonth) + "-" + dayStr;
            }
        }else if(temporalIntMonth<10){//if just month and not day is below 10
            monthStr = "0" + Integer.toString(temporalIntMonth);
            newStr = Integer.toString(temporalIntYear) + "-" + monthStr + "-" + Integer.toString(temporalIntDay);
        }else{
            newStr = Integer.toString(temporalIntYear) + "-" + Integer.toString(temporalIntMonth) + "-" + Integer.toString(temporalIntDay);
        }

        url=url.replace(oldStr,newStr);

        downloadFromURL(url, file);
    }
}

