package com.zxn.chain.service;

import com.zxn.chain.entity.Address;
import com.zxn.chain.model.BasePageResponse;

public interface AddressService {
    void saveAddress(Address address);

    void removeAddress(Long[] ids);

    BasePageResponse<Address> queryAddressPage(int pageNo, int pageSize, Long memberNum);

    void updateAddress(Address address);

    Address selectAddressById(Long id);
}
