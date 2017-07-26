/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.rmi.Naming;
import java.rmi.RemoteException;
import java.util.Scanner;

/**
 *
 * @author zsdaking
 */
public class RMIPublisher {

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

            //Logins / Registos
            Scanner keyboard = new Scanner(System.in);
            String id = null, pw;
            boolean check = true;
            while (check) {
                System.out.println("1 - Registo\n2 - Login");
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
                        if (!myServerObject.registerPublisher(id, pw)) {
                            check = false;
                            System.out.println("Registo bem sucedido\nLogin bem sucedido!");
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
                        if (!myServerObject.loginPublisher(id, pw)) {
                            System.out.println("Dados errados ou username não existe!");
                        } else {
                            check = false;
                            System.out.println("Login bem sucedido");
                        }
                        break;
                }
            }

            //Interação com o publisher
            boolean bWhile = true;
            while (bWhile) {
                System.out.println("1 - Adicionar tópico\n2 - Consultar tópicos existentes\n3 - Inserir notícia num tópico\n4 - Consultar todas as notícias publicadas\n5 - Sair");
                int option = keyboard.nextInt();
                switch (option) {
                    case 1:
                        //Statements
                        //Clear Buffer
                        keyboard.nextLine();
                        System.out.println("Introduz tópico");
                        System.out.println(myServerObject.addTopic(keyboard.nextLine()));
                        break;
                    case 2:
                        //Statements
                        System.out.println(myServerObject.getTopics());
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
                        String news = "";
                        do {
                            System.out.println("Introduz a notícia");
                            news = keyboard.nextLine();
                            if (news.length() > 180) {
                                System.out.println("Notícias têm de ter menos de 180 caractéres!");
                            }
                        } while (news.length() > 180);
                        System.out.println(myServerObject.addNews(topic, news, id));
                        break;
                    case 4:
                        //Statements
                        System.out.println(myServerObject.getNews());                        
                        break;
                    case 5:
                        //Statements
                        bWhile = false;
                        break;
                }
            }
        } catch (Exception e) {
            System.out.println("Exception occured: " + e);
            System.exit(0);
        }
        System.out.println("Cliente fechado");
    }
}
