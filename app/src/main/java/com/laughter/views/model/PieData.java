package com.laughter.views.model;

/**
 * 作者： laughter_jiang
 * 创建时间： 2019/1/2
 * 版权：
 * 描述： com.laughter.views.model
 */
public class PieData {

    private String name;
    private float value;
    private float percentage;

    private int color = 0;
    private float angle = 0;

    public PieData(String name, float value){
        this.name = name;
        this.value = value;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setValue(float values) {
        this.value = value;
    }

    public void setPercentage(float percentage) {
        this.percentage = percentage;
    }

    public void setColor(int color) {
        this.color = color;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public String getName() {
        return name;
    }

    public float getValue() {
        return value;
    }

    public float getPercentage() {
        return percentage;
    }

    public int getColor() {
        return color;
    }

    public float getAngle() {
        return angle;
    }
}
