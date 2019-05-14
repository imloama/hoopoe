package hoopoe.monitor.controller;

import com.alibaba.druid.stat.DruidStatManagerFacade;
import com.github.imloama.mybatisplus.bootext.base.APIResult;
import com.google.common.collect.Maps;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/druid")
public class DruidController {

    @GetMapping("/info")
    public APIResult info(){
        DruidStatManagerFacade instance = DruidStatManagerFacade.getInstance();

        List<Map<String,Object>> list = instance.getDataSourceStatDataList();

//        Map<Integer,List<Map<String, Object>>> sqls = Maps.newHashMap();
        for(Map<String,Object> item : list){
            Integer id = (Integer)item.get("Identity");
//            sqls.put(id, instance.getSqlStatDataList(id));
            item.put("sqls",instance.getSqlStatDataList(id));
        }

        return APIResult.ok("success",list);

    }

}
