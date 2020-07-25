package Core.Threads;

import Core.Util.DataBaseCon;

import java.sql.SQLException;

public class PowerCheckThread extends Thread{
    private String user;
    public PowerCheckThread(String user){
        this.user=user;
    }

    public void run() {
        try {
            DataBaseCon con = new DataBaseCon();
            while (true) {
                    if (con.getPower(user)==-1){
                        System.exit(0);
                    }
            }
            } catch (SQLException e) {
            e.printStackTrace();
            }
    }
}
