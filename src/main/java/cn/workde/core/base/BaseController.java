package cn.workde.core.base;

import cn.workde.core.common.Ret;
import cn.workde.core.db.Paginator;
import cn.workde.core.exception.ResultException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     * ============================     REQUEST    =================================================
     */

    @Autowired
    private HttpServletRequest request;

    /**
     * 获取request
     */
    protected HttpServletRequest getRequest() {
        return this.request;
    }

    protected void render(Ret ret) {
        throw new ResultException(ret);
    }

    protected void paged(Paginator paginator) {
        render(Ret.ok().paged(paginator));
    }
    protected void fail(String msg) {
        render(Ret.fail(msg));
    }

    protected void data(Object data) {
        render(Ret.ok().data(data));
    }
}
