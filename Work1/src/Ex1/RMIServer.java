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
public class RMIServer {

    public static void main(String[] args) {
        System.setSecurityManager(new SecurityManager());
        try {
            System.setProperty("java.rmi.server.hostname","192.168.43.111");
            java.rmi.registry.LocateRegistry.createRegistry(1099);
            System.out.println("RMI registry ready.");
        } catch (Exception e) {
            System.out.println("Exception starting RMI registry:");
            e.printStackTrace();
        }
        try {
            RMIServerImpl implementaInterface = new RMIServerImpl("RMIImpl");
            System.out.println("Servidor está OK");
        } catch (Exception e) {
            System.out.println("Erro no servidor " + e);
        }
    }
}
