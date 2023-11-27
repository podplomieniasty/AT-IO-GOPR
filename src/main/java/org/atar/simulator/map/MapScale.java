package org.atar.simulator.map;

/**
 * MapScale reprezentuje skalę wybraną do reprezentacji mapy w rzeczywistości.
 * Jest ona zrobiona w formie enuma na potrzeby projektu.
 * Konstruktor pozwala na utworzenie enuma z wartością, dzięki czemu możemy pobrać
 * jego zawartość.
 * */
public enum MapScale {

    VERY_BIG(1000000), // why like that? no one knows
    EXACT(1);

    private final double _value;
    private MapScale(double _val) {
        this._value = _val;
    }
    public double value() {
        return this._value;
    }
}
