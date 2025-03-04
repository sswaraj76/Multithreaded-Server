package ThreadPoolServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadPoolServer {
    private ExecutorService threadPool;

    public ThreadPoolServer(int threadPoolSize){
        this.threadPool = Executors.newFixedThreadPool(threadPoolSize);
    }

    public void handle(Socket clientSocket){
        try{
            PrintWriter toClient = new PrintWriter(clientSocket.getOutputStream(), true);
            BufferedReader fromClient = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            toClient.println("Hello From Client" + clientSocket.getInetAddress());
            toClient.close();
            fromClient.close();
            clientSocket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        int port = 8010;
        int poolSize = 50;
        ThreadPoolServer threadPoolServer = new ThreadPoolServer(poolSize);
        try{
            ServerSocket socket = new ServerSocket(port);
            socket.setSoTimeout(70000);
            System.out.println("Server is listening on port: "+port);
             while(true){
                 Socket clientConnection =  socket.accept();
                 System.out.println("Connection accepted on address: " + clientConnection.getRemoteSocketAddress());
                 threadPoolServer.threadPool.execute(()->threadPoolServer.handle(clientConnection));
             }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
