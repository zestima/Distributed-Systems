/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintWriter;
import java.rmi.Naming;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zsdaking
 */
public class RMIServerImpl extends UnicastRemoteObject implements RMIServerInterface {

    private ArrayList<Topics> topics = new ArrayList<>();
    private ArrayList<News> news = new ArrayList<>();
    private ArrayList<Publishers> publishers = new ArrayList<>();
    private ArrayList<Consumers> consumers = new ArrayList<>();
    private ArrayList<News> newsb = new ArrayList<>();
    private int maxNews;
    private static ArrayList<RMIConsumerInterface> client = new ArrayList<>();
    private ArrayList<String> clientids = new ArrayList<>();

    public void disconnect(RMIConsumerInterface c) {
        for (int i = 0; i < client.size(); i++) {
            if (client.get(i).equals(c)) {
                synchronized (client) {
                    client.remove(i);
                }
                synchronized (clientids) {
                    clientids.remove(i);
                }
                break;
            }
        }
    }

    public void connection(String id, RMIConsumerInterface c) throws RemoteException {
        synchronized (client) {
            client.add(c);
            System.out.println(c.toString());
        }
        synchronized (clientids) {
            clientids.add(id);
        }
        System.out.println("connected : " + id);
        try {
            Scanner s = new Scanner(new File("offline"));
            PrintWriter p = new PrintWriter("offline2");
            while (s.hasNextLine()) {
                String sid = s.nextLine();
                if (sid.equals(id)) {
                    c.printOnClient(s.nextLine());
                    c.printOnClient(s.nextLine());
                } else {
                    p.write(s.nextLine());
                }
            }
            p.close();
        } catch (FileNotFoundException ex) {
        }
        File f = new File("offline");
        File f1 = new File("offline2");
        f.delete();
        f1.renameTo(f);

    }

    public RMIServerImpl(String name) throws RemoteException {
        super();
        try {
            Naming.rebind(name, this);
            //Read topics from file
            synchronized (topics) {
                try {
                    FileInputStream fin = new FileInputStream("topics");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    topics = (ArrayList<Topics>) ois.readObject();
                } catch (Exception e) {

                }
            }
            //Read News from file
            synchronized (this.news) {
                try {
                    FileInputStream fin = new FileInputStream("news");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    this.news = (ArrayList<News>) ois.readObject();
                } catch (Exception e) {

                }
            }
            //Read Newsb
            synchronized (this.newsb) {
                try {
                    FileInputStream fin = new FileInputStream("newsb");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    this.newsb = (ArrayList<News>) ois.readObject();
                } catch (Exception e) {

                }
            }
            //Read publishers from file
            synchronized (publishers) {
                try {
                    FileInputStream fin = new FileInputStream("publishers");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    publishers = (ArrayList<Publishers>) ois.readObject();
                } catch (Exception e) {

                }
            }

            synchronized (consumers) {
                try {
                    FileInputStream fin = new FileInputStream("consumers");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    consumers = (ArrayList<Consumers>) ois.readObject();
                } catch (Exception e) {
                }
            }
            Scanner s = new Scanner(new File("/home/zsdaking/Downloads/Trabalho1/config.txt"));
            maxNews = s.nextInt();
            System.out.println(maxNews);
        } catch (Exception e) {
            if (e instanceof RemoteException) {
                throw (RemoteException) e;
            } else {
                throw new RemoteException(e.getMessage());
            }
        }
    }

    //Publishers methods
    public boolean registerPublisher(String id, String pw) {
        for (int i = 0; i < publishers.size(); i++) {
            if (publishers.get(i).getId().equals(id)) {
                return true;
            }
        }
        synchronized (publishers) {
            publishers.add(new Publishers(id, pw));
            try {
                FileOutputStream fout = new FileOutputStream("publishers");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(publishers);
            } catch (Exception e) {

            }
        }
        return false;
    }

    public boolean registerConsumer(String id, String pw) {
        for (int i = 0; i < consumers.size(); i++) {
            if (consumers.get(i).getId().equals(id)) {
                return true;
            }
        }
        synchronized (consumers) {
            consumers.add(new Consumers(id, pw));
            try {
                FileOutputStream fout = new FileOutputStream("consumers");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(consumers);
            } catch (Exception e) {
                System.out.println(e.getMessage());
            }
        }
        return false;
    }

