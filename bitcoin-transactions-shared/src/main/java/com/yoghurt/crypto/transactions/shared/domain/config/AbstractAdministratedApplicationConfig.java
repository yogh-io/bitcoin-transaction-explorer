package com.yoghurt.crypto.transactions.shared.domain.config;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public abstract class AbstractAdministratedApplicationConfig implements AdministratedApplicationConfig {
  private static final long serialVersionUID = 1194639229241504631L;

  private BlockchainSource source;

  private String applicationTitle;
  private String applicationSubTitle;

  public AbstractAdministratedApplicationConfig(final BlockchainSource source) {
    this.setSource(source);
  }

  @Override
  public BlockchainSource getBlockchainSource() {
    return source;
  }

  @Override
  public String getApplicationTitle() {
    return applicationTitle;
  }

  @Override
  public String getApplicationSubTitle() {
    return applicationSubTitle;
  }

  public void setApplicationTitle(String applicationTitle) {
    this.applicationTitle = applicationTitle;
  }

  public void setApplicationSubTitle(String applicationSubTitle) {
    this.applicationSubTitle = applicationSubTitle;
  }

  /**
   * Private setter to (really) prevent field from being made final.
   */
  private void setSource(BlockchainSource source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "AbstractAdministratedApplicationConfig [source=" + source + ", applicationTitle=" + applicationTitle + ", applicationSubTitle=" + applicationSubTitle + "]";
  }
}
