package SingleThreadedServer;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class SingleClient {
    public void run() throws IOException {
        int port = 8010;
        InetAddress address = InetAddress.getByName("localhost");
        Socket socket = new Socket(address,port);
        PrintWriter toServer = new PrintWriter(socket.getOutputStream());
        BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        toServer.println("Hello From the Client");
        System.out.println(fromServer.readLine());
        toServer.close();
        fromServer.close();
        socket.close();
    }

    public static void main(String[] args) {
        SingleClient singleClient = new SingleClient();
        try {
            singleClient.run();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
