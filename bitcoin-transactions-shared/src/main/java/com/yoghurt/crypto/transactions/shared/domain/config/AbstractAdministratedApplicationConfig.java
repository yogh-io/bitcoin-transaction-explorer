package com.yoghurt.crypto.transactions.shared.domain.config;

import java.io.Serializable;

import com.google.gwt.user.client.rpc.IsSerializable;
import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;

public abstract class AbstractAdministratedApplicationConfig implements AdministratedApplicationConfig, Serializable, IsSerializable {
  private static final long serialVersionUID = 1194639229241504631L;

  private BlockchainSource source;

  private String applicationTitle;
  private String applicationSubTitle;

  private String hostDonationAddress;
  private String projectDonationAddress;

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

  public void setApplicationTitle(final String applicationTitle) {
    this.applicationTitle = applicationTitle;
  }

  public void setApplicationSubTitle(final String applicationSubTitle) {
    this.applicationSubTitle = applicationSubTitle;
  }

  @Override
  public String getHostDonationAddress() {
    return hostDonationAddress;
  }

  public void setHostDonationAddress(final String hostDonationAddress) {
    this.hostDonationAddress = hostDonationAddress;
  }

  @Override
  public String getProjectDonationAddress() {
    return projectDonationAddress;
  }

  public void setProjectDonationAddress(final String projectDonationAddress) {
    this.projectDonationAddress = projectDonationAddress;
  }

  /**
   * Private setter to (really) prevent field from being made final.
   */
  private void setSource(final BlockchainSource source) {
    this.source = source;
  }

  @Override
  public String toString() {
    return "AbstractAdministratedApplicationConfig [source=" + source + ", applicationTitle=" + applicationTitle + ", applicationSubTitle=" + applicationSubTitle + "]";
  }
}
