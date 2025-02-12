package com.example.projetandroid;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Locale;
import java.util.Map;


import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Modèle de l'application.
 * permet de récupérer pour chaque requête la snowtam cryptée, la snowtam décrypté et les coordonnées de l'aéroport pour google map *
 */

public class Snowtam{
    private String snowtam;
    private static InputStream file;
    private static BufferedReader bufferedReader;
    private static String latitude;
    private static String longitude;

    /**
     * Constructeur qui prend en paramètre un code OACI et un fichier texte contenant les noms des aéroports et les coordonnées
     * @param snowtam
     * @param file
     */
    public Snowtam(String snowtam, InputStream file) {
        this.snowtam = snowtam;
        this.file = file;
        this.bufferedReader = new BufferedReader(new InputStreamReader(file));
    }

    /**
     * ToString
     * @return a snowtam cryptée, la snowtam décrypté et les coordonnées de l'aéroport pour google map
     */
    @Override
    public String toString() {

        try {
            return getDecryptedSnowtam(getSnowtamDictionnary(snowtam)) + getCoordinates() + "#" + snowtam;
        } catch (IOException e) {
            e.printStackTrace();
            return "IO ERROR";
        }
    }


    /**
     * Renvoie les coordonnées de l'aéroport
     * @return
     */
    private String getCoordinates(){
        return latitude+"/"+longitude;
    }

