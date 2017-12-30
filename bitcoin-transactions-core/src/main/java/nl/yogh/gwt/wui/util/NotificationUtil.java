package nl.yogh.gwt.wui.util;

import com.google.web.bindery.event.shared.EventBus;

import nl.yogh.gwt.wui.event.NotificationEvent;

public final class NotificationUtil {
  private static EventBus eventBus;

  private NotificationUtil() {}

  public static void setEventBus(EventBus eventBus) {
    NotificationUtil.eventBus = eventBus;
  }

  public static void broadcastError(final String message) {
    if (eventBus == null) {
      return;
    }
    
    eventBus.fireEvent(new NotificationEvent(new Notification(true, message)));
  }
}
