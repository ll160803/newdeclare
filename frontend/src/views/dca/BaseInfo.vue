<template>
  <a-card
    :bordered="false"
    class="card-area mycard-area "
  >
    <template v-if="!showAdd">
      <div style="text-align:center;font-size:20px;">
        <p style="font-size:20px!important;font-weight:bold;">个人承诺</p>
        <p style="font-size:20px!important;font-weight:bold;">本人慎重承诺所从事的学术研究符合学术道德规范，所填写内容真实准确，如有不实之处，本人愿意承担相关责任。</p>
       <p style="font-size:22px!important;font-weight:bold;color:red;">本系统上传所有PDF文件不能加密!!!</p>
        <a-button
          @click="handlePromise"
          type="primary"
        >本人确认</a-button>
      </div>
    </template>
    <template v-if="showAdd">
      <a-layout id="custom-trigger">
        <a-layout-sider
          width="280"
          style="background: #fff;"
          v-model="collapsed"
          :trigger="null"
          collapsible
        >
          <a-card
            :bordered="true"
            class="card-area mycard-area myBaseInfo"
            title="请点击左侧小三角展开或关闭"
          >
           <a-icon
           slot="extra"
                  class="trigger"
                  :type="collapsed ? 'menu-unfold' : 'menu-fold'"
                  @click="() => (collapsed = !collapsed)"
                />
          
            <div :style="calcHeight">
              <a-tree
                :key="mouduleTreeKey"
                ref="menuTree"
                :treeData="mouduleTreeData"
                @select="handleTreeClick"
              >
              <template slot="custom" slot-scope="item">
                <span style="color:red">{{item.title}}</span>
              </template>
              </a-tree>
            </div>
          </a-card>
        </a-layout-sider>
        <a-layout>
          <a-layout-content>
            <div style="position: relative">
              <dcaB-user v-if="index==6">
                <!--基本资料-->
              </dcaB-user>
              <dcaBParttime-customer v-if="index==15">
                <!--社会兼职-->
              </dcaBParttime-customer>
              <dcaB-employ v-if="index==60">
                <!--任现职以来完成教学、人才培养情况-->
              </dcaB-employ>
              <!--  <dcaB-teachtalent v-if="index==6">
              教师 任现职以来完成教学、人才培养情况  和emply重复了
            </dcaB-teachtalent>-->
              <dcaB-educationexperice v-if="index==14">
                <!--学习工作经历-->
              </dcaB-educationexperice>
              <!--<dcaB-essaypublish v-if="index==7">
              //论文出版
            </dcaB-essaypublish>-->
              <dcaB-graduate v-if="index==210">
                <!--研究生情况-->
              </dcaB-graduate>
              <dcaB-otherwork v-if="index==240">
                <!--其他工作及成果-->
              </dcaB-otherwork>
              <!-- <dcaB-paperspublish v-if="index==22">
             教学论文出版教材
            </dcaB-paperspublish>-->
              <dcaB-patent v-if="index==100">
                <!--申请专利-->
              </dcaB-patent>
              <dcaB-prizeorpunish v-if="index==20">
                <!--何时何地奖励或处分-->
              </dcaB-prizeorpunish>
              <dcaB-sciencepublish v-if="index==70">
                <!--科研论文-->
              </dcaB-sciencepublish>
              <dcaB-scientificprize v-if="index==90">
                <!--自任职以来科研获奖情况-->
              </dcaB-scientificprize>
              <dcaB-talent v-if="index==200">
                <!--任现职以来完成研究生教学人才培养情况-->
              </dcaB-talent>
              <dcaB-teacherqualify v-if="index==130">
                <!--教师资格-->
              </dcaB-teacherqualify>
              <dcaB-turtor v-if="index==140">
                <!--担任辅导员-->
              </dcaB-turtor>
              <dcaB-undergraduate v-if="index==170">
                <!--本科教学情况-->
              </dcaB-undergraduate>
              <dcaB-applyjob v-if="index==160">
                <!--拟聘岗位-->
              </dcaB-applyjob>
               <dcaB-auditfive v-if="index==270">
               <!--近五年考核情况-->
              </dcaB-auditfive>
              <dcaB-lastemploy v-if="index==150">
                <!--完成上一聘期-->
              </dcaB-lastemploy>
              <dcaB-personalsummary v-if="index==120">
                <!--个人总结-->
              </dcaB-personalsummary>
              <dcaB-politalshow v-if="index==50">
                <!--个人思想政治表现-->
              </dcaB-politalshow>
              <dcaB-innovatebuild v-if="index==180">
                <!--改革及建设项目-->
              </dcaB-innovatebuild>
              <dcaB-sciencesearch v-if="index==80">
                <!--科研项目-->
              </dcaB-sciencesearch>
              <!--<dcaB-fivecomment v-if="index==17">
                近五年总体情况评价
              </dcaB-fivecomment>-->
              <dcaB-goal v-if="index==250">
                <!--拟聘岗位工作思路及预期目标-->
              </dcaB-goal>

              <dcaB-undergraduateprize v-if="index==190">
                <!--任现职以来本科教学工作获奖情况-->
              </dcaB-undergraduateprize>
              <dcaB-attachfile v-if="index==400">
                <!--其他材料附件-->
              </dcaB-attachfile>
              <dcaB-worknum v-if="index==110">
                 <!--门诊工作量-->
              </dcaB-worknum>
              <dcaB-exportcountry v-if="index==40">
                <!--出国情况-->
              </dcaB-exportcountry>
               <dcaB-publicarticle v-if="index==280">
                <!--著作-->
              </dcaB-publicarticle>
             
               <dcaB-courseclass v-if="index==310">
                <!--精品课程情况-->
              </dcaB-courseclass>
                <dcaB-schoolprize v-if="index==300">
                <!--校教学质量奖、校教学成果奖-->
              </dcaB-schoolprize>
                <dcaB-teacherprize v-if="index==290">
                <!--省部级及以上教学获奖-->
              </dcaB-teacherprize>
              <dcaB-youngprize v-if="index==320">
                <!--教师教学竞赛获奖-->
              </dcaB-youngprize>
                <dcaB-doctorturtor v-if="index==212">
                <!--博导时间-->
              </dcaB-doctorturtor>
               <dcaB-medicalaccident v-if="index==340">
                <!--医疗事故评分-->
              </dcaB-medicalaccident>
               <dcaB-academic v-if="index==380">
                <!--学术-->
               </dcaB-academic>
               <dcaB-achievement v-if="index==360">
                <!--医疗业绩-->
              </dcaB-achievement>
               <dcaB-qualification v-if="index==370">
                <!--资质情况-->
              </dcaB-qualification>
               <dcaB-auditDynamic v-if="index==401">
                <!--部门审核结果-->
              </dcaB-auditDynamic> 
              <dcaB-teacheryj v-if="index==330">
                <!--主要教学业绩-->
              </dcaB-teacheryj> 
              <dcaB-sciachievement v-if="index==332">
                <!--主要科研业绩-->
              </dcaB-sciachievement> 
              <dcaB-sureachievement v-if="index==500">
                <!--主要医疗认可-->
              </dcaB-sureachievement> 
            </div>
          </a-layout-content>
        </a-layout>
      </a-layout>
    </template>
  </a-card>