    public boolean loginPublisher(String id, String pw) {
        for (int i = 0; i < publishers.size(); i++) {
            if (publishers.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public boolean loginConsumer(String id, String pw) {
        for (int i = 0; i < consumers.size(); i++) {
            if (consumers.get(i).getId().equals(id)) {
                return true;
            }
        }
        return false;
    }

    public String addTopic(String s) throws RemoteException {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getTopic().equals(s)) {
                return "Tópico já existe!";
            }
        }
        synchronized (topics) {
            topics.add(new Topics(s));
            try {
                FileOutputStream fout = new FileOutputStream("topics");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(topics);
            } catch (Exception e) {

            }
        }
        return "Tópico adicionado com sucesso!";
    }

    public boolean subscribeTopic(String s, String id) throws RemoteException {
        int c = 0;

        for (int i = 0; i < consumers.size(); i++) {
            if (consumers.get(i).getId().equals(id)) {
                for (int j = 0; j < topics.size(); j++) {
                    if (topics.get(j).getTopic().equals(s)) {
                        synchronized (consumers) {
                            consumers.get(i).getTopics().add(s);
                            try {
                                FileOutputStream fout = new FileOutputStream("consumers");
                                ObjectOutputStream oos = new ObjectOutputStream(fout);
                                oos.writeObject(consumers);
                            } catch (Exception e) {

                            }
                        }
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public String getTopics() {
        String s = "Tópicos :\n";
        for (int i = 0; i < topics.size(); i++) {
            s = s + topics.get(i).getTopic() + "\n";
        }
        if (s.equals("Tópicos :\n")) {
            return "Não existem tópicos";
        }
        return s;
    }

    public String getSubscribedTopics(String id) {
        String s = "Tópicos existentes :\n";
        for (int i = 0; i < topics.size(); i++) {
            s = s + topics.get(i).getTopic() + "\n";
        }
        String s1 = "Tópicos subscritos :\n";
        s = s + s1;

        for (int i = 0; i < consumers.size(); i++) {
            if (consumers.get(i).getId().equals(id)) {
                s = s + consumers.get(i).getTopics();

            }
        }

        if (s.equals("Tópicos :\n")) {
            return "Não existem tópicos";
        }
        return s;
    }

    public String getLastTopicNews(String s) throws RemoteException {
        if (existsTopic(s)) {
            for (int i = news.size() - 1; i >= 0; i--) {
                if (news.get(i).getTopic().equals(s)) {
                    return (news.get(i).getNews());
                }
            }
        } else {
            s = "Erro";
        }
        return s;
    }

    public boolean existsTopic(String s) throws RemoteException {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getTopic().equals(s)) {
                return true;
            }
        }
        return false;
    }

    public String addNews(String topic, String news, String publisher) {
        for (int i = 0; i < topics.size(); i++) {
            if (topics.get(i).getTopic().equals(topic)) {
                if (topics.get(i).getNum() == maxNews) {
                    for (int k = 0; k < (this.news.size() / 2); k++) {
                        synchronized (newsb) {
                            newsb.add(this.news.get(k));
                        }
                        synchronized (this.news) {
                            synchronized (topics) {
                                topics.get(i).setNum(topics.get(i).getNum() - 1);
                            }
                        }
                        this.news.remove(k);
                    }
                }
                synchronized (newsb) {
                    try {
                        FileOutputStream fout = new FileOutputStream("newsb");
                        ObjectOutputStream oos = new ObjectOutputStream(fout);
                        oos.writeObject(this.newsb);
                    } catch (Exception e) {

                    }
                }
            }
        }
        Date date = new Date();
        LocalDate localDate = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        int year = localDate.getYear();
        int month = localDate.getMonthValue();
        int day = localDate.getDayOfMonth();
        String d = day + "-" + month + "-" + year;
        News n = new News(topic, news, d, publisher);

        synchronized (this.news) {
            this.news.add(n);
            try {
                FileOutputStream fout = new FileOutputStream("news");
                ObjectOutputStream oos = new ObjectOutputStream(fout);
                oos.writeObject(this.news);
            } catch (Exception e) {

            }
        }
        for (int i = 0;
                i < topics.size();
                i++) {
            if (topics.get(i).getTopic().equals(topic)) {
                synchronized (topics) {
                    topics.get(i).setNum(topics.get(i).getNum() + 1);
                }
            }
        }
        String s = "Tópico: " + n.getTopic() + "\nNotícia : " + n.getNews() + "\n\n";
        try {
            PrintWriter write = new PrintWriter(new FileOutputStream(new File("offline"), true));
            for (int i = 0; i < client.size(); i++) {
                for (int j = 0; j < clientids.size(); j++) {
                    if (consumers.get(i).getId().equals(clientids.get(j))) {
                        if (consumers.get(i).getTopics().contains(topic)) {
                            try {
                                client.get(j).printOnClient(s);
                            } catch (Exception e) {
                                //System.out.println("Erro");
                                //Adicionar em ficheiro para offline mode
                                write.println(clientids.get(j));
                                write.println(s);
                            }
                        }
                    }
                }
            }
            write.close();
        } catch (Exception e) {

        }

        return "Notícia adicionada com sucesso!";
    }

    public String getNews() {
        String s = "";
        for (int i = 0; i < news.size(); i++) {
            s = s + "Tópico: " + news.get(i).getTopic() + "\nNotícia : " + news.get(i).getNews() + "\n\n";

        }
        return s;
    }

    public boolean comparedate(String i, String f, String t) {
        Date idate = null;
        Date fdate = null;
        Date tdate = null;
        try {
            DateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
            idate = dateFormat.parse(i);
            fdate = dateFormat.parse(f);
            tdate = dateFormat.parse(t);

        } catch (ParseException ex) {
            Logger.getLogger(RMIServerImpl.class
                    .getName()).log(Level.SEVERE, null, ex);
        }
        return idate.compareTo(tdate) < 0 && tdate.compareTo(fdate) < 0;
    }

    public String getNewsDate(String t, String in, String f) {
        String s = "";
        for (int i = 0; i < news.size(); i++) {
            if (t.equals(news.get(i).getTopic())) {
                if (this.comparedate(in, f, news.get(i).getDate())) {
                    s = s + "Tópico: " + news.get(i).getTopic() + "\nNotícia : " + news.get(i).getNews() + "\n\n";
                }
            }
        }
        return s;
    }
}
