package com.defano.jmonet.model;

import java.util.Observable;
import java.util.Observer;

/**
 * An extension to Java's {@link Observable} and {@link Observer} with features for chaining and transforming observed
 * values.
 *
 * @param <T> The type of object being "provided"
 */
public class Provider<T> extends ImmutableProvider<T> implements Observer {

    private ProviderTransform transform;

    public Provider() {
        value = null;
    }

    public Provider(T initialValue) {
        this.value = initialValue;
    }

    public <S> Provider(Provider<S> derivedFrom, ProviderTransform<S, T> transform) {
        this.transform = transform;

        setSource(derivedFrom);
        update(derivedFrom, derivedFrom.get());
    }

    public static <S, T> Provider<T> derivedFrom(Provider<S> derivedFrom, ProviderTransform<S, T> transform) {
        return new Provider<>(derivedFrom, transform);
    }

    public void set(T value) {
        this.value = value;

        setChanged();
        notifyObservers(this.value);
    }

    /** {@inheritDoc} */
    @Override
    public void update(Observable o, Object arg) {
        if (transform != null) {
            set((T) transform.transform(arg));
        } else {
            set((T) arg);
        }
    }

}
