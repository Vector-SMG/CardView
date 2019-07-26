package com.cornucopia.welinktemplateoftab.domain;

/**
 * 模板一-顶部视图-Items JavaBean
 *
 * @author l84116371
 * @version 1.0
 * @since 2019/7/18
 */
public class TemplateOneTopItemBean {

    private String title;
    private String title_en;
    private String title_color;
    private String value;
    private String value_color;
    private String description;
    private String description_en;
    private String description_color;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitle_en() {
        return title_en;
    }

    public void setTitle_en(String title_en) {
        this.title_en = title_en;
    }

    public String getTitle_color() {
        return title_color;
    }

    public void setTitle_color(String title_color) {
        this.title_color = title_color;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getValue_color() {
        return value_color;
    }

    public void setValue_color(String value_color) {
        this.value_color = value_color;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription_en() {
        return description_en;
    }

    public void setDescription_en(String description_en) {
        this.description_en = description_en;
    }

    public String getDescription_color() {
        return description_color;
    }

    public void setDescription_color(String description_color) {
        this.description_color = description_color;
    }

    @Override
    public String toString() {
        return "TemplateTwoTopItemBean{" +
                "title='" + title + '\'' +
                ", title_en='" + title_en + '\'' +
                ", title_color='" + title_color + '\'' +
                ", value='" + value + '\'' +
                ", value_color='" + value_color + '\'' +
                ", description='" + description + '\'' +
                ", description_en='" + description_en + '\'' +
                ", description_color='" + description_color + '\'' +
                '}';
    }

}
