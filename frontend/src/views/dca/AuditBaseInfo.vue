<template>
  <a-card
    :bordered="false"
    class="card-area"
  >
    <a-layout id="custom-trigger">
      <a-layout-sider
        width="300"
        style="background: #fff;"
        v-model="collapsed"
        :trigger="null"
        collapsible
      >
        <a-card
          :bordered="true"
          class="card-area myBaseInfo"
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
            </a-tree>
          </div>
        </a-card>
      </a-layout-sider>
      <a-layout>
        <a-layout-content>
          <div style="position: relative">
           
            <dcaB-parttimejob v-if="index==15">
              <!--社会兼职-->
            </dcaB-parttimejob>
            <dcaB-employAudit v-if="index==60">
              <!--任现职以来完成教学、人才培养情况-->
            </dcaB-employAudit>
            <!--  <dcaB-teachtalent v-if="index==6">
              教师 任现职以来完成教学、人才培养情况  和emply重复了
            </dcaB-teachtalent>-->
            <dcaB-educationexpericeAudit v-if="index==14">
              <!--学习工作经历-->
            </dcaB-educationexpericeAudit>
            <!--<dcaB-essaypublish v-if="index==7">
              //论文出版
            </dcaB-essaypublish>-->
            <dcaB-graduateAudit v-if="index==210">
              <!--研究生情况-->
            </dcaB-graduateAudit>
            <dcaB-otherworkAudit v-if="index==240">
              <!--其他工作及成果-->
            </dcaB-otherworkAudit>
            <!-- <dcaB-paperspublish v-if="index==22">
             教学论文出版教材
            </dcaB-paperspublish>-->
            <dcaB-patentAudit v-if="index==100">
              <!--申请专利-->
            </dcaB-patentAudit>
            <dcaB-prizeorpunishAudit v-if="index==20">
              <!--何时何地奖励或处分-->
            </dcaB-prizeorpunishAudit>
            <dcaB-sciencepublishAudit v-if="index==70">
              <!--科研论文-->
            </dcaB-sciencepublishAudit>
            <dcaB-scientificprizeAudit v-if="index==90">
              <!--自任职以来科研获奖情况-->
            </dcaB-scientificprizeAudit>
            <dcaB-talentAudit v-if="index==200">
              <!--任现职以来完成研究生教学人才培养情况-->
            </dcaB-talentAudit>
            <dcaB-teacherqualifyAudit v-if="index==130">
              <!--教师资格-->
            </dcaB-teacherqualifyAudit>
            <dcaB-turtorAudit v-if="index==140">
              <!--担任辅导员-->
            </dcaB-turtorAudit>
            <dcaB-undergraduateAudit v-if="index==170">
              <!--本科教学情况-->
            </dcaB-undergraduateAudit>
            <dcaB-applyjobAudit v-if="index==160">
              <!--拟聘岗位-->
            </dcaB-applyjobAudit>
            <dcaB-auditfiveAudit v-if="index==270">
              <!--近五年考核情况-->
            </dcaB-auditfiveAudit>
            <dcaB-lastemployAudit v-if="index==150">
              <!--完成上一聘期-->
            </dcaB-lastemployAudit>
            <dcaB-personalsummaryAudit v-if="index==120">
              <!--个人总结-->
            </dcaB-personalsummaryAudit>
            <dcaB-politalshowAudit v-if="index==50">
              <!--个人思想政治表现-->
            </dcaB-politalshowAudit>
            <dcaB-innovatebuildAudit v-if="index==180">
              <!--改革及建设项目-->
            </dcaB-innovatebuildAudit>
            <dcaB-sciencesearchAudit v-if="index==80">
              <!--科研项目-->
            </dcaB-sciencesearchAudit>
            <!--<dcaB-fivecommentAudit v-if="index==17">
                近五年总体情况评价
              </dcaB-fivecommentAudit>-->
            <dcaB-goalAudit v-if="index==250">
              <!--拟聘岗位工作思路及预期目标-->
            </dcaB-goalAudit>

            <dcaB-undergraduateprizeAudit v-if="index==190">
              <!--任现职以来本科教学工作获奖情况-->
            </dcaB-undergraduateprizeAudit>
            <dcaB-attachfileAudit v-if="index==400">
              <!--其他材料附件-->
            </dcaB-attachfileAudit>
            <dcaB-worknumAudit v-if="index==110">
              <!-- 门诊工作量-->
            </dcaB-worknumAudit>
            <dcaB-exportcountryAudit v-if="index==40">
              <!--出国情况-->
            </dcaB-exportcountryAudit>
            <dcaB-publicarticleAudit v-if="index==280">
              <!--著作-->
            </dcaB-publicarticleAudit>

            <dcaB-courseclassAudit v-if="index==310">
              <!--精品课程情况-->
            </dcaB-courseclassAudit>
            <dcaB-schoolprizeAudit v-if="index==300">
              <!--校教学质量奖、校教学成果奖-->
            </dcaB-schoolprizeAudit>
            <dcaB-teacherprizeAudit v-if="index==290">
              <!--省部级及以上教学获奖-->
            </dcaB-teacherprizeAudit>
            <dcaB-youngprizeAudit v-if="index==320">
              <!--教师教学竞赛获奖-->
            </dcaB-youngprizeAudit>
            <dcaB-doctorturtorAudit v-if="index==212">
              <!--博导时间-->
            </dcaB-doctorturtorAudit>
            <dcaB-medicalaccidentAudit v-if="index==340">
              <!--医疗事故评分-->
            </dcaB-medicalaccidentAudit>
            <dcaB-academicAudit v-if="index==380">
              <!--学术-->
            </dcaB-academicAudit>
            <dcaB-achievementAudit v-if="index==360">
              <!--医疗业绩-->
            </dcaB-achievementAudit>
            <dcaB-qualificationAudit v-if="index==370">
              <!--资质证书-->
            </dcaB-qualificationAudit>
            <dcaB-userList v-if="index==10">
              <!--用户信息-->
            </dcaB-userList>
            <dcaB-auditDynamicAudit v-if="index==401">
              <!--部门审核结果-->
            </dcaB-auditDynamicAudit>
              <dcaB-teacheryjAudit 
              v-if="index==330">
                <!--主要教学业绩-->
              </dcaB-teacheryjAudit> 
              <dcaB-sciachievementAudit
               v-if="index==332">
                <!--主要科研业绩-->
              </dcaB-sciachievementAudit> 
              <dcaB-sureachievementAudit v-if="index==500">
                <!--主要医疗认可-->
              </dcaB-sureachievementAudit> 
              <dca-b-letter v-if="index==600">
              </dca-b-letter>
          </div>
        </a-layout-content>
      </a-layout>
    </a-layout>
  </a-card>

