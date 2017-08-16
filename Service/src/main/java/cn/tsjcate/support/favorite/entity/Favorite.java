package cn.tsjcate.support.favorite.entity;

import cn.tsjcate.framework.base.entity.BaseEntity;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * 收藏
 */
public class Favorite extends BaseEntity {

    @Getter @Setter private Long userId;
    @Getter @Setter private Long dealId;
    @Getter @Setter private Long dealSkuId;
    @Getter @Setter private Date createTime;
    @Getter @Setter private Date updateTime;

}
