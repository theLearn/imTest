package com.example.hongcheng.data.retrofit.response;


import com.example.hongcheng.common.constant.BaseConstants;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseResponse<T>{
    protected String code;
    protected String msg;
    protected T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public boolean isSuccess(){
        String success = "" + BaseConstants.STATUS_SUCCESS;
        return success.equals(code);
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "code='" + code + '\'' +
                ", msg='" + msg + '\'' +
                ", data=" + data +
                '}';
    }
}
