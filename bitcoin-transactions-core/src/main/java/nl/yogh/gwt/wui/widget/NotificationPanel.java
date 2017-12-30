package nl.yogh.gwt.wui.widget;

import com.google.gwt.core.client.GWT;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.web.bindery.event.shared.EventBus;
import com.google.web.bindery.event.shared.binder.EventBinder;
import com.google.web.bindery.event.shared.binder.EventHandler;

import nl.yogh.gwt.wui.event.NotificationEvent;

public class NotificationPanel extends FlowPanel {
  private final NotificationPanelEventBinder EVENT_BINDER = GWT.create(NotificationPanelEventBinder.class);

  interface NotificationPanelEventBinder extends EventBinder<NotificationPanel> {}

  public void setEventBus(EventBus eventBus) {
    EVENT_BINDER.bindEventHandlers(this, eventBus);
  }

  @EventHandler
  public void onNotification(final NotificationEvent event) {
    add(new NotificationWidget(event.getValue()));
  }
}
