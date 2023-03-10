package com.zxn.chain.controller;

import com.zxn.chain.dto.MemberCollectionDto;
import com.zxn.chain.entity.MemberCollection;
import com.zxn.chain.model.BasePageResponse;
import com.zxn.chain.model.Response;
import com.zxn.chain.service.impl.MemberCollectionServiceImpl;
import com.zxn.chain.utils.JwtUtils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Slf4j
@RestController
@RequestMapping("/collection")
@Api(tags = "用户收藏接口")
public class MemberCollectionController {
    @Autowired
    MemberCollectionServiceImpl memberCollectionService;
    /**
     * 添加收藏
     * @return
     */
    @CacheEvict(value = "shopCollectionCache",allEntries = true)
    @PostMapping("/add")
    @ApiOperation(value = "用户添加收藏接口(前台)")
    public Response<String> add(@RequestBody MemberCollection memberCollection, HttpServletRequest request){
        Long memberNum = Long.valueOf(JwtUtils.getUserId(request.getHeader("token")));
        memberCollection.setMemberNum(memberNum);
        memberCollectionService.addCollection(memberCollection);
        return Response.success("收藏成功");
    }

    /**
     * 删除收藏
     * @param ids
     * @return
     */
    @CacheEvict(value = "shopCollectionCache",allEntries = true)
    @PostMapping("/del")
    @ApiOperation(value = "用户删除收藏接口(前台)")
    public Response<String> del(@RequestParam Long[] ids){
        memberCollectionService.delCollection(ids);
        return Response.success("删除成功");
    }

    /**
     * 分页查询
     * @param pageNo
     * @param pageSize
     * @return
     */
//    @Cacheable(value = "shopCollectionCache",key = "#memberNum+'_'+'collection'")
    @Cacheable(value = "shopCollectionCache",key = "#request.getHeader('token')+'_'+'collection'")
    @GetMapping("/page")
    @ApiOperation(value = "分页查询收藏接口(前台)")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "pageNo",value = "页码",required = true),
            @ApiImplicitParam(name = "pageSize",value = "每页记录数",required = true)
    })
    public Response<BasePageResponse<MemberCollectionDto>> page(int pageNo, int pageSize,HttpServletRequest request){
        Long memberNum = Long.valueOf(JwtUtils.getUserId(request.getHeader("token")));
        System.out.println(memberNum);
        log.info("pageNo={},pageSize={},memberNum={}",pageNo,pageSize);
        BasePageResponse<MemberCollectionDto> response = memberCollectionService.queryCollectionPage(pageNo,pageSize,memberNum);
        return Response.success(response);
    }

}
