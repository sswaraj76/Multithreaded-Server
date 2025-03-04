package SingleThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;

public class SingleServer {
    public void run() throws IOException {
        int port = 8010;
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            serverSocket.setSoTimeout(20000);
            while (true) {
                System.out.println("Server is listening on port: " + port);
                Socket clientConnection = serverSocket.accept();
                System.out.println("Connection Accepted on Client " + clientConnection.getRemoteSocketAddress());
                PrintWriter toClient = new PrintWriter(clientConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientConnection.getInputStream()));
                toClient.println("Hello From the server.");
                toClient.close();
                fromClient.close();
                clientConnection.close();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
    public static void main(String[] args) {
        SingleServer singleThread = new SingleServer();
        try {
            singleThread.run();
        } catch (Exception e){
            e.getStackTrace();
        }
    }
}
