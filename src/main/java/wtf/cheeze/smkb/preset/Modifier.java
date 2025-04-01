package wtf.cheeze.smkb.preset;

public enum Modifier {
    ALT(0),
    CONTROL(1),
    SHIFT(2),
    SUPER(3);

    /**
     * The index of the modifier in the array that amecs stores the modifiers in
     */
    public final int amecsID;

    Modifier(int amecsID) {
        this.amecsID = amecsID;
    }
}