</template>
<script>
const formItemLayout = {
  labelCol: { span: 5 },
  wrapperCol: { span: 18, offset: 1 },
};
import DcaBParttimejob from "../dca/DcaBParttimejob/DcaBParttimejob";
import DcaBEmployAudit from "../dca/DcaBEmploy/DcaBEmployAudit";
import DcaBEducationexpericeAudit from "../dca/DcaBEducationexperice/DcaBEducationexpericeAudit";
import DcaBEssaypublishAudit from "../dca/DcaBEssaypublish/DcaBEssaypublishAudit";
import DcaBGraduateAudit from "../dca/DcaBGraduate/DcaBGraduateAudit";
import DcaBInnovatebuildAudit from "../dca/DcaBInnovatebuild/DcaBInnovatebuildAudit";
import DcaBOtherworkAudit from "../dca/DcaBOtherwork/DcaBOtherworkAudit";
import DcaBPaperspublishAudit from "../dca/DcaBPaperspublish/DcaBPaperspublishAudit";
import DcaBPatentAudit from "../dca/DcaBPatent/DcaBPatentAudit";
import DcaBPrizeorpunishAudit from "../dca/DcaBPrizeorpunish/DcaBPrizeorpunishAudit";
import DcaBSciencepublishAudit from "../dca/DcaBSciencepublish/DcaBSciencepublishAudit";
import DcaBScientificprizeAudit from "../dca/DcaBScientificprize/DcaBScientificprizeAudit";
import DcaBTalentAudit from "../dca/DcaBTalent/DcaBTalentAudit";
import DcaBTeacherqualifyAudit from "../dca/DcaBTeacherqualify/DcaBTeacherqualifyAudit";
import DcaBTurtorAudit from "../dca/DcaBTurtor/DcaBTurtorAudit";
import DcaBUndergraduateAudit from "../dca/DcaBUndergraduate/DcaBUndergraduateAudit";
import DcaBApplyjobAudit from "../dca/DcaBApplyjob/DcaBApplyjobAudit";
import DcaBAuditfiveAudit from "../dca/DcaBAuditfive/DcaBAuditfiveAudit";
import DcaBLastemployAudit from "../dca/DcaBLastemploy/DcaBLastemployAudit";
import DcaBPersonalsummaryAudit from "../dca/DcaBPersonalsummary/DcaBPersonalsummaryAudit";
import DcaBPolitalshowAudit from "../dca/DcaBPolitalshow/DcaBPolitalshowAudit";
import DcaBSciencesearchAudit from "../dca/DcaBSciencesearch/DcaBSciencesearchAudit";
import DcaBFivecommentAudit from "../dca/DcaBFivecomment/DcaBFivecommentAudit";
import DcaBGoalAudit from "../dca/DcaBGoal/DcaBGoalAudit";
import DcaBAttachfileAudit from "../dca/DcaBAttachfile/DcaBAttachfileAudit";
import DcaBWorknumAudit from "../dca/DcaBWorknum/DcaBWorknumAudit";
import DcaBUndergraduateprizeAudit from "../dca/DcaBUndergraduateprize/DcaBUndergraduateprizeAudit";
import DcaBExportcountryAudit from "../dca/DcaBExportcountry/DcaBExportcountryAudit";
import DcaBPublicarticleAudit from "../dca/DcaBPublicarticle/DcaBPublicarticleAudit";
import DcaBCourseclassAudit from "../dca/DcaBCourseclass/DcaBCourseclassAudit";
import DcaBSchoolprizeAudit from "../dca/DcaBSchoolprize/DcaBSchoolprizeAudit";
import DcaBTeacherprizeAudit from "../dca/DcaBTeacherprize/DcaBTeacherprizeAudit";
import DcaBYoungprizeAudit from "../dca/DcaBYoungprize/DcaBYoungprizeAudit";
import DcaBDoctorturtorAudit from "../dca/DcaBDoctorturtor/DcaBDoctorturtorAudit";
import DcaBMedicalaccidentAudit from "../dca/DcaBMedicalaccident/DcaBMedicalaccidentAudit";
import DcaBAchievementAudit from "../dca/DcaBAchievement/DcaBAchievementAudit";
import DcaBAcademicAudit from "../dca/DcaBAcademic/DcaBAcademicAudit";
import DcaBQualificationAudit from "../dca/DcaBQualification/DcaBQualificationAudit";
import DcaBUserList from "../dca/DcaBUser/DcaBUserList";
import DcaBAuditDynamicAudit from "../dca/DcaBUserapply/DcaBAuditDynamicAudit";
import DcaBTeacheryjAudit from "../dca/DcaBTeacheryj/DcaBTeacheryjAudit";
import DcaBSciachievementAudit from "../dca/DcaBSciachievement/DcaBSciachievementAudit";
import DcaBSureachievementAudit from "../dca/DcaBSureachievement/DcaBSureachievementAudit";
import DcaBLetter from './DcaBLetter/DcaBLetter.vue';

