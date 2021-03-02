package cn.workde.core.db;

import io.ebean.PagedList;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.util.List;

@ApiModel("分页结果")
@Getter
@Setter
public class Paginator<T> implements Serializable {

    @ApiModelProperty("当前页码，从1开始")
    private int pageIndex;
    @ApiModelProperty("页面大小")
    private int pageSize;
    @ApiModelProperty("总记录数")
    private int total;
    @ApiModelProperty("总页数")
    private int totalPage;
    @ApiModelProperty("当前页数据")
    private List<T> data;

    public Paginator(PagedList<T> pagedList) {
        pagedList.loadCount();
        this.pageIndex = pagedList.getPageIndex();
        this.pageSize = pagedList.getPageSize();
        this.total = pagedList.getTotalCount();
        this.totalPage = pagedList.getTotalPageCount();
        this.data = pagedList.getList();
    }

    public Paginator(int pageIndex, int pageSize, int total, int totalPage, List<T> data) {
        this.pageIndex = pageIndex;
        this.pageSize = pageSize;
        this.total = total;
        this.totalPage = totalPage;
        this.data = data;
    }

}
