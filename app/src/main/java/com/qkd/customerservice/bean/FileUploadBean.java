package com.qkd.customerservice.bean;

/**
 * Created on 1/11/21 10:28
 * .
 *
 * @author yj
 * @org 趣看点
 */
public class FileUploadBean {

    /**
     * success : true
     * errorMsg : null
     * error : 0
     * data : http://47.114.100.72/files/media/1610332008418b7ea59b3-8bb9-402f-9208-e08406731cef.mp3
     */

    private boolean success;
    private Object errorMsg;
    private int error;
    private String data;

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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}