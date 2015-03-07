package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;

import com.google.gwt.user.server.rpc.RemoteServiceServlet;
import com.yoghurt.crypto.transactions.server.servlets.config.AuthenticatedConfigProvider;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory.ConfigPropertiesRetriever;
import com.yoghurt.crypto.transactions.shared.domain.config.RetrievalHookConfig;
import com.yoghurt.crypto.transactions.shared.domain.exception.ApplicationException;
import com.yoghurt.crypto.transactions.shared.service.ConfigService;

@WebServlet("/application/config")
public class ConfigServlet extends RemoteServiceServlet implements ConfigService {
  private static final long serialVersionUID = -4722553982090409634L;

  private AuthenticatedConfigProvider configProvider;

  @Override
  public void init() throws ServletException {
    super.init();

    configProvider = new AuthenticatedConfigProvider();
  }

  @Override
  public void updateServicePassword(final String oldPasswordHashed, final String newPasswordHashed) throws ApplicationException {
    try {
      configProvider.updateServicePassword(oldPasswordHashed, newPasswordHashed);
    } catch (final IOException e) {
      throw new ApplicationException();
    }
  }

  @Override
  public void setBlockchainHookConfig(final String passwordHashed, final RetrievalHookConfig config) throws ApplicationException {
    final ConfigPropertiesRetriever retriever = ConfigFactory.create(config);

    try {
      // Persist to config file
      configProvider.setBlockchainHookConfig(passwordHashed, retriever.getProperties());
    } catch (final IOException e) {
      // File could not be written?
      e.printStackTrace();
      throw new ApplicationException();
    }

    // Persist to the factory singleton
    BlockchainRetrievalFactory.set(config);
  }

  @Override
  public RetrievalHookConfig attemptAutoConfig(final String passwordHashed, final RetrievalHookConfig config) throws ApplicationException {
    final ConfigPropertiesRetriever retriever = ConfigFactory.create(config);

    // TODO Try/catch this and report if we can't auto-configure.
    retriever.attemptAutoFillProperties();

    return retriever.getConfig();
  }

  @Override
  public boolean isPasswordPresent() throws ApplicationException {
    try {
      return configProvider.isPasswordPresent();
    } catch (final IOException e) {
      throw new ApplicationException();
    }
  }

  @Override
  public boolean isAuthentic(final String passwordHashed) throws ApplicationException {
    return configProvider.isAuthentic(passwordHashed);
  }

  @Override
  public RetrievalHookConfig getCurrentConfig(final String passwordHashed) throws ApplicationException {
    if(!configProvider.isAuthentic(passwordHashed)) {
      throw new ApplicationException();
    }

    Properties props;
    try {
      props = configProvider.getProperties();
    } catch (final IOException e) {
      e.printStackTrace();
      throw new ApplicationException();
    }

    final ConfigPropertiesRetriever retriever = ConfigFactory.create(props);

    return retriever.getConfig();
  }
}
