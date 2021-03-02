package cn.workde.core.base;

import io.ebean.DB;
import io.ebean.Database;
import io.ebean.Ebean;
import io.ebean.EbeanServer;
import io.ebean.bean.EntityBean;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.MappedSuperclass;

@Slf4j
@MappedSuperclass
public abstract class BaseModel {

    /**
     * db name
     *
     * @return db name
     */
    protected abstract String getDbName();

    @Deprecated
    protected EbeanServer ebeanServer() {
        return Ebean.getServer(getDbName());
    }

    protected Database db() {
        return DB.byName(getDbName());
    }

    protected void markAsDirty() {
        db().markAsDirty(this);
    }

    protected void markPropertyUnset(String propertyName) {
        ((EntityBean) this)._ebean_getIntercept().setPropertyLoaded(propertyName, false);
    }

    protected void save() {
        db().save(this);
    }

    protected void flush() {
        db().flush();
    }

    protected void update() {
        db().update(this);
    }

    protected void insert() {
        db().insert(this);
    }

    protected boolean delete() {
        return db().delete(this);
    }

    protected boolean deletePermanent() {
        return db().deletePermanent(this);
    }

    protected void refresh() {
        db().refresh(this);
    }
}
