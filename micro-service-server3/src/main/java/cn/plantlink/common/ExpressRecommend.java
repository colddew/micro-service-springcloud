package cn.plantlink.common;

public enum ExpressRecommend {

    COMMON(0, "普通"),
    IMPORTANT(1, "重要"),
    VERY_IMPORTANT(2, "非常重要");

    private int value;
    private String description;

    ExpressRecommend(int value, String description) {
        this.value = value;
        this.description = description;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
