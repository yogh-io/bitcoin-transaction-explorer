package com.yoghurt.crypto.transactions.server.servlets.config;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Properties;

public class AuthenticatedConfigProvider {
  private static final String CONFIG_FILE_NAME = "yoghurt.conf";

  private final String CONFIG_PASSWORD_KEY = "com.yoghurt.crypto.password";

  public void updateServicePassword(final String oldPasswordHashed, final String newPasswordHashed) throws IOException {
    final Properties props = getProperties();

    // If there is no password or the given old password is valid, then proceed
    if (!isPasswordPresent(props)
        || isAuthentic(props, oldPasswordHashed)
        && newPasswordHashed != null && !newPasswordHashed.isEmpty()) {
      props.setProperty(CONFIG_PASSWORD_KEY, newPasswordHashed);
      saveConfig(props);
    } else {
      throw new IllegalArgumentException("Password incorrect.");
    }
  }

  public boolean isPasswordPresent() throws IOException {
    return isPasswordPresent(getProperties());
  }

  public boolean isAuthentic(final String passwordHashed) {
    try {
      return isAuthentic(getProperties(), passwordHashed);
    } catch (final IOException e) {
      return false;
    }
  }

  public void setBlockchainHookConfig(final String passwordHashed, final Properties config) throws IOException {
    // Strip the password key if it's in there. It shouldn't.
    config.remove(CONFIG_PASSWORD_KEY);

    final Properties currentConfig = getProperties();

    if (!isPasswordPresent(currentConfig)) {
      throw new IllegalStateException("Password is not set.");
    }

    if (!isAuthentic(currentConfig, passwordHashed)) {
      throw new IllegalArgumentException("Password incorrect.");
    }

    currentConfig.putAll(config);

    saveConfig(currentConfig);
  }

  private void saveConfig(final Properties props) throws IOException {
    final File f = new File(Thread.currentThread().getContextClassLoader().getResource(CONFIG_FILE_NAME).getFile());
    if (!f.exists()) {
      f.createNewFile();
    }

    final OutputStream out = new FileOutputStream(f);
    props.store(out, "");
  }

  public Properties getProperties() throws IOException {
    final InputStream resource = Thread.currentThread().getContextClassLoader().getResourceAsStream(CONFIG_FILE_NAME);

    final Properties properties = new Properties();
    properties.load(resource);

    return properties;
  }

  private boolean isPasswordPresent(final Properties config) {
    return config.containsKey(CONFIG_PASSWORD_KEY);
  }

  private boolean isAuthentic(final Properties config, final String passwordHashed) {
    return passwordHashed != null && passwordHashed.equals(config.get(CONFIG_PASSWORD_KEY));
  }
}
