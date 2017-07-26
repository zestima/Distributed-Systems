/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Ex1;

import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author zsdaking
 */
public class ServerBackup {

    private static class Connection extends Thread {

        private ArrayList<News> news = new ArrayList<>();
        private Socket oSocket;

        public Connection(Socket s) {
            super();
            oSocket = s;
            synchronized (news) {
                try {
                    FileInputStream fin = new FileInputStream("newsb");
                    ObjectInputStream ois = new ObjectInputStream(fin);
                    news = (ArrayList<News>) ois.readObject();
                } catch (Exception e) {

                }
            }
            start();
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
                Logger.getLogger(RMIServerImpl.class.getName()).log(Level.SEVERE, null, ex);
            }
            return idate.compareTo(tdate) < 0 && tdate.compareTo(fdate) < 0;
        }

        public void run() {
            try {
                ObjectOutputStream oOS = new ObjectOutputStream(oSocket.getOutputStream());
                ObjectInputStream oIS = new ObjectInputStream(oSocket.getInputStream());
                String t = (String) oIS.readObject();
                String in = (String) oIS.readObject();
                String f = (String) oIS.readObject();
                String s = "";
                for (int i = 0; i < news.size(); i++) {
                    if (t.equals(news.get(i).getTopic())) {
                        if (this.comparedate(in, f, news.get(i).getDate())) {
                            s = s + "Tópico: " + news.get(i).getTopic() + "\nNotícia :\n" + news.get(i).getNews() + "\n\n";
                        }
                    }
                }
                oOS.writeObject(s);
            } catch (Exception e) {

            }
        }
    }
    ServerSocket oSS;

    public ServerBackup() {
        try {
            System.out.println("Server Online");
            oSS = new ServerSocket(2222);
        } catch (Exception e) {

        }
        try {
            while (true) {
                Socket s = oSS.accept();
                System.out.println("Client Connected");
                Connection c = new Connection(s);
            }
        } catch (Exception e) {
        }
    }

    public static void main(String[] args) {
        ServerBackup s = new ServerBackup();
    }
}
