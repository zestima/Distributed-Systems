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
//meter o produtor aqui
public class News implements Serializable {

    private String publisher;
    private String topic;
    private String news;
    private String date;

    public News(String topic, String news, String date, String publisher) {
        this.topic = topic;
        this.news = news;
        this.date = date;
        this.publisher = publisher;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public String getNews() {
        return news;
    }

    public String getTopic() {
        return topic;
    }

    public String getDate() {
        return date;
    }

    @Override
    public String toString() {
        return topic + " : \n" + news ;
    }
}
