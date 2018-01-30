package com.h9.common.db.entity.user;

import com.h9.common.base.BaseEntity;
import com.h9.common.utils.DateUtil;

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


    @Column(name = "day_key",columnDefinition = "varchar(50) comment '日期（如2017-01-01）'")
    private String dayKey = DateUtil.formatDate(new Date(), DateUtil.FormatType.MONTH);

    @Column(name = "people_numbers",columnDefinition = "bigint comment'人数'")
    private Long peopleNumbers = 0L;

    public UserCount( String dayKey, Long peopleNumbers) {
        this.dayKey = dayKey;
        this.peopleNumbers = peopleNumbers;
    }


    public UserCount( ) {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
