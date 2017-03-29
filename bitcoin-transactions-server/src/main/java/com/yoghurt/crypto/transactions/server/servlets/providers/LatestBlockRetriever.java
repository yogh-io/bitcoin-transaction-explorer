package com.yoghurt.crypto.transactions.server.servlets.providers;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import com.yoghurt.crypto.transactions.shared.domain.ApplicationException;

public class LatestBlockRetriever {
  private static final long INITIAL_DELAY = 1;
  private static final long BLOCK_RETRIEVE_DELAY = 30;

  private final AtomicReference<String> atomicLastBlockHash = new AtomicReference<>();

  private final ScheduledExecutorService executor;

  private final Runnable command = new Runnable() {
    @Override
    public void run() {
      try {
        atomicLastBlockHash.set(hook.getLatestBlockHash());
      } catch (final ApplicationException e) {
        // Ignore
      }
    }
  };

  private final BlockchainRetrievalService hook;

  public LatestBlockRetriever(final BlockchainRetrievalService hook) {
    this.hook = hook;
    executor = Executors.newSingleThreadScheduledExecutor();
  }

  public void start() {
    executor.scheduleWithFixedDelay(command, INITIAL_DELAY, BLOCK_RETRIEVE_DELAY, TimeUnit.SECONDS);
  }

  public String getLatestHash() {
    return atomicLastBlockHash.get();
  }

  public void stop() {
    executor.shutdownNow();
  }
}
