import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class server {
    public static void main(String[] args) {
        DataInputStream in = null;
        DataOutputStream out = null;
        Socket socket = null;

        try (ServerSocket serverSocket = new ServerSocket(5000)) {
            System.out.println("waiting for a client to connect...");
            socket = serverSocket.accept();
            System.out.println("Client connected.");

            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());

            String clientMessage;
            String serverMessage;

            while (true) {
                clientMessage = in.readUTF();
                if (clientMessage.equals("exit")) {
                    System.out.println("Client ended the chat.");
                    break;
                }
                System.out.println("Client: " + clientMessage);

                serverMessage = "server get: " + clientMessage;
                out.writeUTF(serverMessage);
            }
        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Error while closing resources: " + e.getMessage());
            }
        }
    }
}
