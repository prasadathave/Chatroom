package serverfiles;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.util.*;
import java.net.*;
public class Handleclient implements Runnable{
    Scanner scn = new Scanner(System.in);
    private String namestr;
    final DataOutputStream dataoutputs;
    final DataInputStream datainputs;

    Socket skt;
    boolean islogin;



    public Handleclient(Socket skt, 
    String namestr,DataOutputStream dataoutputs,
    DataInputStream datainputs){
        this.namestr = namestr;
        this.datainputs = datainputs;
        this.dataoutputs = dataoutputs;
        this.skt = skt;
        this.islogin = true;
    }


    public void run(){
        String rcvdtext;
        while(true)
        {
            try{
                rcvdtext = datainputs.readUTF();
                System.out.println(rcvdtext);

                if(rcvdtext.equals("logout")){
                    this.islogin = false;
                    this.skt.close();
                    break;
                }
                StringTokenizer st = new StringTokenizer(rcvdtext,"-");
                String sendablemsg = st.nextToken();
                String receivingend = st.nextToken();

                for(Handleclient i1 : Serverrun.arr){
                        if(i1.namestr.equals(receivingend) && i1.islogin){
                            i1.dataoutputs.writeUTF(this.namestr+":"+ sendablemsg);
                        }

                }

            }
            catch(Exception e){
                e.printStackTrace();
                break;
            }
        }
        try{
            this.datainputs.close();
            this.dataoutputs.close();
        }
        catch(Exception e){
            e.printStackTrace();
        }
        
    
    }


}