import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class ServerSide {
    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        ServerSocket server = new ServerSocket(8888);
        System.out.println("Server is waiting for connection...");
        Socket client = server.accept();
        System.out.println("Client connected!");

        BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter clientOutput = new PrintWriter(client.getOutputStream(), true);
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

        Thread readThread = new Thread(() -> {
            try {
                String messageFromClient;
                while ((messageFromClient = clientInput.readLine()) != null) {
                    if (messageFromClient.equalsIgnoreCase("exit")) {
                        System.out.println("Client disconnected.");
                        break;
                    }
                    System.out.println("Client: " + messageFromClient);
                }
            } catch (IOException e) {
                System.out.println("Error reading from client: " + e.getMessage());
            }
        });

        Thread writeThread = new Thread(() -> {
            try {
                String messageFromServer;
                while (true) {
                    messageFromServer = serverInput.readLine();
                    if (messageFromServer.equalsIgnoreCase("exit")) {
                        clientOutput.println("Server disconnected.");
                        break;
                    }
                    clientOutput.println(messageFromServer);
                }
            } catch (IOException e) {
                System.out.println("Error writing to client: " + e.getMessage());
            }
        });

        readThread.start();
        writeThread.start();

        try {
            readThread.join();
            writeThread.join();
        } catch (InterruptedException e) {
            System.out.println("Server interrupted.");
        }

        client.close();
        server.close();
        System.out.println("Server stopped.");
    }
}
