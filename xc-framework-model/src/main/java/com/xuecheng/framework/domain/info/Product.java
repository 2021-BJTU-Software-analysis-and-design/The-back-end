package com.xuecheng.framework.domain.info;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.sql.Timestamp;
import java.util.Objects;

@Entity
public class Product {
    private int id;
    private String productName;
    private String productUrl;
    private String productIntro;
    private String productPic;
    private String orgId;
    private String manager;
    private Byte isPublish;
    private Timestamp uploadTime;
    private Integer studentNum;
    private String mainManager;
    private Timestamp addTime;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "product_name")
    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    @Basic
    @Column(name = "product_url")
    public String getProductUrl() {
        return productUrl;
    }

    public void setProductUrl(String productUrl) {
        this.productUrl = productUrl;
    }

    @Basic
    @Column(name = "product_intro")
    public String getProductIntro() {
        return productIntro;
    }

    public void setProductIntro(String productIntro) {
        this.productIntro = productIntro;
    }

    @Basic
    @Column(name = "product_pic")
    public String getProductPic() {
        return productPic;
    }

    public void setProductPic(String productPic) {
        this.productPic = productPic;
    }

    @Basic
    @Column(name = "org_id")
    public String getOrgId() {
        return orgId;
    }

    public void setOrgId(String orgId) {
        this.orgId = orgId;
    }

    @Basic
    @Column(name = "manager")
    public String getManager() {
        return manager;
    }

    public void setManager(String manager) {
        this.manager = manager;
    }

    @Basic
    @Column(name = "is_publish")
    public Byte getIsPublish() {
        return isPublish;
    }

    public void setIsPublish(Byte isPublish) {
        this.isPublish = isPublish;
    }

    @Basic
    @Column(name = "upload_time")
    public Timestamp getUploadTime() {
        return uploadTime;
    }

    public void setUploadTime(Timestamp uploadTime) {
        this.uploadTime = uploadTime;
    }

    @Basic
    @Column(name = "student_num")
    public Integer getStudentNum() {
        return studentNum;
    }

    public void setStudentNum(Integer studentNum) {
        this.studentNum = studentNum;
    }

    @Basic
    @Column(name = "main_manager")
    public String getMainManager() {
        return mainManager;
    }

    public void setMainManager(String mainManager) {
        this.mainManager = mainManager;
    }

    @Basic
    @Column(name = "add_time")
    public Timestamp getAddTime() {
        return addTime;
    }

    public void setAddTime(Timestamp addTime) {
        this.addTime = addTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Product product = (Product) o;
        return id == product.id &&
                Objects.equals(productName, product.productName) &&
                Objects.equals(productUrl, product.productUrl) &&
                Objects.equals(productIntro, product.productIntro) &&
                Objects.equals(productPic, product.productPic) &&
                Objects.equals(orgId, product.orgId) &&
                Objects.equals(manager, product.manager) &&
                Objects.equals(isPublish, product.isPublish) &&
                Objects.equals(uploadTime, product.uploadTime) &&
                Objects.equals(studentNum, product.studentNum) &&
                Objects.equals(mainManager, product.mainManager) &&
                Objects.equals(addTime, product.addTime);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, productName, productUrl, productIntro, productPic, orgId, manager, isPublish, uploadTime, studentNum, mainManager, addTime);
    }
}
