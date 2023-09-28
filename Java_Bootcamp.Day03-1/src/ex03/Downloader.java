package ex03;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;

public class Downloader extends Thread {

    private final UrlHandler urls;
    private final int numOfThread;
    private final File downloadFolder;

    public Downloader(UrlHandler urls, int numOfThread) {
        this.urls = urls;
        this.numOfThread = numOfThread;
        downloadFolder = createFolder(Constants.DOWNLOAD_FOLDER);
    }

    @Override
    public void run() {
        while (!urls.isDone()) {
            String str = urls.getUrl();
            int num;
            int bytes;
            byte[] buff = new byte[1024];
            File fileName = new File(str.substring(str.lastIndexOf("/")));
            try (BufferedInputStream bis = new BufferedInputStream(new URL(str).openStream());
                 FileOutputStream fos = new FileOutputStream(downloadFolder.getName() + File.separator + fileName)) {
                num = urls.getUrlsListIndex(str);
                System.out.println("Thread-" + numOfThread + " start download file number " + num);
                while ((bytes = bis.read(buff)) != -1) {
                    fos.write(buff, 0, bytes);
                }
                System.out.println("Thread-" + numOfThread + " finish download file number " + num);
            } catch (IOException e) {
                System.out.println("File not found");
                System.out.println(e.getMessage());
            }
        }
    }

    private File createFolder(String path) {
        File file = new File(path);
        file.mkdir();
        return file;
    }

}
