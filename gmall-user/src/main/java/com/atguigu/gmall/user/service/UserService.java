package com.atguigu.gmall.user.service;

import java.util.List;

import com.atguigu.gmall.user.bean.UmsMember;
import com.atguigu.gmall.user.bean.UmsMemberReceiveAddress;

/**
 * 会员表(ums_member)表服务接口
 *
 * @author caijia
 * @since 2020-02-27 17:42:45
 */
public interface UserService {

    /**
     * 通过ID查询单条数据
     *
     * @param id 主键
     * @return 实例对象
     */
    UmsMember queryById(Long id);

    /**
     * 查询多条数据
     *
     * @param offset 查询起始位置
     * @param limit 查询条数
     * @return 对象列表
     */
    List<UmsMember> queryAllByLimit(int offset, int limit);

    /**
     * 新增数据
     *
     * @param umsMember 实例对象
     * @return 实例对象
     */
    UmsMember insert(UmsMember umsMember);

    /**
     * 修改数据
     *
     * @param umsMember 实例对象
     * @return 实例对象
     */
    UmsMember update(UmsMember umsMember);

    /**
     * 通过主键删除数据
     *
     * @param id 主键
     * @return 是否成功
     */
    boolean deleteById(Long id);

	/**
	 * 通过用户ID查询用户收货地址
	 * @param memberId
	 * @return 收货地址实体类列表
	 */
	List<UmsMemberReceiveAddress> getReceiveAddressByMemeberId(Long memberId);

}
