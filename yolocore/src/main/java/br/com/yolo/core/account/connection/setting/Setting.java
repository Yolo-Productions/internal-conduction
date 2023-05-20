package br.com.yolo.core.account.connection.setting;

import br.com.yolo.core.util.json.AbstractJsonObject;

public final class Setting extends AbstractJsonObject {

    public Setting(Object initialValue) {
        super(initialValue);
    }

    public Setting(String initialValue) {
        super(initialValue);
    }

    public Setting(Long initialValue) {
        super(initialValue);
    }

    public Setting(Double initialValue) {
        super(initialValue);
    }

    @SuppressWarnings("UnnecessaryBoxing")
    public Setting(int initialValue) {
        // É necessário valores wrappers para identificação do gson
        super(new Integer(initialValue));
    }

    public boolean hasEmptyString() {
        if (isString())
            return getAsString().isEmpty();
        return false;
    }
}
