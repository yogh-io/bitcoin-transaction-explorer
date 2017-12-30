package nl.yogh.gwt.wui.event;

import com.google.web.bindery.event.shared.binder.GenericEvent;

/**
 * Simple event, encapsulating a generically typed object.
 *
 * @param <V> The object type this event is wrapping
 */
public abstract class SimpleGenericEvent<V> extends GenericEvent {
  private V object;

  /**
   * Create a {@link SimpleGenericEvent} with the given object.
   */
  public SimpleGenericEvent() {}

  /**
   * Create a {@link SimpleGenericEvent} with the given object.
   *
   * @param obj object to encapsulate
   */
  public SimpleGenericEvent(final V obj) {
    this.object = obj;
  }

  public V getValue() {
    return object;
  }

  public void setValue(final V current) {
    object = current;
  }
}
