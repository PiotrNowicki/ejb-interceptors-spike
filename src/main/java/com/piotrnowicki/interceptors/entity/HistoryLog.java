package com.piotrnowicki.interceptors.entity;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.PrePersist;

import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

@Entity
@NamedQuery(name = HistoryLog.FIND_ALL, query = "SELECT h FROM HistoryLog h")
public class HistoryLog {

    public final static String FIND_ALL = "HistoryLog.findAll";

    @Id
    @GeneratedValue
    private Long id;

    private Date date;

    private String additionalData;

    // -------------------------------------------------------------------------------||
    // Constructors ------------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    /**
     * For JPA purposes only.
     */
    HistoryLog() {
    }

    public HistoryLog(String additionalData) {
        this.additionalData = additionalData;
    }

    // -------------------------------------------------------------------------------||
    // Lifecycle methods -------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @PrePersist
    private void prePersist() {
        date = new Date();
    }

    // -------------------------------------------------------------------------------||
    // Getters and setters -----------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    public Long getId() {
        return id;
    }

    public Date getDate() {
        return date;
    }

    public String getAdditionalData() {
        return additionalData;
    }

    public void setAdditionalData(String additionalData) {
        this.additionalData = additionalData;
    }

    // -------------------------------------------------------------------------------||
    // Contract ----------------------------------------------------------------------||
    // -------------------------------------------------------------------------------||

    @Override
    public String toString() {
        ToStringBuilder builder = new ToStringBuilder(this, ToStringStyle.SIMPLE_STYLE);

        builder.append("id", id);
        builder.append("date", date);
        builder.append("additionalData", additionalData);

        return builder.toString();
    }

}
