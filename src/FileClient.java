/******
 * compilation: javac FileClient.java
 * execution:   java FileClient
 *
 * Author(s): Deepak
 * Team: Alec, Deepak, and Soobin
 * Date: 05/10/2024
*******/

import java.io.*;
import java.net.*;
import java.util.Scanner;

public class FileClient {
    private static final String HOST = "localhost";
    private static final int PORT = 6969;

    public static void main(String[] args) {
        try (Socket socket = new Socket(HOST, PORT);
             DataOutputStream dos = new DataOutputStream(socket.getOutputStream());
             Scanner scanner = new Scanner(System.in)) {

            System.out.println("Connected to the server");

            while (true) {
                System.out.print("Enter command (send <filepath>, decode <filename>, exit): ");
                String command = scanner.nextLine();

                if (command.equals("exit")) {
                    break; // Exit the loop and close the connection properly
                } else if (command.startsWith("send ")) {
                    String filePath = command.substring(5);
                    sendFile(dos, filePath);
                } else if (command.startsWith("decode ")) {
                    String fileName = command.substring(7);
                    requestDecoding(dos, fileName);
                } else {
                    System.out.println("Unknown command.");
                }
            }
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

    private static void sendFile(DataOutputStream dos, String filePath) throws IOException {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.println("File does not exist.");
            return;
        }

        // Compress the file
        File compressedFile = new File(file.getName() + ".huf");
        try (FileInputStream fis = new FileInputStream(file);
             FileOutputStream fos = new FileOutputStream(compressedFile)) {
            Huffman.compress(fis, fos);
        }

        try (FileInputStream fisCompressed = new FileInputStream(compressedFile)) {
            dos.writeUTF("send");
            dos.writeUTF(compressedFile.getName());
            byte[] buffer = new byte[4096];
            int read;
            while ((read = fisCompressed.read(buffer)) > 0) {
                dos.write(buffer, 0, read);
            }
            dos.flush();
        }
    }

    private static void requestDecoding(DataOutputStream dos, String fileName) throws IOException {
        dos.writeUTF("decode");
        dos.writeUTF(fileName);
    }
}
