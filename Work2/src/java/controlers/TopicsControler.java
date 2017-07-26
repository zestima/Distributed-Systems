/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.TopicsBean;
import beans.UsersBean;
import entities.Topics;
import entities.Users;
import java.util.ArrayList;
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
@ManagedBean(name = "topicsControler")
@Named(value = "topicsControler ")
@RequestScoped
public class TopicsControler {

    @EJB
    UsersBean u;

    public Topics getNewTopic() {
        return newTopic;
    }

    public void setNewTopic(Topics newTopic) {
        this.newTopic = newTopic;
    }

    @EJB
    TopicsBean topic;
    List<Topics> topicsList = new ArrayList<>();
    Topics newTopic = new Topics();
    String topicname;
    String username;

    public List<Topics> getTopicsList() {
        return topicsList;
    }

    public void setTopicsList(List<Topics> topicsList) {
        this.topicsList = topicsList;
    }

    public String getTopicname() {
        return topicname;
    }

    public void setTopicname(String topicname) {
        this.topicname = topicname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<Topics> getTopics() {
        topicsList = topic.getTopics();
        return topicsList;
    }

    public String addNewTopic() {
        newTopic.setTopic(topicname);
        newTopic.setUsernameFk(new Users(u.getUsername(), u.getPassword()));
        try {
            topic.addTopic(newTopic);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(topicname, new FacesMessage("Tópico já existe!"));
            return "addtopics.xhtml";
        }
        topicsList = topic.getTopics();
        return "topicslist.xhtml";
    }
}
