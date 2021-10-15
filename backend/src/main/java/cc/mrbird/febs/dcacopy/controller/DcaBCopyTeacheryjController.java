package cc.mrbird.febs.dcacopy.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dcacopy.service.IDcaBCopyTeacheryjService;
import cc.mrbird.febs.dcacopy.entity.DcaBCopyTeacheryj;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.wuwenze.poi.ExcelKit;
import lombok.extern.slf4j.Slf4j;
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

/**
 *
 * @author viki
 * @since 2021-09-30
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBCopyTeacheryj")

public class DcaBCopyTeacheryjController extends BaseController{

private String message;
@Autowired
public IDcaBCopyTeacheryjService iDcaBCopyTeacheryjService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'主要教学业绩','/dca/DcaBCopyTeacheryj/DcaBCopyTeacheryj','dca/DcaBCopyTeacheryj/DcaBCopyTeacheryj','dcaBCopyTeacheryj:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要教学业绩新增','dcaBCopyTeacheryj:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要教学业绩编辑','dcaBCopyTeacheryj:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'主要教学业绩删除','dcaBCopyTeacheryj:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBCopyTeacheryj 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBCopyTeacheryj:view")
public Map<String, Object> List(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj){
        return getDataTable(this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj));
        }

/**
 * 添加
 * @param  dcaBCopyTeacheryj
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBCopyTeacheryj:add")
public void addDcaBCopyTeacheryj(@Valid DcaBCopyTeacheryj dcaBCopyTeacheryj)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBCopyTeacheryj.setCreateUserId(currentUser.getUserId());
        this.iDcaBCopyTeacheryjService.createDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBCopyTeacheryj
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBCopyTeacheryj:update")
public void updateDcaBCopyTeacheryj(@Valid DcaBCopyTeacheryj dcaBCopyTeacheryj)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBCopyTeacheryj.setModifyUserId(currentUser.getUserId());
        this.iDcaBCopyTeacheryjService.updateDcaBCopyTeacheryj(dcaBCopyTeacheryj);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBCopyTeacheryj:delete")
public void deleteDcaBCopyTeacheryjs(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBCopyTeacheryjService.deleteDcaBCopyTeacheryjs(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
@PostMapping("excel")
@RequiresPermissions("dcaBCopyTeacheryj:export")
public void export(QueryRequest request, DcaBCopyTeacheryj dcaBCopyTeacheryj, HttpServletResponse response) throws FebsException {
        try {
        List<DcaBCopyTeacheryj> dcaBCopyTeacheryjs = this.iDcaBCopyTeacheryjService.findDcaBCopyTeacheryjs(request, dcaBCopyTeacheryj).getRecords();
        ExcelKit.$Export(DcaBCopyTeacheryj.class, response).downXlsx(dcaBCopyTeacheryjs, false);
        } catch (Exception e) {
        message = "导出Excel失败";
        log.error(message, e);
        throw new FebsException(message);
        }
        }

@GetMapping("/{id}")
public DcaBCopyTeacheryj detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBCopyTeacheryj dcaBCopyTeacheryj=this.iDcaBCopyTeacheryjService.getById(id);
        return dcaBCopyTeacheryj;
        }
        }