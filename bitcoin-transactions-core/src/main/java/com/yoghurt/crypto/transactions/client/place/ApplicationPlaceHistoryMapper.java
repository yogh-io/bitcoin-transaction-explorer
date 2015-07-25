package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.PlaceHistoryMapper;
import com.google.gwt.place.shared.WithTokenizers;

@WithTokenizers({ StartupPlace.Tokenizer.class, TransactionPlace.Tokenizer.class, BlockPlace.Tokenizer.class,
  MinePlace.Tokenizer.class, ScriptPlace.Tokenizer.class, ConfigPlace.Tokenizer.class,
  ContributePlace.Tokenizer.class, RPCResponsePlace.Tokenizer.class })
public interface ApplicationPlaceHistoryMapper extends PlaceHistoryMapper {}
