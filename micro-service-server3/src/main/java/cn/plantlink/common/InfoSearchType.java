package cn.plantlink.common;

public enum InfoSearchType {

    COLUMN(1, "专栏"),
    USER_ARTICLE(2, "用户发文"),
    INFORMATION(3, "资讯"),
    EXPRESS(4, "快讯"),
    MOMENTS(5, "社区帖子");

    private int value;
    private String description;

    InfoSearchType(int value, String description) {
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
