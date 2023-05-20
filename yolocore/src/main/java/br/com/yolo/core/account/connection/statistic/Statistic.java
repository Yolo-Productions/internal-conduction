package br.com.yolo.core.account.connection.statistic;

import br.com.yolo.core.util.json.AbstractJsonObject;
import lombok.Getter;
import lombok.Setter;

public final class Statistic extends AbstractJsonObject {

    @Getter @Setter private boolean ignoreNegative = false;

    @SuppressWarnings("UnnecessaryBoxing")
    public Statistic(int initialValue) {
        // É necessário valores wrappers para identificação do gson
        super(Integer.valueOf(initialValue));
    }

    @Override
    public void setValue(Object value) {
        if (value instanceof Integer) {
            if (!ignoreNegative && ((Integer) value) < 0)
                value = 0;
            super.setValue(value);
        }
    }

    public int add(int i) {
        setValue(getAsInt() + i);
        return getAsInt();
    }

    public int subtract(int i) {
        setValue(getAsInt() - i);
        return getAsInt();
    }

    public int multiply(int i) {
        setValue(getAsInt() * i);
        return getAsInt();
    }
}
