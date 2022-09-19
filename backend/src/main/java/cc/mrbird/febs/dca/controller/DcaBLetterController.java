package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.common.utils.ExportExcelUtils;
import cc.mrbird.febs.dca.service.IDcaBLetterService;
import cc.mrbird.febs.dca.entity.DcaBLetter;

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
 * @since 2022-07-26
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBLetter")

public class DcaBLetterController extends BaseController{

private String message;
@Autowired
public IDcaBLetterService iDcaBLetterService;

/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'通讯评审','/dca/DcaBLetter/DcaBLetter','dca/DcaBLetter/DcaBLetter','dcaBLetter:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'通讯评审新增','dcaBLetter:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'通讯评审编辑','dcaBLetter:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'通讯评审删除','dcaBLetter:delete',1,1,NOW())
*/


/**
 * 分页查询数据
 *
 * @param  request 分页信息
 * @param dcaBLetter 查询条件
 * @return
 */
@GetMapping
@RequiresPermissions("dcaBLetter:view")
public Map<String, Object> List(QueryRequest request, DcaBLetter dcaBLetter){
        return getDataTable(this.iDcaBLetterService.findDcaBLetters(request, dcaBLetter));
        }

    @GetMapping("custom")
    public Map<String, Object> ListCustom(QueryRequest request, DcaBLetter dcaBWorknum){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBWorknum.setUserAccount(currentUser.getUsername());
        dcaBWorknum.setIsDeletemark(1);
        request.setPageSize(1000);
        request.setSortField("year");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBLetterService.findDcaBLetters(request, dcaBWorknum));
    }
    @GetMapping("audit")
    public Map<String, Object> List2(QueryRequest request, DcaBLetter dcaBWorknum){
        User currentUser= FebsUtil.getCurrentUser();
        dcaBWorknum.setIsDeletemark(1);
        request.setSortField("user_account asc,state ");
        request.setSortOrder("ascend");
        return getDataTable(this.iDcaBLetterService.findDcaBLetters(request, dcaBWorknum));
    }
/**
 * 添加
 * @param  dcaBLetter
 * @return
 */
@Log("新增/按钮")
@PostMapping
@RequiresPermissions("dcaBLetter:add")
public void addDcaBLetter(@Valid DcaBLetter dcaBLetter)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
        dcaBLetter.setCreateUserId(currentUser.getUserId());
        this.iDcaBLetterService.createDcaBLetter(dcaBLetter);
        }catch(Exception e){
        message="新增/按钮失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }

/**
 * 修改
 * @param dcaBLetter
 * @return
 */
@Log("修改")
@PutMapping
@RequiresPermissions("dcaBLetter:update")
public void updateDcaBLetter(@Valid DcaBLetter dcaBLetter)throws FebsException{
        try{
        User currentUser= FebsUtil.getCurrentUser();
      dcaBLetter.setModifyUserId(currentUser.getUserId());
        this.iDcaBLetterService.updateDcaBLetter(dcaBLetter);
        }catch(Exception e){
        message="修改失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }


@Log("删除")
@DeleteMapping("/{ids}")
@RequiresPermissions("dcaBLetter:delete")
public void deleteDcaBLetters(@NotBlank(message = "{required}") @PathVariable String ids)throws FebsException{
        try{
        String[]arr_ids=ids.split(StringPool.COMMA);
        this.iDcaBLetterService.deleteDcaBLetters(arr_ids);
        }catch(Exception e){
        message="删除失败" ;
        log.error(message,e);
        throw new FebsException(message);
        }
        }
    @PostMapping("excel")
    public void export(QueryRequest request, DcaBLetter dcaBSciencesearch,String dataJson,HttpServletResponse response)throws FebsException{
        try{
            request.setPageNum(1);
            request.setPageSize(10000);
            User currentUser = FebsUtil.getCurrentUser();

            dcaBSciencesearch.setIsDeletemark(1);
            request.setSortField("user_account asc,state ");
            request.setSortOrder("ascend");
            List<DcaBLetter> dcaBSciencepublishList=  this.iDcaBLetterService.findDcaBLetters(request, dcaBSciencesearch).getRecords();


            //ExcelKit.$Export(DcaBAuditdynamic.class,response).downXlsx(dcaBAuditdynamics,false);
            ExportExcelUtils.exportCustomExcel_han(response, dcaBSciencepublishList,dataJson,"");
        }catch(Exception e){
            message="导出Excel失败";
            log.error(message,e);
            throw new FebsException(message);
        }
    }
@GetMapping("/{id}")
public DcaBLetter detail(@NotBlank(message = "{required}") @PathVariable String id) {
    DcaBLetter dcaBLetter=this.iDcaBLetterService.getById(id);
        return dcaBLetter;
        }
        }