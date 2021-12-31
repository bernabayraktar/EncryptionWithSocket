import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

public class Main {
    static ServerSocket ss;
    static Socket s;
    static DataInputStream din;
    static DataOutputStream dout;

    static List<ServerConnection> connections = new ArrayList<ServerConnection>();
    static boolean serverRun = true;
    public static void main(String[] args){

        try{
            ss = new ServerSocket(5966);
            System.out.println("Server Run");
            while(serverRun){
                s = ss.accept();
                System.out.println("Client connected");
                ServerConnection sc = new ServerConnection(s);
                sc.start();
                connections.add(sc);
                System.out.println("cli number : " + connections.size());

            }
            din = new DataInputStream(s.getInputStream());
            dout = new DataOutputStream(s.getOutputStream());
        }catch (Exception e){
            System.out.println(e);
        }


    }
}
