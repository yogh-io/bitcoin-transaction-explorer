package nl.yogh.gwt.wui.event;

import nl.yogh.gwt.wui.util.Notification;

public class NotificationEvent extends SimpleGenericEvent<Notification> {
  public NotificationEvent(final Notification notification) {
    super(notification);
  }
}
