package ex01;

import java.io.*;
import java.util.*;

public class WordsAnalysis {

    private final TreeSet<String> dictionary;
    private final Map<String, Integer[]> map;

    public WordsAnalysis() {
        dictionary = new TreeSet<>();
        map = new HashMap<>();
    }

    public double getSimilarity(final String fileOne, final String fileTwo) {
        readFile(fileOne, 0);
        readFile(fileTwo, 1);
        createDictionary();
        return similarityCalculation();
    }

    private void createDictionary() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(Constants.DICTIONARY))) {
            int count = 1;
            for (String word : dictionary) {
                bw.append(word);
                bw.append(count++ % 10 == 0 ? "\n" : count <= dictionary.size() ? ", " : "");
            }
        } catch (IOException e) {
            System.out.println("Failed to create file");
        }
    }

    private double similarityCalculation() {
        int numerator = 0;
        double denominator;
        double denominatorA = 0;
        double denominatorB = 0;
        for (String word : map.keySet()) {
            int valOne = map.get(word)[0];
            int valTwo = map.get(word)[1];
            numerator += valOne * valTwo;
            denominatorA += valOne * valOne;
            denominatorB += valTwo * valTwo;
        }
        denominator = Math.sqrt(denominatorA) * Math.sqrt(denominatorB);
        return Math.floor((numerator / denominator) * Constants.ROUND) / Constants.ROUND;
    }

    private void readFile(String file, int index) {
        try (BufferedReader br = new BufferedReader(new FileReader(file))) {
            String str;
            String[] line;
            while ((str = br.readLine()) != null) {
                line = str.split("[\\p{Punct}—\\s]+");
                for (String word : line) {
                    if (!word.isEmpty()) {
                        if (map.containsKey(word)) {
                            map.get(word)[index]++;
                        } else {
                            Integer[] data = new Integer[]{0, 0};
                            data[index] = 1;
                            map.put(word, data);
                        }
                        dictionary.add(word);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
    }

}
