package com.zxn.chain.service.impl;

import com.zxn.chain.common.CustomException;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.ShopLikeDao;
import com.zxn.chain.dao.SupplierDao;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.ShopService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class ShopServiceImpl implements ShopService {
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    ShopLikeDao shopLikeDao;
    SnowService snowService = new SnowService(1, 1);

    @Override
    public void saveShop(ShopDto shopDto) {
        Long shopSupplierId = supplierDao.selectSupplierByName(shopDto.getShopSupplier());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        shopDto.setShopSupplierId(shopSupplierId);
        shopDto.setId(snowService.getId());
        shopDao.saveShop(shopDto);
    }

    @Override
    public void removeShop(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            shopDao.removeShop(id);
        }
    }

    @Override
    public BasePageResponse<ShopDto> queryShopPage(int pageNo, int pageSize, String shopName, String category) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<ShopDto> queryList = shopDao.queryShopPage(pageNo1,pageSize,shopName,category);
        for (ShopDto shopDto : queryList) {
            Integer num = shopLikeDao.selectShopCountLike(shopDto.getId());
            shopDto.setLikeCount(num);
        }
        ArrayList<ShopDto> arrayList = new ArrayList<>(queryList);
        int totalCount = shopDao.queryShopCount(pageNo1,pageSize,shopName,category);
        BasePageResponse<ShopDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateShop(ShopDto shopDto) {
        Long shopSupplierId = supplierDao.selectSupplierByName(shopDto.getShopSupplier());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        shopDto.setShopSupplierId(shopSupplierId);
        shopDao.updateShop(shopDto);
    }

    @Override
    public ShopDto selectShopById(Long id) {
        ShopDto shopDto = shopDao.selectShopById(id);
        SupplierDto supplierDto = null;
        try {
            supplierDto = supplierDao.selectSupplierById(shopDto.getShopSupplierId());
        } catch (Exception e) {
            throw new CustomException("未查询到商品");
        }
        shopDto.setShopSupplier(supplierDto.getSupplierName());
        Integer num = shopLikeDao.selectShopCountLike(shopDto.getId());
        shopDto.setLikeCount(num);
        return shopDto;
    }

    @Override
    public BigDecimal querySellPrice(Long shopNum) {
        BigDecimal sellPrice = shopDao.querySellPrice(shopNum);
        return sellPrice;
    }
}
