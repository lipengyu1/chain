package com.zxn.chain.dao;

import com.zxn.chain.dto.SupplierDto;

import java.util.List;

public interface SupplierDao {
    void saveSupplier(SupplierDto supplierDto);

    void removeSupplier(Long id);

    List<SupplierDto> querySupplierPage(int pageNo, int pageSize, String supplierAddress, String shopCategory);

    int querySupplierCount(int pageNo, int pageSize, String supplierAddress, String shopCategory);

    void updateSupplier(SupplierDto supplierDto);

    SupplierDto selectSupplierById(Long id);
}
