package com.zxn.chain.service.impl;

import com.zxn.chain.common.CustomException;
import com.zxn.chain.dao.StoreDao;
import com.zxn.chain.dao.StoreManagerDao;
import com.zxn.chain.dao.SupplierDao;
import com.zxn.chain.dto.StoreDto;
import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.entity.Store;
import com.zxn.chain.entity.StoreManager;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.SnowService;
import com.zxn.chain.service.StoreService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class StoreServiceImpl implements StoreService {
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    StoreDao storeDao;
    @Autowired
    StoreManagerDao storeManagerDao;
    SnowService snowService = new SnowService(1, 1);

    @Override
    public void saveStore(StoreDto storeDto) {
        storeDto.setId(snowService.getId());
        storeDto.setCreateTime(LocalDateTime.now());
        StoreManager managerName = null;
        try {
            managerName = storeManagerDao.selectStoreManagerByNumber(storeDto.getStoreLeaderId());
            storeDto.setStoreLeaderName(managerName.getStoreEmployeeName());
        } catch (Exception e) {
            throw new CustomException("该负责人不存在");
        }
        Long shopSupplierId = supplierDao.selectSupplierByName(storeDto.getDefaultShipper());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        Store store = storeDao.selectStoreByNum(storeDto.getStoreNum());
        if (store != null){
            throw new CustomException("该门店编号已存在，请重新输入");
        }
        storeDao.saveStore(storeDto);
    }

    @Override
    public void removeStore(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            storeDao.removeStore(id);
        }
    }

    @Override
    public BasePageResponse<StoreDto> queryStorePage(int pageNo, int pageSize, String storeType, String storeArea, String storeLeaderName) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<StoreDto> queryList = storeDao.queryStorePage(pageNo1,pageSize,storeType,storeArea,storeLeaderName);
        ArrayList<StoreDto> arrayList = new ArrayList<>(queryList);
        int totalCount = storeDao.querySupplierCount(pageNo1,pageSize,storeType,storeArea,storeLeaderName);
        BasePageResponse<StoreDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateStore(StoreDto storeDto) {
        StoreManager managerName = null;
        try {
            managerName = storeManagerDao.selectStoreManagerByNumber(storeDto.getStoreLeaderId());
            storeDto.setStoreLeaderName(managerName.getStoreEmployeeName());
        } catch (Exception e) {
            throw new CustomException("该负责人不存在");
        }
        Long shopSupplierId = supplierDao.selectSupplierByName(storeDto.getDefaultShipper());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        storeDao.updateStore(storeDto);
    }

    @Override
    public StoreDto selectStoreById(Long id) {
        return storeDao.selectStoreById(id);
    }
}
