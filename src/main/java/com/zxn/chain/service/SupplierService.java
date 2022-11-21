package com.zxn.chain.service;

import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.model.BasePageResponse;

public interface SupplierService {
    void saveSupplier(SupplierDto supplierDto);

    void removeSupplier(Long[] ids);

    BasePageResponse<SupplierDto> querySupplierPage(int pageNo, int pageSize, String supplierAddress, String shopCategory);

    void updateSupplier(SupplierDto supplierDto);

    SupplierDto selectSupplierById(Long id);
}
