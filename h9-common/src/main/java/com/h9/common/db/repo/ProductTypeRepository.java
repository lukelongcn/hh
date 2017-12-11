package com.h9.common.db.repo;


import com.h9.common.base.BaseRepository;
import com.h9.common.db.entity.ProductType;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

/**
 * @ClassName: ProductTypeRepository
 * @Description: ProductType 的查询
 * @author: shadow.liu
 * @date: 2016年6月27日 下午3:18:36
 */
@Repository
public interface ProductTypeRepository extends BaseRepository<ProductType> {

    @Query("select pt from ProductType pt where pt.name = ?1 ")
    ProductType findByName(String name);

    default ProductType findOrNew(String name){
        ProductType productType = findByName(name);
        if(productType!=null){
            return productType;
        }
        productType = new ProductType();
        productType.setName(name);
        productType = saveAndFlush(productType);
        return productType;
    }

}
