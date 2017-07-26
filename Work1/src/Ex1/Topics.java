/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.Serializable;

/**
 *
 * @author zsdaking
 */
public class Topics implements Serializable{

    private String topic;
    private int Num;

    public Topics(String topic) {
        this.topic = topic;
        this.Num = 0;
    }

    public String getTopic() {
        return topic;
    }

    public int getNum() {
        return Num;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public void setNum(int Num) {
        this.Num = Num;
    }

}
