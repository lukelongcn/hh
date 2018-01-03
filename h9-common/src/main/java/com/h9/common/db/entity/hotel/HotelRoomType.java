package com.h9.common.db.entity.hotel;

import com.h9.common.base.BaseEntity;
import com.h9.common.db.entity.hotel.Hotel;
import lombok.Data;
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

    @Column(name = "type_name",columnDefinition = "varchar(255) comment '类型名'")
    private String typeName;

    @Column(name = "original_price",columnDefinition = "decimal(10,2) comment '原价'")
    private BigDecimal originalPrice;

    @Column(name = "real_price",columnDefinition = "decimal(10,2) comment '售价'")
    private BigDecimal realPrice;

    @ManyToOne
    @JoinColumn(name = "hotel_id", referencedColumnName = "id", columnDefinition = "bigint(40)  COMMENT '酒店Id'",foreignKey = @ForeignKey(value = ConstraintMode.NO_CONSTRAINT))
    private Hotel hotel;

    @Column(name = "include", columnDefinition = "varchar(255) COMMENT '包含,如间 单早|大床'")
    private String include;

    @Column(name = "status",columnDefinition = "int COMMENT '状态'")
    private Integer status;

}
