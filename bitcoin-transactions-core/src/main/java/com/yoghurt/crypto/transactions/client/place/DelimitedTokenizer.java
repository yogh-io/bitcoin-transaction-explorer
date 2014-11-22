package com.yoghurt.crypto.transactions.client.place;

import com.google.gwt.place.shared.Place;
import com.google.gwt.place.shared.PlaceTokenizer;
import com.yoghurt.crypto.transactions.client.util.misc.StringUtils;

public abstract class DelimitedTokenizer<E extends Place> implements PlaceTokenizer<E> {
  private static final String DELIMITER = ":";

  @Override
  public E getPlace(final String token) {
    final String[] tokens = token.split(DELIMITER);

    return createPlace(tokens);
  }

  protected abstract E createPlace(final String[] tokens);

  @Override
  public String getToken(final E place) {
    return StringUtils.join(DELIMITER, getTokens(place));
  }

  protected abstract String[] getTokens(final E place);
}
