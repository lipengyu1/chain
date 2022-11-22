package com.zxn.chain.service.impl;

import com.zxn.chain.dao.MemberDao;
import com.zxn.chain.dto.MemberDto;
import com.zxn.chain.dto.SupplierDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.service.MemberService;
import com.zxn.chain.service.SnowService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MemberServiceImpl implements MemberService {
    @Autowired
    MemberDao memberDao;

    SnowService snowService = new SnowService(1, 1);

    @Override
    public void saveMember(MemberDto memberDto) {
        memberDto.setId(snowService.getId());
        memberDao.saveMember(memberDto);
    }

    @Override
    public void removeMember(Long[] ids) {
        for (int i = 0; i <ids.length; i++) {
            Long id = ids[i];
            memberDao.removeMember(id);
        }
    }

    @Override
    public BasePageResponse<MemberDto> queryMemberPage(int pageNo, int pageSize, String memberName, String memberNum, String memberStatus) {
        int pageNo1 = pageSize * (pageNo - 1);
        List<MemberDto> queryList = memberDao.queryMemberPage(pageNo1,pageSize,memberName,memberNum,memberStatus);
        ArrayList<MemberDto> arrayList = new ArrayList<>(queryList);
        int totalCount = memberDao.queryMemberCount(pageNo1,pageSize,memberName,memberNum,memberStatus);
        BasePageResponse<MemberDto> basePageResponse = new BasePageResponse<>();
        basePageResponse.setPageNo(pageNo);
        basePageResponse.setPageSize(pageSize);
        basePageResponse.setTotalPage((int)Math.ceil((float)totalCount/pageSize));
        basePageResponse.setResultList(arrayList);
        basePageResponse.setTotalCount(totalCount);
        return basePageResponse;
    }

    @Override
    public void updateMember(MemberDto memberDto) {
        memberDao.updateMember(memberDto);
    }

    @Override
    public MemberDto selectMemberById(Long id) {
        return memberDao.selectMemberById(id);
    }
}
