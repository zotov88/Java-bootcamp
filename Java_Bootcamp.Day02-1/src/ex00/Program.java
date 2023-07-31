package ex00;

import java.util.Map;
import java.util.Scanner;

public class Program {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        Map<String, String> map = SignatureHandler.signalToMap();
        String path;
        while (!(path = sc.nextLine()).equals("42")) {
            SignatureHandler.scanFile(path, map);
        }
        sc.close();
    }
}
