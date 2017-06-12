package edu.hm;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;
import com.google.inject.servlet.ServletModule;

//import edu.hm.shareit.persistence.MediaPersistence;
//import edu.hm.shareit.persistence.MediaPersistenceImpl;
import edu.hm.shareit.Services.MediaService;
import edu.hm.shareit.Services.MediaServiceImpl;
import edu.hm.shareit.persistence.DatabaseManager;
import edu.hm.shareit.persistence.DatabaseManagerImpl;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

/**
 * Context Listener to enable usage of google guice together with jersey.
 * @author <a mailto:axel.boettcher@hm.edu>Axel B&ouml;ttcher</a>
 *
 */
public class ShareitServletContextListener extends GuiceServletContextListener {

    private static final Injector injector = Guice.createInjector(new ServletModule() {
        @Override
        protected void configureServlets() {
            bind(MediaService.class).to(MediaServiceImpl.class);
            bind(DatabaseManager.class).to(DatabaseManagerImpl.class);
            bind(SessionFactory.class).toInstance(new Configuration().configure().buildSessionFactory());
        }
    });

    @Override
    protected Injector getInjector() {
        return injector;
    }

    /**
     * This method is only required for the HK2-Guice-Bridge in the Application class.
     * @return Injector instance.
     */
    public static Injector getInjectorInstance() {
        return injector;
    }

}