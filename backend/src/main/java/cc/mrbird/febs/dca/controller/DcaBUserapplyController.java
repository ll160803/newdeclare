package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.DcaUserYj;
import cc.mrbird.febs.dca.service.IDcaBAuditdynamicService;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.service.IDcaBUserapplyService;
import cc.mrbird.febs.dca.entity.DcaBUserapply;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.dca.service.IDcaUserYjService;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 *
 * @author viki
 * @since 2020-11-05
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBUserapply")

public class DcaBUserapplyController extends BaseController{

private String message;
@Autowired
public IDcaBUserapplyService iDcaBUserapplyService;

    @Autowired
    public IDcaUserYjService iDcaUserYjService;

    @Autowired
    public IDcaBAuditdynamicService iDcaBAuditdynamicService;

    @Autowired
    public IDcaBReportService iDcaBReportService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBUserapply/DcaBUserapply','dca/DcaBUserapply/DcaBUserapply','dcaBUserapply:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????','dcaBUserapply:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????','dcaBUserapply:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'??????','dcaBUserapply:delete',1,1,NOW())
*/


/**
 * ??????????????????
 *
 * @param  request ????????????
 * @param dcaBUserapply ????????????
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBUserapply:view")
public Map<String, Object> List(QueryRequest request, DcaBUserapply dcaBUserapply){
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply));
        }

    @GetMapping("person")
    @RequiresPermissions("dcaBUserapply:view")
    public Map<String, Object> List2(QueryRequest request, DcaBUserapply dcaBUserapply){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setUserAccount(currentUser.getUsername());
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply));
    }
    @GetMapping("audit")
    public Map<String, Object> List4(QueryRequest request, DcaBUserapply dcaBUserapply){
        return getDataTable(this.iDcaBUserapplyService.findDcaBUserapplyAudit(request, dcaBUserapply));
    }
    @PostMapping("auditExcel")
    public void export_depart(QueryRequest request, DcaBUserapply dcaBUserapply,String dataJson, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
            String url="D:/depart.xlsx";
            List<DcaBUserapply> dcaBUserapplys = this.iDcaBUserapplyService.findDcaBUserapplyAudit(request, dcaBUserapply).getRecords();
            ExportExcelUtils.exportCustomExcel_departAudit(response,dcaBUserapplys,dataJson,"");
        } catch (Exception e) {
            message = "??????Excel??????";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
/**
 * ??????
 * @param  dcaBUserapply
 * @return
 */
@Log("??????/??????")
@PostMapping
@RequiresPermissions("dcaBUserapply:add")
public void addDcaBUserapply(@Valid DcaBUserapply dcaBUserapply,String yjIDs,String deleFlag)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setCreateUserId(currentUser.getUserId());
        dcaBUserapply.setUserAccount(currentUser.getUsername());
            dcaBUserapply.setUserAccountName(currentUser.getRealname());


           // 2021???????????? ?????????????????????2020??????????????? ?????????????????????????????? ???????????????  ??????????????????

            if( !"??????,??????".contains(dcaBUserapply.getGwdj())) {
//                LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
//                queryWrapper.eq(DcaBUserapply::getDcaYear, "2021");
//                queryWrapper.eq(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
//                // queryWrapper.eq(DcaBReport::getClshjg,"??????");
//                List<DcaBUserapply> bReportList = this.iDcaBUserapplyService.list(queryWrapper);
//                if (bReportList.size() > 0) {
//                    throw new FebsException("2021???????????????????????????????????????");
//                }

            }
            else{
                int count= this.iDcaBUserapplyService.countAccount(dcaBUserapply.getUserAccount(),dcaBUserapply.getGwdj(),dcaBUserapply.getDcaYear());
                if(count>0){
                    throw new FebsException("??????????????????????????????????????????????????????");
                }
            }
            if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
                this.iDcaBUserapplyService.createDcaBUserapply(dcaBUserapply);
            }else {
                throw new FebsException("?????????????????????????????????");
            }

           if(StringUtils.isNotEmpty(yjIDs)) {
               String[] areaIds = yjIDs.split(StringPool.COMMA);
               setUserYj(currentUser, areaIds,dcaBUserapply.getDcaYear());
           }
