package cn.tsjcate.web.site.helper;

import cn.tsjcate.groupon.deal.constant.DealConstant;
import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.web.base.helper.FrontendBaseHelper;
import cn.tsjcate.web.constants.WebConstants;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

/**
 * Created by 醒悟wjn on 2017/7/20.
 */
@Component
public class BaseSiteHelper extends FrontendBaseHelper {

    public String getindexDealimagelist(Deal deal){
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_LIST);

    }
    public String getDealimage1(Deal deal){
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_INDEX_B);

    }

    public String getDealimage2(Deal deal){
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_INDEX_S);

    }

    public String getDealimage3(Deal deal){
        return getObjectImageUrl(DealConstant.PICTURE_MODULE_DEAL, deal.getImageId(),
                DealConstant.PICTURE_SIZE_BY_TYPE_DETAIL);

    }

    /**
     * 获取CSS文件URL
     */
    public String getCSSFileUrl(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        } else{
            return WebConstants.SITE_CSS_DOMAIN_NAME + uri;
        }
    }

    /**
     * 获取JS文件URL
     */
    public String getJSFileUrl(String uri) {
        if (StringUtils.isEmpty(uri)) {
            return "";
        } else{
            return WebConstants.SITE_JS_DOMAIN_NAME + uri;
        }
    }

}
