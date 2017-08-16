package cn.tsjcate.admin.product.controller;

import cn.tsjcate.admin.base.controller.AjaxResult;
import cn.tsjcate.admin.base.controller.BaseAdminController;
import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.framework.common.search.Search;
import cn.tsjcate.groupon.deal.constant.DealConstant;
import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.groupon.deal.entity.DealDetail;
import cn.tsjcate.groupon.deal.service.DealCategoryService;
import cn.tsjcate.groupon.deal.service.DealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

/**
 * 商品Controller
 */
@Controller
@RequestMapping(value = "/product/product")
public class ProductController extends BaseAdminController {

	@Autowired private DealService dealService; // 商品服务
	
	@Autowired private DealCategoryService dealCategoryService;

	/**
	 * 显示商品列表页
	 * @return
     */
	@RequestMapping(value = "/index")
	public String productList() {
		return "/product/product_list";
	}

	/**
	 * 加载商品列表页数据
	 * 利用aop实现router的构造
	 * @param search
	 * @return
     */
	@RequestMapping(value = "/listProduct", method = RequestMethod.POST)
	public @ResponseBody PagingResult<Deal> listProduct(Search search) {
		PagingResult<Deal> dealListPage = dealService.getDealList(search);
		if (dealListPage != null && dealListPage.getRows().size() > 0) {
			/*给每个商品添加图片url*/
			for (Deal deal : dealListPage.getRows()) {
				String imagePath = getObjectImageUrl(deal.getImageId(), DealConstant.PICTURE_MODULE_DEAL, 2);
				deal.setImageGenPath(imagePath);
			}
		}
		return dealListPage;
	}

	/**
	 * 商品编辑页面
	 * @param id
	 * @param model
     * @return
     */
	@RequestMapping(value = "/edit/{id}", method = RequestMethod.GET)
	public String editProductWithoutHeaderAndFooter(@PathVariable Long id, Model model) {
		if (id != 0) {// 修改商品
			Deal deal = dealService.getById(id);
			deal.setImageGenPath(getObjectImageUrl(deal.getImageId(), DealConstant.PICTURE_MODULE_DEAL, 1));
			model.addAttribute("deal", deal);
		}
		return "/product/product_edit";
	}

	/**
	 * 新建/保存商品信息
	 * @param deal 商品
	 * @param dealDetailInfo 商品详情(包含图片路径)
	 * @param dealImgFile 商品图片
     * @return
     */
	@RequestMapping(value = "/saveOrUpdateProduct", method = RequestMethod.POST)
	@ResponseBody
	public AjaxResult saveOrUpdateProduct(Deal deal, String dealDetailInfo, MultipartFile dealImgFile) {
		Long dealId = deal.getId();
		DealDetail dealDetail = new DealDetail();
		dealDetail.setDealDetail(dealDetailInfo);
		deal.setDealDetail(dealDetail);

		if (null == deal || 0 == dealId) {//新建商品
			//复制商品时不用必须上传图片,所以可能会出现dealImgFile为空但是imageId不为空
			if ((null == deal.getImageId() || 0 == deal.getImageId()) && dealImgFile.isEmpty()) {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "请选择商品图片");
			}
			//复制商品:同一商品不同型号可能是不同的SKU

			boolean saveDealSuccess = false;
			try {
				//保存商品
				saveDealSuccess = dealService.saveDeal(deal, dealImgFile.isEmpty() ? null : dealImgFile.getBytes());
			} catch (IOException e) {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存失败, 读取图片错误");
			}
			if (saveDealSuccess) {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存成功");
			} else {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品保存失败");
			}
		} else {//更新商品
			byte[] dealImgBytes = null;
			if (!dealImgFile.isEmpty()) {
				try {
					dealImgBytes = dealImgFile.getBytes();
				} catch (IOException e) {
					return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新失败, 读取图片错误");
				}
			}
			boolean updateDealSuccess = dealService.updateDeal(deal, dealImgBytes);//更新商品
			if (updateDealSuccess) {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新成功");
			} else {
				return new AjaxResult(AjaxResult.AJAX_STATUS_CODE_ERROR, "商品更新失败");
			}
		}
	}

}