</template>
<script>
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18, offset: 1 },
};
import DcaBParttimeCustomer from "../dca/DcaBParttimejob/DcaBParttimeCustomer";
import DcaBEmploy from "../dca/DcaBEmploy/DcaBEmploy";
import DcaBEducationexperice from "../dca/DcaBEducationexperice/DcaBEducationexperice";
import DcaBEssaypublish from "../dca/DcaBEssaypublish/DcaBEssaypublish";
import DcaBGraduate from "../dca/DcaBGraduate/DcaBGraduate";
import DcaBInnovatebuild from "../dca/DcaBInnovatebuild/DcaBInnovatebuild";
import DcaBOtherwork from "../dca/DcaBOtherwork/DcaBOtherwork";
import DcaBPaperspublish from "../dca/DcaBPaperspublish/DcaBPaperspublish";
import DcaBPatent from "../dca/DcaBPatent/DcaBPatent";
import DcaBPrizeorpunish from "../dca/DcaBPrizeorpunish/DcaBPrizeorpunish";
import DcaBSciencepublish from "../dca/DcaBSciencepublish/DcaBSciencepublish";
import DcaBScientificprize from "../dca/DcaBScientificprize/DcaBScientificprize";
import DcaBTalent from "../dca/DcaBTalent/DcaBTalent";
import DcaBTeacherqualify from "../dca/DcaBTeacherqualify/DcaBTeacherqualify";
import DcaBTeachtalent from "../dca/DcaBTeachtalent/DcaBTeachtalent";
import DcaBTurtor from "../dca/DcaBTurtor/DcaBTurtor";
import DcaBUndergraduate from "../dca/DcaBUndergraduate/DcaBUndergraduate";
import DcaBApplyjob from "../dca/DcaBApplyjob/DcaBApplyjob";
import DcaBAuditfive from "../dca/DcaBAuditfive/DcaBAuditfive";
import DcaBLastemploy from "../dca/DcaBLastemploy/DcaBLastemploy";
import DcaBPersonalsummary from "../dca/DcaBPersonalsummary/DcaBPersonalsummary";
import DcaBPolitalshow from "../dca/DcaBPolitalshow/DcaBPolitalshow";
import DcaBSciencesearch from "../dca/DcaBSciencesearch/DcaBSciencesearch";
import DcaBFivecomment from "../dca/DcaBFivecomment/DcaBFivecomment";
import DcaBGoal from "../dca/DcaBGoal/DcaBGoal";
import DcaBUndergraduateprize from "../dca/DcaBUndergraduateprize/DcaBUndergraduateprize";
import DcaBUser from "../dca/DcaBUser/DcaBUser";
import DcaBAttachfile from "../dca/DcaBAttachfile/DcaBAttachfile";
import DcaBWorknum from "../dca/DcaBWorknum/DcaBWorknum";
import DcaBExportcountry from "../dca/DcaBExportcountry/DcaBExportcountry";
import DcaBPublicarticle from "../dca/DcaBPublicarticle/DcaBPublicarticle";
import DcaBCourseclass from "../dca/DcaBCourseclass/DcaBCourseclass";
import DcaBSchoolprize from "../dca/DcaBSchoolprize/DcaBSchoolprize";
import DcaBTeacherprize from "../dca/DcaBTeacherprize/DcaBTeacherprize";
import DcaBYoungprize from "../dca/DcaBYoungprize/DcaBYoungprize";
import DcaBDoctorturtor from "../dca/DcaBDoctorturtor/DcaBDoctorturtor";
import DcaBMedicalaccident from "../dca/DcaBMedicalaccident/DcaBMedicalaccident";
import DcaBAcademic from "../dca/DcaBAcademic/DcaBAcademic";
import DcaBAchievement from "../dca/DcaBAchievement/DcaBAchievement";
import DcaBQualification from "../dca/DcaBQualification/DcaBQualification";
import DcaBAuditDynamic from "../dca/DcaBUserapply/DcaBAuditDynamic";
import DcaBSciachievement from "../dca/DcaBSciachievement/DcaBSciachievement";
import DcaBTeacheryj from "../dca/DcaBTeacheryj/DcaBTeacheryj";
import DcaBSureachievement from "../dca/DcaBSureachievement/DcaBSureachievement";

