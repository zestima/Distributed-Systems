/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Topics;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zsdaking
 */
@Stateless
public class TopicsBean {

    public TopicsBean() {
    }

    @PersistenceContext
    EntityManager em;

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Topics addTopic(Topics tpc) {
        em.persist(tpc);
        return tpc;
    }

    public List<Topics> getTopics() {
        return (List<Topics>) em.createNamedQuery("Topics.findAll").getResultList();
    }

    public List<Topics> findTopic(String t) {
        return (List<Topics>) em.createNamedQuery("Topics.findByTopic").setParameter("topic", t).getResultList();
    }
}
