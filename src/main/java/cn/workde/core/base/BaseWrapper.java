package cn.workde.core.base;

import cn.workde.core.db.Paginator;

import java.util.List;
import java.util.stream.Collectors;

public abstract class BaseWrapper<E, V> {

    /**
     * 单个实体类包装
     *
     * @param entity
     * @return
     */
    public abstract V entityVO(E entity);

    /**
     * 实体类集合包装
     *
     * @param list
     * @return
     */
    public List<V> listVO(List<E> list) {
        return list.stream().map(this::entityVO).collect(Collectors.toList());
    }


    /**
     * 分页实体类集合包装
     * @param paginator
     * @return
     */
    public Paginator<V> pageVO(Paginator<E> paginator) {
        List<V> records = listVO(paginator.getData());
        Paginator<V> pageVo = new Paginator<>(paginator.getPageIndex(), paginator.getPageSize(), paginator.getTotal(), paginator.getTotalPage(), records);
        return pageVo;
    }

}
