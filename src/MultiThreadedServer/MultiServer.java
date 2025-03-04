package MultiThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.function.Consumer;

public class MultiServer {
    public Consumer<Socket> socketConsumer(){
        return (newConnection)->{
            try {
                PrintWriter toClient = new PrintWriter(newConnection.getOutputStream());
                BufferedReader fromClient = new BufferedReader(new InputStreamReader(newConnection.getInputStream()));
                toClient.println("Hello From the Server");
            } catch (Exception e) {
                e.printStackTrace();
            }

        };
    }

    public void runServer() throws IOException {
        int port = 8010;
        MultiServer multiServer = new MultiServer();
        try{
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(20000);
            while(true){
                System.out.println("Server is listening on port: " + port);
                Socket clientConnection = socket.accept();
                System.out.println("Connection Accepted on Client " + clientConnection.getRemoteSocketAddress());
                Thread newThread = new Thread(()->multiServer.socketConsumer().accept(clientConnection));
                newThread.start();
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MultiServer multiServer = new MultiServer();
        try {
            multiServer.runServer();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
