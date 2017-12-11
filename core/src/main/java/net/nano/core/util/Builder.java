package net.nano.core.util;

public interface Builder<B extends Builder,T> {

    B reset();

    T build();
}
