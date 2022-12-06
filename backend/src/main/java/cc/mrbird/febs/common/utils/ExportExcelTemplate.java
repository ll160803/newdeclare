package cc.mrbird.febs.common.utils;

import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.entity.ExportAfferentCustomExcel;
import cn.hutool.core.io.IoUtil;
import cn.hutool.poi.excel.ExcelReader;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import cn.hutool.poi.excel.StyleSet;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.TypeReference;
import com.baomidou.mybatisplus.annotation.TableField;
import com.wuwenze.poi.annotation.ExcelField;
import lombok.extern.log4j.Log4j2;
import org.apache.poi.ss.usermodel.CellStyle;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

@Log4j2
public class ExportExcelTemplate {
    public static void exportCustomExcelCutome3(HttpServletResponse response, List<DcaBReport> list, String tempUrl) throws NoSuchFieldException, IllegalAccessException, IOException {
        List<ExportAfferentCustomExcel> exportList = new ArrayList<>();
        String dcayear = "";

        // 通过工具类创建writer
        InputStream inputStream = new FileInputStream(tempUrl);
        ExcelReader reader = ExcelUtil.getReader(inputStream, 0);

        dcayear = list.get(0).getYear();

        List<List<Object>> readAll = reader.read();
        if (readAll.size() > 0 && list.size()>0) { //大表使用 其他不需要 20210531
            if (!dcayear.equals("")) {
                String titleA = readAll.get(0).get(0).toString();
                titleA = titleA.replace("2022", dcayear);
                reader.getCell(0, 0).setCellValue(titleA);
            }
            List<String> doubleAccounts = new ArrayList<>();
         //  doubleAccounts.add("20000");
            Map<String, Long> doubleUser = list.stream().filter(p -> p.getNpPositionName().equals("教授") || p.getNpPositionName().equals("主任医师")).collect(Collectors.groupingBy(
                    DcaBReport::getUserAccount, Collectors.counting()
            ));
            doubleUser.forEach((k, v) -> {
                if (v > 1) {
                    if (doubleAccounts.indexOf(k) < 0) {
                        doubleAccounts.add(k);
                    }
                }
            });
            writeRow(reader, 3,"教授", doubleAccounts, list);//教授主任医师
            String[] arr = new String[]{"教授", "主任医师", "研究员"};
            int row = 4;
            for (String np : arr
            ) {
                writeRow_single(reader, row, np, doubleAccounts, list);
                row += 1;
            }
            String[] arr2 = new String[]{ "主任护师", "主任技师", "主任药师", "教授级高级工程师"};
             row = 7;
            for (String np : arr2
            ) {
                writeRow_single_huli(reader, row, np, doubleAccounts, list);
                row += 1;
            }
            //合计
            writeRow_gwdj(reader,11,"正高",list);

            // 副高
            List<String> doubleAccounts2 = new ArrayList<>();
          //  doubleAccounts2.add("100000");
            Map<String, Long> doubleUser2 = list.stream().filter(p -> p.getNpPositionName().equals("副教授") || p.getNpPositionName().equals("副主任医师")).collect(Collectors.groupingBy(
                    DcaBReport::getUserAccount, Collectors.counting()
            ));
            doubleUser2.forEach((k, v) -> {
                if (v > 1) {
                    if (doubleAccounts2.indexOf(k) < 0) {
                        doubleAccounts2.add(k);
                    }
                }
            });

            writeRow(reader, 12,"副教授", doubleAccounts2, list);//教授主任医师
            String[] arr3 = new String[]{"副教授", "副主任医师", "副研究员"};
             row = 13;
            for (String np : arr3
            ) {
                writeRow_single(reader, row, np, doubleAccounts2, list);
                row += 1;
            }
            String[] arr4 = new String[]{ "副主任护师", "副主任技师", "副主任药师", "高级工程师"};
            row = 16;
            for (String np : arr4
            ) {
                writeRow_single_huli(reader, row, np, doubleAccounts2, list);
                row += 1;
            }
            //合计
            writeRow_gwdj(reader,20,"副高",list);


            writeRow_All(reader,21,list);
        }


        ExcelWriter writer = reader.getWriter();


        //response为HttpServletResponse对象
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //response.setContentType("application/vnd.ms-excel;charset=utf-8");
        //test.xls是弹出下载对话框的文件名，不能为中文，中文请自行编码
        response.setHeader("Content-Disposition", "attachment;filename=test.xls");
        ServletOutputStream out = response.getOutputStream();

        writer.flush(out, true);
        // 关闭writer，释放内存
        writer.close();
        //此处记得关闭输出Servlet流
        IoUtil.close(out);
    }

