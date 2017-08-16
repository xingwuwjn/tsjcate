package cn.tsjcate.web.site.controller.main;

import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.groupon.deal.entity.DealCategory;
import cn.tsjcate.groupon.deal.service.DealCategoryService;
import cn.tsjcate.groupon.deal.service.DealService;
import cn.tsjcate.web.base.controller.FrontendBaseController;
import cn.tsjcate.web.site.dto.DealCategory_dto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by 醒悟wjn on 2017/7/16.
 */
@Controller
public class indexController extends FrontendBaseController {
    @Autowired
    private DealCategoryService dealCategoryService;
    @Autowired
    private DealService dealService;

    @RequestMapping(value="/",method= RequestMethod.GET)
    public String index(Model model, HttpServletRequest request){
        List<DealCategory> categoryList=dealCategoryService.getCategories();//获得所有的商品分类
        model.addAttribute("categoryList",categoryList);
        long areaid=getAreaId(request);//获取地区id
        List<DealCategory_dto> dealcategorydtos=new ArrayList<>();
        categoryList.forEach(dealCategory->{
            List<Deal>deallist=dealService.getDealsForIndex(areaid,dealCategory.getSelfAndChildrenIds());
            dealcategorydtos.add(new DealCategory_dto(dealCategory,deallist));
        });
        model.addAttribute("dealcategorydtos",dealcategorydtos);
      return "main/index";
    }
}
