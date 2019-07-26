package com.cornucopia.welinktemplateoftab.domain;

/**
 * 模板三-item bean
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/10
 */
public class TemplateThreeItemBean {

    private int resourceId;
    private String title;

    public TemplateThreeItemBean(int resourceId, String title) {
        this.resourceId = resourceId;
        this.title = title;
    }

    public int getResourceId() {
        return resourceId;
    }

    public void setResourceId(int resourceId) {
        this.resourceId = resourceId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
