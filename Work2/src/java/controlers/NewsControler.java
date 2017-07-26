/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.NewsBean;
import beans.TopicsBean;
import entities.News;
import entities.Topics;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import javax.ejb.EJB;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.context.FacesContext;
import javax.inject.Named;

/**
 *
 * @author zsdaking
 */
@ManagedBean(name = "newsControler")
@Named(value = "newsControler ")
@RequestScoped
public class NewsControler {

    @EJB
    NewsBean n;

    @EJB
    TopicsBean t;

    List<News> newsList = new ArrayList<>();
    News newNews = new News();
    String topic;
    String description;

    public List<News> getNewsList() {
        return newsList;
    }

    public void setNewsList(List<News> newsList) {
        this.newsList = newsList;
    }

    public News getNewNews() {
        return newNews;
    }

    public void setNewNews(News newNews) {
        this.newNews = newNews;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public List<News> getNews() {
        newsList = n.getNews();
        return newsList;
    }

    public String addNewNews() {
        Calendar cal = Calendar.getInstance();
        cal.add(Calendar.DATE, 1);
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
        newNews.setId(n.getI());
        n.setI(n.getI() + 1);
        newNews.setDate(format1.format(cal.getTime()));
        newNews.setDescription(description);
        if (!t.findTopic(topic).isEmpty()) {
            newNews.setTopicFk(new Topics(topic));
        } else {
            FacesContext.getCurrentInstance().addMessage(description, new FacesMessage("Tópico não existe!"));
            return "addnews.xhtml";
        }
        try {
            n.addNews(newNews);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(description, new FacesMessage("Erro ao adicionar a notícia!"));
            return "addnews.xhtml";
        }
        newsList = n.getNews();
        return "newslist.xhtml";
    }

    public String getLastNews() {
        if (t.findTopic(topic).isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(description, new FacesMessage("Tópico não existe!"));
            return "lastnewstopic.xhtml";
        } else {
            newsList = new ArrayList<>();
            newsList.add(n.getLastNewsTopic(topic).get(n.getLastNewsTopic(topic).size()-1));
            return "showlastnewstopic.xhtml";
        }
    }

    public NewsBean getN() {
        return n;
    }

    public void setN(NewsBean n) {
        this.n = n;
    }
}
