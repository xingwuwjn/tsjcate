package cn.tsjcate.web.site.dto;

import cn.tsjcate.groupon.deal.entity.Deal;
import cn.tsjcate.groupon.deal.entity.DealCategory;
import lombok.Getter;
import lombok.Setter;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by 醒悟wjn on 2017/7/16.
 */
public class DealCategory_dto {
    @Setter @Getter private DealCategory dealCategory;
    @Setter @Getter private List<Deal> first;
    @Setter @Getter private List<Deal> second;
    public DealCategory_dto(DealCategory dealCategory,List<Deal>dealist){
      this.dealCategory=dealCategory;
      if(dealist.size()==0){
          this.first=new ArrayList<>();
          this.second=new ArrayList<>();
      }
      else if(dealist.size()>4&&dealist.size()<=8){
          this.first=new ArrayList<>(dealist.subList(0,4));
          this.second=new ArrayList<>(dealist.subList(4,dealist.size()));

      }
      else if(dealist.size()>8){
          this.first=new ArrayList<>(dealist.subList(0,4));
          this.second=new ArrayList<>(dealist.subList(4,8));
      }
      else{
          this.first=new ArrayList<>(dealist);
          this.second=new ArrayList<>();
      }


    }

}
