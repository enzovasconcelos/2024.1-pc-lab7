package client;

import java.net.*;
import java.io.*;
import java.util.Scanner;
import java.util.Random;

public class Client {

    private static String hostName = "localhost";
    private static int port = 5050;
    private static String filePathName = "./client/items.txt";
    private static Random random = new Random();
    private static int limitQuantities = 7;
    
    public static void main(String[] args) {
        try {
            Socket conn = new Socket(hostName, port);
            PrintWriter out = new PrintWriter(conn.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String items = getRandomItems();
            out.println(items); 
            // esperando resposta...
            System.out.println(in.readLine());
        } catch(IOException e) {
            System.err.println(e);
        }
    }
    
    private static String getRandomItems() {
        File itemsFile = new File(filePathName);
        String randomItems = null;
        try {
            Scanner scanner = new Scanner(itemsFile);
            String[] items = scanner.nextLine().split(" ");
            int numItems = random.nextInt(items.length);
            randomItems = "";
            for (int i = 0; i < numItems; i++) {
                int randomQuantity = random.nextInt(limitQuantities);
                randomItems += items[random.nextInt(items.length - 1)] + " ";
                randomItems += randomQuantity + " ";
            }
            scanner.close();
        } catch(FileNotFoundException e) {
            System.err.println(e);
        }

        return randomItems;
    }

}
