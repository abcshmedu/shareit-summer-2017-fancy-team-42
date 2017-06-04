package edu.hm;

import javax.inject.Inject;

import org.glassfish.hk2.api.ServiceLocator;
import org.glassfish.jersey.server.ResourceConfig;
import org.jvnet.hk2.guice.bridge.api.GuiceBridge;
import org.jvnet.hk2.guice.bridge.api.GuiceIntoHK2Bridge;

/**
 * 
 * @author Thomas Murschall
 *
 */
public class ShareItApplication extends ResourceConfig {
    /**
     * 
     * @param serviceLocator 
     */
    @Inject
    public ShareItApplication(ServiceLocator serviceLocator) {
        GuiceBridge.getGuiceBridge().initializeGuiceBridge(serviceLocator);
        GuiceIntoHK2Bridge guiceBridge = serviceLocator.getService(GuiceIntoHK2Bridge.class);
        guiceBridge.bridgeGuiceInjector(ShareitServletContextListener.getInjectorInstance());
    }
}
