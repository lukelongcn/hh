package com.h9.admin.service;

import com.h9.admin.model.dto.community.GoodsTypeAddDTO;
import com.h9.admin.model.dto.community.GoodsTypeEditDTO;
import com.h9.common.modle.vo.admin.transaction.GoodsTypeVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * @author: George
 * @date: 2017/11/29 16:42
 */
@Service
@Transactional
public class TransactionService {

    @Autowired
    private GoodsTypeReposiroty goodsTypeReposiroty;

    public Result<GoodsType> addGoodsType(GoodsTypeAddDTO goodsTypeAddDTO) {
        return Result.success(this.goodsTypeReposiroty.save(goodsTypeAddDTO.toGoodsType()));
    }

    public Result<GoodsType> updateGoodsType(GoodsTypeEditDTO goodsTypeEditDTO) {
        GoodsType goodsType = this.goodsTypeReposiroty.findOne(goodsTypeEditDTO.getId());
        if (goodsType == null) {
            return Result.fail("商品类型不存在");
        }
        BeanUtils.copyProperties(goodsTypeEditDTO,goodsType);
        return Result.success(this.goodsTypeReposiroty.save(goodsType));
    }

    public Result<PageResult<GoodsTypeVO>> listGoodsType(PageDTO pageDTO) {
        Page<GoodsTypeVO> goodsTypeVOPage = this.goodsTypeReposiroty.findAllByPage(pageDTO.toPageRequest());
        return  Result.success(new PageResult<>(goodsTypeVOPage));
    }

    public Result<List<GoodsType>> listEnableGoodsType() {
        List<GoodsType> goodsTypeList = this.goodsTypeReposiroty.findByStatus(GoodsType.StatusEnum.ENABLED.getId());
        return  Result.success(goodsTypeList);
    }
}
