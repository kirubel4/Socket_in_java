import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;
import java.util.Scanner;

public class client{
    public static void main(String[] args) {
        DataInputStream in = null;
        DataOutputStream out = null;
        Socket socket = null;

        try {
            socket = new Socket("localhost", 5000);
            System.out.println("Successfully connected to the server");
            in = new DataInputStream(socket.getInputStream());
            out = new DataOutputStream(socket.getOutputStream());
            Scanner scanner = new Scanner(System.in);

            String clientMessage;
            String serverMessage;

            while (true) {
                System.out.print("You>> ");
                clientMessage = scanner.nextLine();
                out.writeUTF(clientMessage);
                if (clientMessage.equalsIgnoreCase("exit")) {
                    System.out.println("Chat ended.");
                    break;
                }
                serverMessage = in.readUTF();
                if (serverMessage.equals("exit")) {
                    System.out.println("Server disconnected.");
                    break;
                }
                System.out.println("Server: " + serverMessage);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            try {
                if (in != null) in.close();
                if (out != null) out.close();
                if (socket != null) socket.close();
            } catch (IOException e) {
                System.out.println("Error: " + e.getMessage());
            }
        }
    }
}