package serverfiles;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.*;

public class Serverrun{
    static ArrayList<Handleclient> arr = new ArrayList<Handleclient>();

    static int countclients =0;

    public static void main(String[] args) {
        Socket skt;
        ServerSocket ss;

        try{
        ss = new ServerSocket(12345);
       
        }
        catch( Exception e){
            e.printStackTrace();
            return;

        }
       
        while(countclients<111){
            try{
            skt = ss.accept();
            System.out.println("New client arrived"+skt);


            DataInputStream datainputs = new DataInputStream(skt.getInputStream());
            DataOutputStream dataoutputs = new DataOutputStream(skt.getOutputStream());

            Handleclient handleobj = new Handleclient(skt, "Client"+countclients, dataoutputs, datainputs);

            Thread t = new Thread(handleobj);
            System.out.println("New thread added:");

            arr.add(handleobj);

            t.start();

           // purpose of I is 
            countclients++;
            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }

        }
        try{
            ss.close();
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("error in closing");
        }

        
    }

}