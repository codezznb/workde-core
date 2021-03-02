package cn.workde.core.db.encypt;

import cn.workde.WorkdeApplication;
import io.ebean.config.EncryptKey;
import io.ebean.config.EncryptKeyManager;

public class BasicEncyptKeyManager implements EncryptKeyManager {

    @Override
    public EncryptKey getEncryptKey(String s, String s1) {
        String key = WorkdeApplication.getConfig("app.encryptKey", "ydool");
        return new BasicEncryptKey(key);
    }
}
