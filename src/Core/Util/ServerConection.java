package Core.Util;

import java.io.*;
import java.net.Socket;

public class ServerConection {
    private BufferedReader out;
    public ServerConection(){
        try {
            Configeration con = new Configeration("pass.config");
            Socket server = new Socket(con.getProp("IP"), 26939);
            InputStream input = server.getInputStream();
            OutputStream output = server.getOutputStream();
            BufferedReader readers = new BufferedReader(new InputStreamReader(input));
            String line = readers.readLine();
            PrintWriter writer = new PrintWriter(output, true);
            writer.println(con.getProp("pass"));
            line = readers.readLine();
            out=readers;
        }catch (IOException e){

        }
    }
    public BufferedReader getServerInfo(){
        return out;
    }
}
