package Core.Handlers;

import Core.objects.PlayerlistObject;

import javax.swing.*;
import java.util.ArrayList;

public class PlayerListHandler {
    PlayerlistObject list = new PlayerlistObject();
    DefaultListModel model = (DefaultListModel) list.getModel();
    ArrayList<String> data = new ArrayList<>();
    public PlayerListHandler(){
    }

    public PlayerlistObject getList() {
        return list;
    }

    public void addElement(String line){
        model.addElement(line);

    }
    private void populateList(){
        for (int i =0;i<data.size();i++) {
            if (data.get(i).contains("deaths=")) {
                String[] j = data.get(i).split(",");
                addElement(j[1]);
            }
        }
    }
    public void addData(ArrayList<String> list ){
        data=list;
        populateList();
    }

}