    /**
     * Décrypte la snowtam
     * @param snowtamDictionnary
     * @return
     * @throws IOException
     */
    private static String getDecryptedSnowtam(Map<Character,String> snowtamDictionnary) throws IOException {
        String decryptedSnowtam = "";
        Iterator it = snowtamDictionnary.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry) it.next();
            switch(pair.getKey().toString().charAt(0)){
                case 'A':
                    decryptedSnowtam += getAirportName(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'B':
                    decryptedSnowtam += getDateHour(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'C':
                    decryptedSnowtam += "Runway designator : " + "\n" + getRunwayDesignator(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'D':
                    decryptedSnowtam += "Cleared runway length : " + "\n" + getClearedRunwayLength(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'E':
                    decryptedSnowtam += "Cleared runway width : " + "\n" + getClearedRunwayWidth(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'F':
                    decryptedSnowtam += "Deposits over total runway length : " + "\n" + getDepositsOverTotalRunwayLength(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'G':
                    decryptedSnowtam += "Mean depth deposit for each third of total runway length  : " + "\n" + getMeanDepthDeposit(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'H':
                    decryptedSnowtam += "Friction measurements and friction measurement device : " + "\n" + getFrictionMeasurements(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'J':
                    decryptedSnowtam += "Critical snowbanks : " + "\n" + getCriticalSnowbanks(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'K':
                    decryptedSnowtam += "Runway lights : " + "\n" + getRunwayLight(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'L':
                    decryptedSnowtam += "Further clearance : " + "\n" + getFurtherClearance(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'M':
                    decryptedSnowtam += "further clearance expected to be completed : " + "\n" + getFurtherClearanceCompletionTime(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'N':
                    decryptedSnowtam += "Taxiway : " + "\n" + getTaxiway(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'P':
                    decryptedSnowtam += "Taxiway snowbanks : " + "\n" + getTaxiwaySnowbanks(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'R':
                    decryptedSnowtam += "Apron : " + "\n" + getApron(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'S':
                    decryptedSnowtam += "Next planned observation/measurement : " + "\n" + getNextPlannedMeasurementTime(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                case 'T':
                    decryptedSnowtam += "Plain-language remarks  : " + "\n" + getPlainLanguageRemarks(pair.getValue().toString().replace(" ","")) + "\n";
                    break;
                default:

            }
            it.remove();
        }
        return decryptedSnowtam;

    }

    /**
     * Crée un dictionnaire ayant pour clé une lettre et en valeur, les données correspondantes
     * Cela permet de faciliter le traitement dans la methode getDecryptedSnowtam
     * @param snowtam
     * @return
     */
    private static Map<Character,String> getSnowtamDictionnary(String snowtam){
        // Expressions régulières pour le parse de la snowtam
        String delimiterLetter = "\\)";
        String delimiterContent = ".\\)";

        // Création d'un tableau pour les lettres et un tableau pour le contenu
        String[] tokensContent = snowtam.split(delimiterLetter);
        char[] tokensLetter = new char[tokensContent.length-1];
        for(int i=0; i <tokensLetter.length; i++) {
            tokensLetter[i] = tokensContent[i].charAt(tokensContent[i].length()-1);
        }
        tokensContent = snowtam.split(delimiterContent);
        tokensContent = Arrays.copyOfRange(tokensContent, 1, tokensContent.length);

        // Création d'un dictionnaire pour simplifier le traitement qui va suivre
        Map<Character,String> dictionnary = new LinkedHashMap<Character, String>();
        for(int i=0; i <tokensLetter.length; i++){
            dictionnary.put(tokensLetter[i],tokensContent[i].replace("\n","").replace(".","").replace(")",""));
        }
        return dictionnary;
    }

    private static String getAirportName(String data) throws IOException {


        String line;
        while ((line = bufferedReader.readLine()) != null) {
            String[] liste = line.split(",");
            if(liste[5].equals("\"" + data + "\"") ) {
                latitude = liste[6];
                longitude = liste[7];
                file.reset();
                return liste[1].replace("\"", "");
            }
            else if(liste[6].equals("\"" + data + "\"")) {
                latitude = liste[7];
                longitude = liste[8];
                file.reset();
                return liste[1].replace("\"", "");
            }
        }
        return "can't find airport";
    }

    private static String getDateHour(String data){
        String[] months = {"January","February","March","April","May","June","July","August","September","October","November","December"};
        String day = data.substring(2,4);
        String month = "";
        String heure = data.substring(4,6) + "h" + data.substring(6,8) + " UTC";

        int index = Integer.parseInt(data.substring(0,2));
        month = months[index-1];

        return day + " " + month + " " + heure;
    }

    private static String getRunwayDesignator(String data){
        return "RUNWAY " + data;
    }

    private static String getClearedRunwayLength(String data){
        return "CLEARED RUNWAY LENGTH " + data + "M";
    }

    private static String getClearedRunwayWidth(String data){
        String width;

        if(data.length() == 1)
            width = data;
        else
            width = data.substring(0,data.length()-1);

        if(data.charAt(data.length()-1) == 'R')
            return "CLEARED RUNWAY WIDTH " + width + "M RIGHT";
        else if(data.charAt(data.length()-1) == 'L')
            return "CLEARED RUNWAY WIDTH " + width + "M LEFT";
        else{
            return "CLEARED RUNWAY WIDTH " + width + "M";
        }
    }

    public static String getDepositsOverTotalRunwayLength(String data){
        String[] weathers = {"CLEAR AND DRY","DAMP","WATER PATCHES","RIME OR FROST COVERED","DRY SNOW","WET SNOW","SLUSH","ICE","COMPACTED OR ROLLED SNOW","FROZEN RUTS OR RIDGES","NON-EXISTENT"};
        String[] tab = data.split("/");

        for(int i = 0; i<3; i++){
            if(tab[i].equals("NIL") || (Integer.parseInt(tab[i])<0 || Integer.parseInt(tab[i])>8)){
                tab[i] = "10";
            }        }
        int[] indexes = {Integer.parseInt(tab[0]),Integer.parseInt(tab[1]),Integer.parseInt(tab[2])};

        return "Threshold: " + weathers[indexes[0]] + " / Mid runway: " + weathers[indexes[1]] + " / Roll out: " + weathers[indexes[2]] ;
    }

    private static String getMeanDepthDeposit(String data){
        String[] tab = data.split("/");

        for(int i=0; i<3 ; i++){
            if(tab[i].equals("XX")){
                tab[i] = "not significant";
            }
            else{
                tab[i] += "mm";
            }
        }
        return "MEAN DEPTH Threshold: " + tab[0] + " / Mid runway: " + tab[1] + " / Roll out: " + tab[2];
    }

    private static String getFrictionMeasurements(String data){
        String[] instrumentsAbbreviation = {"BRD","GRT","MUM","RFT","SFH","SFL","SKH","SKL","TAP"};
        String[] instruments = {"Brakemeter-Dynometer","Grip tester","Mu-meter","Runway frition tester","Surface friction tester (high-pressure tire)","Surface friction tester (low-pressure tire)","Skiddometer (high-pressure tire)","Skiddometer (low-pressure tire)","Tapley meter"};

        String instrument = "";
        for(int i=0; i<instruments.length; i++){
            if(instrumentsAbbreviation[i].equals(data.substring(data.length()-3,data.length()))){
                instrument = instruments[i];
                break;
            }
        }
        String[] numbers;
        if(instrument.equals("")){
            numbers = data.substring(0,data.length()).split("/");
            instrument = "¯\\_(ツ)_/¯";
        }
        else{
            numbers = data.substring(0,data.length()-3).split("/");
        }


        for(int i=0; i<numbers.length ;i++){
            int number = Integer.parseInt(numbers[i]);
            if(number >= 40 || number == 5)
                numbers[i] = "GOOD";
            else if((number <= 39 && number >= 36) || number == 4)
                numbers[i] = "MEDIUM TO GOOD";
            else if((number <= 35 && number >= 30) || number == 3)
                numbers[i] = "MEDIUM";
            else if((number <= 29 && number >= 26) || number == 2)
                numbers[i] = "MEDIUM TO POOR";
            else if((number <= 25 && number >= 10) || number == 1)
                numbers[i] = "POOR";
            else if(number == 9){
                numbers[i] = "impossible to measure";
            }
        }

        return "BRAKING ACTION Threshold: " + numbers[0] + " / Mid runway: " + numbers[1] + " / Roll Out: " + numbers[2] + " Instrument: " + instrument;
    }

    private static String getCriticalSnowbanks(String data){
        String[] tab = data.split("/");
        String height = tab[0];
        tab = tab[1].split("");

        String distance = "";
        String side = "";
        int i = 0;
        while(!(tab[i].equals("R") || tab[i].equals("L"))){
            distance += tab[i];
            i++;
        }
        if(tab.length - i == 2)
            side = "LEFT AND RIGHT";
        else if(tab[i].equals("L"))
            side = "LEFT";
        else if(tab[i].equals("R"))
            side = "RIGHT";

        return "CRITICAL SNOW BANK " + height + "cm / " + distance + "m " + side + " of Runway";


    }

    private static String getRunwayLight(String data){
        data = data.substring(3,data.length());
        String side = "";
        if(data.length() == 1){
            if(data.equals("L"))
                side = "LEFT";
            else if(data.equals("R"))
                side = "RIGHT";
        }
        else
            side = "LEFT AND RIGHT";

        return "Lights obscured : YES " + side + " of RUNWAY";

    }

    private static String getFurtherClearance(String data){
        if(data.equals("TOTAL"))
            return "FURTHER CLEARANCE TOTAL";
        else{
            String[] HeightAndWidth = data.split("/");
            return "FURTHER CLEARANCE " + HeightAndWidth[0] + "m / " + HeightAndWidth[1] + "m";
        }
    }

    private static String getFurtherClearanceCompletionTime(String data){
        String hh = data.substring(0,2);
        String mm = data.substring(2,4);

        return "Anticipated time of completion " + hh + "h" + mm + " UTC";
    }

    private static String getTaxiway(String data){
        return data;
    }

    private static String getTaxiwaySnowbanks(String data){
        String distance  = data.substring(3,data.length());
        return "SNOW BANKS: YES SPACE " + distance + "m";
    }

    private static String getApron(String data){
        return data;
    }

    private static String getNextPlannedMeasurementTime(String data){
        return "NEXT OBSERVATION " + getDateHour(data);
    }

    private static String getPlainLanguageRemarks(String data){
        return data;
    }
}
