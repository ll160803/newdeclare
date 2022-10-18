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
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBUserapply:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBUserapply:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBUserapply:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBUserapply 查询条件
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
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }
/**
 * 添加
 * @param  dcaBUserapply
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBUserapply:add")
public void addDcaBUserapply(@Valid DcaBUserapply dcaBUserapply,String yjIDs,String deleFlag)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBUserapply.setCreateUserId(currentUser.getUserId());
        dcaBUserapply.setUserAccount(currentUser.getUsername());
            dcaBUserapply.setUserAccountName(currentUser.getRealname());


           // 2021年中初级 需要判断是否在2020年中初级里 此人是否已经申报，且 审核结果为  确定或者评聘

            if( !"中级,初级".contains(dcaBUserapply.getGwdj())) {
//                LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
//                queryWrapper.eq(DcaBUserapply::getDcaYear, "2021");
//                queryWrapper.eq(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
//                // queryWrapper.eq(DcaBReport::getClshjg,"合格");
//                List<DcaBUserapply> bReportList = this.iDcaBUserapplyService.list(queryWrapper);
//                if (bReportList.size() > 0) {
//                    throw new FebsException("2021年已经申报，请勿须重复申报");
//                }

            }
            else{
                int count= this.iDcaBUserapplyService.countAccount(dcaBUserapply.getUserAccount(),dcaBUserapply.getGwdj(),dcaBUserapply.getDcaYear());
                if(count>0){
                    throw new FebsException("该年度你已经通过评审，请勿须重复申报");
                }
            }
            if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
                this.iDcaBUserapplyService.createDcaBUserapply(dcaBUserapply);
            }else {
                throw new FebsException("当前年度已经有申报记录");
            }

           if(StringUtils.isNotEmpty(yjIDs)) {
               String[] areaIds = yjIDs.split(StringPool.COMMA);
               setUserYj(currentUser, areaIds,dcaBUserapply.getDcaYear());
           }
//           if(StringUtils.isNotEmpty(deleFlag) && deleFlag.equals("1")){ //删除考核表
//               this.iDcaBAuditdynamicService.DeleteByAccount(currentUser.getUsername());
//           }
        }catch(Exception e){
        //message="新增/按钮失败" ;
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
 * 修改
 * @param dcaBUserapply
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBUserapply:update")
public void updateDcaBUserapply(@Valid DcaBUserapply dcaBUserapply,String yjIDs)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBUserapply.setModifyUserId(currentUser.getUserId());
            dcaBUserapply.setUserAccount(currentUser.getUsername());



            // 2021年高级 需要判断是否在2020年中初级里 此人是否已经申报，且 审核结果为  确定或者评聘
            if( !"中级,初级".contains(dcaBUserapply.getGwdj())) {
//                LambdaQueryWrapper<DcaBUserapply> queryWrapper = new LambdaQueryWrapper<>();
//                queryWrapper.eq(DcaBUserapply::getUserAccount, dcaBUserapply.getUserAccount());
//                queryWrapper.eq(DcaBUserapply::getDcaYear, "2021");
//                queryWrapper.eq(DcaBUserapply::getNpPositionName, dcaBUserapply.getNpPositionName());
//                // queryWrapper.eq(DcaBReport::getClshjg,"合格");
//                List<DcaBUserapply> bReportList = this.iDcaBUserapplyService.list(queryWrapper);
//                if (bReportList.size() > 0) {
//                    throw new FebsException("2021年已经申报，请勿须重复申报");
//                }
            }
            else{
                int count= this.iDcaBUserapplyService.countAccount(dcaBUserapply.getUserAccount(),dcaBUserapply.getGwdj(),dcaBUserapply.getDcaYear());
                if(count>0){
                    throw new FebsException("该年度你已经通过评审，请勿须重复申报");
                }
            }

            if(this.iDcaBUserapplyService.IsExistApply(dcaBUserapply)){
                this.iDcaBUserapplyService.updateDcaBUserapply(dcaBUserapply);}
            else {
                throw new FebsException("当前年度已经有申报记录");
            }

         //删除已经存在的
            this.iDcaUserYjService.deleteByuserid(currentUser.getUsername(),dcaBUserapply.getDcaYear());

            if(StringUtils.isNotEmpty(yjIDs)) {
                String[] areaIds = yjIDs.split(StringPool.COMMA);
                setUserYj(currentUser, areaIds,dcaBUserapply.getDcaYear());
            }
        }catch(Exception e){
       // message="修改失败" ;
        log.error(message,e);
        throw new FebsException(e.getMessage());
        }
        }

    @Log("修改")
    @PutMapping("state")
    public void updateDcaBUserapplyState(@Valid DcaBUserapply dcaBUserapply)throws FebsException{
        try{
            User currentUser= FebsUtil.getCurrentUser();
            dcaBUserapply.setModifyUserId(currentUser.getUserId());
           // dcaBUserapply.setUserAccount(currentUser.getUsername());
            this.iDcaBUserapplyService.updateDcaBUserapplyState(dcaBUserapply);

        }catch(Exception e){
            message="修改失败" ;
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBUserapply:delete")
public void deleteDcaBUserapplys(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBUserapplyService.deleteDcaBUserapplys(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
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
        message = "导出Excel失败";
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