package org.atar.utils;


/**
 * A class that stores two values of specified type.
 * Useful when in need of storing coordinates or dimension.
 * */
public class Pair<A, B> {
    private A first;
    private B second;

    public Pair(A _first, B _second) {
        this.first = _first;
        this.second = _second;
    }

    public void setFirst(A _val) { this.first = _val; }
    public void setSecond(B _val) { this.second = _val; }
    public A getFirst() { return this.first; }
    public B getSecond() { return this.second; }
}
