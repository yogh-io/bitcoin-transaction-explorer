package com.yoghurt.crypto.transactions.server.servlets.background;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;

import org.apache.http.HttpException;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.util.EntityUtils;

import com.yoghurt.crypto.transactions.server.servlets.util.HttpClientProxy;

public class LatestBlockRetriever {
  /**
   * This call should be blocking (up to 2 minutes, reportedly) from the second request onward, making it (more) safe to call with only a short delay.
   */
  private static final String BLOCK_EXPLORER_API_LATEST_BLOCK = "http://blockexplorer.com/q/latesthash";
  //  private static final String BLOCKR_API_LATEST_BLOCK = "http://btc.blockr.io/api/v1/block/info/last";
  //  private static final String BLOCKCHAIN_API_LATEST_BLOCK = "https://blockchain.info/latestblock";

  private static final long INITIAL_DELAY = 1;
  private static final long BLOCK_RETRIEVE_DELAY = 30;

  private final AtomicReference<String> lastBlockHash = new AtomicReference<>();

  private final ScheduledExecutorService executor;

  private final Runnable command = new Runnable() {
    @Override
    public void run() {
      try (CloseableHttpClient client = HttpClientBuilder.create().build()) {
        lastBlockHash.set(EntityUtils.toString(HttpClientProxy.getRemoteContent(client, BLOCK_EXPLORER_API_LATEST_BLOCK)));

        // Log to sysout for now
        System.out.println(lastBlockHash.get());
      } catch (URISyntaxException | IOException | HttpException e) {
        e.printStackTrace();
      }
    }
  };

  public LatestBlockRetriever() {
    executor = Executors.newSingleThreadScheduledExecutor();
  }

  public void start() {
    executor.scheduleWithFixedDelay(command, INITIAL_DELAY, BLOCK_RETRIEVE_DELAY, TimeUnit.SECONDS);
  }

  public String getLatestHash() {
    return lastBlockHash.get();
  }

  public void stop() {
    executor.shutdownNow();
  }
}
