package cn.tsjcate.admin.security.service;

import java.util.List;

import cn.tsjcate.admin.security.dao.AdminFunctionDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import cn.tsjcate.admin.security.entity.AdminFunction;
import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.framework.common.search.Search;

/**
 * AdminFunctionService
 */
@Service
public class AdminFunctionService {
	
	@Autowired private AdminFunctionDAO adminFunctionDAO;
	
	/**
	 * 保存菜单信息
	 * @param adminFunction
	 */
	public AdminFunction save(AdminFunction adminFunction) {
		this.adminFunctionDAO.save(adminFunction);
		return adminFunction;
	}
	
	/**
	 * 修改菜单信息
	 * @param adminFunction
	 */
	public void update(AdminFunction adminFunction) {
		this.adminFunctionDAO.update(adminFunction);
	}
	
	/**
	 * 根据ID删除菜单
	 * @param id
	 */
	public void delete(Long id) {
		this.adminFunctionDAO.deleteById(id);
	}
	
	/**
	 * 分页查询
	 * @param search
	 * @return
	 */
	public PagingResult<AdminFunction> getPage(Search search) {
		return this.adminFunctionDAO.findPage(search);
	}
	
	public List<AdminFunction> getAll() {
		return this.adminFunctionDAO.findAll();
	}

}
