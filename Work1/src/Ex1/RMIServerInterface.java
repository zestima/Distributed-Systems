/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

/**
 *
 * @author zsdaking
 */
public interface RMIServerInterface extends java.rmi.Remote {

    //Publishers methods    
    public boolean registerPublisher(String id, String pw) throws java.rmi.RemoteException;

    public void connection(String id, RMIConsumerInterface c) throws java.rmi.RemoteException;
    
    public void disconnect(RMIConsumerInterface c) throws java.rmi.RemoteException;

    public boolean loginPublisher(String id, String pw) throws java.rmi.RemoteException;

    public boolean registerConsumer(String id, String pw) throws java.rmi.RemoteException;

    public boolean loginConsumer(String id, String pw) throws java.rmi.RemoteException;

    public boolean subscribeTopic(String s, String id) throws java.rmi.RemoteException;

    public String addTopic(String s) throws java.rmi.RemoteException;

    public String getTopics() throws java.rmi.RemoteException;

    public String getSubscribedTopics(String id) throws java.rmi.RemoteException;

    public String getLastTopicNews(String s) throws java.rmi.RemoteException;

    public String getNewsDate(String t, String in, String f) throws java.rmi.RemoteException;

    public String getNews() throws java.rmi.RemoteException;

    public boolean existsTopic(String s) throws java.rmi.RemoteException;

    public String addNews(String topic, String news, String publisher) throws java.rmi.RemoteException;

    public boolean comparedate(String i, String f, String t) throws java.rmi.RemoteException;

}
