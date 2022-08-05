/*
 * @Author: Wonder2019
 * @Date: 2019-11-14 17:29:01
 * @Last Modified by: Wonder2020
 * @Last Modified time: 2021-02-24 17:03:17
 */
package top.imwonder.util;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Pattern;

public class FileOperatingUtil {

    public static Pattern filePattern = Pattern.compile("[:*?\"<>|]");

    private FileOperatingUtil() {
    }

    public static void downloadBinFileWithHttp(String uri, String savePath) throws IOException {
        new File(savePath).getParentFile().mkdirs();
        try (InputStream in = openHttpForInputStream(uri); OutputStream out = new FileOutputStream(savePath)) {
            FileOperatingUtil.transferBinaryFile(in, out);
        }
    }

    public static void downloadAsciiFileWithHttp(String uri, String savePath) throws IOException {
        new File(savePath).getParentFile().mkdirs();
        try (InputStream in = openHttpForInputStream(uri); OutputStream out = new FileOutputStream(savePath)) {
            FileOperatingUtil.transferAsciiFile(in, out);
        }
    }

    public static InputStream openHttpForInputStream(String uri) throws IOException {
        URL url = new URL(uri);
        URLConnection conn = url.openConnection();
        conn.setRequestProperty("accept", "*/*");
        conn.setRequestProperty("connection", "Keep-Alive");
        conn.setRequestProperty("user-agent", "Mozilla/4.0 (compatible; MSIE 6.0; Windows NT 5.1;SV1)");
        conn.setDoInput(true);
        return conn.getInputStream();
    }

    public static String filenameFilter(String str) {
        return str == null ? null : filePattern.matcher(str).replaceAll("");
    }

    public static String readForStringNoThrow(File file) {
        try {
            return readForString(file);
        } catch (IOException e) {
            return "";
        }
    }

    public static String readForString(File file) throws IOException {
        try (FileReader reader = new FileReader(file)) {
            return readForString(reader);
        } catch (IOException e) {
            throw e;
        }
    }

    public static String readForString(InputStream in) throws IOException {
        return readForString(new InputStreamReader(in));
    }

    public static String readForString(Reader reader) throws IOException {
        BufferedReader br = new BufferedReader(reader);
        String line = null;
        StringBuffer sb = new StringBuffer();
        while ((line = br.readLine()) != null) {
            sb.append("\n");
            sb.append(line);
        }
        String result = sb.toString();
        if (result.length() > 0) {
            return result.substring(1);
        }
        return result;
    }

    public static void printToFile(String file, String line) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));) {
            pw.print(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printlnToFile(String file, String line) {
        try (PrintWriter pw = new PrintWriter(new FileOutputStream(file, true));) {
            pw.println(line);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void transferBinaryFile(InputStream in, OutputStream out) throws IOException {
        BufferedInputStream bis = new BufferedInputStream(in);
        BufferedOutputStream bof = new BufferedOutputStream(out);
        byte[] buf = new byte[1024];
        for (int len = bis.read(buf); len != -1; len = bis.read(buf)) {
            bof.write(buf, 0, len);
        }
        bis.close();
        bof.close();
    }

    public static void transferAsciiFile(InputStream in, OutputStream out) throws IOException {
        transferAsciiFile(in, out, false);
    }

    public static void transferAsciiFile(InputStream in, OutputStream out, boolean autoflush) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        PrintWriter pw = new PrintWriter(out, autoflush);
        String s;
        while ((s = br.readLine()) != null) {
            pw.println(s);
        }
        br.close();
        pw.close();
    }
}
