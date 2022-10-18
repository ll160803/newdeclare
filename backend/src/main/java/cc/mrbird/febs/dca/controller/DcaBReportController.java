package cc.mrbird.febs.dca.controller;

import cc.mrbird.febs.common.annotation.Log;
import cc.mrbird.febs.common.controller.BaseController;
import cc.mrbird.febs.common.domain.FebsResponse;
import cc.mrbird.febs.common.domain.router.VueRouter;
import cc.mrbird.febs.common.exception.FebsException;
import cc.mrbird.febs.common.domain.QueryRequest;

import cc.mrbird.febs.dca.entity.DcaBAcademic;
import cc.mrbird.febs.dca.entity.DcaBReportImport;
import cc.mrbird.febs.dca.entity.DcaBReportImportZj;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.dca.entity.DcaBReport;

import cc.mrbird.febs.common.utils.FebsUtil;
import cc.mrbird.febs.system.domain.User;
import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.convert.Convert;
import cn.hutool.core.map.MapUtil;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringPool;
import com.beust.jcommander.internal.Lists;
import com.wuwenze.poi.ExcelKit;
import com.wuwenze.poi.handler.ExcelReadHandler;
import com.wuwenze.poi.pojo.ExcelErrorField;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author viki
 * @since 2020-11-13
 */
@Slf4j
@Validated
@RestController
@RequestMapping("dcaBReport")

public class DcaBReportController extends BaseController {

    private String message;
    @Autowired
    public IDcaBReportService iDcaBReportService;


/**
 INSERT into t_menu(parent_id,menu_name,path,component,perms,icon,type,order_num,CREATE_time)
 VALUES (0,'','/dca/DcaBReport/DcaBReport','dca/DcaBReport/DcaBReport','dcaBReport:view','fork',0,1,NOW())
 SELECT MAX(MENU_ID) from t_menu;
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'新增','dcaBReport:add',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'编辑','dcaBReport:update',1,1,NOW())
 INSERT into t_menu(parent_id,MENU_NAME,perms,type,order_num,CREATE_time) VALUES(0,'删除','dcaBReport:delete',1,1,NOW())
 */


    /**
     * 分页查询数据
     *
     * @param request    分页信息
     * @param dcaBReport 查询条件
     * @return
     */
    @GetMapping
    public Map<String, Object> List(QueryRequest request, DcaBReport dcaBReport) {
        return getDataTable(this.iDcaBReportService.findDcaBReports(request, dcaBReport));
    }

    /**
     * 添加
     *
     * @param dcaBReport
     * @return
     */
    @Log("新增/按钮")
    @PostMapping
    public FebsResponse addDcaBReport(@Valid DcaBReport dcaBReport) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBReport.setCreateUserId(currentUser.getUserId());
            if (!StringUtils.isNotBlank(dcaBReport.getIfshuangbao())) {
                dcaBReport.setIfshuangbao("否");
            }
            if (!StringUtils.isNotBlank(dcaBReport.getIffuhediyi())) {
                dcaBReport.setIffuhediyi("否");
            }
            if (!StringUtils.isNotBlank(dcaBReport.getIffuhekeyan())) {
                dcaBReport.setIffuhekeyan("否");
            }
            if (!StringUtils.isNotBlank(dcaBReport.getIfdaitou())) {
                dcaBReport.setIfdaitou("否");
            }

