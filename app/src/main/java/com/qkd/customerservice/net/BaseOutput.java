package com.qkd.customerservice.net;

import java.io.Serializable;

/**
 * Created on 2020/10/22 14:33
 * .
 *
 * @author yj
 */
public class BaseOutput implements Serializable {

    private boolean success;
    private Object errorMsg;
    private int error;

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Object getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(Object errorMsg) {
        this.errorMsg = errorMsg;
    }

    public int getError() {
        return error;
    }

    public void setError(int error) {
        this.error = error;
    }
}
