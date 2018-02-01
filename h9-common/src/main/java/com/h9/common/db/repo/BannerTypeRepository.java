package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.BannerType;
import com.h9.common.modle.dto.PageDTO;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.xml.ws.Service;

/**
 * @author: George
 * @date: 2017/11/1 16:57
 */
public interface BannerTypeRepository extends BaseRepository<BannerType> {

    BannerType findByCode(String code);

    BannerType findByIdNotAndCode(long id,String code);

    //BannerType findBy

    @Query("select o from BannerType o order by o.enable desc , o.id desc ")
    Page<BannerType> findAllByPage(Pageable page);

    @Query("select o from BannerType o where o.location=?1 order by o.enable desc , o.id desc ")
    Page<BannerType> findAllByPage(int location,Pageable page);


    public default Page<BannerType> findAllByPage(Integer location,Pageable page){
        if(location == null){
            return findAllByPage(page);
        }else{
            return findAllByPage(location,page);
        }

    }


}
