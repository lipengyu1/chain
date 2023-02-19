package com.zxn.chain.service.impl;

import com.zxn.chain.dao.MemberCollectionDao;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.ShopLikeDao;
import com.zxn.chain.dto.MemberCollectionDto;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.entity.MemberCollection;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.MemberCollectionService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
public class MemberCollectionServiceImpl implements MemberCollectionService {
    @Autowired
    ShopDao shopDao;
    @Autowired
    ShopLikeDao shopLikeDao;
    @Autowired
    MemberCollectionDao memberCollectionDao;
    SnowService snowService = new SnowService(1, 1);
    @Override
    public void addCollection(MemberCollection memberCollection) {
        memberCollection.setCreateTime(LocalDateTime.now());
        memberCollection.setId(snowService.getId());
        memberCollectionDao.addCollection(memberCollection);
    }

    @Override
    public void delCollection(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            memberCollectionDao.delCollection(id);
        }
    }

    @Override
    public BasePageResponse<MemberCollectionDto> queryCollectionPage(int pageNo, int pageSize,Long memberNum) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<MemberCollection> queryList = memberCollectionDao.queryCollectionPage(pageNo1,pageSize,memberNum);
        List<MemberCollectionDto> queryListDetail = new ArrayList<>();
        for (MemberCollection memberCollection : queryList) {
            Long shopNum = memberCollection.getShopNum();
            ShopDto shopDto = shopDao.selectShopByNum(shopNum);
            Integer num = shopLikeDao.selectShopCountLike(shopDto.getId());
            shopDto.setLikeCount(num);
            MemberCollectionDto memberCollectionDto = new MemberCollectionDto();
            memberCollectionDto.setId(memberCollection.getId());
            memberCollectionDto.setCreateTime(memberCollection.getCreateTime());
            memberCollectionDto.setShopNum(memberCollection.getShopNum());
            memberCollectionDto.setCategory(shopDto.getCategory());
            memberCollectionDto.setSellPrice(shopDto.getSellPrice());
            memberCollectionDto.setShopName(shopDto.getShopName());
            memberCollectionDto.setLikeCount(shopDto.getLikeCount());
            memberCollectionDto.setPicture(shopDto.getPicture());
            queryListDetail.add(memberCollectionDto);
        }
        ArrayList<MemberCollectionDto> arrayList = new ArrayList<>(queryListDetail);
        int totalCount = memberCollectionDao.queryCollectionPageCount(pageNo1,pageSize,memberNum);
        BasePageResponse<MemberCollectionDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }
}
