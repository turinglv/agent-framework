package com.google.common.collect;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImmutableList {

  public static <T> List<T> copyOf(List<T> list) {
    return new ArrayList<>(list);
  }

  public static <T> List<T> copyOf(Iterable<T> iterable) {
    if (iterable == null) {
      return Collections.emptyList();
    }
    List<T> list = new ArrayList<>();
    for (T t : iterable) {
      list.add(t);
    }
    return list;
  }

  public static <T> List<T> of(T... elements) {
    if (elements == null) {
      return Collections.emptyList();
    }
    ArrayList<T> list = new ArrayList<>();
    for (T t : elements) {
      list.add(t);
    }
    return list;
  }
}
