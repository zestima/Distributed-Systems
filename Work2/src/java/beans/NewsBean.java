/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.News;
import entities.Topics;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zsdaking
 */
@Stateless
public class NewsBean {

    int i = 0;

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    @PersistenceContext
    EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public News addNews(News n) {
        em.persist(n);
        return n;
    }

    public List<News> getNews() {
        return (List<News>) em.createNamedQuery("News.findAll").getResultList();
    }

    public List<News> getLastNewsTopic(String topic) {
        List<Topics> tt = em.createNamedQuery("Topics.findByTopic").setParameter("topic", topic).getResultList();
        List<News> t = em.createNamedQuery("News.findAll").getResultList();
        List<News> n = new ArrayList<>();
        for (int i = 0; i < t.size(); i++) {
            if (t.get(i).getTopicFk().equals(tt.get(0))) {
                n.add(t.get(i));
            }
        }
        return n;
    }
}
