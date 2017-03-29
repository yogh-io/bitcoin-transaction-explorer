package com.yoghurt.crypto.transactions.server.servlets;

import java.io.IOException;
import java.util.Properties;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;

import com.yoghurt.crypto.transactions.server.servlets.config.AuthenticatedConfigProvider;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory;
import com.yoghurt.crypto.transactions.server.servlets.config.ConfigFactory.ConfigPropertiesRetriever;
import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;
import com.yoghurt.crypto.transactions.shared.domain.UserApplicationConfig;

@WebServlet("/application/config-retrieve")
public class ConfigServlet extends HttpServlet {
  private static final long serialVersionUID = -4722553982090409634L;

  private AuthenticatedConfigProvider configProvider;

  @Override
  public void init() throws ServletException {
    super.init();

    configProvider = new AuthenticatedConfigProvider();
  }

  public UserApplicationConfig getApplicationConfig() throws ApplicationException {
    final Properties props;
    try {
      props = configProvider.getProperties();
    } catch (final IOException e) {
      e.printStackTrace();
      throw new ApplicationException();
    }

    final ConfigPropertiesRetriever retriever = ConfigFactory.create(props);

    return retriever.getApplicationConfig();
  }

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
    ObjectMapper mapper = new ObjectMapper();

    Object obj;
    try {
      obj = getApplicationConfig();
    } catch (ApplicationException e) {
      obj = e;
    }

    // Object to JSON in file
    mapper.writeValue(resp.getOutputStream(), obj);
  }
}