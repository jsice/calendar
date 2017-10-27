package ku.cs.calendar.server;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
/**
 * Wiwadh Chinanuphandh
 * 5810400051
 */
public class Main {

    public static void main(String[] args) {
        ApplicationContext bf = new ClassPathXmlApplicationContext("server-config.xml");
        System.out.println("Server is running...");
    }

}
