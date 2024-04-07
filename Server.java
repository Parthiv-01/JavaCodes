import java.io.*;
import java.net.*;

public class Server {
    public static void main(String[] args) {
        final int PORT = 8080;

        try (ServerSocket serverSocket = new ServerSocket(PORT)) {
            System.out.println("Server started on port " + PORT);

            // Wait for client connection
            Socket clientSocket = serverSocket.accept();
            System.out.println("Client connected: " + clientSocket.getInetAddress());

            // Open input and output streams for communication with the client
            BufferedReader in = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            PrintWriter out = new PrintWriter(clientSocket.getOutputStream(), true);

            // Start a new thread for receiving messages from the client
            Thread receivingThread = new Thread(() -> {
                String inputLine;
                try {
                    while ((inputLine = in.readLine()) != null) {
                        System.out.println("Client: " + inputLine);
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
            receivingThread.start();

            // Read messages from console and send to the client
            BufferedReader consoleIn = new BufferedReader(new InputStreamReader(System.in));
            String outputLine;
            while ((outputLine = consoleIn.readLine()) != null) {
                out.println(outputLine);
            }

            // Close resources
            receivingThread.join();
            clientSocket.close();
        } catch (IOException | InterruptedException e) {
            e.printStackTrace();
        }
    }
}
