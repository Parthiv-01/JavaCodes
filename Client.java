import java.io.*;
import java.net.*;

public class Client {
    public static void main(String[] args) {
        final String SERVER_ADDRESS = "localhost";
        final int PORT = 8080;

        try (
            Socket socket = new Socket(SERVER_ADDRESS, PORT);
            PrintWriter out = new PrintWriter(socket.getOutputStream(), true);
            BufferedReader in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in))
        ) {
            System.out.println("Connected to server: " + SERVER_ADDRESS + ":" + PORT);

            Thread receivingThread = new Thread(() -> {
                String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Server: " + inputLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receivingThread.start();

            String outputLine;
            while ((outputLine = consoleIn.readLine()) != null) {
                out.println(outputLine);
            }

            receivingThread.join();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
