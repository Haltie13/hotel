package pl.edu.agh.kis.pz1.util;
import java.util.*;

public class MyMap<K, V> implements Map<K, V> {
    private final List<K> keys = new ArrayList<>();
    private final List<V> values = new ArrayList<>();

    @Override
    public V put(K key, V value) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        int index = keys.indexOf(key);
        if (index == -1) {
            keys.add(key);
            values.add(value);
            return null;
        }
        return values.set(index, value);
    }

    @Override
    public V get(Object key) {
        if (key == null) {
            throw new NullPointerException("key is null");
        }
        int index = keys.indexOf(key);
        if (index != -1) {
            return values.get(index);
        }
        return null;
    }

    @Override
    public List<V> values() {
        return new ArrayList<>(values);
    }

    public List<K> keys() {
        return new ArrayList<>(keys);
    }

    @Override
    public int size() {
        return keys.size();
    }

    @Override
    public boolean isEmpty() {
        return keys.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsValue(Object value) {
        if (value == null)
            return false;
        return values.contains(value);
    }


    @Override
    public V remove(Object key) {
        return null;
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> m) {
        throw new UnsupportedOperationException();
    }


    @Override
    public void clear() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<K> keySet() {
        throw new UnsupportedOperationException();
    }

    @Override
    public Set<Entry<K, V>> entrySet() {
        throw new UnsupportedOperationException();
    }


}
