package com.zxn.chain.controller;

import com.zxn.chain.dto.MemberDto;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.MemberServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/member")
@Api(tags = "会员信息管理相关接口")
public class MemberController {
    @Autowired
    private MemberServiceImpl memberService;
    /**
     * 新增会员
     * @return
     */
    @PostMapping
    @ApiOperation(value = "新增会员接口")
    public Response<String> save(@RequestBody MemberDto memberDto){
        log.info(memberDto.toString());
        memberService.saveMember(memberDto);
        return Response.success("新增会员成功");
    }
    /**
     * 删除会员（单选、多选）逻辑删除
     * @param ids
     * @return
     */
    @PutMapping("/del")
    @ApiOperation(value = "删除会员接口")
    public Response<String> delete(@RequestParam Long[] ids){
        log.info("ids:{}",ids);
        memberService.removeMember(ids);
        return Response.success("会员删除成功");
    }

    /**
     * 会员分页查询
     * @param pageNo
     * @param pageSize
     * @param memberName
     * @param memberNum
     * @param memberStatus
     * @return
     */
    @GetMapping("/page")
    @ApiOperation(value = "分页查询会员接口")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true),
            @ApiImplicitParam(name = "memberName",value = "会员姓名",required = false),
            @ApiImplicitParam(name = "memberNum",value = "会员卡号",required = false),
            @ApiImplicitParam(name = "memberStatus",value = "会员状态",required = false)
    })
    public Response<BasePageResponse<MemberDto>> page(int pageNo, int pageSize, String memberName, String memberNum,String memberStatus){
        log.info("pageNo={},pageSize={},memberName={},memberNum={},memberStatus+{}",pageNo,pageSize,memberName,memberNum,memberStatus);
        BasePageResponse<MemberDto> response = memberService.queryMemberPage(pageNo,pageSize,memberName,memberNum,memberStatus);
        return Response.success(response);
    }

    /**
     * 修改会员
     * @param memberDto
     * @return
     */
    @PutMapping
    @ApiOperation(value = "修改会员接口")
    public Response<String> update(@RequestBody MemberDto memberDto){
        log.info(memberDto.toString());
        memberService.updateMember(memberDto);
        return Response.success("会员修改成功");
    }

    /**
     * id查询会员（回显）
     * @param id
     * @return
     */
    @GetMapping("/{id}")
    @ApiOperation(value = "查询会员接口(id)")
    public Response<MemberDto> getById(@PathVariable Long id){
        log.info("根据id查询供应商...");
        MemberDto memberDto = memberService.selectMemberById(id);
        if (memberDto != null){
            return Response.success(memberDto);
        }
        return Response.error("未查询到会员");
    }

}
