package com.yoghurt.crypto.transactions.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Global resource class, access resources via R.css(). or R.clr().
 */
public final class R {
  private static final ApplicationResource RESOURCES = GWT.create(ApplicationResource.class);

  private static ColorPicker colorPicker;

  /**
   * Application CssResources.
   */
  public interface ApplicationCssResource extends CssResource {
    String applicationMainPanel();

    String applicationHeaderPanel();

    String applicationProgressContainer();

    String applicationContent();

    String applicationFooterPanel();

    String labelledValueFieldLabel();

    String labelledValueFieldValue();

    String labelledValueContainer();

    String link();

    String alignRight();

    String alignCenter();

    String flex();

    String columns();

    String distribute();
    
    String stretch();

    String grow();

    String panel();

    String error();
  }

  /**
   * Ensures css is injected. Should be called as soon as possible on startup.
   */
  public static void init(final ColorPicker colorPicker) {
    R.colorPicker = colorPicker;

    RESOURCES.css().ensureInjected();
  }

  public interface ApplicationResource extends ClientBundle {
    @Source("strict.gss")
    ApplicationCssResource css();
  }

  /**
   * Access to css resources.
   */
  public static ApplicationCssResource css() {
    return RESOURCES.css();
  }

  public static ColorPicker color() {
    return colorPicker;
  }
}
