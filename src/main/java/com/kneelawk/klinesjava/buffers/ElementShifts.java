package com.kneelawk.klinesjava.buffers;

public class ElementShifts {
    public static final int ELEMENT_SHIFT_BYTE = 0;
    public static final int ELEMENT_SHIFT_SHORT = 1;
    public static final int ELEMENT_SHIFT_CHAR = 1;
    public static final int ELEMENT_SHIFT_INT = 2;
    public static final int ELEMENT_SHIFT_LONG = 3;
    public static final int ELEMENT_SHIFT_FLOAT = 2;
    public static final int ELEMENT_SHIFT_DOUBLE = 3;

    public static final int ELEMENT_SIZE_BYTE = 1 << ELEMENT_SHIFT_BYTE;
    public static final int ELEMENT_SIZE_SHORT = 1 << ELEMENT_SHIFT_SHORT;
    public static final int ELEMENT_SIZE_CHAR = 1 << ELEMENT_SHIFT_CHAR;
    public static final int ELEMENT_SIZE_INT = 1 << ELEMENT_SHIFT_INT;
    public static final int ELEMENT_SIZE_LONG = 1 << ELEMENT_SHIFT_LONG;
    public static final int ELEMENT_SIZE_FLOAT = 1 << ELEMENT_SHIFT_FLOAT;
    public static final int ELEMENT_SIZE_DOUBLE = 1 << ELEMENT_SHIFT_DOUBLE;
}
