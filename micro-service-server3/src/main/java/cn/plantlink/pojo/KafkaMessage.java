package cn.plantlink.pojo;

import java.util.Date;

/**
 * @Author colddew
 * @Date 2022-02-13
 */
public class KafkaMessage {

    private String Id;
    private String content;
    private Date date;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }
}
