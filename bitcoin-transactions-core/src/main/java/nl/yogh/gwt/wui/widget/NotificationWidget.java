package nl.yogh.gwt.wui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;

import nl.yogh.gwt.wui.util.Notification;

public class NotificationWidget extends Composite {
  private static final NotificationWidgetUiBinder UI_BINDER = GWT.create(NotificationWidgetUiBinder.class);

  interface NotificationWidgetUiBinder extends UiBinder<Widget, NotificationWidget> {}

  public interface CustomStyle extends CssResource {
    String error();
  }

  @UiField Label message;

  @UiField CustomStyle style;

  public NotificationWidget(final Notification notification) {
    initWidget(UI_BINDER.createAndBindUi(this));

    message.setText(notification.getMessage());

    setStyleName(style.error(), notification.isError());
  }

  @UiHandler("message")
  public void onMessageClick(final ClickEvent e) {
    removeFromParent();
  }
}
