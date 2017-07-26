/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package beans;

import entities.Users;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author zsdaking
 */
@Stateless
public class UsersBean {

    @PersistenceContext
    EntityManager em;

    String username;
    String password;

    public UsersBean() {

    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    // Add business logic below. (Right-click in editor and choose
    // "Insert Code > Add Business Method")
    public Users addUser(Users usr) {
        em.persist(usr);
        return usr;
    }

    public List<Users> getUsers() {
        return (List<Users>) em.createNamedQuery("Users.findAll").getResultList();
    }

    public List<Users> isRegistered(String name, String password) {
        return em.createNamedQuery("Users.findByUserAndPassword")
                .setParameter("username", name)
                .setParameter("password", password).getResultList();

    }
}
