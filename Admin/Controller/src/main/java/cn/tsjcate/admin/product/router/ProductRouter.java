package cn.tsjcate.admin.product.router;

import cn.tsjcate.admin.base.controller.AjaxResult;
import cn.tsjcate.admin.base.router.BaseRouter;
import cn.tsjcate.framework.base.entity.BaseEntity;
import cn.tsjcate.framework.util.DateUtil;
import cn.tsjcate.groupon.deal.constant.DealConstant;
import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.groupon.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.Map;

/**
 * 商品Router
 */
@Component
public class ProductRouter extends BaseRouter {

	@Autowired private DealService dealService; // 商品服务

	public ProductRouter() {
		this.clazz = Deal.class;
		super.addMethodDisplayName("oosStatusInvalid", "可售");
		super.addMethodDisplayName("oosStatusValid", "已卖光");
		super.addMethodDisplayName("deleteProduct", "删除");
		super.addMethodDisplayName("publishProduct", "发布");
		super.addMethodDisplayName("auditProduct", "审核");
		super.addMethodDisplayName("cancelPublish", "取消发布");
	}

	@SuppressWarnings("unchecked")
	@Override
	public Deal loadEntity(Long id) {
		Deal deal = new Deal();
		deal.setId(id);
		return deal;
	}

	/**
	 *
	 * @param entity  商品实体
	 * @param method   操作方法
	 * @return
	 */
	public boolean isButtonDisabled(BaseEntity entity, String method) {
		Deal deal = (Deal) entity;
		boolean disableFlag = false;
		switch (method) {
			case "oosStatusInvalid"://是否售完
				if (deal.getOosStatus() == DealConstant.DEAL_OOS_STATUS_NO) {//售完
					disableFlag = true;
				}
				break;
			case "oosStatusValid"://是否卖完
				if (deal.getOosStatus() == DealConstant.DEAL_OOS_STATUS_YES) {//已卖光
					disableFlag = true;
				}
				break;
			case "deleteProduct":
				if (deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_DELETED) {//已删除
					disableFlag = true;
				}
				break;
			case "publishProduct":
				if (deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_PUBLISH) {
					disableFlag = true;
				}
				break;
			case "auditProduct":
				if (deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_AUDITED || deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_PUBLISH) {
					disableFlag = true;
				}
				break;
			case "cancelPublish":
				if (deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_NEW
						|| deal.getPublishStatus() == DealConstant.DEAL_PUBLISH_STATUS_AUDITED) {
					disableFlag = true;
				}
				break;
			default:
				break;
		}
		return disableFlag;
	}

	/**
	 * 删除商品
	 * @param user
	 * @param params
	 * @return
	 */
	public AjaxResult deleteProduct(BaseEntity user, Deal deal, Map<String, String> params) {
		deal.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_DELETED);
		AjaxResult modifyResult = new AjaxResult();
		int modifyFlag = dealService.modifyStatusById(deal);//删除商品

		if (modifyFlag > -1) {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		} else {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		}

		return modifyResult;
	}

	/**
	 * 发布商品
	 * @param user
	 * @param deal
	 * @param params
	 * @return
	 */
	public AjaxResult publishProduct(BaseEntity user, Deal deal, Map<String, String> params) {
		AjaxResult modifyResult = null;
		try {
			deal.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_PUBLISH);
			modifyResult = new AjaxResult();
			Deal existInfo = dealService.getById(deal.getId());
			if (null != existInfo) {
				if (null != existInfo.getEndTime()) {
					if (existInfo.getEndTime().compareTo(new Date()) < 0) {
						deal.setEndTime(DateUtil.getSevenDaysAfterOnSale());
					}
				} else {
					deal.setEndTime(DateUtil.getSevenDaysAfterOnSale());
				}
			}

			int modifyFlag = dealService.modifyStatusById(deal);
			if (modifyFlag > -1) {
				modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
			} else {
				modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return modifyResult;
	}

	/**
	 * 取消发布商品
	 * @param user
	 * @param deal
	 * @param params
	 * @return
	 */
	public AjaxResult cancelPublish(BaseEntity user, Deal deal, Map<String, String> params) {
		// 取消发布，默认为已审核
		deal.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_AUDITED);
		AjaxResult modifyResult = new AjaxResult();
		int modifyFlag = dealService.modifyStatusById(deal);

		if (modifyFlag > -1) {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		} else {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		}
		return modifyResult;
	}

	/**
	 * 审核商品
	 * @param user
	 * @param deal
	 * @param params
	 * @return
	 */
	public AjaxResult auditProduct(BaseEntity user, Deal deal, Map<String, String> params) {
		deal.setPublishStatus(DealConstant.DEAL_PUBLISH_STATUS_AUDITED);
		AjaxResult modifyResult = new AjaxResult();
		int modifyFlag = dealService.modifyStatusById(deal);

		if (modifyFlag > -1) {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		} else {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		}
		return modifyResult;
	}

	/**
	 * 设置已卖光
	 * @param user
	 * @param deal
	 * @param params
	 * @return
	 */
	public AjaxResult oosStatusValid(BaseEntity user, Deal deal, Map<String, String> params) {
		deal.setOosStatus(DealConstant.DEAL_OOS_STATUS_YES);
		AjaxResult modifyResult = new AjaxResult();
		int modifyFlag = dealService.modifyOosStatusById(deal);

		if (modifyFlag > -1) {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		} else {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		}
		return modifyResult;
	}

	/**
	 * 可售
	 * @param user
	 * @param productInfo
	 * @param params
	 * @return
	 */
	public AjaxResult oosStatusInvalid(BaseEntity user, Deal productInfo, Map<String, String> params) {
		productInfo.setOosStatus(DealConstant.DEAL_OOS_STATUS_NO);
		AjaxResult modifyResult = new AjaxResult();
		int modifyFlag = dealService.modifyOosStatusById(productInfo);

		if (modifyFlag > -1) {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_SUCCESS);
		} else {
			modifyResult.setStatusCode(AjaxResult.AJAX_STATUS_CODE_ERROR);
		}
		return modifyResult;
	}

}
