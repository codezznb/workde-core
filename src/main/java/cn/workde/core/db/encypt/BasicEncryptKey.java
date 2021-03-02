package cn.workde.core.db.encypt;

import io.ebean.config.EncryptKey;

public class BasicEncryptKey implements EncryptKey {

    private String key;

    public BasicEncryptKey(String key) {
        this.key = key;
    }


    @Override
    public String getStringValue() {
        return key;
    }
}
