package clientfiles;
import java.net.InetAddress;
import java.util.Scanner;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.net.*;
import java.util.*;
public class Clientrun
{
    
    public static void main(String[] args) throws Exception {
        Scanner scn = new Scanner(System.in);
        Socket s;
        InetAddress ip = InetAddress.getByName("localhost");
        s = new Socket(ip,12345);

        DataInputStream datainputs = new DataInputStream(s.getInputStream());
        DataOutputStream dataoutputs = new DataOutputStream(s.getOutputStream());

        Thread sendmsg = new Thread(new Runnable(){
            
            public void run(){
                while(true){
                    String msg = scn.nextLine();
                    if(msg.equals("exit")){
                        break;
                    }

                    try{
                        dataoutputs.writeUTF(msg);
                    }
                    catch(Exception e){
                        e.printStackTrace();
                        break;
                        
                    }

                }

            }
        });

        // Read 

        Thread readmsg = new Thread(new Runnable(){
           public void run(){
            while(true){
            try{
                String m1 = datainputs.readUTF();
                if(m1.equals("bye")){
                    break;
                }
                System.out.println(m1);
            }catch(Exception e){
                e.printStackTrace();
            }

           } 
        }
        });



        sendmsg.start();
        readmsg.start();
        try {
            readmsg.join();
            sendmsg.join();
            
        } catch (Exception e) {
            //TODO: handle exception
        }


        
    }






}