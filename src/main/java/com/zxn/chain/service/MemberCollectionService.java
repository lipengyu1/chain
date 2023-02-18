package com.zxn.chain.service;

import com.zxn.chain.dto.MemberCollectionDto;
import com.zxn.chain.entity.MemberCollection;
import com.zxn.chain.model.BasePageResponse;

public interface MemberCollectionService {
    void addCollection(MemberCollection memberCollection);

    void delCollection(Long[] ids);

    BasePageResponse<MemberCollectionDto> queryCollectionPage(int pageNo, int pageSize,Long memberNum);
}
