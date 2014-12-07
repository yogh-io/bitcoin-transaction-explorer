package com.yoghurt.crypto.transactions.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Global resource class, access resources via R.css(). or R.images().
 */
public final class R {
  private static final ApplicationResource RESOURCES = GWT.create(ApplicationResource.class);

  /**
   * Application CssResources.
   */
  public interface ApplicationCssResource extends CssResource {
    String applicationMainPanel();

    String applicationHeaderPanel();

    String applicationTitle();

    String applicationSubTitle();

    String applicationContentContainer();

    String applicationFooter();

    String applicationInnerFooter();

    String labelledValueFieldLabel();

    String labelledValueFieldValue();

    String labelledValueContainer();

    String applicationContent();

    String left();

    String right();
  }

  /**
   * Ensures css is injected. Should be called as soon as possible on startup.
   */
  public static void init() {
    RESOURCES.css().ensureInjected();
  }

  public interface ApplicationResource extends ClientBundle {
    @Source("strict.css")
    ApplicationCssResource css();
  }

  /**
   * Access to css resources.
   */
  public static ApplicationCssResource css() {
    return RESOURCES.css();
  }
}
