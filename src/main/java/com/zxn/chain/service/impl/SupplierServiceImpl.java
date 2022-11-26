package com.zxn.chain.service.impl;

import com.zxn.chain.common.CustomException;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.StoreDao;
import com.zxn.chain.dao.SupplierDao;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.dto.StoreDto;
import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.entity.Shop;
import com.zxn.chain.entity.Store;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.SnowService;
import com.zxn.chain.service.SupplierService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class SupplierServiceImpl implements SupplierService {
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    ShopDao shopDao;
    @Autowired
    StoreDao storeDao;
    SnowService snowService = new SnowService(1, 1);
    @Override
    public void saveSupplier(SupplierDto supplierDto) {
        supplierDto.setId(snowService.getId());
        Long shopSupplierId = supplierDao.selectSupplierByName(supplierDto.getSupplierName());
        if (shopSupplierId != null){
            throw new CustomException("该供货商已存在");
        }
        supplierDao.saveSupplier(supplierDto);
    }

    @Override
    public void removeSupplier(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            //获取商品供应商id,若存在则无法删除
            List<ShopDto> shop = shopDao.selectShopBySupplierId(id);
            System.out.println(shop);
            if (!(shop.isEmpty())){
                throw new CustomException("该供应商使用中，无法删除");
            }
        }
        for (int i = 0; i <ids.length; i++) {
            //获取门店供应商,若存在则无法删除
            Long id = ids[i];
            //获取门店供应商名
            SupplierDto supplierDtos = supplierDao.selectSupplierById(id);
            String defaultShipper = supplierDtos.getSupplierName();
            //查询门店供应商是否存在，存在则无法删除
            List<StoreDto> storeDtos = storeDao.selectByDefaultShipper(defaultShipper);
            if (!(storeDtos.isEmpty())){
                throw new CustomException("该供应商使用中，无法删除");
            }
        }
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            supplierDao.removeSupplier(id);
        }
    }

    @Override
    public BasePageResponse<SupplierDto> querySupplierPage(int pageNo, int pageSize, String supplierAddress, String shopCategory) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<SupplierDto> queryList = supplierDao.querySupplierPage(pageNo1,pageSize,supplierAddress,shopCategory);
        ArrayList<SupplierDto> arrayList = new ArrayList<>(queryList);
        int totalCount = supplierDao.querySupplierCount(pageNo1,pageSize,supplierAddress,shopCategory);
        BasePageResponse<SupplierDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateSupplier(SupplierDto supplierDto) {
        supplierDao.updateSupplier(supplierDto);
    }

    @Override
    public SupplierDto selectSupplierById(Long id) {
        return supplierDao.selectSupplierById(id);
    }
}
