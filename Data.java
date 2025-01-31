import java.util.ArrayList;
import java.util.List;
import java.util.Arrays;
import java.util.Scanner; 
import java.io.*;

public class Data { 


    static List<List<String>> MD = new ArrayList<List<String>>();
    static List<List<String>> CFA = new ArrayList<List<String>>(); 
    
    static List<Double> scoresMD = new ArrayList<Double>();
    static List<Double> scoresCFA = new ArrayList<Double>();

    static double meanMD = 0.0;
    static double sdMD = 0.0;
    static double meanCFA = 0.0;
    static double sdCFA = 0.0;

    public static void main(String[] args) {
        //read the data from the csv files and load it into lists 
        readCFA();
        readMcDonlads();

        // calculate the nutrition score for each menu item and add it to the list
        calcScoreMD();
        calcScoreCFA();

        // find the mean of the nutrition scores 
        meanMD = calculateMean(scoresMD);
        meanCFA = calculateMean(scoresCFA);

        // find the standard deviations of the nutrition scores 
        sdMD = calculateStandardDeviation(scoresMD);
        sdCFA = calculateStandardDeviation(scoresCFA);
        
        System.out.println();
        System.out.println("Mean of McDonald's nutritional scores: " + String.valueOf(meanMD));
        System.out.println("Standard Deviation of McDonald's nutritional scores: " + String.valueOf(sdMD));
        System.out.println();

        System.out.println("Mean of Chick-Fil-A's nutritional scores: " + String.valueOf(meanCFA));
        System.out.println("Standard Deviation of Chick-Fil-A's nutritional scores: " + String.valueOf(sdCFA));
        System.out.println();
    }

    public static void readMcDonlads() { 
        // reading the data from the csv file 
        try {
            FileReader fr = new FileReader("MD.csv");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));
                MD.add(lineData);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static void readCFA() { 
        // code
        // reading the data from the csv file 
        List<List<String>> data = new ArrayList<>(); 
        try {
            FileReader fr = new FileReader("CFA.csv");
            BufferedReader br = new BufferedReader(fr);
            String line = br.readLine();
            while (line != null) {
                List<String> lineData = Arrays.asList(line.split(","));
                CFA.add(lineData);
                line = br.readLine();
            }
            br.close();
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    public static double calcNutrition(double servingSize, double calories, double fat, double protein, double fiber, double sugar) {
        double value = 0.0; 
        if (calories/servingSize < 200) { 
            value = value + 10;
        }
        if (fat/servingSize < 10) { 
            value = value + 7;
        }
        if (protein/servingSize > 15) { 
            value = value + 7;
        }
        if (fiber/servingSize > 7) { 
            value = value + 5;
        }
        if (sugar/servingSize < 10) { 
            value = value + 5;
        }
        return value;
    }

    public static void calcScoreMD() { 
        // go through each menu item and calculate score
        for (int i = 1; i < MD.size(); i++) {

            // if there are any commas in the title
            int adder = 0;
            List<String> row = MD.get(i);;
            try {
                double value = Double.parseDouble(row.get(1));
            } catch (NumberFormatException e) {
                adder = 1;
            }
            
            double cal = Double.parseDouble(row.get(1 + adder));
            double fat = Double.parseDouble(row.get(3 + adder));
            double protein = Double.parseDouble(row.get(11 + adder));
            double fiber = Double.parseDouble(row.get(9 + adder));
            double sugar = Double.parseDouble(row.get(10 + adder));
            double value = calcNutrition(1, cal, fat, protein, fiber, sugar);
            scoresMD.add(value);
        }
    }

    public static void calcScoreCFA() { 
        // go through each menu item and calculate score
        for (int i = 1; i < CFA.size(); i++) {

            // if there are any commas in the title
            int adder = 0;
            List<String> row = CFA.get(i);;
            try {
                double value = Double.parseDouble(row.get(1));
            } catch (NumberFormatException e) {
                adder = 1;
            }

            // remove gram from serving size 
            String servingsize = row.get(1 + adder).replace("g", "");

            double SS = Double.parseDouble(servingsize);
            double cal = Double.parseDouble(row.get(2 + adder));
            double fat = Double.parseDouble(row.get(3 + adder));
            double protein = Double.parseDouble(row.get(row.size() - 1));
            double fiber = Double.parseDouble(row.get(9 + adder));
            double sugar = Double.parseDouble(row.get(10 + adder));
            double value = calcNutrition(SS/200, cal, fat, protein, fiber, sugar);
            scoresCFA.add(value);
        }
    }

    public static double calculateMean(List<Double> array) {
        // get the sum of array
        double sum = 0.0;
        for (double i : array) {
            sum += i;
        }
    
        // get the mean of array
        int length = array.size();
        double mean = sum / length;

        return mean;
    }

    public static double calculateStandardDeviation(List<Double> array) {

        // get the sum of array
        double sum = 0.0;
        for (double i : array) {
            sum += i;
        }
    
        // get the mean of array
        int length = array.size();
        double mean = sum / length;
    
        // calculate the standard deviation
        double standardDeviation = 0.0;
        for (double num : array) {
            standardDeviation += Math.pow(num - mean, 2);
        }
    
        return Math.sqrt(standardDeviation / length);
    }
}