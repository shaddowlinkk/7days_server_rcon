package Core.Util;

import java.io.*;
import java.net.Socket;
import java.util.ArrayList;

public class ServerConection {
    private BufferedReader out;
    private PrintWriter writer;
    private InputStream in;
    ArrayList<String> data = new ArrayList<>();
    public ServerConection(){
        try {
            Configeration con = new Configeration("pass.config");
            Socket server = new Socket(con.getProp("IP"), 26939);
            InputStream input = server.getInputStream();
            OutputStream output = server.getOutputStream();
            BufferedReader readers = new BufferedReader(new InputStreamReader(input));
            String line = readers.readLine();
            writer = new PrintWriter(output, true);
            writer.println(con.getProp("pass"));
            line = readers.readLine();
            while (!line.contains("successful")){
                writer.println(con.getProp("pass"));
                line = readers.readLine();
            }
            out=readers;
            in=input;
        }catch (IOException e){

        }
    }
    public BufferedReader getServerInfo(){
        return out;
    }
    public PrintWriter getOutStream(){
        return writer;
    }
    public InputStream getIn() {
        return in;
    }

    public ArrayList<String> getData() {
        return data;
    }
    public void starting(){
        try {

            String line = "";
            while (!line.contains("end session")) {
                line = out.readLine();
            }
            line = out.readLine();
            line = out.readLine();
            writer.println("lp");
            line = out.readLine();
            while (!line.contains("in the game")) {
                data.add(line);
                line = out.readLine();
            }
        }catch (IOException e){

        }
    }
}
