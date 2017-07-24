package com.thrblock.cino.util.structure;

/**
 * 泛型对象对
 * @author thrblock
 *
 * @param <K>
 * @param <V>
 */
public class Pair<K,V> {
    private final K key;
    private final V value;
    /**构造一个泛型对象对
     * @param k
     * @param v
     */
    public Pair(K k,V v) {
        this.key = k;
        this.value = v;
    }
    public K getKey() {
        return key;
    }
    public V getValue() {
        return value;
    }
    
    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Pair) {
            Pair<?, ?> another = (Pair<?, ?>)obj;
            return another.key.equals(key) && another.value.equals(value);
        }
        return false;
    }
    
    @Override
    public int hashCode() {
        return key.hashCode() ^ value.hashCode();
    }
    
}
