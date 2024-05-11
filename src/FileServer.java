/******
 * compilation: javac FileServer.java
 * execution:   java FileServer
 *
 * dependecies: Huffman.java
 *
 * Author(s): Deepak
 * Team: Alec, Deepak, and Soobin
 * Date: 05/10/2024
*******/

import java.io.*;
import java.net.*;

/**
 * The FileServer class represents a server that listens for incoming client connections
 * and handles file transfer and decoding requests.
 */
public class FileServer {

    private static final int PORT = 6969;

    public static void main(String[] args) {
        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server is listening on port " + PORT);

            while (true) {
                Socket socket = serverSocket.accept();
                System.out.println("Client connected");
                new Thread(new ClientHandler(socket)).start();
            }
        } catch (IOException ex) {
            System.out.println("Server exception: " + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private static class ClientHandler implements Runnable {
        private Socket socket;

        public ClientHandler(Socket socket) {
            this.socket = socket;
        }

        public void run() {
            try {
                DataInputStream dis = new DataInputStream(socket.getInputStream());
                System.out.println(dis);
                while (!socket.isClosed()) {
                    String command = dis.readUTF(); // This can throw EOFException if client disconnect
                    System.out.println(command);

                    switch (command) {
                        case "send":
                            String fileName = dis.readUTF();
                            System.out.println(fileName);
                            receiveFile(dis, fileName);
                            break;
                        case "decode":
                            fileName = dis.readUTF();
                            decodeFile(fileName);
                            break;
                        case "exit":
                            System.out.println("Exit command received. Closing connection.");
                            socket.close();
                            return;
                        default:
                            System.out.println("Received unknown command: " + command);
                            break;
                    }
                }
            } catch (EOFException e) {
                e.printStackTrace();
            } catch (IOException e) {
                //System.out.println("Server exception: " + e.getMessage());
                e.printStackTrace();
            }
            finally {
                try {
                    System.out.println("Closing the socket");
                    socket.close();
                } catch (IOException e) {
                    System.out.println("Error closing socket: " + e.getMessage());
                }
            }
        }

        /**
         * All client objects recieve the file send from one Client instance
         * @param dis DataInputStream through which the file is passed
         * @param fileName FileName that is being sent over the socket connection
         * @return None
         * @see saved huffman encoded file
         */
        private void receiveFile(DataInputStream dis, String fileName) throws IOException {
            File file = new File(fileName);
            try (FileOutputStream fos = new FileOutputStream(file)) {
                byte[] buffer = new byte[4096];
                int read;

                fos.write(buffer, 0, dis.read(buffer));

                System.out.println("Receiving Huffman Encoded File");
            }
        }

        /**
         * Decodes the huffman encoded file
         * @param fileName Name of the huffman encoded file
         * @return None
         * @see See the expanded Huffman file (decoded data)
         */
        private void decodeFile(String fileName) throws IOException {
            System.out.println("Getting into decode file function");
            File inputFile = new File(fileName);
            File outputFile = new File("decoded_" + fileName);
            try (FileInputStream fis = new FileInputStream(inputFile);
                 FileOutputStream fos = new FileOutputStream(outputFile)) {
                Huffman.expand(fis, fos);
            }
            System.out.println("Decoded file: " + outputFile.getName());
        }
    }
}