export default {
  name: "DcaBPatent2",
  components: {
    DcaBParttimeCustomer,
    DcaBEmploy,
    DcaBEducationexperice,
    DcaBEssaypublish,
    DcaBGraduate,
    DcaBPublicarticle,
    DcaBCourseclass,
    DcaBSchoolprize,
    DcaBTeacherprize,
    DcaBYoungprize,
    DcaBMedicalaccident,
    DcaBDoctorturtor,
    DcaBInnovatebuild,
    DcaBOtherwork,
    DcaBPaperspublish,
    DcaBPatent,
    DcaBPrizeorpunish,
    DcaBSciencepublish,
    DcaBScientificprize,
    DcaBTalent,
    DcaBTeacherqualify,
    DcaBTurtor,
    DcaBUndergraduate,
    DcaBApplyjob,
    DcaBAuditfive,
    DcaBLastemploy,
    DcaBPersonalsummary,
    DcaBPolitalshow,
    DcaBSciencesearch,
    DcaBFivecomment,
    DcaBGoal,
    DcaBTeachtalent,
    DcaBUndergraduateprize,
    DcaBUser,
    DcaBAttachfile,
    DcaBWorknum,
    DcaBExportcountry,
    DcaBAcademic,
    DcaBAchievement,
    DcaBQualification,
    DcaBAuditDynamic,
    DcaBTeacheryj,
    DcaBSciachievement,
    DcaBSciachievement,
    DcaBTeacheryj,
    DcaBSureachievement
  },
  data() {
    return {
      mouduleTreeKey: +new Date(),
      loading: false,
      bordered: true,
      mouduleTreeData: [],
      allTreeKeys: [],
      formItemLayout,
      index: 6,
      collapsed: false,
      showAdd: false,
      calcHeight: {
        height: "",
        overflow: "auto",
      },
      componentKey: 0,
    };
  },
  mounted() {
   
    this.fetch();
  },
  created() {
        this.$EventBus.$on('selectMoudles',(index)=> {
         // this.componentKey += 1; 
          this.fetch();
          this.index = index
    })
    window.addEventListener("resize", this.getHeight);
    this.getHeight();
  },
  methods: {
    getHeight() {
      this.calcHeight.height = window.innerHeight - 59 - 39 - 100 + "px";
    },
    handlePromise() {
      this.showAdd = true;
    },
    fetch() {
      let codes = "272,273,121,251,601,600";
      this.$get("dcaDMudules/doctree/" + codes).then((r) => {
        var drows = r.data.rows.children;
        drows[0].children = drows[0].children.filter((p) => p.id != 10);//这里是第一组数据 [1]树的第二组
        // drows[0].children.forEach(element => {
        //    element['scopedSlots'] = {title: 'custom'}
        // });
        // console.info(drows)
        // var drows=r.data.rows.children.filter(p=>p.id!=11) //医疗工作量
        this.mouduleTreeData = drows;
        this.allTreeKeys = r.data.ids;
      });
    },
    handleTreeClick(keys, event) {
      // console.info(event.node)
      if (event.node.getNodeChildren().length > 0) {
        let previous = event.node.$el.firstElementChild;
        previous.click();
      } else {
        if (keys.length > 0) {
          this.index = keys[0];
        }
      }
    },
  },
};
</script>
<style>
.trigger {
  font-size: 18px;
  line-height: 20px;
  padding: 0 5px;
  cursor: pointer;
  transition: color 0.3s;
}
#custom-trigger .trigger:hover {
  color: #1890ff;
}

.ant-card-body {
  padding: 8px !important;
}
.myBaseInfo .ant-card-head-title {
  color: red;
}

/* .ant-tree li span.ant-tree-switcher {
  width: 5px !important;
} */
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>

