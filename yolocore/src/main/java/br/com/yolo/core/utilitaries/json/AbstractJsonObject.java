package br.com.yolo.core.utilitaries.json;

import com.google.gson.internal.LazilyParsedNumber;

import java.math.BigDecimal;
import java.math.BigInteger;

public abstract class AbstractJsonObject {

    private Object value;

    public AbstractJsonObject(Object initialValue) {
        this.value = initialValue;
    }

    public void setValue(Object value) {
        this.value = value;
    }

    public Object getAsObject() {
        return value;
    }

    public boolean isBoolean() {
        return value instanceof Boolean;
    }

    public boolean getAsBoolean() {
        if (isBoolean())
            return (Boolean) value;
        return Boolean.parseBoolean(getAsString());
    }

    public boolean isNumber() {
        return value instanceof Number;
    }

    public Number getAsNumber() {
        return isString() ? new LazilyParsedNumber((String) value) : (Number) value;
    }

    public boolean isString() {
        return value instanceof String;
    }

    public String getAsString() {
        if (isNumber()) {
            return getAsNumber().toString();
        } else if (isBoolean()) {
            return ((Boolean) value).toString();
        } else {
            return (String) value;
        }
    }

    public double getAsDouble() {
        return isNumber() ? getAsNumber().doubleValue() : Double.parseDouble(getAsString());
    }

    public BigDecimal getAsBigDecimal() {
        return value instanceof BigDecimal ? (BigDecimal) value : new BigDecimal(value.toString());
    }

    public BigInteger getAsBigInteger() {
        return value instanceof BigInteger ?
                (BigInteger) value : new BigInteger(value.toString());
    }

    public float getAsFloat() {
        return isNumber() ? getAsNumber().floatValue() : Float.parseFloat(getAsString());
    }

    public long getAsLong() {
        return isNumber() ? getAsNumber().longValue() : Long.parseLong(getAsString());
    }

    public short getAsShort() {
        return isNumber() ? getAsNumber().shortValue() : Short.parseShort(getAsString());
    }

    public int getAsInt() {
        return isNumber() ? getAsNumber().intValue() : Integer.parseInt(getAsString());
    }

    public byte getAsByte() {
        return isNumber() ? getAsNumber().byteValue() : Byte.parseByte(getAsString());
    }

    public char getAsCharacter() {
        return getAsString().charAt(0);
    }
}

