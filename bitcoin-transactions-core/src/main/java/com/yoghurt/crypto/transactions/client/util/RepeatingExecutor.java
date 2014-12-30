package com.yoghurt.crypto.transactions.client.util;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.RepeatingCommand;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;

/**
 * Wraps Scheduler.get().scheduleFixedPeriod(cmd, delay) to allow for cancelling and resuming.
 */
public class RepeatingExecutor {
  private boolean started = false;
  private boolean cancel = true;
  private boolean cancelled = true;

  private final RepeatingCommand repeatingCommand = new RepeatingCommand() {
    @Override
    public boolean execute() {
      // If indicated to cancel, set the cancelled flag and stop executing this command,
      // otherwise call the function
      if (cancel) {
        cancelled = true;
      } else {
        function.execute();
      }

      return !cancel;
    }
  };

  private final ScheduledCommand function;

  public RepeatingExecutor(final ScheduledCommand function) {
    this.function = function;
  }

  /**
   * Start the executor, calling the preset Command once every given delay period.
   * 
   * Multiple calls to this method do nothing if already running (note: they will also not re-set the delay period).
   */
  public void start(final int delay) {
    // If we've indicated to cancel, and we haven't yet cancelled, flip the cancel switch (continuing)
    if (cancel && !cancelled) {
      cancel = false;
      return;
    }

    // If we haven't indicated to cancel, and we haven't actually cancelled, get the hell out (already running)
    if (isRunning()) {
      return;
    }

    // Set the started flag
    started = true;

    // Otherwise, reset the cancel values and start
    cancel = false;
    cancelled = false;

    Scheduler.get().scheduleFixedPeriod(repeatingCommand, delay);
  }

  public boolean isStarted() {
    return started;
  }

  public boolean isRunning() {
    return !cancel && !cancelled;
  }

  public void cancel() {
    cancel = true;
  }
}
