package book.flow.enity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;
import java.util.Set;

/**
 * 举报表.
 * User: huang
 * Date: 17-8-18
 */
@Entity
@Table(name = "report")
public class Report implements Serializable {

    @Id
    @GeneratedValue
    private Integer reportId;
    /** 举报人. */
    @ManyToOne
    @JoinColumn(name = "report")
    private User report;
    /** 被举报人. */
    @ManyToOne
    @JoinColumn(name = "beReport")
    private User beReport;
    /** 时间. */
    @Temporal(TemporalType.TIMESTAMP)
    private Date reportDate;
    /** 内容说明. */
    private String reportText;
    /** 相关图片. */
    @OneToMany
    @JoinColumn(name = "imgs")
    private Set<ReportImg> img;
    /** 状态. 0 为未处理， 1 为举报成功， 2 为举报失败*/
    private int status = 0;

    public Integer getReportId() {
        return reportId;
    }

    public void setReportId(Integer reportId) {
        this.reportId = reportId;
    }

    public User getReport() {
        return report;
    }

    public void setReport(User report) {
        this.report = report;
    }

    public User getBeReport() {
        return beReport;
    }

    public void setBeReport(User beReport) {
        this.beReport = beReport;
    }

    public Date getReportDate() {
        return reportDate;
    }

    public void setReportDate(Date reportDate) {
        this.reportDate = reportDate;
    }

    public String getReportText() {
        return reportText;
    }

    public void setReportText(String reportText) {
        this.reportText = reportText;
    }

    public Set<ReportImg> getImg() {
        return img;
    }

    public void setImg(Set<ReportImg> img) {
        this.img = img;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
