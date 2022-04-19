package lt.bit.todo.listeners;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

@WebListener
public class EMFListener implements ServletContextListener {
    
    private EntityManagerFactory emf;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        this.emf = Persistence.createEntityManagerFactory("todoPU");
        ServletContext sc = sce.getServletContext();
        sc.setAttribute("emf", this.emf);
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        this.emf.close();
    }
}
