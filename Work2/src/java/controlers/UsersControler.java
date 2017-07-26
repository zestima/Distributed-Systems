/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controlers;

import beans.UsersBean;
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
@ManagedBean(name = "usersControler")
@Named(value = "usersControler ")
@RequestScoped
public class UsersControler {

    public UsersControler() {
    }

    @EJB
    UsersBean user;
    List<Users> usersList = new ArrayList<>();
    Users newUser = new Users();
    String username;
    String password;

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

    public String login() {
        newUser.setUsername(username);
        newUser.setPassword(password);
        if (user.isRegistered(username, password).contains(newUser)) {
            user.setUsername(username);
            user.setPassword(password);
            return "loginsucessful.xhtml";
        } else {
            FacesContext.getCurrentInstance().addMessage(username, new FacesMessage("Login inválido!"));
            return "login.xhtml";
        }
    }

    public String logout() {
        user.setPassword("");
        user.setUsername("");
        return "index.xhtml";
    }

    public String addNewUser() {
        newUser.setUsername(username);
        newUser.setPassword(password);

        try {
            user.addUser(newUser);
        } catch (Exception e) {
            FacesContext.getCurrentInstance().addMessage(username, new FacesMessage("Username já existe ou é maior que 25 caracteres!"));
            return "registar.xhtml";
        }
        usersList = user.getUsers();
        return "login.xhtml";
    }

}
