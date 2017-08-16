package cn.tsjcate.admin.security.entity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import cn.tsjcate.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

/**
 * 后台用户
 */
public class AdminUser extends BaseEntity {

	//用户名称
	@Getter @Setter private String name;
	 
	 //用户密码
	@Getter @Setter private String password;
	 
	//最后登录时间
	@Getter @Setter private Date createTime;
	 
	// 最后登录ip
	@Getter @Setter private Date updateTime;
	 
	//在用户登陆时候查询出adminuserrole角色
	@Getter @Setter private List<AdminUserRole> adminUserRole = new ArrayList<>();

}