    private static void writeRow(ExcelReader reader, int row, String np,  List<String> doubleAccounts, List<DcaBReport> list) {
        //申请人员总数
        int num = doubleAccounts.size();
        reader.getCell(1,row).setCellValue(num);
        //占指标申请评聘人数
        Long zzb_num = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 && p.getSblx().equals("顺升")).count();
        reader.getCell(2,row).setCellValue(zzb_num);
        //评审组分布
        Long psz_numA = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 && p.getSblx().equals("顺升") && p.getPingshenfenzu()!=null && p.getPingshenfenzu()!=""  &&p.getPingshenfenzu().equals("A")).count();
        Long psz_numB = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 && p.getSblx().equals("顺升") && p.getPingshenfenzu()!=null && p.getPingshenfenzu()!=""  &&!p.getPingshenfenzu().equals("A")).count();

        setRowValue(reader, row, 3, psz_numA, psz_numB);

        //援疆/援藏
        Long help_numA = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).count();
        Long help_numB = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).count();

        setRowValue(reader, row, 7, help_numA, help_numB);
        //单靠
        Long dk_numA = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("单靠"))).count();
        Long dk_numB = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("单靠"))).count();

        setRowValue(reader, row, 8, dk_numA, dk_numB);

        //抗疫单列
        Long ky_numA = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 && p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("抗疫单列"))).count();
        Long ky_numB = list.stream().filter(p -> p.getNpPositionName().equals(np) && doubleAccounts.indexOf(p.getUserAccount()) >= 0 && p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("抗疫单列"))).count();
        setRowValue(reader, row, 9, ky_numA, ky_numB);
    }

    private static void setRowValue(ExcelReader reader, int row, int span, Long A, Long B) {
        String ps = reader.getCell( span,row).getStringCellValue();
        reader.getCell(span,row).setCellValue(ps.replace("A", A.toString()).replace("B", B.toString()));
    }

    private static void writeRow_single(ExcelReader reader, int row, String npPostionName, List<String> doubleAccounts, List<DcaBReport> list) {
        //申请人员总数
        Long num = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 ).count();
        reader.getCell(1,row).setCellValue(num);
        //占指标申请评聘人数
        Long zzb_num = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 && p.getSblx().equals("顺升")).count();
        reader.getCell(2,row).setCellValue(zzb_num);
        //评审组分布
        Long psz_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 &&p.getSblx().equals("顺升")&& p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A")).count();
        Long psz_numB = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 &&p.getSblx().equals("顺升")&&  p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A")).count();

        setRowValue(reader, row, 3, psz_numA, psz_numB);

        //援疆/援藏
        Long help_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 &&  p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).count();
        Long help_numB = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 &&  p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).count();

        setRowValue(reader, row, 7, help_numA, help_numB);
        //单靠
        Long dk_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0   &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("单靠"))).count();
        Long dk_numB = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("单靠"))).count();

        setRowValue(reader, row, 8, dk_numA, dk_numB);

        //抗疫单列
        Long ky_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A") && (p.getSblx().equals("抗疫单列"))).count();
        Long ky_numB = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0  &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&!p.getPingshenfenzu().equals("A") && (p.getSblx().equals("抗疫单列"))).count();
        setRowValue(reader, row, 9, ky_numA, ky_numB);


    }

    private static void setRowValue_huli(ExcelReader reader,  int span,int row, Long A) {
        reader.getCell(row, span).setCellValue(A.toString());
    }
    private static void writeRow_single_huli(ExcelReader reader, int row, String npPostionName, List<String> doubleAccounts, List<DcaBReport> list) {
        //申请人员总数
        Long num = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 ).count();
        log.info("ppppppppp",row);
        reader.getCell(1,row).setCellValue(num);
        log.info("kkkkkkkkkkk",row);
        //占指标申请评聘人数
        Long zzb_num = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 && p.getSblx().equals("顺升")).count();
        reader.getCell(2,row).setCellValue(zzb_num);
        //评审组分布
      //  Long psz_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 ).count();


      //  setRowValue_huli(reader, row, 3, psz_numA);

        //援疆/援藏
        Long help_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).count();


        setRowValue_huli(reader, row, 7, help_numA);
        //单靠
        Long dk_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 && (p.getSblx().equals("单靠"))).count();


        setRowValue_huli(reader, row, 8, dk_numA);

        //抗疫单列
        Long ky_numA = list.stream().filter(p -> p.getNpPositionName().equals(npPostionName) && doubleAccounts.indexOf(p.getUserAccount()) < 0 && (p.getSblx().equals("抗疫单列"))).count();
        setRowValue_huli(reader, row, 9, ky_numA);
    }

    private static void writeRow_gwdj(ExcelReader reader, int row, String gwdj, List<DcaBReport> list) {
        //申请人员总数
        Long num = list.stream().filter(p -> p.getGwdj().equals(gwdj) ).map(p->p.getUserAccount()).distinct().count();
        reader.getCell(1,row).setCellValue(num);
        //占指标申请评聘人数
        Long zzb_num = list.stream().filter(p -> p.getGwdj().equals(gwdj) && p.getSblx().equals("顺升")).map(p->p.getUserAccount()).distinct().count();
        reader.getCell(2,row).setCellValue(zzb_num);
        //评审组分布
//        Long psz_numA = list.stream().filter(p -> p.getGwdj().equals(gwdj)&& p.getPingshenfenzu()!=null && p.getPingshenfenzu()!="" &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A")).count();
//
//        setRowValue_huli(reader, row, 3, psz_numA);

        //援疆/援藏
        Long help_numA = list.stream().filter(p -> p.getGwdj().equals(gwdj) && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).map(p->p.getUserAccount()).distinct().count();


        setRowValue_huli(reader, row, 7, help_numA);
        //单靠
        Long dk_numA = list.stream().filter(p -> p.getGwdj().equals(gwdj) && (p.getSblx().equals("单靠"))).map(p->p.getUserAccount()).distinct().count();


        setRowValue_huli(reader, row, 8, dk_numA);

        //抗疫单列
        Long ky_numA = list.stream().filter(p -> p.getGwdj().equals(gwdj) && (p.getSblx().equals("抗疫单列"))).map(p->p.getUserAccount()).distinct().count();
        setRowValue_huli(reader, row, 9, ky_numA);
    }


    private static void writeRow_All(ExcelReader reader, int row, List<DcaBReport> list) {
        //申请人员总数
        Long num =list.stream().map(p->p.getUserAccount()).distinct().count();
        reader.getCell(1,row).setCellValue(num);
        //占指标申请评聘人数
        Long zzb_num = list.stream().filter(p ->  p.getSblx().equals("顺升")).map(p->p.getUserAccount()).distinct().count();
        reader.getCell(2,row).setCellValue(zzb_num);
        //评审组分布
//        Long psz_numA = list.stream().filter(p -> p.getGwdj().equals(gwdj)&& p.getPingshenfenzu()!=null && p.getPingshenfenzu()!="" &&p.getPingshenfenzu()!=null &&p.getPingshenfenzu()!="" &&p.getPingshenfenzu().equals("A")).count();
//
//        setRowValue_huli(reader, row, 3, psz_numA);

        //援疆/援藏
        Long help_numA = list.stream().filter(p ->p.getSblx()!=null && (p.getSblx().equals("援疆") || p.getSblx().equals("援藏"))).map(p->p.getUserAccount()).distinct().count();


        setRowValue_huli(reader, row, 7, help_numA);
        //单靠
        Long dk_numA = list.stream().filter(p -> p.getSblx()!=null &&  (p.getSblx().equals("单靠"))).map(p->p.getUserAccount()).distinct().count();


        setRowValue_huli(reader, row, 8, dk_numA);

        //抗疫单列
        Long ky_numA = list.stream().filter(p -> p.getSblx()!=null &&  (p.getSblx().equals("抗疫单列"))).map(p->p.getUserAccount()).distinct().count();
        setRowValue_huli(reader, row, 9, ky_numA);
    }
}
