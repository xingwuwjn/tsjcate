package cn.tsjcate.admin.area.controller;

import cn.tsjcate.admin.base.controller.BaseAdminController;
import cn.tsjcate.admin.common.tree.EasyUITreeNode;
import cn.tsjcate.framework.common.page.PagingResult;
import cn.tsjcate.framework.common.search.Search;
import cn.tsjcate.support.area.entity.Area;
import cn.tsjcate.support.area.entity.AreaType;
import cn.tsjcate.support.area.service.AreaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * 地区
 */
@Controller
@RequestMapping("/area")
public class AreaController extends BaseAdminController {

    @Autowired private AreaService areaService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/area/area_tree";
    }

    // 加载地区树
    @RequestMapping(value = "/buildAreaTree", method = RequestMethod.GET)
    @ResponseBody
    public List<EasyUITreeNode> buildProductCategoryTree() {
        List<EasyUITreeNode> nodes = new ArrayList<>();

        List<Area> list = areaService.getAll();//获得所有的根
        for (Area area : list) {
            EasyUITreeNode node = new EasyUITreeNode();
            node.setChecked(false);
            node.setId(area.getId());
            node.setText(area.getName());
            node.setState("closed");
            initChildren(node, area.getChildren(), area.getType());
            nodes.add(node);
        }
        return nodes;
    }

    private void initChildren(EasyUITreeNode parent, List<Area> areas, AreaType areaType) {
        List<EasyUITreeNode> childrenVO = new ArrayList<>();
        if (null == areas) {
            if (Objects.equals(AreaType.PROVINCE, areaType)) {
                EasyUITreeNode childVO = new EasyUITreeNode();
                childVO.setChecked(false);
                childVO.setId(parent.getId());
                childVO.setParentId(parent.getId());
                childVO.setText(parent.getText());
                childrenVO.add(childVO);
                parent.setChildren(childrenVO);
            }
            return;
        }


        for (Area area : areas) {
            EasyUITreeNode childVO = new EasyUITreeNode();
            childVO.setChecked(false);
            childVO.setId(area.getId());
            childVO.setParentId(parent.getId());
            childVO.setText(area.getName());
            initChildren(childVO, area.getChildren(), area.getType());
            childrenVO.add(childVO);
        }
        parent.setChildren(childrenVO);
    }

    // 加载地区列表
    @RequestMapping(value = "/listArea", method = RequestMethod.POST)
    @ResponseBody
    public PagingResult<Area> listArea(Search search) {
        return this.areaService.getPage(search);
    }


}
