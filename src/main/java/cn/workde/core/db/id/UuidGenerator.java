package cn.workde.core.db.id;

import io.ebean.config.IdGenerator;

import java.util.UUID;

public class UuidGenerator implements IdGenerator {

    @Override
    public Object nextValue() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }

    @Override
    public String getName() {
        return "uuid";
    }
}
