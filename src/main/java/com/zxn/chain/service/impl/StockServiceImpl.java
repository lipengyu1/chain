package com.zxn.chain.service.impl;

import com.zxn.chain.common.CustomException;
import com.zxn.chain.dao.ShopDao;
import com.zxn.chain.dao.StockDao;
import com.zxn.chain.dao.SupplierDao;
import com.zxn.chain.dto.StockDto;
import com.zxn.chain.entity.Shop;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.SnowService;
import com.zxn.chain.service.StockService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StockServiceImpl implements StockService {

    @Autowired
    StockDao stockDao;
    @Autowired
    SupplierDao supplierDao;
    @Autowired
    ShopDao shopDao;
    SnowService snowService = new SnowService(1, 1);

    @Override
    public void saveStock(StockDto stockDto) {
        stockDto.setId(snowService.getId());
        //商品编号限制
        Shop shop = shopDao.selectShopNameByShopId(stockDto.getShopId());
        System.out.println(shop);
        if (shop==null){
            throw new CustomException("该商品不存在，请重新输入");
        }
        //供应商限制
        Long shopSupplierId = supplierDao.selectSupplierByName(stockDto.getShopSupplier());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        //产地限制
        if (!(shop.getShopBase().equals(stockDto.getShopBase()))){
            throw new CustomException("产地错误，请重新输入");
        }
        //进货数量限制
        if (!(shop.getQuantity().equals(stockDto.getStartNum()))){
            throw new CustomException("进货数量错误，请重新输入");
        }
        stockDao.saveStock(stockDto);
    }

    @Override
    public void removeStock(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            stockDao.removeStock(id);
        }
    }

    @Override
    public BasePageResponse<StockDto> queryShopPage(int pageNo, int pageSize, String shopSupplier, String shopCategory) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<StockDto> queryList = stockDao.queryStockPage(pageNo1,pageSize,shopSupplier,shopCategory);
        ArrayList<StockDto> arrayList = new ArrayList<>(queryList);
        int totalCount = stockDao.queryStockCount(pageNo1,pageSize,shopSupplier,shopCategory);
        BasePageResponse<StockDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateStock(StockDto stockDto) {
        Shop shop = shopDao.selectShopNameByShopId(stockDto.getShopId());
        System.out.println(shop);
        if (shop==null){
            throw new CustomException("该商品不存在，请重新输入");
        }
        Long shopSupplierId = supplierDao.selectSupplierByName(stockDto.getShopSupplier());
        if (shopSupplierId == null){
            throw new CustomException("该供货商不存在，请重新输入");
        }
        if (!(shop.getShopBase().equals(stockDto.getShopBase()))){
            throw new CustomException("产地错误，请重新输入");
        }
        if (!(shop.getQuantity().equals(stockDto.getStartNum()))){
            throw new CustomException("进货数量错误，请重新输入");
        }
        stockDao.updateStock(stockDto);
    }

    @Override
    public StockDto selectStockById(Long id) {
        return stockDao.selectStockById(id);
    }
}
