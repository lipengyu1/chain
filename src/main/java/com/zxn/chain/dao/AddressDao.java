package com.zxn.chain.dao;

import com.zxn.chain.entity.Address;

import java.util.List;

public interface AddressDao {
    void saveAddress(Address address);

    void removeAddress(Long id);

    List<Address> queryAddressPage(int pageNo, int pageSize, Long memberNum);

    int queryAddressCount(int pageNo, int pageSize, Long memberNum);

    void updateAddress(Address address);

    Address selectAddressById(Long id);
}
