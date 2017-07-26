/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author zsdaking
 */
public class Consumers implements Serializable{

    private ArrayList<String> topics;
    private String id;
    private String password;
    //nome da interface remota

    public Consumers(String id, String password) {
        this.id = id;
        this.password = password;
        this.topics = new ArrayList<>();
    }

    public ArrayList<String> getTopics() {
        return topics;
    }

    public void setTopics(ArrayList<String> topics) {
        this.topics = topics;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}
