package com.zxn.chain.service.impl;

import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.ShopLikeDao;
import com.zxn.chain.dto.ShopDto;
import com.zxn.chain.dto.ShopUserHistoryDto;
import com.zxn.chain.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Service
public class HistoryServiceImpl implements HistoryService {
    @Autowired
    private RedisServiceImpl redisService;
    @Autowired
    ShopLikeDao shopLikeDao;
    @Autowired
    ShopDao shopDao;
    @Override
    public void saveHistory(Long memberNum, LocalDateTime date, Long id) {
        redisService.saveHistory(memberNum,date,id);
    }

    @Override
    public void delHistory(Long memberNum, Long id) {
        redisService.delHistory(memberNum,id);
    }

    @Override
    public ArrayList<ShopUserHistoryDto> queryHistory(Long memberNum) {
        //获取用户浏览记录
        Map map = redisService.queryHistory(memberNum);
        //取出key
        Set keyset = map.keySet();
        ShopDto shopDto;
        ArrayList<ShopUserHistoryDto> arrayList = new ArrayList<>();
        for (Object o :keyset) {
            ShopUserHistoryDto shopUserHistoryDto = new ShopUserHistoryDto();
            Long id1 = (Long) o;
            shopDto = shopDao.selectShopById(id1);
            Integer num = shopLikeDao.selectShopCountLike(id1);
            shopUserHistoryDto.setLikeCount(num);
            shopUserHistoryDto.setId(shopDto.getId());
            shopUserHistoryDto.setShopName(shopDto.getShopName());
            shopUserHistoryDto.setShopNum(shopDto.getShopNum());
            shopUserHistoryDto.setPicture(shopDto.getPicture());
            shopUserHistoryDto.setShopSupplier(shopDto.getShopSupplier());
            shopUserHistoryDto.setShopSupplierId(shopDto.getShopSupplierId());
            shopUserHistoryDto.setShopBase(shopDto.getShopBase());
            shopUserHistoryDto.setSellPrice(shopDto.getSellPrice());
            shopUserHistoryDto.setQuantity(shopDto.getQuantity());
            shopUserHistoryDto.setCategory(shopDto.getCategory());
            //根据map中的key获取value阅读时间
            String time = (String) map.get(o);
            shopUserHistoryDto.setReadTime(time);
            arrayList.add(shopUserHistoryDto);
        }
        return arrayList;
    }
}
