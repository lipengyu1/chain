package com.zxn.chain.service;

import com.zxn.chain.dto.MemberDto;
import com.zxn.chain.model.BasePageResponse;

public interface MemberService {
    void saveMember(MemberDto memberDto);

    void removeMember(Long[] ids);

    BasePageResponse<MemberDto> queryMemberPage(int pageNo, int pageSize, String memberName, String memberNum, String memberStatus);

    void updateMember(MemberDto memberDto);

    MemberDto selectMemberById(Long id);

    MemberDto queryMemberByTel(String memberTel);

    void memberLogin(String memberTel);

    void addMemberInfo(MemberDto memberDto);
}
