package ex00;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class SignatureHandler {

    private final static String SIGNAL = "/home/user/IdeaProjects/day02/src/ex00/signal.txt";

    private SignatureHandler() {
    }

    public static Map<String, String> signalToMap() {
        Map<String, String> map = new HashMap<>();
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fio = new FileInputStream(SIGNAL)) {
            int i;
            while ((i = fio.read()) != -1) {
                sb.append((char) i);
            }
            String[] data = sb.toString().split("\n");
            for (String line : data) {
                sb.setLength(0);
                String[] first = line.split(", ");
                String[] second = first[1].split(" ");
                for (String str : second) {
                    sb.append(str);
                }
                map.put(first[0], sb.toString());
            }
        } catch (IOException e) {
            System.err.println("File is not exist");
        }
        return map;
    }

    public static void scanFile(final String path, Map<String, String> signatures) {
        String formatOfFile = "";
        String signatureOfFile = readSignature(path);
        if (!signatureOfFile.isEmpty()) {
            for (String format : signatures.keySet()) {
                if (signatureOfFile.startsWith(signatures.get(format))) {
                    formatOfFile = format + "\n";
                    System.out.println("PROCESSED");
                    break;
                }
            }
            if (!formatOfFile.isEmpty()) {
                try (FileOutputStream fos = new FileOutputStream("result.txt", true)) {
                    fos.write(formatOfFile.getBytes());
                } catch (IOException e) {
                    System.err.println("Failed to create file");
                }
            } else {
                System.out.println("UNDEFINED");
            }
        }

    }

    private static String readSignature(final String path) {
        StringBuilder sb = new StringBuilder();
        try (FileInputStream fio = new FileInputStream(path)) {
            int count = 8;
            while (count-- > 0) {
                sb.append(String.format("%02X", fio.read()));
            }
        } catch (IOException e) {
            System.err.println("File is not exist");
        }
        return sb.toString();
    }

}
