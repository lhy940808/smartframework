package com.lhy.javaweb.smart.bean;

/**
 * 返回数据对象
 * @author liuhaiyan
 * @date 2019-12-31 17:19
 */
public class Data {

    /**
     * 模型数据
     * */
    private Object model;

    public Data(Object model) {
        this.model = model;
    }

    public Object getModel() {
        return model;
    }
}
