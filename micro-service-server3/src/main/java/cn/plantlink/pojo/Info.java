package cn.plantlink.pojo;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;
import java.util.List;

/**
 * @Author colddew
 * @Date 2022-01-27
 */
public class Info {

    private String contentId;
    private Integer contentType;
    private String title;
    private String content;
    private String introduce;
    private String authorId;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date releaseTime;
    private Integer recommend;
    private List<String> stockCodes;
    private List<String> stockNames;
    private List<String> stockSpellCodes;
    private List<String> tags;
    private Double artificialWeight;
    private Double complexWeight;

    public String getContentId() {
        return contentId;
    }

    public void setContentId(String contentId) {
        this.contentId = contentId;
    }

    public Integer getContentType() {
        return contentType;
    }

    public void setContentType(Integer contentType) {
        this.contentType = contentType;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getIntroduce() {
        return introduce;
    }

    public void setIntroduce(String introduce) {
        this.introduce = introduce;
    }

    public String getAuthorId() {
        return authorId;
    }

    public void setAuthorId(String authorId) {
        this.authorId = authorId;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(Date releaseTime) {
        this.releaseTime = releaseTime;
    }

    public Integer getRecommend() {
        return recommend;
    }

    public void setRecommend(Integer recommend) {
        this.recommend = recommend;
    }

    public List<String> getStockCodes() {
        return stockCodes;
    }

    public void setStockCodes(List<String> stockCodes) {
        this.stockCodes = stockCodes;
    }

    public List<String> getStockNames() {
        return stockNames;
    }

    public void setStockNames(List<String> stockNames) {
        this.stockNames = stockNames;
    }

    public List<String> getStockSpellCodes() {
        return stockSpellCodes;
    }

    public void setStockSpellCodes(List<String> stockSpellCodes) {
        this.stockSpellCodes = stockSpellCodes;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public Double getArtificialWeight() {
        return artificialWeight;
    }

    public void setArtificialWeight(Double artificialWeight) {
        this.artificialWeight = artificialWeight;
    }

    public Double getComplexWeight() {
        return complexWeight;
    }

    public void setComplexWeight(Double complexWeight) {
        this.complexWeight = complexWeight;
    }
}