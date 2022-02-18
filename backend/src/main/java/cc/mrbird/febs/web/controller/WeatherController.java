package cc.mrbird.febs.web.controller;

import cc.mrbird.febs.common.domain.FebsConstant;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.QueryRequest;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.common.utils.HttpUtil;
import cc.mrbird.febs.dca.entity.DcaBSciencepublish;
import cc.mrbird.febs.scm.RFC.BackInfo;
import cc.mrbird.febs.scm.RFC.RfcNOC;
import cc.mrbird.febs.system.domain.User;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletResponse;
import javax.validation.constraints.NotBlank;
import javax.xml.ws.Holder;
import java.util.List;

@Slf4j
@Validated
@RestController
@RequestMapping("weather")
public class WeatherController {

    @GetMapping
    @RequiresPermissions("weather:view")
    public FebsResponse queryWeather(@NotBlank(message = "{required}") String areaId) throws FebsException {
        try {
            String data = HttpUtil.sendPost(FebsConstant.MEIZU_WEATHER_URL, "cityIds=" + areaId);
            return new FebsResponse().data(data);
        } catch (Exception e) {
            String message = "天气查询失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
    @GetMapping("info")
    public FebsResponse getList( String id){

       log.info("info:"+id);
        RfcNOC rfcNOC =new RfcNOC();
      List<BackInfo> backInfoList=  rfcNOC.GetInfoList(id);
     return  new FebsResponse().data(backInfoList);
    }
    @GetMapping("info2")
    public FebsResponse getList2( String id){

        log.info("info2:"+id);
        RfcNOC rfcNOC =new RfcNOC();
        String reStr=  rfcNOC.GetStr(id);
        return  new FebsResponse().data(reStr);
    }
    @PostMapping("excel")
    public void export(QueryRequest request, String id, String dataJson, HttpServletResponse response)throws FebsException{
        String message="";
        try{

            RfcNOC rfcNOC =new RfcNOC();
            List<BackInfo> backInfoList=  rfcNOC.GetInfoList(id);
            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, backInfoList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
}