            if (StringUtils.isNotBlank(dcaBReport.getId()) && !dcaBReport.getId().contains(dcaBReport.getUserAccount())) {
                this.iDcaBReportService.updateDcaBReport(dcaBReport);
            } else {
                this.iDcaBReportService.createDcaBReport(dcaBReport);
            }
            //退回事  修改双报标志
//            if(dcaBReport.getGwdj().equals("正高") || dcaBReport.getGwdj().equals("副高")){
//                this.iDcaBReportService.updateShuangBaoDcaBReport(dcaBReport);
//            }
            return new FebsResponse().data(dcaBReport.getId());
        } catch (Exception e) {
            message = "新增或提交失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @Log("批量增加")
    @PostMapping("saveData")
    public FebsResponse addBulkDcaBReport(@Valid String data, int state) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            List<DcaBReport> list = JSON.parseObject(data, new TypeReference<List<DcaBReport>>() {
            });
            for (DcaBReport dcaBReport : list
            ) {
                dcaBReport.setCreateUserId(currentUser.getUserId());
                dcaBReport.setState(state);
                if (!StringUtils.isNotBlank(dcaBReport.getIfshuangbao())) {
                    dcaBReport.setIfshuangbao("否");
                }
                if (!StringUtils.isNotBlank(dcaBReport.getIffuhediyi())) {
                    dcaBReport.setIffuhediyi("否");
                }
                if (!StringUtils.isNotBlank(dcaBReport.getIffuhekeyan())) {
                    dcaBReport.setIffuhekeyan("否");
                }
                if (!StringUtils.isNotBlank(dcaBReport.getIfdaitou())) {
                    dcaBReport.setIfdaitou("否");
                }

                if (StringUtils.isNotBlank(dcaBReport.getId()) && !dcaBReport.getId().equals(dcaBReport.getUserAccount())) {
                    this.iDcaBReportService.updateDcaBReport(dcaBReport);
                } else {
                    this.iDcaBReportService.createDcaBReport(dcaBReport);
                }
                // 是否双报
//                if (dcaBReport.getGwdj().equals("正高") || dcaBReport.getGwdj().equals("副高")) {
//                    this.iDcaBReportService.updateShuangBaoDcaBReport(dcaBReport);
//                }
            }
            return new FebsResponse().data("提交成功");
        } catch (Exception e) {
            message = "新增或提交失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    /**
     * 修改
     *
     * @param dcaBReport
     * @return
     */
    @Log("修改")
    @PutMapping
    public void updateDcaBReport(@Valid DcaBReport dcaBReport) throws FebsException {
        try {
            User currentUser = FebsUtil.getCurrentUser();
            dcaBReport.setModifyUserId(currentUser.getUserId());
            this.iDcaBReportService.updateDcaBReport(dcaBReport);

        } catch (Exception e) {
            message = "修改失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }


    @Log("批量推送数据")
    @DeleteMapping("/{ids}")
    public void deleteDcaBReports(@NotBlank(message = "{required}") @PathVariable String ids, String dataInfo) throws FebsException {
        try {
            String[] arr_ids = ids.split(StringPool.COMMA);
            this.iDcaBReportService.deleteDcaBReports(arr_ids);
        } catch (Exception e) {
            message = "删除失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @PostMapping("excel")
    @RequiresPermissions("dcaBReport:export")
    public void export(QueryRequest request, DcaBReport dcaBReport, HttpServletResponse response) throws FebsException {
        try {
            List<DcaBReport> dcaBReports = this.iDcaBReportService.findDcaBReports(request, dcaBReport).getRecords();
            ExcelKit.$Export(DcaBReport.class, response).downXlsx(dcaBReports, false);
        } catch (Exception e) {
            message = "导出Excel失败";
            log.error(message, e);
            throw new FebsException(message);
        }
    }

    @GetMapping("/{id}")
    public DcaBReport detail(@NotBlank(message = "{required}") @PathVariable String id) {
        DcaBReport dcaBReport = this.iDcaBReportService.getById(id);
        return dcaBReport;
    }

    @PostMapping("/user")
    public List<DcaBReport> detail2(String ids) {
        String[] arr = ids.split(",");
        //  String[] asblx= "评聘,确定".split(","); //中初级
        LambdaQueryWrapper<DcaBReport> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark, 1);//1是未删 0是已删
        queryWrapper.eq(DcaBReport::getClshjg, "合格"); //正高,二三级
        //  queryWrapper.in(DcaBReport::getSblx,asblx); //中初级
        queryWrapper.in(DcaBReport::getYear, "2020");
        queryWrapper.in(DcaBReport::getUserAccount, arr);
        List<DcaBReport> dcaBReports2 = this.iDcaBReportService.list(queryWrapper);
        List<DcaBReport> dcaBReports = new ArrayList<>();
        for (String id : arr) {
            List<DcaBReport> d = dcaBReports2.stream().filter(p -> p.getUserAccount().equals(id)).sorted(Comparator.comparing(DcaBReport::getNpPositionName).reversed()).collect(Collectors.toList());
            dcaBReports.addAll(d);
        }
        // dcaBReports= dcaBReports.stream().sorted(Comparator.comparing(DcaBReport::getNpPositionName).collect(Collectors.toList());
        return dcaBReports;
    }

    @RequestMapping(value = "downTemplateZj", method = RequestMethod.POST)
    public void downTemplate(HttpServletResponse response, QueryRequest request, DcaBReport dcaBReport, int activeKey,String dcaYear) {
        List<DcaBReportImport> publishList = new ArrayList<>();

        request.setPageNum(1);
        request.setPageSize(20000);
        LambdaQueryWrapper<DcaBReport> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dcaBReport.getKs())) {
            queryWrapper.in(DcaBReport::getGwdj, dcaBReport.getKs().split(","));
        }
        if (StringUtils.isNotBlank(dcaBReport.getUserAccount())) {
            queryWrapper.and(wrap -> wrap.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount()).or().like(DcaBReport::getUserAccountName, dcaBReport.getUserAccount()));
        }
        if (StringUtils.isNotBlank(dcaYear)) {
            queryWrapper.eq(DcaBReport::getYear, dcaYear);
        }
        queryWrapper.eq(DcaBReport::getState,0);
        List<DcaBReport> bReportList = this.iDcaBReportService.list(queryWrapper);



            List<DcaBReportImportZj> dcaBReportImportList = new ArrayList<>();
            for (DcaBReport report : bReportList) {
                DcaBReportImportZj bReportImport = new DcaBReportImportZj();
                BeanUtil.copyProperties(report, bReportImport, CopyOptions.create().setIgnoreNullValue(true));
                dcaBReportImportList.add(bReportImport);
            }

            ExcelKit.$Export(DcaBReportImportZj.class, response).downXlsx(dcaBReportImportList, false);


    }

    @RequestMapping(value = "importZj", method = RequestMethod.POST)
    public ResponseEntity<?> importUser(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DcaBReportImportZj> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(DcaBReportImportZj.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<DcaBReportImportZj>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, DcaBReportImportZj entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {

            for (DcaBReportImportZj dcaBReportImport : successList
            ) {
                LambdaQueryWrapper<DcaBReport> queryWrapperD = new LambdaQueryWrapper<>();
                queryWrapperD.eq(DcaBReport::getUserAccount, dcaBReportImport.getUserAccount());
                queryWrapperD.eq(DcaBReport::getYear, dcaBReportImport.getYear());
                queryWrapperD.eq(DcaBReport::getGwdj, dcaBReportImport.getGwdj());
                queryWrapperD.eq(DcaBReport::getNpPositionName, dcaBReportImport.getNpPositionName());

                DcaBReport dcaBReport = new DcaBReport();
                BeanUtil.copyProperties(dcaBReportImport, dcaBReport, CopyOptions.create().setIgnoreNullValue(true));

                this.iDcaBReportService.update(dcaBReport, queryWrapperD);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }

    @RequestMapping(value = "downTemplate", method = RequestMethod.POST)
    public void downTemplate2(HttpServletResponse response, QueryRequest request, DcaBReport dcaBReport, int activeKey,String dcaYear) {
        List<DcaBReportImport> publishList = new ArrayList<>();

        request.setPageNum(1);
        request.setPageSize(20000);
        LambdaQueryWrapper<DcaBReport> queryWrapper = new LambdaQueryWrapper<>();
        if (StringUtils.isNotBlank(dcaBReport.getKs())) {
            queryWrapper.in(DcaBReport::getGwdj, dcaBReport.getKs().split(","));
        }
        if (StringUtils.isNotBlank(dcaBReport.getUserAccount())) {
            queryWrapper.and(wrap -> wrap.eq(DcaBReport::getUserAccount, dcaBReport.getUserAccount()).or().like(DcaBReport::getUserAccountName, dcaBReport.getUserAccount()));
        }
        if (StringUtils.isNotBlank(dcaYear)) {
            queryWrapper.eq(DcaBReport::getYear, dcaYear);
        }
        queryWrapper.eq(DcaBReport::getState,0);
        List<DcaBReport> bReportList = this.iDcaBReportService.list(queryWrapper);



        List<DcaBReportImport> dcaBReportImportList = new ArrayList<>();
        for (DcaBReport report : bReportList) {
            DcaBReportImport bReportImport = new DcaBReportImport();
            BeanUtil.copyProperties(report, bReportImport, CopyOptions.create().setIgnoreNullValue(true));
            dcaBReportImportList.add(bReportImport);
        }

        ExcelKit.$Export(DcaBReportImport.class, response).downXlsx(dcaBReportImportList, false);


    }

    @RequestMapping(value = "import", method = RequestMethod.POST)
    public ResponseEntity<?> importUser2(@RequestParam MultipartFile file)
            throws IOException {
        long beginMillis = System.currentTimeMillis();

        List<DcaBReportImport> successList = Lists.newArrayList();
        List<Map<String, Object>> errorList = Lists.newArrayList();
        List<Map<String, Object>> resultList = Lists.newArrayList();

        User currentUser = FebsUtil.getCurrentUser();


        ExcelKit.$Import(DcaBReportImport.class)
                .readXlsx(file.getInputStream(), new ExcelReadHandler<DcaBReportImport>() {

                    @Override
                    public void onSuccess(int sheetIndex, int rowIndex, DcaBReportImport entity) {
                        successList.add(entity); // 单行读取成功，加入入库队列。
                    }

                    @Override
                    public void onError(int sheetIndex, int rowIndex,
                                        java.util.List<ExcelErrorField> errorFields) {
                        // 读取数据失败，记录了当前行所有失败的数据
                        errorList.add(MapUtil.of("sheetIndex", sheetIndex));
                        errorList.add(MapUtil.of("rowIndex", rowIndex));
                        errorList.add(MapUtil.of("errorFields", errorFields));
                    }
                });

        // TODO: 执行successList的入库操作。
        if (CollectionUtil.isEmpty(errorList)) {

            for (DcaBReportImport dcaBReportImport : successList
            ) {
                LambdaQueryWrapper<DcaBReport> queryWrapperD = new LambdaQueryWrapper<>();
                queryWrapperD.eq(DcaBReport::getUserAccount, dcaBReportImport.getUserAccount());
                queryWrapperD.eq(DcaBReport::getYear, dcaBReportImport.getYear());
                queryWrapperD.eq(DcaBReport::getGwdj, dcaBReportImport.getGwdj());
                queryWrapperD.eq(DcaBReport::getNpPositionName, dcaBReportImport.getNpPositionName());

                DcaBReport dcaBReport = new DcaBReport();
                BeanUtil.copyProperties(dcaBReportImport, dcaBReport, CopyOptions.create().setIgnoreNullValue(true));

                this.iDcaBReportService.update(dcaBReport, queryWrapperD);
            }
        }

        resultList.add(MapUtil.of("data", successList));
        resultList.add(MapUtil.of("haveError", !CollectionUtil.isEmpty(errorList)));
        resultList.add(MapUtil.of("error", errorList));
        resultList.add(MapUtil.of("timeConsuming", (System.currentTimeMillis() - beginMillis) / 1000L));
        return ResponseEntity.ok(resultList);
    }
}