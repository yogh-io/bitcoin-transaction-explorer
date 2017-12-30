package com.yoghurt.crypto.transactions.client.ui;

import java.util.ArrayList;

import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.AcceptsOneWidget;
import com.google.inject.Inject;
import com.google.inject.assistedinject.Assisted;
import com.googlecode.gwt.crypto.bouncycastle.util.encoders.Hex;
import com.googlecode.gwt.crypto.util.Str;
import com.yoghurt.crypto.transactions.client.place.BlockPlace;
import com.yoghurt.crypto.transactions.client.util.AppAsyncCallback;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;
import com.yoghurt.crypto.transactions.shared.domain.BlockInformation;
import com.yoghurt.crypto.transactions.shared.service.BlockchainRetrievalServiceAsync;
import com.yoghurt.crypto.transactions.shared.util.ArrayUtil;

public class BlockActivity extends LookupActivity<BlockInformation, BlockPlace> implements BlockView.Presenter {
  private final BlockView view;
  private int height;

  @Inject
  public BlockActivity(final BlockView view, @Assisted final BlockPlace place, final BlockchainRetrievalServiceAsync service) {
    super(place, service);

    this.view = view;

    view.setPresenter(this);
  }

  @Override
  protected void doDeferredStart(final AcceptsOneWidget panel, final BlockInformation blockInformation) {
    panel.setWidget(view);

    if(blockInformation == null) {
      return;
    }

    height = blockInformation.getHeight();

    view.setBlock(blockInformation);
  }

  @Override
  protected void doLookup(final BlockPlace place, final AsyncCallback<BlockInformation> callback) {
    switch (place.getType()) {
    case HEIGHT:
      service.getBlockInformationFromHeight(Integer.parseInt(place.getPayload()), callback);
      break;
    case ID:
      service.getBlockInformationFromHash(place.getPayload(), callback);
      break;
    case LAST:
      service.getBlockInformationLast(callback);
      break;
    case RAW:
      final byte[] computeDoubleSHA256 = ComputeUtil.computeDoubleSHA256(Hex.decode(place.getPayload()));
      ArrayUtil.reverse(computeDoubleSHA256);
      final String blockHash = Str.toString(Hex.encode(computeDoubleSHA256));
      service.getBlockInformationFromHash(blockHash, callback);
      break;
    default:
      callback.onFailure(new IllegalStateException("No support lookup for type: " + place.getType().name()));
      return;
    }
  }

  @Override
  public void loadTransactionList() {
    service.getTransactionList(height, new AppAsyncCallback<ArrayList<String>>() {
      @Override
      public void onSuccess(final ArrayList<String> result) {
        view.setTransactionList(result);
      }
    });
  }

  @Override
  protected void doDeferredError(final AcceptsOneWidget panel, final Throwable caught) {
    // Not supported
  }
}