package cn.tsjcate.admin.security.controller;

import cn.tsjcate.admin.common.cache.NativeCacheOperator;
import cn.tsjcate.admin.common.tree.EasyUITreeNode;
import cn.tsjcate.admin.security.entity.*;
import cn.tsjcate.admin.base.controller.BaseAdminController;
import cn.tsjcate.admin.security.entity.*;
import cn.tsjcate.admin.security.service.AdminRoleService;
import cn.tsjcate.framework.util.EncryptionUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.*;
import java.util.stream.Collectors;

/**
 * LoginController
 */
@Controller
public class LoginController extends BaseAdminController {
	
	private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

	@Autowired private NativeCacheOperator cacheOperator;

	@Autowired private AdminRoleService adminRoleService;

	@RequestMapping(value = "/login",method = {RequestMethod.POST, RequestMethod.GET})
	public String login(Model model, HttpServletRequest request){
		Subject user = SecurityUtils.getSubject();
		if (!user.isAuthenticated()) {
			return "/lg/login";
		}
		
		return "redirect:/main";
	}

	@RequestMapping(value = "/main", method = {RequestMethod.POST, RequestMethod.GET})
	public String index(Model model, AdminUser curUser) {
		UsernamePasswordToken token = null;
		try {
			Subject subject = SecurityUtils.getSubject();
			if (!subject.isAuthenticated()) {//没有被授权
				curUser.setPassword(EncryptionUtil.MD5(curUser.getPassword()));
				token = new UsernamePasswordToken(curUser.getName(), curUser.getPassword());
				subject.login(token);//进行登录验证
			}
			else {//被授权
				curUser = getCurrentUser();
			}
            //获得当前用户的
	    	List<AdminUserRole> adminUserRoles = (super.getCurrentUser()).getAdminUserRole();
	    	if (!Objects.equals("admin", curUser.getName()) && (null == adminUserRoles || 0 == adminUserRoles.size())) {
	    		logger.info("ERP用户没有设置权限 : " + (super.getCurrentUser()).getName());
	    		return "/lg/login";
	    	}
			/*List<Long> adminRoleIdList= new ArrayList<>();
	        for (AdminUserRole role : adminUserRoles) {
	        	adminRoleIdList.add(role.getAdminRoleId());
	        }*/
			List<Long> adminRoleIdList=adminUserRoles.stream().map(adminUserRole -> adminUserRole.getAdminRoleId()).collect(Collectors.toList());
	        //如果不是超级管理员，将其对应的角色放入缓存中
			if (!Objects.equals("admin", curUser.getName())) {
				List<AdminRole> adminRoles = this.adminRoleService.getAdminRoleByIds(adminRoleIdList);
				cacheOperator.setAdminRoles(super.getCurrentUser().getId(), adminRoles);
			}
			logger.info("ERP登录 : " + (super.getCurrentUser()).getName());
			return "/layout/main";
	    } catch (Exception e) {
			if (null != token) {
				token.clear();
			}
			model.addAttribute("errorMsg", e);
	    	return "/lg/login";
	    }
	}
	
	@RequestMapping(value = "/logout", method = {RequestMethod.POST, RequestMethod.GET})
	public String logout(Model model,AdminUser currUser){
		Subject user = SecurityUtils.getSubject();
		user.logout();
    	return "/lg/login";
	}

	//获取权限范围内的菜单树
	@RequestMapping(value = "/buildFunctionTreeForNavigation", method = RequestMethod.POST)
	@ResponseBody
	public List<EasyUITreeNode> buildFunctionTreeForNavigation() {
		AdminUser user = super.getCurrentUser();
		Map<Long, EasyUITreeNode> nodeMap = new HashMap<>();//包含所有树的节点
		Set<EasyUITreeNode> permissionAdminFunctionSet = new HashSet<>();

		if (Objects.equals("admin", user.getName())) {
			for (AdminFunction func : cacheOperator.getAdminFunctions()) {
				EasyUITreeNode node = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
				node.addAttribute("url", func.getUrl());
				nodeMap.put(func.getId(), node);
				permissionAdminFunctionSet.add(node);
			}
		}
		else {
			List<AdminRole> adminRoles = cacheOperator.getAdminRoles(user.getId());

			List<Long> roleIds = new ArrayList<>();
			adminRoles.forEach(ar -> roleIds.add(ar.getId()));

			List<AdminRoleFunction> roleFunctions = adminRoleService.getAdminRoleFunctionsByRoleIds(roleIds);

			Set<Long> functionIds = new HashSet<>();//得到所有的权限菜单id
			roleFunctions.forEach(rf -> functionIds.add(rf.getAdminFunctionId()));

			for (AdminFunction func : cacheOperator.getAdminFunctions()) {
				EasyUITreeNode node = new EasyUITreeNode(func.getId(), func.getParentId(), func.getName(), func.getState());
				node.addAttribute("url", func.getUrl());
				nodeMap.put(func.getId(), node);
				if (functionIds.contains(func.getId())) {
					permissionAdminFunctionSet.add(node);
				}
			}

			Set<EasyUITreeNode> tempPermissionSet = new HashSet<>();//存放权限节点的父节点
			for (EasyUITreeNode node : permissionAdminFunctionSet) {
				completeTreeNode(nodeMap, tempPermissionSet, nodeMap.get(node.getParentId()));
			}

			permissionAdminFunctionSet.addAll(tempPermissionSet);
		}
		EasyUITreeNode root = nodeMap.get(1l);//获得根
		List<EasyUITreeNode> treeNodeList = new ArrayList<>(permissionAdminFunctionSet);
		Collections.sort(treeNodeList);
		buildTree(treeNodeList, root);

		List<EasyUITreeNode> result = new ArrayList<>();
		result.add(root);
		return result;
	}
//   将菜单的父菜单加入到tempPermissonAdminFunctionSet中（其实数据库中已经存入了其父菜单了）
	private void completeTreeNode(Map<Long, EasyUITreeNode> nodeMap, Set<EasyUITreeNode> tempPermissionAdminFunctionSet, EasyUITreeNode node) {
		//说明不是一级节点
		if (node.getId() != 1) {
			tempPermissionAdminFunctionSet.add(node);//将父节点添加到
			completeTreeNode(nodeMap, tempPermissionAdminFunctionSet, nodeMap.get(node.getParentId()));
		}
	}
//递归构造菜单树
	private void buildTree(List<EasyUITreeNode> treeNodeList, EasyUITreeNode parent) {
		EasyUITreeNode node;
		for (Iterator<EasyUITreeNode> ite = treeNodeList.iterator(); ite.hasNext();) {
			node = ite.next();
			if (node.getParentId() == parent.getId()) {
				parent.getChildren().add(node);
				buildTree(treeNodeList, node);
			}
		}
	}
	
}
