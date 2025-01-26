package dev.eths.legacytab.util;

import lombok.Getter;

import java.io.Serializable;
import java.util.Objects;

@Getter
public class Pair<K,V> implements Serializable {

    private final K key;

    private final V value;

    public Pair(K key, V value) {
        this.key = key;
        this.value = value;
    }

    @Override
    public String toString() {
        return key + "=" + value;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true; // Same object reference
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false; // Null or different class
        }
        Pair<?, ?> other = (Pair<?, ?>) obj; // Cast to Pair
        return Objects.equals(key, other.key) && Objects.equals(value, other.value);
    }

    @Override
    public int hashCode() {
        return Objects.hash(key, value); // Use both fields to generate hash
    }
}
