package com.zxn.chain.service.impl;

import com.zxn.chain.dao.SupplierDao;
import com.zxn.chain.dto.SupplierDto;
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
    SnowService snowService = new SnowService(1, 1);
    @Override
    public void saveSupplier(SupplierDto supplierDto) {
        supplierDto.setId(snowService.getId());
        supplierDao.saveSupplier(supplierDto);
    }

    @Override
    public void removeSupplier(Long[] ids) {
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
