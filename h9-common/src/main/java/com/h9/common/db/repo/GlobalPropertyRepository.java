package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.GlobalProperty;
import com.h9.common.db.entity.LotteryFlow;
import com.h9.common.db.entity.User;
import com.h9.common.modle.dto.LotteryFlowActivityDTO;
import com.h9.common.modle.vo.GlobalPropertyVO;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: George
 * @date: 2017/11/3 10:57
 */
public interface GlobalPropertyRepository extends BaseRepository<GlobalProperty> {

    GlobalProperty findByCode(String code);

    GlobalProperty findByIdNotAndCode(long id,String code);

    @Query("select new com.h9.common.modle.vo.GlobalPropertyVO(o) from GlobalProperty o  order by o.id desc ")
    Page<GlobalPropertyVO> findAllByPage(Pageable page);

    @SuppressWarnings("Convert2Lambda")
    default Specification<GlobalProperty> buildActivitySpecification(String key){
        return  new Specification<GlobalProperty>() {
            @Override
            public Predicate toPredicate(Root<GlobalProperty> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
                List<Predicate> predicates = new ArrayList<>();
                if (!StringUtils.isEmpty(key)) {
                    Predicate namePredicate = cb.like(root.get("name").as(String.class),"%"+key+"%");
                    Predicate codePredicate = cb.like(root.get("code").as(String.class),"%"+key+"%");
                    predicates.add(cb.or(namePredicate,codePredicate));
                }
                Predicate[] pre = new Predicate[predicates.size()];
                return query.where(predicates.toArray(pre)).getRestriction();
            }
        };

    }

}
