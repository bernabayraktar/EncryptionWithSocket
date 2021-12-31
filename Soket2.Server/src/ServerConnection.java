import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class ServerConnection extends Thread{
    Socket socket;
    DataInputStream din;
    DataOutputStream dout;
    boolean serverRun = true;

    public ServerConnection(Socket socket){
        super("Server ConnectionThread");
        this.socket = socket;
    }

    public void sendToClient(String text){
        try{
            dout.writeUTF(text);
            dout.flush();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void semdToAllClient(String text){
        for(int i = 0; i<Main.connections.size(); i++){
            ServerConnection sc = Main.connections.get(i);
            sc.sendToClient(text);
        }
    }




    public void run(){
        try {
            din = new DataInputStream(socket.getInputStream());
            dout = new DataOutputStream(socket.getOutputStream());
            while(serverRun){
                while(din.available() == 0){
                    try{
                        Thread.sleep(1);
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    String text = din.readUTF();
                    System.out.println(text);
                    semdToAllClient(text);
                }
            }
            din.close();
            dout.close();
            socket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
