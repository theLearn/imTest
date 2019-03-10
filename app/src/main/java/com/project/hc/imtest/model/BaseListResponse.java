package com.project.hc.imtest.model;

import java.util.List;

/**
 * Created by hongcheng on 16/3/30.
 */
public class BaseListResponse<T>{
    protected String count;
    protected List<T> data;

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }
}
