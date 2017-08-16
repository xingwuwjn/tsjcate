package cn.tsjcate.web.site.controller.deal;

import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.groupon.deal.entity.DealCategory;
import cn.tsjcate.groupon.deal.service.DealCategoryService;
import cn.tsjcate.groupon.deal.service.DealService;
import cn.tsjcate.web.base.controller.FrontendBaseController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Locale;

/**
 * Created by 醒悟wjn on 2017/7/22.
 */
@Controller
public class DealController extends FrontendBaseController{
    @Autowired
    private DealCategoryService dealCategoryService;
    @Autowired
    private DealService dealService;


    /*@RequestMapping(value="/category/{urlName}",method= RequestMethod.GET)
    public String result(){
        return "deal/result";
    }*/

    @RequestMapping(value="/category/{urlname}",method= RequestMethod.GET)
    public String search_result(Model model, @PathVariable String urlname,HttpServletRequest request){
        return searchpage_result(model,urlname,1,request);
    }

    /**
     *根据类别分页查询出商品信息
     * @param model
     * @param urlname 分类url名称
     * @return
     */
    @RequestMapping(value="/category/{urlname}/{page}",method= RequestMethod.GET)
    public String searchpage_result(Model model, @PathVariable String urlname, @PathVariable int page, HttpServletRequest request){
        List<DealCategory> categoryList=dealCategoryService.getCategories();//获得所有的商品分类
        model.addAttribute("categoryList",categoryList);
        DealCategory dealCategory =dealCategoryService.getByUrlName(urlname);
        model.addAttribute("dealCategory",dealCategory);
        List<Long> ids=dealCategory.getSelfAndChildrenIds();//获得其下级所有id
        PagingResult<Deal> deals=dealService.getDealsOfCategories(ids,getAreaId(request),page,16);
        model.addAttribute("deals",deals);
        return "deal/result";
    }
}