//           if(StringUtils.isNotEmpty(deleFlag) && deleFlag.equals("1")){ //???????????????
//               this.iDcaBAuditdynamicService.DeleteByAccount(currentUser.getUsername());
//           }
        }catch(Exception e){
        //message="??????/????????????" ;
        log.error(message,e);
        throw new FebsException(e.getMessage());
        }
        }
    private void setUserYj(User user, String[] areaIds,String year) {
        Arrays.stream(areaIds).forEach(menuId -> {
            DcaUserYj rm = new DcaUserYj();
            // rm.setId(UUID.randomUUID().toString());
            rm.setYjId(Long.parseLong(menuId));
            rm.setUserId(user.getUsername());
            rm.setDcaYear(year);
            this.iDcaUserYjService.createDcaUserYj(rm);
        });
    }
/**
 * ??????
 * @param dcaBUserapply
 * @return
 */
@Log("??????")
@PutMapping
@RequiresPermissions("dcaBUserapply:update")
public void updateDcaBUserapply(@Valid DcaBUserapply dcaBUserapply,String yjIDs)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBUserapply.setModifyUserId(currentUser.getUserId());
            dcaBUserapply.setUserAccount(currentUser.getUsername());



            // 2021????????? ?????????????????????2020??????????????? ?????????????????????????????? ???????????????  ??????????????????
            if( !"??????,??????".contains(dcaBUserapply.getGwdj())) {
//                LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
//                queryWrapper.eq(DcaBUserapply::getDcaYear, "2021");
//                queryWrapper.eq(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
//                // queryWrapper.eq(DcaBReport::getClshjg,"??????");
//                List<DcaBUserapply> bReportList = this.iDcaBUserapplyService.list(queryWrapper);
//                if (bReportList.size() > 0) {
//                    throw new FebsException("2021???????????????????????????????????????");
//                }
            }
            else{
                int count= this.iDcaBUserapplyService.countAccount(dcaBUserapply.getUserAccount(),dcaBUserapply.getGwdj(),dcaBUserapply.getDcaYear());
                if(count>0){
                    throw new FebsException("??????????????????????????????????????????????????????");
                }
            }

            if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
                this.iDcaBUserapplyService.updateDcaBUserapply(dcaBUserapply);}
            else {
                throw new FebsException("?????????????????????????????????");
            }

         //?????????????????????
            this.iDcaUserYjService.deleteByuserid(currentUser.getUsername(),dcaBUserapply.getDcaYear());

            if(StringUtils.isNotEmpty(yjIDs)) {
                String[] areaIds = yjIDs.split(StringPool.COMMA);
                setUserYj(currentUser, areaIds,dcaBUserapply.getDcaYear());
            }
        }catch(Exception e){
       // message="????????????" ;
        log.error(message,e);
        throw new FebsException(e.getMessage());
        }
        }

    @Log("??????")
    @PutMapping("state")
    public void updateDcaBUserapplyState(@Valid DcaBUserapply dcaBUserapply)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBUserapply.setModifyUserId(currentUser.getUserId());
           // dcaBUserapply.setUserAccount(currentUser.getUsername());
            this.iDcaBUserapplyService.updateDcaBUserapplyState(dcaBUserapply);

        }catch(Exception e){
            message="????????????" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@Log("??????")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUserapply:delete")
public void deleteDcaBUserapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUserapplyService.deleteDcaBUserapplys(arr_ids);
        }catch(Exception e){
        message="????????????" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
public void export(QueryRequest request, DcaBUserapply dcaBUserapply, HttpServletResponse response) throws FebsException {
        try {
            request.setPageNum(1);
            request.setPageSize(10000);
        List<DcaBUserapply> dcaBUserapplys = this.iDcaBUserapplyService.findDcaBUserapplys(request, dcaBUserapply).getRecords();
        ExcelKit.$Export(DcaBUserapply.class, response).downXlsx(dcaBUserapplys, false);
        } catch (Exception e) {
        message = "??????Excel??????";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBUserapply detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBUserapply dcaBUserapply=this.iDcaBUserapplyService.getById(id);
        return dcaBUserapply;
        }
        }