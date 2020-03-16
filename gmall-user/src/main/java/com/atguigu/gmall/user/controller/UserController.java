package com.atguigu.gmall.user.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.bean.UmsMemberReceiveAddress;
import com.atguigu.gmall.user.service.UserService;

/**
 * 会员表(ums_member)表控制层
 *
 * @author caijia
 * @since 2020-02-27 17:42:45
 */
@RestController
// @RequestMapping("user")
public class UserController {
    /**
     * 服务对象
     */
    @Autowired
    private UserService userService;

    /**
     * 通过主键查询单条数据
     *
     * @param id 主键
     * @return 单条数据
     */
    @GetMapping(value = "selectOne")
    public UmsMember selectOne(Long id) {
        return this.userService.queryById(id);
    }

    @RequestMapping("/test")
    public String testd(){
    	return "nihao";
	}
	@RequestMapping("/getReceiveAddressByMemberId")
	public List<UmsMemberReceiveAddress> getReceiveAddressByMemberId(Long memberId){
    	return userService.getReceiveAddressByMemeberId(memberId);
	}
	
	@RequestMapping("/getAllUser")
    public UmsMember getAllUser(Integer id){
		UmsMember query = userService.queryById(Long.valueOf(id));
		return query;
	}
}