export default {
  name: "AuditBaseInfo",
  components: {
    DcaBAuditDynamicAudit,
    DcaBParttimejob,
    DcaBEmployAudit,
    DcaBEducationexpericeAudit,
    DcaBEssaypublishAudit,
    DcaBGraduateAudit,
    DcaBPublicarticleAudit,
    DcaBCourseclassAudit,
    DcaBSchoolprizeAudit,
    DcaBTeacherprizeAudit,
    DcaBYoungprizeAudit,
    DcaBInnovatebuildAudit,
    DcaBOtherworkAudit,
    DcaBPaperspublishAudit,
    DcaBPatentAudit,
    DcaBPrizeorpunishAudit,
    DcaBSciencepublishAudit,
    DcaBScientificprizeAudit,
    DcaBTalentAudit,
    DcaBTeacherqualifyAudit,
    DcaBTurtorAudit,
    DcaBUndergraduateAudit,
    DcaBApplyjobAudit,
    DcaBAuditfiveAudit,
    DcaBLastemployAudit,
    DcaBPersonalsummaryAudit,
    DcaBPolitalshowAudit,
    DcaBSciencesearchAudit,
    DcaBFivecommentAudit,
    DcaBGoalAudit,
    DcaBAttachfileAudit,
    DcaBWorknumAudit,
    DcaBUndergraduateprizeAudit,
    DcaBExportcountryAudit,
    DcaBMedicalaccidentAudit,
    DcaBDoctorturtorAudit,
    DcaBAcademicAudit,
    DcaBAchievementAudit,
    DcaBQualificationAudit,
    DcaBUserList,
    DcaBTeacheryjAudit,
    DcaBSciachievementAudit,
    DcaBSureachievementAudit,
    DcaBLetter
  },
  data() {
    return {
      mouduleTreeKey: +new Date(),
      loading: false,
      bordered: true,
      mouduleTreeData: [],
      allTreeKeys: [],
      formItemLayout,
      index: 0,
      collapsed: false,
      calcHeight: {
        height: "",
        overflow: "auto",
      },
    };
  },
  mounted() {
    this.fetch();
  },
  created() {
    window.addEventListener("resize", this.getHeight);
    this.getHeight();
  },
  methods: {
    getHeight() {
      this.calcHeight.height = window.innerHeight - 59 - 39 - 100 + "px";
    },
    fetch() {
      this.$get("dcaDMudules/treeByUserId").then((r) => {
        //console.info(222)
        this.mouduleTreeData = r.data.rows.children;
        this.allTreeKeys = r.data.ids;
        var rowsDatas = r.data.rows.children;
        this.index = rowsDatas[0].children[0].id;
      });
    },
    handleTreeClick(keys, event) {
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
  padding: 8px;
}
.ant-tabs-bar {
  margin-bottom: 8px;
}
.myBaseInfo .ant-card-head-title {
  color: red;
}
</style>
<style lang="less" scoped>
@import "../../../static/less/Common";
</style>