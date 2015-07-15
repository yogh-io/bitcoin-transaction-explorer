package com.yoghurt.crypto.transactions.shared.domain.config;

import com.yoghurt.crypto.transactions.shared.domain.BlockchainSource;


/**
 * There's some duplication with administrator app context I'm not happy about, but considering
 * they live in different security domains this is for the best.
 */
public class UserApplicationConfig implements ApplicationContextBase {
  private static final long serialVersionUID = 8385948536213767529L;

  private BlockchainSource blockchainSource;

  private String applicationTitle;
  private String applicationSubTitle;

  private String hostDonationAddress;
  private String projectDonationAddress;

  public UserApplicationConfig() {
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

  public void setHostDonationAddress(final String hostDonationAddress) {
    this.hostDonationAddress = hostDonationAddress;
  }

  @Override
  public String getHostDonationAddress() {
    return hostDonationAddress;
  }

  public void setProjectDonationAddress(final String projectDonationAddress) {
    this.projectDonationAddress = projectDonationAddress;
  }

  @Override
  public String getProjectDonationAddress() {
    return projectDonationAddress;
  }

  public void setBlockchainSource(final BlockchainSource blockchainSource) {
    this.blockchainSource = blockchainSource;
  }

  public BlockchainSource getBlockchainSource() {
    return blockchainSource;
  }

  @Override
  public String toString() {
    return "UserApplicationConfig [applicationTitle=" + applicationTitle + ", applicationSubTitle=" + applicationSubTitle + "]";
  }
}
