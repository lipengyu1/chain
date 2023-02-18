package com.zxn.chain.service;


import com.zxn.chain.dto.ShopUserHistoryDto;

import java.time.LocalDateTime;
import java.util.ArrayList;

public interface HistoryService {
    void saveHistory(Long memberNum, LocalDateTime date, Long id);

    void delHistory(Long memberNum, Long shopId);

    ArrayList<ShopUserHistoryDto> queryHistory(Long memberNum);
}
