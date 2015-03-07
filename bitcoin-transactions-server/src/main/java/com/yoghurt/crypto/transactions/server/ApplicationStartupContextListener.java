package com.yoghurt.crypto.transactions.server;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import com.yoghurt.crypto.transactions.server.servlets.BlockchainRetrievalFactory;
import com.yoghurt.crypto.transactions.server.servlets.config.AuthenticatedConfigProvider;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory.ConfigPropertiesRetriever;

@WebListener
public class ApplicationStartupContextListener implements ServletContextListener {

  @Override
  public void contextInitialized(final ServletContextEvent sce) {
    final AuthenticatedConfigProvider configProvider = new AuthenticatedConfigProvider();

    Properties props;
    try {
      props = configProvider.getProperties();
    } catch (final IOException e) {
      System.out.println("Could not find properties. No application config can be set.");
      e.printStackTrace();
      return;
    }

    final ConfigPropertiesRetriever retriever = ConfigFactory.create(props);

    BlockchainRetrievalFactory.set(retriever.getConfig());
  }

  @Override
  public void contextDestroyed(final ServletContextEvent sce) {
    // No-op here
  }
}
