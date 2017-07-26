/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.File;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author Rui
 */
public class RMIConsumer extends java.rmi.server.UnicastRemoteObject implements RMIConsumerInterface {

    public RMIConsumer() throws RemoteException {
        super();
    }

    public void printOnClient(String s) throws java.rmi.RemoteException {
        System.out.println("Nova notícia: \n" + s);
    }

    /**
     * @param args the command line arguments
     */
    public static void main(String[] argv) {
        String serverName = "";
        System.setSecurityManager(new SecurityManager());
        if (argv.length != 1) {
            try {
                serverName = "192.168.43.111";
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            serverName = argv[0];
        }
        if (serverName.equals("")) {
            System.out.println("usage: java RMIClient < host running RMI server>");
            System.exit(0);
        }
        try {
            //bind server object to object in client
            RMIServerInterface myServerObject = (RMIServerInterface) Naming.lookup("//" + serverName + "/RMIImpl");
            //invoke method on server object
            RMIConsumer c = new RMIConsumer();

            //Logins / Registos
            Scanner keyboard = new Scanner(System.in);
            String id = "", pw;
            boolean check = true;
            while (check) {
                System.out.println("1 - Registo\n2 - Login\n3 - Sem login");
                int option = keyboard.nextInt();
                switch (option) {
                    case 1:
                        //Statements
                        //Clear Buffer
                        keyboard.nextLine();
                        System.out.println("Introduz o id");
                        id = keyboard.nextLine();
                        System.out.println("Introduz a password");
                        pw = keyboard.nextLine();
                        if (!myServerObject.registerConsumer(id, pw)) {
                            check = false;
                            System.out.println("Registo bem sucedido\nLogin bem sucedido!");
                            myServerObject.connection(id, (RMIConsumerInterface) c);
                        } else {
                            System.out.println("Erro");
                        }
                        break;
                    case 2:
                        //Statements
                        //Clear Buffer
                        keyboard.nextLine();
                        System.out.println("Introduz o id");
                        id = keyboard.nextLine();
                        System.out.println("Introduz a password");
                        pw = keyboard.nextLine();
                        if (!myServerObject.loginConsumer(id, pw)) {
                            System.out.println("Dados errados ou username não existe!");
                        } else {
                            check = false;
                            myServerObject.connection(id, (RMIConsumerInterface) c);
                            System.out.println("Login bem sucedido");
                        }
                        break;
                    case 3:
                        //Statements
                        check = false;
                        break;
                }
            }
            boolean bWhile = true;
            if (!id.equals("")) {
                while (bWhile) {
                    System.out.println("1 - Subscrever tópico\n2 - Consultar tópicos \n3 - Consultar notícia num tópico\n4 - Consultar última notícia de um tópico\n5 - Sair");
                    int option = keyboard.nextInt();
                    switch (option) {
                        case 1:
                            //Statements
                            //Clear Buffer
                            keyboard.nextLine();
                            System.out.println("Introduz tópico");
                            if (myServerObject.subscribeTopic(keyboard.nextLine(), id)) {
                                System.out.println("Tópico subscrito com sucesso!");
                            } else {
                                System.out.println("Tópico não existe!");
                            }
                            break;
                        case 2:
                            //Statements

                            System.out.println(myServerObject.getSubscribedTopics(id));
                            break;
                        case 3:
                            //Statements
                            //Clear Buffer
                            keyboard.nextLine();
                            System.out.println("Introduz o tópico");
                            String topic = keyboard.nextLine();
                            if (!myServerObject.existsTopic(topic)) {
                                System.out.println("Tópico não existe!");
                                break;
                            }
                            System.out.println("Ano");
                            int year = keyboard.nextInt();
                            System.out.println("Mês");
                            int month = keyboard.nextInt();
                            System.out.println("Dia");
                            int day = keyboard.nextInt();
                            String i = day + "-" + month + "-" + year;
                            System.out.println(i);
                            System.out.println("Ano");
                            year = keyboard.nextInt();
                            System.out.println("Mês");
                            month = keyboard.nextInt();
                            System.out.println("Dia");
                            day = keyboard.nextInt();
                            String f = day + "-" + month + "-" + year;
                            System.out.println(f);
                            System.out.println(myServerObject.getNewsDate(topic, i, f));
                            System.out.println("Noticias do servidor de backup");
                            Socket oSocket = new Socket("192.168.43.111", 2222);
                            ObjectOutputStream oOS = new ObjectOutputStream(oSocket.getOutputStream());
                            ObjectInputStream oIS = new ObjectInputStream(oSocket.getInputStream());
                            oOS.writeObject(topic);
                            oOS.flush();
                            oOS.writeObject(i);
                            oOS.flush();
                            oOS.writeObject(f);
                            oOS.flush();
                            System.out.println(oIS.readObject());

                            break;

                        case 4:
                            //Statements
                            keyboard.nextLine();
                            System.out.println("Introduz o tópico");
                            topic = keyboard.nextLine();

                            if (!myServerObject.existsTopic(topic)) {
                                System.out.println("Tópico não existe!");
                            } else {
                                System.out.println(myServerObject.getLastTopicNews(topic));
                            }
                            break;
                        case 5:
                            //Statements
                            bWhile = false;
                            break;
                    }
                }
            } else {
                bWhile = true;
                while (bWhile) {
                    System.out.println("1 - Consultar notícia num tópico\n2 - Consultar última notícia de um tópico\n3 - Sair");
                    int option = keyboard.nextInt();
                    switch (option) {
                        case 1:
                            //Statements
                            //Clear Buffer
                            keyboard.nextLine();
                            System.out.println("Introduz o tópico");
                            String topic = keyboard.nextLine();
                            if (!myServerObject.existsTopic(topic)) {
                                System.out.println("Tópico não existe!");
                                break;
                            }
                            System.out.println("Ano");
                            int year = keyboard.nextInt();
                            System.out.println("Mês");
                            int month = keyboard.nextInt();
                            System.out.println("Dia");
                            int day = keyboard.nextInt();
                            String i = day + "-" + month + "-" + year;
                            System.out.println(i);
                            System.out.println("Ano");
                            year = keyboard.nextInt();
                            System.out.println("Mês");
                            month = keyboard.nextInt();
                            System.out.println("Dia");
                            day = keyboard.nextInt();
                            String f = day + "-" + month + "-" + year;
                            System.out.println(f);
                            System.out.println(myServerObject.getNewsDate(topic, i, f));
                            System.out.println("Mensagens no servidor backup");
                            Socket oSocket = new Socket("192.168.43.111", 2222);
                            ObjectOutputStream oOS = new ObjectOutputStream(oSocket.getOutputStream());
                            ObjectInputStream oIS = new ObjectInputStream(oSocket.getInputStream());
                            oOS.writeObject(topic);
                            oOS.flush();
                            oOS.writeObject(i);
                            oOS.flush();
                            oOS.writeObject(f);
                            oOS.flush();
                            System.out.println(oIS.readObject());
                            break;

                        case 2:
                            //Statements
                            //Clear Buffer
                            keyboard.nextLine();
                            System.out.println("Introduz o tópico");
                            topic = keyboard.nextLine();

                            if (!myServerObject.existsTopic(topic)) {
                                System.out.println("Tópico não existe!");
                            } else {
                                System.out.println(myServerObject.getLastTopicNews(topic));
                            }
                            break;
                        case 3:
                            //Statements
                            bWhile = false;
                            break;
                    }
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
            System.exit(0);
        }
        System.out.println("Cliente fechado");
        System.exit(0);
    }
}
