package com.yoghurt.crypto.transactions.shared.domain;

/**
 * There's some duplication with administrator app context I'm not happy about,
 * but considering they live in different security domains this is for the best.
 */
public class UserApplicationConfig implements ApplicationContextBase {
  private static final long serialVersionUID = 8385948536213767529L;

  private String applicationTitle;
  private String applicationSubTitle;

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

  @Override
  public String toString() {
    return "UserApplicationConfig [applicationTitle=" + applicationTitle + ", applicationSubTitle=" + applicationSubTitle + "]";
  }
}
