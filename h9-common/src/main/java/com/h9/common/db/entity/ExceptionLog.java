package com.h9.common.db.entity;

import com.h9.common.base.BaseEntity;
import lombok.Data;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

/**
 * Created with IntelliJ IDEA.
 * ExceptionLog:刘敏华 shadow.liu@hey900.com
 * Date: 2018/1/16
 * Time: 11:30
 */
@Data
@Entity
@Table(name = "exception_log")
public class ExceptionLog extends BaseEntity {


    @Id
    @SequenceGenerator(name = "h9-parentSeq", sequenceName = "h9-parent_SEQ", allocationSize = 1, initialValue = 1)
    @GeneratedValue(strategy = IDENTITY, generator = "h9-parentSeq")
    private Long id;
    
    @Column(name = "message", nullable = false, columnDefinition = "varchar(255) default '' COMMENT ''")
    private String message;

    @Column(name = "level", nullable = false, columnDefinition = "varchar(16) default '' COMMENT '日志级别'")
    private String level;

    @Column(name = "tag", nullable = false, columnDefinition = "varchar(64) default '' COMMENT '标签'")
    private String tag;


    public enum Level{
        Debug(0,"debug"),
        warn(0,"warn"),
        error(1,"error");
  
        Level(int id,String name){
            this.id = id;
            this.name = name;
        }

        private int id;
        private String name;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
