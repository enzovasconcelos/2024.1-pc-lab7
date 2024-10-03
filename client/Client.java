package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Client {

    private static String hostName = "localhost";
    private static int port = 5050;
    private static String filePathName = "../../items.txt";
    private static Random random = new Random();
    
    public static void main(String[] args) {
        try {
            Socket conn = new Socket(hostName, port);
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            out.println(getRandomItems());
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    private static String getRandomItems() {
        File itemsFile = new File(filePathName);
        try {
            Scanner scanner = new Scanner(itemsFile);
            String[] items = scanner.nextLine().split(" ");
            int start = random.nextInt(items.length / 2);
            int end = start + random.nextInt(items.length / 2);
            String itemsResult = "";
        } catch(FileNotFoundException e) {
            System.err.println(e);
            return "";
        }
    }
}
