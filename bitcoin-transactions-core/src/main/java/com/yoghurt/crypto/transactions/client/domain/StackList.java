package com.yoghurt.crypto.transactions.client.domain;

import java.util.ArrayList;

public class StackList<E> extends ArrayList<E> {
  private static final long serialVersionUID = -7591319741756648589L;

  /**
   * Pop the top item off the stack and return it.
   * 
   * @return Top item on the stack.
   */
  public E pop() {
    final E e = peek();
    remove(size() - 1);
    return e;
  }

  /**
   * Return the top item on the stack.
   * 
   * @return Top item on the stack.
   */
  public E peek() {
    return get(size() - 1);
  }

  /**
   * Push an item to the top of the stack.
   * 
   * @param e Item to push onto the stack.
   */
  public void push(final E e) {
    add(e);
  }
}