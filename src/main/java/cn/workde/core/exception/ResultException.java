package cn.workde.core.exception;

import cn.workde.core.common.Ret;

public class ResultException extends RuntimeException {

    private Ret ret;

    public ResultException(){
        this.ret = Ret.ok();
    }

    public ResultException(Ret ret){
        this.ret = ret;
    }

    public Ret getResult() {
        return ret;
    }
}
