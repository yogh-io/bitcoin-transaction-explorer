package com.yoghurt.crypto.transactions.client.di;

import com.yoghurt.crypto.transactions.client.place.AddressPlaceRouter;
import com.yoghurt.crypto.transactions.client.place.BlockPlaceRouter;
import com.yoghurt.crypto.transactions.client.place.ScriptPlaceRouter;
import com.yoghurt.crypto.transactions.client.place.TransactionPlaceRouter;

public interface BitcoinPlaceRouter extends BlockPlaceRouter, TransactionPlaceRouter, ScriptPlaceRouter, AddressPlaceRouter {


}
