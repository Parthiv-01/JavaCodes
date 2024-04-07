import java.io.IOException;
import java.net.*;
import java.util.Scanner;

public class Host {

    public static void main(String[] args) throws IOException {
        try {
            Scanner sc = new Scanner(System.in);
            InetAddress ip = InetAddress.getLocalHost();
            System.out.println("Local Host:");
            System.out.println("Host Name: " + ip.getHostName());
            System.out.println("IP Address: " + ip.getHostAddress());
            System.out.println("Enter remote host name: ");
            String host = sc.nextLine();
            if (InetAddress.getByName(host).isReachable(3000)) {
                System.out.println("Host is reachable");
                InetAddress[] addresses = InetAddress.getAllByName(host);
                for (InetAddress address : addresses) {
                    System.out.println("Host Name: " + address.getHostName());
                    System.out.println("IP Address: " + address.getHostAddress());
                }
            } else {
                System.out.println("Host is not reachable");
            }
            sc.close();
        } catch (UnknownHostException e) {
            System.err.println("Error: " + e.getMessage());
            e.printStackTrace();
        }
    }

}