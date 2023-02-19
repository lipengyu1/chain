package com.zxn.chain.service.impl;

import com.zxn.chain.dao.AddressDao;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.entity.Address;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.AddressService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {
    SnowService snowService = new SnowService(1, 1);
    @Autowired
    AddressDao addressDao;
    @Override
    public void saveAddress(Address address) {
        address.setId(snowService.getId());
        addressDao.saveAddress(address);
    }

    @Override
    public void removeAddress(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            addressDao.removeAddress(id);
        }
    }

    @Override
    public BasePageResponse<Address> queryAddressPage(int pageNo, int pageSize, Long memberNum) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<Address> queryList = addressDao.queryAddressPage(pageNo1,pageSize,memberNum);
        ArrayList<Address> arrayList = new ArrayList<>(queryList);
        int totalCount = addressDao.queryAddressCount(pageNo1,pageSize,memberNum);
        BasePageResponse<Address> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateAddress(Address address) {
        addressDao.updateAddress(address);
    }

    @Override
    public Address selectAddressById(Long id) {
        return addressDao.selectAddressById(id);
    }
}
