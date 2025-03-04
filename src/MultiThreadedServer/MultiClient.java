package MultiThreadedServer;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;

public class MultiClient implements Runnable{
    @Override
    public void run() {
        int port = 8010;
        try{
            InetAddress address = InetAddress.getByName("localhost");
            Socket socket = new Socket(address,port);
            PrintWriter toServer = new PrintWriter(socket.getOutputStream());
            BufferedReader fromServer = new BufferedReader(new InputStreamReader(socket.getInputStream()));
            System.out.println("Connected:" + fromServer.readLine());
            toServer.println(socket.getLocalSocketAddress());
            String s = fromServer.readLine();
            System.out.println("Response: "+s);
            toServer.close();
            fromServer.close();
            socket.close();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        MultiClient multiClient = new MultiClient();

        for(int i=0; i<10; i++){
            Thread t1 = new Thread(multiClient);
            t1.start();
        }
    }
}
