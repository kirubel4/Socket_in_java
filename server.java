import java.io.*;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) throws IOException {
        System.out.println("Server started");
        ServerSocket server = new ServerSocket(8888);
        System.out.println("Server is waiting for connection");
        Socket client = server.accept();
        System.out.println("Client connected");

        BufferedReader clientInput = new BufferedReader(new InputStreamReader(client.getInputStream()));
        PrintWriter clientOutput = new PrintWriter(client.getOutputStream(), true);
        BufferedReader serverInput = new BufferedReader(new InputStreamReader(System.in));

        String messageFromClient, messageFromServer;

        while (true) {
            // Read message from client
            if (clientInput.ready()) {
                messageFromClient = clientInput.readLine();
                if (messageFromClient.equalsIgnoreCase("exit")) {
                    System.out.println("Client disconnected.");
                    break;
                }
                System.out.println("Client: " + messageFromClient);
            }

            // Read and send message from server
            System.out.print("You: ");
            messageFromServer = serverInput.readLine();
            if (messageFromServer.equalsIgnoreCase("exit")) {
                clientOutput.println("Server disconnected.");
                break;
            }
            clientOutput.println(messageFromServer);
        }

        client.close();
        server.close();
        System.out.println("Server stopped.");
    }
}
