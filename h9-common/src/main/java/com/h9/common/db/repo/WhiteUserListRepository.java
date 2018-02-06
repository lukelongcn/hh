package com.h9.common.db.repo;

import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.config.WhiteUserList;
import com.h9.common.modle.vo.admin.basis.WhiteListVO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import java.util.Date;
import java.util.List;

/**
 * Created by itservice on 2017/11/29.
 */
public interface WhiteUserListRepository extends BaseRepository<WhiteUserList> {

    @Query(value = "select o from WhiteUserList o where o.startTime < ?2 and o.endTime > ?2 and o.status = 1 and o.userId = ?1")
    List<WhiteUserList> findByUserId(Long userId, Date date);

    @Query("select new com.h9.common.modle.vo.admin.basis.WhiteListVO(o,u) from WhiteUserList o,User u" +
            " where o.userId = u.id order by o.status asc ,o.id desc ")
    Page<WhiteListVO> findAllByPage(Pageable pageable);
}
