package com.yoghurt.crypto.transactions.client.util.script;

import java.util.Deque;
import java.util.Iterator;

import com.googlecode.gwt.crypto.bouncycastle.util.Arrays;
import com.yoghurt.crypto.transactions.client.util.transaction.ComputeUtil;
import com.yoghurt.crypto.transactions.client.util.transaction.ScriptOperationUtil;
import com.yoghurt.crypto.transactions.shared.domain.StackObject;

public final class ScriptExecutionUtil {
  private static final byte[] FALSE = new byte[] { };

  private static final byte[] TRUE = new byte[] { 0x01 };

  private ScriptExecutionUtil() {}

  public static void execute(final ExecutionStep step) {
    final Deque<StackObject> stack = step.getStack();

    if(ScriptOperationUtil.isDataPushOperation(step.getInstruction().getOperation())) {
      addStackObject(stack, step.getInstruction().getBytes());
    }

    switch(step.getInstruction().getOperation()) {
    case OP_DUP:
      addStackObject(stack, stack.peek());
      break;
    case OP_2DUP:
      final Iterator<StackObject> dup2Iterator = stack.iterator();
      addStackObject(stack, dup2Iterator.next());
      addStackObject(stack, dup2Iterator.next());
      break;
    case OP_3DUP:
      final Iterator<StackObject> dup3Iterator = stack.iterator();
      addStackObject(stack, dup3Iterator.next());
      addStackObject(stack, dup3Iterator.next());
      addStackObject(stack, dup3Iterator.next());
      break;
    case OP_DROP:
      stack.remove();
      break;
    case OP_2DROP:
      stack.remove();
      stack.remove();
      break;
    case OP_CHECKSIG:
      stack.remove();
      stack.remove();

      // TODO Do actual checksig
      addTrue(stack);
      break;
    case OP_EQUAL:
      final StackObject objEqualA = stack.poll();
      final StackObject objEqualB = stack.poll();

      if(Arrays.areEqual(objEqualA.getBytes(), objEqualB.getBytes())) {
        addTrue(stack);
      } else {
        addFalse(stack);
      }
      break;
    case OP_EQUALVERIFY:
      final StackObject objEqualVerifyA = stack.poll();
      final StackObject objEqualVerifyB = stack.poll();

      if(!Arrays.areEqual(objEqualVerifyA.getBytes(), objEqualVerifyB.getBytes())) {
        addException(step);
      }
      break;
    case OP_HASH160:
      final StackObject poll = stack.poll();

      final byte[] hash160 = ComputeUtil.computeHash160(poll.getBytes());

      addStackObject(stack, hash160);
      break;
    case OP_TRUE:
      addTrue(stack);
      break;
    case OP_FALSE:
      addFalse(stack);
      break;
    case OP_2:
    case OP_3:
    case OP_4:
    case OP_5:
    case OP_6:
    case OP_7:
    case OP_8:
    case OP_9:
    case OP_10:
    case OP_11:
    case OP_12:
    case OP_13:
    case OP_14:
    case OP_15:
    case OP_16:
      addStackObject(stack, step.getInstruction().getOperation().getOpcode() - 80);
      break;
    default:
    }
  }

  private static void addException(final ExecutionStep step) {
    step.setExecutionError(true);
  }

  private static void addTrue(final Deque<StackObject> stack) {
    addStackObject(stack, TRUE);
  }

  private static void addFalse(final Deque<StackObject> stack) {
    addStackObject(stack, FALSE);
  }

  private static void addStackObject(final Deque<StackObject> stack, final int value) {
    addStackObject(stack, new StackObject(new byte[] { (byte) value }));
  }

  private static void addStackObject(final Deque<StackObject> stack, final byte[] bytes) {
    addStackObject(stack, new StackObject(bytes));
  }

  private static void addStackObject(final Deque<StackObject> stack, final StackObject object) {
    stack.addFirst(object);
  }
}
