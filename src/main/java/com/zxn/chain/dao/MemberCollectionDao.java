package com.zxn.chain.dao;

import com.zxn.chain.entity.MemberCollection;

import java.util.List;

public interface MemberCollectionDao {
    void addCollection(MemberCollection memberCollection);

    void delCollection(Long id);

    List<MemberCollection> queryCollectionPage(int pageNo, int pageSize, Long memberNum);

    int queryCollectionPageCount(int pageNo, int pageSize, Long memberNum);
}
