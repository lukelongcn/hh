package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import javax.persistence.*;
import java.util.Date;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created by itservice on 2018/1/29.
 */

@Entity
@Table(name = "user_count")
public class UserCount extends BaseEntity{
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @Column(name = "date",columnDefinition = "datetime comment '具体时间'")
    private Date date;

    @Column(name = "day_key",columnDefinition = "varchar(50) comment '日期（如2017-01-01）'")
    private String dayKey;

    @Column(name = "people_numbers",columnDefinition = "bigint comment'人数'")
    private Long peopleNumbers;

    public UserCount(Date date, String dayKey, Long peopleNumbers) {
        this.date = date;
        this.dayKey = dayKey;
        this.peopleNumbers = peopleNumbers;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getDayKey() {
        return dayKey;
    }

    public void setDayKey(String dayKey) {
        this.dayKey = dayKey;
    }

    public Long getPeopleNumbers() {
        return peopleNumbers;
    }

    public void setPeopleNumbers(Long peopleNumbers) {
        this.peopleNumbers = peopleNumbers;
    }
}
