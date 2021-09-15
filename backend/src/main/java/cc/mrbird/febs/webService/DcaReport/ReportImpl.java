package cc.mrbird.febs.webService.DcaReport;

import cc.mrbird.febs.dca.entity.DcaBReport;
import cc.mrbird.febs.dca.service.IDcaBReportService;
import cc.mrbird.febs.out.entity.OutBInfo;
import cc.mrbird.febs.out.service.IOutBInfoService;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.jws.WebService;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Component
@Service
@WebService(name = "dca", targetNamespace = "urn:DcaReport.webService.febs.mrbird.cc",
        endpointInterface = "cc.mrbird.febs.webService.DcaReport.IReportService"// 接口地址
)
public class ReportImpl implements IReportService {

    @Autowired
   private IDcaBReportService iDcaBReportService;


    @Override
    public List<DcaBReport> GetReport() {
        /*
        LambdaQueryWrapper<DcaBReport> queryWrapper =new LambdaQueryWrapper<>();
        queryWrapper.eq(DcaBReport::getIsDeletemark,1);
        queryWrapper.eq(DcaBReport::getYear,"2020");
        queryWrapper.eq(DcaBReport::getState,2);
        return  this.iDcaBReportService.list(queryWrapper);*/
        List<DcaBReport>  resultReport =new ArrayList<>();
        List<DcaBReport>  list= this.iDcaBReportService.getReportTest();
        List<String> userAccountList =new ArrayList<>();
        for (DcaBReport report:list
             ) {
            if(userAccountList.contains(report.getUserAccount())){
                DcaBReport report1 =resultReport.stream().filter(p->p.getUserAccount().equals(report.getUserAccount())).findFirst().get();

                report1.setPublishA("申报"+report1.getNpPositionName()+" "+report1.getPublishA()+","+"申报"+report.getNpPositionName()+" "+report.getPublishA());
                report1.setPublishB("申报"+report1.getNpPositionName()+" "+report1.getPublishB()+","+"申报"+report.getNpPositionName()+" "+report.getPublishB());
                report1.setPublishC("申报"+report1.getNpPositionName()+" "+report1.getPublishC()+","+"申报"+report.getNpPositionName()+" "+report.getPublishC());
                report1.setPublishD("申报"+report1.getNpPositionName()+" "+report1.getPublishD()+","+"申报"+report.getNpPositionName()+" "+report.getPublishD());
                report1.setPublishE("申报"+report1.getNpPositionName()+" "+report1.getPublishE()+","+"申报"+report.getNpPositionName()+" "+report.getPublishE());
                report1.setPublishF("申报"+report1.getNpPositionName()+" "+report1.getPublishF()+","+"申报"+report.getNpPositionName()+" "+report.getPublishF());
                report1.setPublishDup("申报"+report1.getNpPositionName()+" "+report1.getPublishDup()+","+"申报"+report.getNpPositionName()+" "+report.getPublishDup());
                report1.setPublishEup("申报"+report1.getNpPositionName()+" "+report1.getPublishEup()+","+"申报"+report.getNpPositionName()+" "+report.getPublishEup());
                report1.setPublishFup("申报"+report1.getNpPositionName()+" "+report1.getPublishFup()+","+"申报"+report.getNpPositionName()+" "+report.getPublishFup());
                report1.setSciDjlb("申报"+report1.getNpPositionName()+":#"+report1.getSciDjlb()+"#"+"申报"+report.getNpPositionName()+":#"+report.getSciDjlb());
                report1.setSciDjfund("-#"+report1.getSciDjfund()+"#"+"-#"+report.getSciDjfund());
                report1.setSciDjranknum("-#"+report1.getSciDjranknum()+"#"+"-#"+report.getSciDjranknum());
                report1.setSciJflb("申报"+report1.getNpPositionName()+":#"+report1.getSciJflb()+"#"+"申报"+report.getNpPositionName()+":#"+report.getSciJflb());
                report1.setSciJffund("-#"+report1.getSciJffund()+"#"+"-#"+report.getSciJffund());
                report1.setSciJfranknum("-#"+report1.getSciJfranknum()+"#"+"-#"+report.getSciJfranknum());
                report1.setNpPositionName(report1.getNpPositionName()+report.getNpPositionName());
            }
            else {
                resultReport.add(report);
                userAccountList.add(report.getUserAccount());
            }

        }

        return  resultReport;
    }

}
