
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.ServerSocket;
import java.net.Socket;
import java.rmi.server.ExportException;
import java.util.ArrayList;
import java.util.function.Consumer;


public class Client extends Thread {

    //all the necessary things we need
    Socket socketClient;
    ObjectOutputStream out;
    ObjectInputStream in;
    int port;
    String ip;
    private Consumer<Serializable> callback;
    HangmanSerializable hm = new HangmanSerializable();;




    //Client constructor
    Client(Consumer<Serializable> call, int portNum, String ipAd){
        port = portNum;
        callback = call;

        ip = ipAd;

    }




    public void run() {
        try {
            //socketClient
            socketClient= new Socket(ip,port);
            //incoming data and outgoing data
            out = new ObjectOutputStream(socketClient.getOutputStream());
            in = new ObjectInputStream(socketClient.getInputStream());
            socketClient.setTcpNoDelay(true);


        }
        catch(Exception e) {
            e.printStackTrace();
        }
    }


    public void send(HangmanSerializable hangman) {

        try {
            out.writeObject(hangman);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public void updateInfo() throws IOException, ClassNotFoundException {


        System.out.println(1);
        try{


            HangmanSerializable data = (HangmanSerializable) in.readObject();
            hm = data;

        }
        catch(Exception e)
        {
            e.printStackTrace();
        }

        System.out.println(2);

    }




}
