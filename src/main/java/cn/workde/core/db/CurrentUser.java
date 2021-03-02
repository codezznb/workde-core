package cn.workde.core.db;

import io.ebean.config.CurrentUserProvider;
import org.springframework.stereotype.Component;

@Component
public class CurrentUser implements CurrentUserProvider {

    @Override
    public Object currentUser() {
        return "guest";
    }
}
