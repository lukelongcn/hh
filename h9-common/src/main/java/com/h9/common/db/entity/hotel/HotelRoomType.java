package com.h9.common.db.entity.hotel;

import com.h9.common.base.BaseEntity;
import lombok.Data;
import lombok.Getter;
import lombok.experimental.Accessors;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by itservice on 2018/1/2.
 */
@Entity
@Table(name = "hotel_room_type")
@Accessors(chain = true)
@Data
public class HotelRoomType extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "room_name",columnDefinition ="varchar(255) comment '房间名'" )
    private String roomName;

    @Column(name = "type_name",columnDefinition = "varchar(255) comment '类型名'")
    private String typeName;

    @Column(name = "original_price",columnDefinition = "decimal(10,2) comment '原价'")
    private BigDecimal originalPrice;

    @Column(name = "real_price",columnDefinition = "decimal(10,2) comment '售价'")
    private BigDecimal realPrice;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", columnDefinition = "bigint(40)  COMMENT '酒店Id'",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Hotel hotel;

    @Column(name = "include", columnDefinition = "varchar(255) COMMENT '包含,如间 单早'")
    private String include;

    @Column(name ="bed_size",columnDefinition = "varchar(255) comment '床类型'")
    private String bedSize;
    /**
     * description: 状态
     * @see Status
     */
    @Column(name = "status",columnDefinition = "int COMMENT '状态 1正常 0禁用'")
    private Integer status = 1;

    @Column(name ="image",columnDefinition = "varchar(255) comment '图片'")
    private String image;

    @Getter
    public static enum Status{
        NORMAL(1, "1正常"),
        BAN(0,"禁用");
        public int code;
        public String desc;

        Status(int code, String desc) {
            this.code = code;
            this.desc = desc;
        }

        public static Status findByCode(int code) {
            Status[] values = values();
            for (Status elEnum : values) {
                if (code == elEnum.getCode()) {
                    return elEnum;
                }
            }
            return null;
        }
    }

}
