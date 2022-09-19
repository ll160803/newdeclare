package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaUserYjService;
import cc.mrbird.febs.dca.entity.DcaUserYj;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import cn.hutool.core.date.DateUtil;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 *
 * @author viki
 * @since 2021-09-23
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaUserYj")

public class DcaUserYjController extends BaseController{

private String message;
@Autowired
public IDcaUserYjService iDcaUserYjService;


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaUserYj 查询条件
 * @return
 */
@GetMapping
public Map<String, Object> List(QueryRequest request, DcaUserYj dcaUserYj){
        return getDataTable(this.iDcaUserYjService.findDcaUserYjs(request, dcaUserYj));
        }
@GetMapping("custom")
public Map<String, Object> ListCustom(QueryRequest request, DcaUserYj dcaUserYj){
        User currentUser= FebsUtil.getCurrentUser();

        request.setPageSize(1000);
        request.setSortField("display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaUserYjService.findDcaUserYjs(request, dcaUserYj));
        }
@GetMapping("audit")
public Map<String, Object> List2(QueryRequest request, DcaUserYj dcaUserYj){
        User currentUser= FebsUtil.getCurrentUser();

        request.setSortField("user_account asc,state asc,display_Index");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaUserYjService.findDcaUserYjs(request, dcaUserYj));
        }
@Log("新增/按钮")
@PostMapping("addNew")
public void addDcaUserYjCustom(@Valid String jsonStr,int state)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        List<DcaUserYj> list=JSON.parseObject(jsonStr,new TypeReference<List<DcaUserYj>>(){
        });
        int countid=0;
        /**
         * 先删除数据，然后再添加
         */
        this.iDcaUserYjService.deleteByuseraccount(currentUser.getUsername());
        int display=this.iDcaUserYjService.getMaxDisplayIndexByuseraccount(currentUser.getUsername())+1;

        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@Log("审核/按钮")
@PostMapping("updateNew")
public void updateNewDcaUserYj(@Valid String jsonStr ,int state )throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
    DcaUserYj dcaUserYj= JSON.parseObject(jsonStr, new TypeReference<DcaUserYj>() {
        });

        this.iDcaUserYjService.updateDcaUserYj(dcaUserYj);

        }catch(Exception e){
        message="审核/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 添加
 * @param  dcaUserYj
 * @return
 */
@Log("新增/按钮")
@PostMapping
public void addDcaUserYj(@Valid DcaUserYj dcaUserYj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();
        this.iDcaUserYjService.deleteByuseraccount(currentUser.getUsername());
        this.iDcaUserYjService.createDcaUserYj(dcaUserYj);
        }catch(Exception e){
        message="新增/按钮失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaUserYj
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaUserYj:update")
public void updateDcaUserYj(@Valid DcaUserYj dcaUserYj)throws FebsException{
        try{
        User currentUser=FebsUtil.getCurrentUser();

        this.iDcaUserYjService.updateDcaUserYj(dcaUserYj);
        }catch(Exception e){
        message="修改失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaUserYj:delete")
public void deleteDcaUserYjs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaUserYjService.deleteDcaUserYjs(arr_ids);
        }catch(Exception e){
        message="删除失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }

@PostMapping("excel")
public void export(QueryRequest request, DcaUserYj dcaUserYj,String dataJson,HttpServletResponse response)throws FebsException{
        try{
        request.setPageNum(1);
        request.setPageSize(10000);
        User currentUser = FebsUtil.getCurrentUser();


        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        List<DcaUserYj> dcaUserYjList=  this.iDcaUserYjService.findDcaUserYjs(request, dcaUserYj).getRecords();
        //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
        ExportExcelUtils.exportCustomExcel_han(response, dcaUserYjList,dataJson,"");
        }catch(Exception e){
        message="导出Excel失败";
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@GetMapping("/{id}")
public DcaUserYj detail(@NotBlank(message = "{required}") @PathVariable String id){
    DcaUserYj dcaUserYj=this.iDcaUserYjService.getById(id);
        return dcaUserYj;
        }
    @GetMapping("mudules/{year}")
    public List<String> getRoleMenus(@NotBlank(message = "{required}") @PathVariable String year,String dj) {
        User currentUser=FebsUtil.getCurrentUser();
        String userId =currentUser.getUsername();
        List<DcaUserYj> list = this.iDcaUserYjService.getMudulesByUserId(userId,year,dj);
        return list.stream().map(area -> String.valueOf(area.getYjId())).collect(Collectors.toList());
    }
        }