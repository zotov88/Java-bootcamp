package ex03;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

public class UrlHandler {

    private final Map<Integer, String> urls;
    private int countOfUrl = 1;
    private boolean isDone;

    public UrlHandler() {
        urls = getUrls(Constants.URLS_FILE);
    }

    private Map<Integer, String> getUrls(String urlsFile) {
        Map<Integer, String> map = new HashMap<>();
        try (BufferedReader br = new BufferedReader(new FileReader(urlsFile))) {
            String str;
            String[] line;
            while ((str = br.readLine()) != null) {
                line = str.split(" ");
                map.put(Integer.parseInt(line[0]), line[1]);
            }
        } catch (IOException e) {
            System.err.println("File not found");
        }
        return map;
    }

    public boolean isDone() {
        return isDone;
    }

    public synchronized String getUrl() {
        String url = urls.get(countOfUrl++);
        checkRemainder();
        return url;
    }

    private void checkRemainder() {
        isDone = !urls.containsKey(countOfUrl);
    }

    public int getUrlsListIndex(String str) {
        for (Integer num : urls.keySet()) {
            if (urls.get(num).equals(str)) {
                return num;
            }
        }
        return -1;
    }

}
