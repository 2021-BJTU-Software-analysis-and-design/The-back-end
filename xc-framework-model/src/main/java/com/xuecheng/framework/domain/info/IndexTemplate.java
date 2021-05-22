package com.xuecheng.framework.domain.info;

import javax.persistence.*;
import java.util.Objects;

@Entity
@Table(name = "index_template", schema = "info")
public class IndexTemplate {
    private int id;
    private Integer registerNum;
    private Integer learningNum;
    private Integer likeNum;
    private Integer downloadNum;
    private String indexPic;
    private String productIdList;
    private String companyIntro;
    private String contactEmail;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "register_num")
    public Integer getRegisterNum() {
        return registerNum;
    }

    public void setRegisterNum(Integer registerNum) {
        this.registerNum = registerNum;
    }

    @Basic
    @Column(name = "learning_num")
    public Integer getLearningNum() {
        return learningNum;
    }

    public void setLearningNum(Integer learningNum) {
        this.learningNum = learningNum;
    }

    @Basic
    @Column(name = "like_num")
    public Integer getLikeNum() {
        return likeNum;
    }

    public void setLikeNum(Integer likeNum) {
        this.likeNum = likeNum;
    }

    @Basic
    @Column(name = "download_num")
    public Integer getDownloadNum() {
        return downloadNum;
    }

    public void setDownloadNum(Integer downloadNum) {
        this.downloadNum = downloadNum;
    }

    @Basic
    @Column(name = "index_pic")
    public String getIndexPic() {
        return indexPic;
    }

    public void setIndexPic(String indexPic) {
        this.indexPic = indexPic;
    }

    @Basic
    @Column(name = "product_id_list")
    public String getProductIdList() {
        return productIdList;
    }

    public void setProductIdList(String productIdList) {
        this.productIdList = productIdList;
    }

    @Basic
    @Column(name = "company_intro")
    public String getCompanyIntro() {
        return companyIntro;
    }

    public void setCompanyIntro(String companyIntro) {
        this.companyIntro = companyIntro;
    }

    @Basic
    @Column(name = "contact_email")
    public String getContactEmail() {
        return contactEmail;
    }

    public void setContactEmail(String contactEmail) {
        this.contactEmail = contactEmail;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        IndexTemplate that = (IndexTemplate) o;
        return id == that.id &&
                Objects.equals(registerNum, that.registerNum) &&
                Objects.equals(learningNum, that.learningNum) &&
                Objects.equals(likeNum, that.likeNum) &&
                Objects.equals(downloadNum, that.downloadNum) &&
                Objects.equals(indexPic, that.indexPic) &&
                Objects.equals(productIdList, that.productIdList) &&
                Objects.equals(companyIntro, that.companyIntro) &&
                Objects.equals(contactEmail, that.contactEmail);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, registerNum, learningNum, likeNum, downloadNum, indexPic, productIdList, companyIntro, contactEmail);
    }
}
