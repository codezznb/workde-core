package cn.workde.core.base;

import cn.workde.core.db.Paginator;
import cn.workde.core.db.SimpleQuery;
import io.ebean.PagedList;
import io.ebean.Query;

import java.util.List;
import java.util.Optional;

public abstract class BaseService<T extends BaseEntity> {

    protected final BaseEntity.Finder<T> finder;

    protected final Class<T> entityClass;

    public BaseService(Class<T> entityClass) {
        this.entityClass = entityClass;
        finder = new BaseEntity.Finder<>(this.entityClass);
    }

    public Optional<T> findById(String id) {
        return Optional.ofNullable(finder.byId(id));
    }

    public List<T> findList(SimpleQuery cond) {
        Query<T> query = getQuery();
        return query.select(String.join(",", cond.getColumns())).where(cond.whereExpression(query)).setOrderBy(cond.orderBy()).findList();
    }

    public T findBy(String column, Object value) {
        Query<T> query = getQuery();
        return query.where().eq(column, value).findOne();
    }

    public Paginator<T> find(SimpleQuery cond) {
        Query<T> query = getQuery();
        PagedList<T> pagedList = query
                .select(String.join(",", cond.getColumns()))
                .where(cond.whereExpression(query))
                .setOrderBy(cond.orderBy())
                .setMaxRows(cond.getPageSize())
                .setFirstRow(cond.offSet())
                .findPagedList();
        return new Paginator<T>(pagedList);
    }

    public Query<T> getQuery() {
        return this.finder.query();
    }
}
