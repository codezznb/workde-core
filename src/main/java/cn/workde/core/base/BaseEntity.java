package cn.workde.core.base;

import cn.workde.WorkdeApplication;
import io.ebean.annotation.*;
import io.swagger.annotations.ApiModelProperty;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.Version;
import java.time.LocalDateTime;

@Slf4j
@Getter
@Setter
@EqualsAndHashCode(of = "id", callSuper = false)
@Cache(naturalKey = "id")
@MappedSuperclass
public abstract class BaseEntity extends BaseModel {

    @Id
    @GeneratedValue(generator="uuid")
    protected String id;

    @Version
    @ApiModelProperty(hidden = true)
    private Long version;

    /**
     * 插入时间
     */
    @WhenCreated
    @ApiModelProperty(hidden = true)
    protected LocalDateTime createdAt;

    /**
     * 最后更新时间
     */
    @WhenModified
    @ApiModelProperty(hidden = true)
    protected LocalDateTime updatedAt;

    @WhoCreated
    @ApiModelProperty(hidden = true)
    private String createdBy;

    @WhoModified
    @ApiModelProperty(hidden = true)
    private String updatedBy;

    @SoftDelete
    @ApiModelProperty(hidden = true)
    private boolean deleted;

    @Override
    protected String getDbName() {
        Class<? extends BaseModel> modelClass = getClass();
        return getDbNameByModel(modelClass);
    }

    private static String getDbNameByModel(Class<? extends BaseModel> modelClass) {
        String pluginName = modelClass.getPackage().getName().split("\\.")[0];
        return WorkdeApplication.getPlugin(pluginName).getDbName();
    }

    @Override
    protected void save() {
        super.save();
    }

    @Override
    protected void update() {
        super.update();
    }

    @Override
    protected void insert() {
        super.insert();
    }

    public static class Finder<T extends BaseModel> extends io.ebean.Finder<String, T> {
        protected final Class<T> type;

        public Finder(Class<T> type) {
            super(type, getDbNameByModel(type));
            this.type = type;
        }
    }

}
