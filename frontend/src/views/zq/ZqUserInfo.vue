<template>
  <a-modal
    title="用户基本信息"
    :visible="infoVisiable"
    :footer="null"
    @cancel="cancelInfo"
    :maskClosable="false"
    width="80%"
  >
    <a-card title="个人照片">
      <a-row>
        <a-col :span="4"> <img :src="picUrl" width="120" height="120" /></a-col>
        <a-col :span="20">
          <a-row>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="姓名">
                {{ dcaBUser.userAccountName }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="人事编号">
                {{ dcaBUser.userAccount }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="所在科室">
                {{ dcaBUser.ks }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="联系电话">
                {{ dcaBUser.telephone }}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="教学岗位">
                {{ dcaBUser.zyjsgw }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="聘任时间(教学)">
                {{
                  dcaBUser.appointedDate == null
                    ? ""
                    : moment(dcaBUser.appointedDate).format("YYYY-MM-DD")
                }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="临床岗位">
                {{ dcaBUser.zyjsgwLc }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="聘任时间(临床)">
                {{
                  dcaBUser.appointedDateLc == null
                    ? ""
                    : moment(dcaBUser.appointedDateLc).format("YYYY-MM-DD")
                }}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="拟聘岗位职务">
                {{ dcaBUser.npPositionName }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="博士毕业时间">
                {{ dcaBUser.doctorDesc }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="来院时间">
                {{
                  dcaBUser.schoolDate == null
                    ? ""
                    : moment(dcaBUser.schoolDate).format("YYYY-MM-DD")
                }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="职员职级">
                {{ dcaBUser.staffGrade }}
              </a-form-item>
            </a-col>
          </a-row>
          <a-row>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="职员聘任时间">
                {{
                  dcaBUser.staffDate == null
                    ? ""
                    : moment(dcaBUser.staffDate).format("YYYY-MM-DD")
                }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="来院工作时间">
                {{
                  dcaBUser.schoolDate == null
                    ? ""
                    : moment(dcaBUser.schoolDate).format("YYYY-MM-DD")
                }}
              </a-form-item>
            </a-col>
            <a-col :span="6">
              <a-form-item v-bind="formItemLayout" label="手机号">
                {{ dcaBUser.telephone }}
              </a-form-item>
            </a-col>
          </a-row>
        </a-col>
      </a-row>
    </a-card>
    <a-card title="科研情况——发表的论文">
      <a-table
        :columns="eduColumns"
        :pagination="false"
        :data-source="eduList"
        :rowKey="(record) => record.id"
        :scroll="{x:1600,y:300}"
        bordered
      >
      </a-table>
    </a-card>
    <a-card title="科研情况——科研项目">
      <a-table
        :columns="partjobColumns"
        :pagination="false"
        :data-source="partjobList"
        :rowKey="(record) => record.id"
        bordered
        :scroll="{x:1200,y:300}"
      >
      </a-table>
    </a-card>
    <a-card title="论文著作">
      <a-table
        :columns="articleColumns"
        :pagination="false"
        :data-source="auditList"
        :rowKey="(record) => record.id"
        bordered
        :scroll="{x:1400,y:300}"
      >
      </a-table>
    </a-card>
    <a-card title="科研获奖">
      <a-table
        :columns="prizeColumns"
        :pagination="false"
        :data-source="punishOrPrizeList"
        :rowKey="(record) => record.id"
        bordered
        :scroll="{x:1200,y:300}"
      >
      </a-table>
    </a-card>
    <a-card title="考核目标">
      <div>{{ goal }}</div>
    </a-card>
     <a-card title="完成考核目标的情况">
      <div>{{ employ }}</div>
    </a-card>
    <a-card title="中期总结报告">
      <div>{{ summary }}</div>
    </a-card>
    <a-card title="打分" v-if="isShow<3" style="text-align: center">
       <a-row>
            <a-col :span="4">
              <a-form-item v-bind="formItemLayout2" label="评分等级">
               <a-select
            v-model="fenzu"
            style="width: 100%"
          >
            <a-select-option value="优">
              优
            </a-select-option>
             <a-select-option value="良">
              良
            </a-select-option>
            <a-select-option value="中">
              中
            </a-select-option>
            <a-select-option value="差">
              差
            </a-select-option>
               </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="8">
               <a-form-item v-bind="formItemLayout" label="该职工是否能按期完成考核目标">
               <a-select
            v-model="ifDone"
            style="width: 100%"
          >
            <a-select-option value="是">
              是
            </a-select-option>
             <a-select-option value="否">
              否
            </a-select-option>
               </a-select>
              </a-form-item>
            </a-col>
            <a-col :span="12">
              <a-form-item v-bind="formItemLayout3" label="整改建议">
                 <a-textarea v-model="suggestion" :disabled="isUpdate">
                 </a-textarea>
              </a-form-item>
            </a-col>
          </a-row>
     <a-button @click="handleSubmit" v-if="isShow==0" type="primary" :loading="loading" style="margin-left:20px;"
        >提交</a-button
      >
    </a-card>
  </a-modal>
</template>
  
  <script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 14 },
  wrapperCol: { span: 10 },
};
const formItemLayout2 = {
  labelCol: { span: 10 },
  wrapperCol: { span: 14 },
};
const formItemLayout3 = {
  labelCol: { span: 6 },
  wrapperCol: { span: 18 },
};
export default {
  name: "userInfo",
  data() {
    return {
      eduList: [],
      partjobList: [],
      punishOrPrizeList: [],
      boardList: [],
      auditList: [],
      acdemicList: [],
      summary: "",
      employ:'',
      goal: '',
      formItemLayout,
      formItemLayout2,
      formItemLayout3,
      dcaBUser: {},
      score: 0,
      zqDScore: {},
      loading: false,
      ifDone: '',
      fenzu:'',
      suggestion: ''
    };
  },
  props: {
    infoVisiable: {
      default: false,
    },
    userAccount: {
      default: "",
    },
    year: {
      default: "",
    },
    picUrl: {
      default: "",
    },
     isShow: {
      default: 0,
    },
  },
  watch: {
    infoVisiable() {
      if (this.infoVisiable) {
        this.fetch(this.userAccount, this.year);
        //this.dcaBUser = this.dcaBUser1
        this.getUserInfo(this.userAccount);
      }
    },
  },
  computed: {
    isUpdate() {
       if(this.ifDone=='否'){
         return false;
       }
       else{
        this.suggestion =''
       }
       return true;
    },
    eduColumns() {
      return [
        {
          title: "论文名",
          dataIndex: "paperName",
          width: 200,
          scopedSlots: { customRender: "paperName" },
         
        },
        {
          title: "期刊名",
          dataIndex: "journalName",
          width: 200,
        
          scopedSlots: { customRender: "journalName" },
        },
        {
          title: "期刊号",
          dataIndex: "journalCode",
          width: 120,
          scopedSlots: { customRender: "journalCode" },
         
        },
        {
          title: "发表年月",
          dataIndex: "paperPublishdate",
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        
        },
        {
          title: "收录情况",
          dataIndex: "paperShoulu",
          width: 120,
          scopedSlots: { customRender: "paperShoulu" },
        },
        {
          title: "影响因子",
          dataIndex: "paperCause",
          width: 120,
          scopedSlots: { customRender: "paperCause" },
        },
        {
          title: "是否一流期刊",
          dataIndex: "isBest",
          width: 120,
          scopedSlots: { customRender: "isBest" },
        },
        {
          title: "他引次数",
          dataIndex: "otherTimes",
          width: 120,
          scopedSlots: { customRender: "otherTimes" },
        },
        {
          title: "期刊级别",
          dataIndex: "qkjb",
          width: 100,
          scopedSlots: { customRender: "qkjb" },
        },
        {
          title: "第一或通讯作者",
          dataIndex: "authorRank",
          width: 200,
          scopedSlots: { customRender: "authorRank" },
        },
        {
          title: "第几作者",
          dataIndex: "djzz",
          width: 80,
          scopedSlots: { customRender: "djzz" },
        },
        {
          title: "期刊类型",
          dataIndex: "wzlx",
          width: 80,
          scopedSlots: { customRender: "wzlx" },
        },
        {
          title: "是否SCI",
          dataIndex: "sciValue",
          width: 80,
          scopedSlots: { customRender: "sciValue" },
        },
        {
          title: "rank值(百分制)",
          dataIndex: "rankValue",
          width: 80,
          scopedSlots: { customRender: "rankValue" },
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }
      ];
    },
    partjobColumns() {
      return [
        {
          title: "项目名称",
          dataIndex: "projectName",
          width: 200,
          scopedSlots: { customRender: "projectName" },
         
        },
        {
          title: "项目性质",
          dataIndex: "projectType",
          width: 130,
          scopedSlots: { customRender: "projectType" },
        },
        {
          title: "项目来源",
          dataIndex: "projectSource",
          width: 130,
          scopedSlots: { customRender: "projectSource" },
         
        },
        {
          title: "合同经费(单位：万)",
          dataIndex: "contractFund",
          width: 130,
          scopedSlots: { customRender: "contractFund" },
        },
        {
          title: "实到经费(单位：万)",
          dataIndex: "realFund",
          width: 130,
          scopedSlots: { customRender: "realFund" },
        },
        {
          title: "批准年月",
          dataIndex: "auditDate2",
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "起始日期",
          dataIndex: "startDate",
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "终止日期",
          dataIndex: "endDate",
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "本人排名",
          dataIndex: "rankNum",
          width: 130,
          scopedSlots: { customRender: "rankNum" },
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }
      ];
    },
    prizeColumns() {
      return [
        {
          title: "获奖项目名称",
          dataIndex: "spProjectName",
          width: 200,
          scopedSlots: { customRender: "spProjectName" },
        
        },
        {
          title: "奖项级别",
          dataIndex: "srProjectGrade",
          width: 130,
          scopedSlots: { customRender: "srProjectGrade" },
          
        },
        {
          title: "奖项等级",
          dataIndex: "srProjectLevel",
          width: 130,
          scopedSlots: { customRender: "srProjectLevel" },
        },
        {
          title: "授奖部门",
          dataIndex: "srPrizeDept",
          width: 130,
          scopedSlots: { customRender: "srPrizeDept" },
        },
        {
          title: "批准年月",
          dataIndex: "srPrizeDate",
          width: 130,
          customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "本人排名",
          dataIndex: "srPrizeRanknum",
          width: 130,
          scopedSlots: { customRender: "srPrizeRanknum" },
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }
      ];
    },
    articleColumns() {
      return [
        {
          title: "著作类型",
          dataIndex: "zzlx",
          width: 130,
          scopedSlots: { customRender: "zzlx" },
       
        },
        {
          title: "著作名称",
          dataIndex: "zzmc",
          width: 300,
          scopedSlots: { customRender: "zzmc" },
         
        },
        {
          title: "著者身份",
          dataIndex: "zzsf",
          width: 130,
          scopedSlots: { customRender: "zzsf" },
        },
        {
          title: "出版时间",
          dataIndex: "cbDate",
          width: 130,
         customRender: (text, row, index) => {
            if(text==null) return ''
            return moment(text).format('YYYY-MM-DD')
          },
        },
        {
          title: "第几版",
          dataIndex: "ranknum",
          width: 130,
          scopedSlots: { customRender: "ranknum" },
        },
        {
          title: " 第几次印刷",
          dataIndex: "printnum",
          width: 130,
          scopedSlots: { customRender: "printnum" },
        },
        {
          title: " 书号（ISNB号）",
          dataIndex: "bookNo",
          width: 130,
          scopedSlots: { customRender: "bookNo" },
        },
        {
          title: "出版社名称",
          dataIndex: "cbsmc",
          width: 130,
          scopedSlots: { customRender: "cbsmc" },
        },
        {
          title: "作为第一编写人编写章节名称",
          dataIndex: "bxzjmc",
          width: 130,
          scopedSlots: { customRender: "bxzjmc" },
        },
        {
          title: "作为第一编写人编写章节起止页",
          dataIndex: "bxwzqzy",
          width: 130,
          scopedSlots: { customRender: "bxwzqzy" },
        },
        {
          title: "作为第一编写人编写字数合计（单位：万字）",
          dataIndex: "bxwzzshj",
          width: 130,
          scopedSlots: { customRender: "bxwzzshj" },
        }, {
          title: '附件',
          dataIndex: 'fileId',
          customRender: (text, row, index) => {
            if (text != null && text != '') {
              return <a href={row.fileUrl} target="_blank" >查看</a>
            }
            return ''
          },
          width: 80
        }
      ];
    },
  },
  methods: {
    moment,
    reset() {
      this.eduList = [];
      this.partjobList = [];
      this.punishOrPrizeList = [];
      this.boardList = [];
      this.auditList = [];
      this.acdemicList = [];
      this.summary =''
      this.employ = ''
      this.goal = ''
      this.suggestion = ''
      this.ifDone = ''
      this.fenzu = ''
    },
    fetch(userAccount, year) {
      this.$get("zqDScore/getBaseInfo/" + userAccount, {
        year: year,
      }).then((r) => {
        let data = r.data;
        this.eduList = data.publishList;
        this.partjobList = data.sciencesearchList;
        this.summary = data.summary;
        this.employ = data.employ;
        this.goal = data.goal;
        this.punishOrPrizeList = data.scientificprizeList;
        this.auditList = data.publicarticleList;
      });
    },
  
    getUserInfo(userAccount) {
      if (userAccount != "") {
        this.$get("dcaBUser", {
          userAccount: userAccount,
        }).then((r) => {
          let data = r.data;
          if (data.rows.length > 0) {
            this.dcaBUser = data.rows[0];
          
          }
        });
      }
    },
    setKh(record) {
      this.zqDScore.id = record.id;
      this.suggestion = record.suggestion;
      this.ifDone= record.ifDone
      this.fenzu= record.fenzu 
    },
    handleSubmit() {
      let zqDScore = this.zqDScore;
      if(this.fenzu==''||this.fenzu==null){
        this.$message.warn('请填写评分等级')
      }
      else if(this.ifDone==''||this.ifDone==null){
        this.$message.warn('该职工是否能按期完成考核目标')
      }
      else if(this.ifDone=='否' && this.suggestion==''){
        this.$message.warn('请填写整改意见')
      }
      else{
        zqDScore.ifDone= this.ifDone
        zqDScore.fenzu= this.fenzu
        zqDScore.suggestion = this.suggestion
      this.$put("zqDScore/update", {
        ...zqDScore,
      })
        .then(() => {
          this.reset();
          this.$emit("success");
        })
        .catch(() => {
          this.loading = false;
        });
      }
    },
    cancelInfo() {
      this.reset();
      this.$emit("close");
    },
  },
};
</script>
  
  <style>
</style>