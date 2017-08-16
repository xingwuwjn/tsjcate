package cn.tsjcate.admin.base.controller;

import cn.tsjcate.admin.base.router.BaseRouter;
import cn.tsjcate.admin.base.constant.Constants;
import cn.tsjcate.admin.security.entity.AdminUser;
import cn.tsjcate.framework.base.context.SpringApplicationContext;
import cn.tsjcate.framework.base.entity.BaseEntity;
import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.framework.common.search.Search;
import cn.tsjcate.framework.util.image.ImageUtil;
import cn.tsjcate.framework.web.context.RequestContext;
import cn.tsjcate.framework.web.controller.BaseController;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.shiro.SecurityUtils;

/**
 *
 */
public class BaseAdminController extends BaseController {
	
	public BaseAdminController() {}
	
	public AdminUser getCurrentUser() {
		return (AdminUser) SecurityUtils.getSubject().getPrincipal();
	}

	/**
	 * 获得Router实例
	 * @param routerName 简称,不需要包含"Router"(所有的Router必须以"**Router"格式命名)
	 * @return
     */
	protected BaseRouter getRouter(String routerName) {
		Object router = SpringApplicationContext.getBean(routerName + "Router");
		if (null == router || !(router instanceof BaseRouter)) {
			return null;
		}
		return (BaseRouter) router;
	}

	/**给查询出来的每条数据（商品）在这里是entity加上router属性
	 * 生成Router按钮(HTML)//
	 * @param pagingResult
	 * @param search
	 * @param <T>
     */
	public <T extends BaseEntity> void generateRouterCallButtons(PagingResult<T> pagingResult, Search search) {
		/*如果没有传入参数则不执行*/
		if (!search.containsRouterCall()) {
			return;
		}
		//依照routername获得router实例
		BaseRouter router = getRouter(search.getRouterName());
		if (null == router) {
			return;
		}

		String[] methods = search.getMethods();//获得router所有的方法
		for (T entity : pagingResult.getRows()) {//遍历每条数据(如商品),写入routerCall属性
			StringBuilder routerCall = new StringBuilder();
			for (String method : methods) {//循环生成每个method的html
				String button = generateRouterCallButton(router, search.getRouterName(), entity, method,
						ArrayUtils.contains(search.getConfirmMethods(), method), search.getGridId());
				if (null != button) {
					routerCall.append(button);
				}
			}
			entity.setRouterCall(routerCall.toString());//每个实体添加router属性
		}
	}

	/**
	 *生成某个列表一条数据的操作方法
	 * @param router  实例
	 * @param routerName  要生成的router名称
	 * @param entity  当前要操作的实体（如商品，订单。。。）
	 * @param method  每个router方法/按钮（如已售完，发布。。）
	 * @param confirm
	 * @param gridId  router的id
	 * @param <T>
	 * @return
	 */
	private <T extends BaseEntity> String generateRouterCallButton(BaseRouter router, String routerName, T entity,
																   String method, boolean confirm, String gridId) {

		String methodName = router.getMethodDisplayName(method);//method显示名称
		StringBuilder html = new StringBuilder();
		html.append("<button onclick=\"javascript:ajaxRouterCall('");
		html.append(RequestContext.getCurrentContext().getContextPath()).append("','").append(routerName).append("','");
		html.append(method).append("','").append(methodName).append("','");
		html.append(entity.getId()).append("',").append(confirm ? "true" : "false")
				.append(",'").append(gridId).append("')\" class='easyui-linkbutton'");
		if (router.isButtonDisabled(entity, method)) {
			html.append(" disabled = 'true' ");
		}
		html.append(">").append(methodName).append("</button>");
		return html.toString();
		/*<button onclick="javascript:ajaxRouterCall('http://localhost:8080/admin'," +
				"'product'," +
				"'oosStatusInvalid'," +
				"'可售','12'," +
				"true,'productListGrid')"
		class="easyui-linkbutton" disabled="true">可售
				</button>*/
	}


	/**
	 * 获取图片URL
	 * @param imageId 图片ID
	 * @param moduleName 模块名称，如：deal
	 * @param imageConfigNumber 详见image.properties
	 * @return
	 */
	protected String getObjectImageUrl(long imageId, String moduleName, int imageConfigNumber) {
		return ImageUtil.getImageUrl(Constants.IMAGE_DOMAIN + "/images/", imageId, moduleName, imageConfigNumber);
	}
}