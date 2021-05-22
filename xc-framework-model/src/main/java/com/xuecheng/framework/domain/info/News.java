package com.xuecheng.framework.domain.info;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class News {
    private int id;
    private String newsName;
    private String newsIntro;
    private String newsPic;
    private Integer orgId;
    private String newsUrl;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "news_name")
    public String getNewsName() {
        return newsName;
    }

    public void setNewsName(String newsName) {
        this.newsName = newsName;
    }

    @Basic
    @Column(name = "news_intro")
    public String getNewsIntro() {
        return newsIntro;
    }

    public void setNewsIntro(String newsIntro) {
        this.newsIntro = newsIntro;
    }

    @Basic
    @Column(name = "news_pic")
    public String getNewsPic() {
        return newsPic;
    }

    public void setNewsPic(String newsPic) {
        this.newsPic = newsPic;
    }

    @Basic
    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "news_url")
    public String getNewsUrl() {
        return newsUrl;
    }

    public void setNewsUrl(String newsUrl) {
        this.newsUrl = newsUrl;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        News news = (News) o;
        return id == news.id &&
                Objects.equals(newsName, news.newsName) &&
                Objects.equals(newsIntro, news.newsIntro) &&
                Objects.equals(newsPic, news.newsPic) &&
                Objects.equals(orgId, news.orgId) &&
                Objects.equals(newsUrl, news.newsUrl);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, newsName, newsIntro, newsPic, orgId, newsUrl);
    }
}
