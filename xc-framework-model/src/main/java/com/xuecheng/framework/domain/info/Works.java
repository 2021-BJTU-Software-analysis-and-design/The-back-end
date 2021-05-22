package com.xuecheng.framework.domain.info;

import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import java.util.Objects;

@Entity
public class Works {
    private int id;
    private String name;
    private String worksIntro;
    private String worksPic;
    private String detailUrl;
    private Integer courseId;
    private Integer orgId;

    @Id
    @Column(name = "id")
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    @Basic
    @Column(name = "name")
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Basic
    @Column(name = "works_intro")
    public String getWorksIntro() {
        return worksIntro;
    }

    public void setWorksIntro(String worksIntro) {
        this.worksIntro = worksIntro;
    }

    @Basic
    @Column(name = "works_pic")
    public String getWorksPic() {
        return worksPic;
    }

    public void setWorksPic(String worksPic) {
        this.worksPic = worksPic;
    }

    @Basic
    @Column(name = "detail_url")
    public String getDetailUrl() {
        return detailUrl;
    }

    public void setDetailUrl(String detailUrl) {
        this.detailUrl = detailUrl;
    }

    @Basic
    @Column(name = "course_id")
    public Integer getCourseId() {
        return courseId;
    }

    public void setCourseId(Integer courseId) {
        this.courseId = courseId;
    }

    @Basic
    @Column(name = "org_id")
    public Integer getOrgId() {
        return orgId;
    }

    public void setOrgId(Integer orgId) {
        this.orgId = orgId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Works works = (Works) o;
        return id == works.id &&
                Objects.equals(name, works.name) &&
                Objects.equals(worksIntro, works.worksIntro) &&
                Objects.equals(worksPic, works.worksPic) &&
                Objects.equals(detailUrl, works.detailUrl) &&
                Objects.equals(courseId, works.courseId) &&
                Objects.equals(orgId, works.orgId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, worksIntro, worksPic, detailUrl, courseId, orgId);
    }
}
