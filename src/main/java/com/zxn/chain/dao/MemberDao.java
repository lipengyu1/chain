package com.zxn.chain.dao;

import com.zxn.chain.dto.MemberDto;

import java.util.List;

public interface MemberDao {
    void saveMember(MemberDto memberDto);

    void removeMember(Long id);

    List<MemberDto> queryMemberPage(int pageNo, int pageSize, String memberName, String memberNum, String memberStatus);

    int queryMemberCount(int pageNo, int pageSize, String memberName, String memberNum, String memberStatus);

    void updateMember(MemberDto memberDto);

    MemberDto selectMemberById(Long id);
}
