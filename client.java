import java.io.*;
import java.net.Socket;

public class client {
    public static void main(String[] args) throws IOException {
        Socket clientSocket = new Socket("localhost", 8888);

        BufferedReader socketReader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
        PrintWriter socketWriter = new PrintWriter(clientSocket.getOutputStream(), true);
        BufferedReader consoleReader = new BufferedReader(new InputStreamReader(System.in));

        Thread readThread = new Thread(() -> {
            try {
                String receivedMessage;
                while ((receivedMessage = socketReader.readLine()) != null) {
                    if (receivedMessage.equalsIgnoreCase("Server disconnected.")) {
                        System.out.println("Server disconnected.");
                        break;
                    }
                    System.out.println("Server: " + receivedMessage);
                }
            } catch (IOException e) {
                System.out.println("Error reading from server: " + e.getMessage());
            }
        });

        Thread writeThread = new Thread(() -> {
            try {
                String sentMessage;
                while (true) {
                    System.out.print("You: ");
                    sentMessage = consoleReader.readLine();
                    if (sentMessage.equalsIgnoreCase("exit")) {
                        socketWriter.println("exit");
                        break;
                    }
                    socketWriter.println(sentMessage);
                }
            } catch (IOException e) {
                System.out.println("Error writing to server: " + e.getMessage());
            }
        });

        readThread.start();
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            System.out.println("Client interrupted.");
        }

        clientSocket.close();
        System.out.println("Client stopped.");
    }
}
