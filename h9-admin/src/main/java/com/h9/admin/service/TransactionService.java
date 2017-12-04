package com.h9.admin.service;

import com.h9.admin.model.dto.community.GoodsTypeAddDTO;
import com.h9.admin.model.dto.community.GoodsTypeEditDTO;
import com.h9.common.db.entity.CardCoupons;
import com.h9.common.db.repo.CardCouponsRepository;
import com.h9.common.db.repo.GoodsReposiroty;
import com.h9.common.modle.dto.transaction.CardCouponsDTO;
import com.h9.common.modle.vo.admin.transaction.CardCouponsVO;
import com.h9.common.modle.vo.admin.transaction.GoodsTypeVO;
import com.h9.common.base.PageResult;
import com.h9.common.base.Result;
import com.h9.common.db.entity.GoodsType;
import com.h9.common.db.repo.GoodsTypeReposiroty;
import com.h9.common.modle.dto.PageDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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
    @Autowired
    private CardCouponsRepository cardCouponsRepository;
    @Autowired
    private GoodsReposiroty goodsReposiroty;

    public Result<GoodsType> addGoodsType(GoodsTypeAddDTO goodsTypeAddDTO) {
        if (this.goodsTypeReposiroty.findByCode(goodsTypeAddDTO.getCode()) != null) {
            return Result.fail("标识已存在");
        }
        return Result.success(this.goodsTypeReposiroty.save(goodsTypeAddDTO.toGoodsType()));
    }

    public Result<GoodsType> updateGoodsType(GoodsTypeEditDTO goodsTypeEditDTO) {
        GoodsType goodsType = this.goodsTypeReposiroty.findOne(goodsTypeEditDTO.getId());
        if (goodsType == null) {
            return Result.fail("商品类型不存在");
        }
        if (this.goodsTypeReposiroty.findByCodeAndIdNot(goodsTypeEditDTO.getCode(),goodsTypeEditDTO.getId()) != null) {
            return Result.fail("标识已存在");
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

   /* public Result addCardCouponsList(CardCouponsListAddDTO cardCouponsListAddDTO) {
        if (this.goodsReposiroty.findOne(cardCouponsListAddDTO.getGoodsId()) == null) {
            return Result.fail("商品不存在");
        }
        List<String> noList = Arrays.asList(cardCouponsListAddDTO.getNos().split("\n"));
        List<String> validNoList = new ArrayList<>();
        noList.forEach(item -> {
            if (StringUtils.isNotBlank(item)) {
                validNoList.add(item.replace("\\s*",""));
            }
        });
        String lastNo =
        return Result.success(this.goodsTypeReposiroty.save(goodsTypeAddDTO.toGoodsType()));
    }*/

    public Result<List<String>> listCardCouponsBatchNo(long goodsId) {
        List<String> batchNoList = this.cardCouponsRepository.findAllBatchNoByGoodsId(goodsId);
        return  Result.success(batchNoList);
    }

    public Result<PageResult<CardCouponsVO>> listCardCoupons(CardCouponsDTO cardCouponsDTO) {
        Sort sort = new Sort(Sort.Direction.DESC,"id");
        Page<CardCoupons> cardCouponsPage = this.cardCouponsRepository.findAll(this.cardCouponsRepository
                .buildSpecification(cardCouponsDTO),cardCouponsDTO.toPageRequest(sort));
        return  Result.success(new PageResult<>(CardCouponsVO.toCardCouponsVO(cardCouponsPage)));
    }

}
