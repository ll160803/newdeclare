<template>
  <a-drawer
    title="编辑查看"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 5px); overflow: auto; padding-bottom: 53px"
  >
    <a-row>
      <a-col :sm="24">
        <sci-seek
          :jbLbList2="jbLbList"
          @setValue="getSearchValue"
          :isAudit="true"
          ref="seek"
        ></sci-seek>
      </a-col>
    </a-row>
    <a-form :form="form" layout="vertical">
      <a-card
        title="个人论文信息填写"
        :headStyle="{
          fontWeight: 'bold',
          fontSize: '20px',
        }"
      >
   
              <a-row
          ><a-col :span="23">
            <a-form-item label="论文名">
              <a-input
                :style="{ fontWeight: 'bold' }"
                placeholder="请输入论文名"
                v-decorator="[
                  'paperName',
                  { rules: [{ required: true, message: '论文名不能为空' }] },
                ]"
              /> </a-form-item></a-col></a-row
        ><a-row
          ><a-col :span="7">
            <a-form-item label="期刊名">
              <a-input
                placeholder="请输入期刊名"
                v-decorator="[
                  'journalName',
                  { rules: [{ required: true, message: '期刊名不能为空' }] },
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="期刊号">
              <a-input
                placeholder="请输入期刊号"
                v-decorator="[
                  'journalCode',
                  { rules: [{ required: true, message: '期刊号不能为空' }] },
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="发表年月">
              <a-date-picker
                v-decorator="[
                  'paperPublishdate',
                  { rules: [{ required: true, message: '发表年月不能为空' }] },
                ]"
              /> </a-form-item></a-col></a-row
        ><a-row
          ><a-col :span="7">
            <a-form-item label="期刊级别">
              <a-select
                placeholder="请输入期刊级别"
                v-decorator="[
                  'qkjb',
                  { rules: [{ required: true, message: '期刊级别不能为空' }] },
                ]"
                style="width: 100%"
              >
                <a-select-option value="A"> A </a-select-option>
                <a-select-option value="B"> B </a-select-option>
                <a-select-option value="C"> C </a-select-option>
                <a-select-option value="D"> D </a-select-option>
                <a-select-option value="E"> E </a-select-option>
                <a-select-option value="F"> F </a-select-option>
              </a-select>
            </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="收录情况">
              <a-input
                placeholder="请输入收录情况"
                v-decorator="['paperShoulu']"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="影响因子">
              <a-input
                @blur="(e)=>inputCauseChange(e.target.value)"
                placeholder="请输入影响因子"
                v-decorator="['paperCause']"
              /> </a-form-item></a-col></a-row
        ><a-row
          ><a-col :span="7">
            <a-form-item label="是否一流期刊">
              <a-select
                style="width: 100%"
                placeholder="请输入是否一流期刊"
                v-decorator="[
                  'isBest',

                  {
                    rules: [],
                  },
                ]"
              >
                <a-select-option value="是">是</a-select-option>
                <a-select-option value="否">否</a-select-option>
              </a-select>
            </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="他引次数" >
              <a-input
                placeholder="请输入他引次数"
                v-decorator="['otherTimes']"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="期刊类型">
              <a-select
                placeholder="请输入期刊类型"
                v-decorator="['wzlx']"
                style="width: 100%"
              >
                <a-select-option value="科研"> 科研 </a-select-option>
                <a-select-option value="教学"> 教学 </a-select-option>
              </a-select>
            </a-form-item> </a-col
          ></a-row
        ><a-row>
          <a-col :span="7" >
           <a-form-item label="第一或通讯作者">
               <a-select
                style="width: 100%"
                mode="multiple"
                v-decorator="[
                  'authorRank',
                  {
                    rules: [
                     
                    ],
                  },
                ]"
              >
                <a-select-option value="第一作者" key="第一作者">
                  第一作者
                </a-select-option>
                <a-select-option value="通讯作者" key="通讯作者">
                  通讯作者
                </a-select-option>
                <a-select-option value="共同第一作者" key="共同第一作者">
                  共同第一作者
                </a-select-option>
                <a-select-option value="共同通讯作者" key="共同通讯作者">
                  共同通讯作者
                </a-select-option>
                <a-select-option value="其他" key="其他">
                  其他
                </a-select-option>
              </a-select> </a-form-item
          ></a-col
          >
           <a-col :span="7" :offset="1">
             <a-form-item label="第一作者或通讯作者共几人">
              <a-input-number
              @change="changeAuditTotalnum"
                :precision="0"
                placeholder="请输入第一作者或通讯作者共几人"
                v-decorator="['auditTotalnum']"
              /> </a-form-item
          >
          </a-col
          >
          <a-col :span="7" :offset="1">
            <a-form-item label="排第几">
              <a-input-number
                :precision="0"
                @change="changeDjzz"
                placeholder="请输入排第几"
                v-decorator="['djzz']"
              /> </a-form-item
          ></a-col></a-row>
              <a-row>
      <a-col :span="7" >
           <a-form-item label="文章类型">
              <a-select
                placeholder="文章类型"
                v-decorator="['jxzcsl']"
                @change="changeWzlx"
              >
              <a-select-option value="Article">Article</a-select-option>
              <a-select-option value="研究型Letter">研究型Letter</a-select-option>
              <a-select-option value="Review">Review</a-select-option>
              <a-select-option value="Case Report">Case Report</a-select-option>
              <a-select-option value="Editorial material/commentary">Editorial material/commentary</a-select-option>
               <a-select-option value="Meta分析">Meta分析</a-select-option>
              </a-select>
               </a-form-item
          ></a-col
          >  <a-col :span="7" :offset="1">
           <a-form-item label="文章分数">
              <a-input-number
                :disabled="true"
                placeholder="请输入文章分数"
                v-decorator="['cdzs']"
              /> </a-form-item
          ></a-col
          >
         <a-col :sm="7" :offset="1">
              <a-form-item label="第一作者第一单位">
              <a-input
                placeholder="请输入第一作者第一单位"
                v-decorator="['firstUnitAuthor']"
              /> </a-form-item
          >
          </a-col>
            </a-row>
            <a-row>
          <a-col :sm="7" >
            <a-form-item label="论文附件">
              <upload-single-file
                ref="fileagent3"
                @uploadRemove="removeAgent_3"
                @uploadSuc="uploadAgent_3"
              >
              </upload-single-file>
            </a-form-item>
          </a-col>
        </a-row>
      </a-card>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 3rem" type="primary">取消</a-button>
      </a-popconfirm>
                <a-button @click="handleSubmit(0)" v-if="state==0||state==2" type="primary" :loading="loading"
        >保存草稿不提交</a-button
      >
      <a-button @click="handleSubmit(1)" v-if="state==0||state==2" type="primary" :loading="loading"
        >直接提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import SciSeek from "../../dca/DcaBSciencepublish/SciSeek.vue";
import UploadSingleFile from "../../common/uploadSingleFile.vue";
import moment from "moment";
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  components: { SciSeek, UploadSingleFile },
  name: "DcaBSciencepublishEdit",
  props: {
    editVisiable: {
      default: false,
    },
  },
  data() {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBSciencepublish: {},
      dcaSearch: {},
      jbLbList: [],
      pulishDate: "",
      isShow: true,
      isNoEdit: true,
      id: "",
      state: 1,
    };
  },
  watch: {
    editVisiable() {
      if (this.editVisiable) {
        this.fetchJb();
       
      }
    },
  },
  methods: {
    moment,
    reset() {
      this.loading = false;
      this.dcaBSciencepublish = {};
      this.dcaSearch = {};
      this.isShow = true;
      this.isNoEdit = true;

      this.$refs.seek.reset();
      this.$refs.fileagent3.reset();
      this.form.resetFields();
    },
    uploadAgent_3(fileId, fileUrl) {
      this.dcaBSciencepublish.fileId = fileId;
      this.dcaBSciencepublish.fileUrl = fileUrl;
    },
    removeAgent_3() {
      this.dcaBSciencepublish.fileId = "";
      this.dcaBSciencepublish.fileUrl = "";
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
   
      calcResult(cause,djzz,wzlx,auditTotalnum){
    let cdzs=0 
     let sci =this.calcWzfs(parseFloat(cause))
    if (wzlx == "Article" || wzlx == "研究型Letter") {
        cdzs = sci;
      }
      if (wzlx == "Review") {
        cdzs = sci * 0.3;
      }
      if (wzlx == "Case Report" || wzlx == "Editorial material/commentary") {
        cdzs = sci * 0.1;
      }
      if (wzlx == "Meta分析") {
         if (parseFloat(cause) >= 10) {
          cdzs = sci * 0.5;
        }
      }
      if(parseFloat(djzz)==1){ //
        this.form.setFieldsValue({ cdzs: cdzs.toFixed(2) });
      }
      else{
        //  this.form.getFieldDecorator("auditTotalnum");
        //  let auditTotalnum= this.form.getFieldValue("auditTotalnum")
         if(parseFloat(auditTotalnum)>0){
           this.form.setFieldsValue({ cdzs: (cdzs/auditTotalnum).toFixed(2) });
         }
           else{
          this.form.setFieldsValue({ cdzs: cdzs.toFixed(2) });
         }
      }
   },

     inputCauseChange(cause){
       this.form.getFieldDecorator("jxzcsl");
       let wzlx= this.form.getFieldValue("jxzcsl")
       this.form.getFieldDecorator("djzz");
       let djzz= this.form.getFieldValue("djzz")

         this.form.getFieldDecorator("auditTotalnum");
         let auditTotalnum= this.form.getFieldValue("auditTotalnum")
        this.calcResult(cause,djzz,wzlx,auditTotalnum);
      },
    calcWzfs(sci) {
      if (sci == "" || sci == undefined || sci == null) {
        return 0;
      }
      let sci_num = parseFloat(sci);
      if (sci_num > 15) {
        return 15 * 5 + (sci_num - 15) * 6;
      }
      if (sci_num > 10 && sci_num <= 15) {
        return 5 * sci_num - 10;
      }
      if (sci_num > 5 && sci_num <= 10) {
        return 4 * sci_num - 5;
      }
      if (sci_num > 3 && sci_num <= 5) {
        return 3 * sci_num - 3;
      }
      if (sci_num <= 3) {
        return 2 * sci_num;
      }
    },
changeAuditTotalnum(auditTotalnum){
   this.form.getFieldDecorator("paperCause");
      let cause = this.form.getFieldValue("paperCause")
      this.form.getFieldDecorator("jxzcsl");
       let wzlx= this.form.getFieldValue("jxzcsl")
 this.form.getFieldDecorator("djzz");
       let djzz= this.form.getFieldValue("djzz")
        
        this.calcResult(cause,djzz,wzlx,auditTotalnum);
},
    changeDjzz(djzz){

this.form.getFieldDecorator("paperCause");
      let cause = this.form.getFieldValue("paperCause")
      this.form.getFieldDecorator("jxzcsl");
       let wzlx= this.form.getFieldValue("jxzcsl")

         this.form.getFieldDecorator("auditTotalnum");
         let auditTotalnum= this.form.getFieldValue("auditTotalnum")
        this.calcResult(cause,djzz,wzlx,auditTotalnum);
    },
    changeWzlx(wzlx) {
      this.form.getFieldDecorator("paperCause");
      let cause = this.form.getFieldValue("paperCause")
       this.form.getFieldDecorator("djzz");
       let djzz= this.form.getFieldValue("djzz")

         this.form.getFieldDecorator("auditTotalnum");
         let auditTotalnum= this.form.getFieldValue("auditTotalnum")
        this.calcResult(cause,djzz,wzlx,auditTotalnum);
      
    },
    fetchJb() {
      this.$get("dcaBSciencepublish/jbLb", {}).then((r) => {
        this.jbLbList = r.data;
      });
    },
    handleAuditNext () {
      let that = this
      
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将进入下一个审核人',
        centered: true,
        onOk () {
           that.setFields()
           console.info(that.dcaBSciencepublish.auditState)
         let jsonStr = JSON.stringify({...that.dcaBSciencepublish})
          that.loading = true
          that.$post('dcaBDocSciencepublish/updateNew', {
            jsonStr: jsonStr,
            state: 1,
            auditState: that.dcaBSciencepublish.auditState
          }).then(() => {
            //this.reset()
            that.loading = false
            that.reset();
            that.$emit("success");
            
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleSave () {
      let that = this
      this.$confirm({
        title: '确定保存通过此记录?',
        content: '当您点击确定按钮后，此记录将保存',
        centered: true,
        onOk () {
           that.setFields()
          let jsonStr = JSON.stringify({...that.dcaBSciencepublish})
          that.loading = true
          that.$post('dcaBDocSciencepublish/updateNew', {
            jsonStr: jsonStr,
            state:  that.dcaBSciencepublish.state,
            auditState: -1
          }).then(() => {
            that.loading = false
            that.reset();
            that.$emit("success");
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAudit () {
      let that = this
      this.$confirm({
        title: '确定审核通过此记录?',
        content: '当您点击确定按钮后，此记录将审核通过',
        centered: true,
        onOk () {
           that.setFields()
          let jsonStr = JSON.stringify({...that.dcaBSciencepublish})
          that.loading = true
          that.$post('dcaBDocSciencepublish/updateNew', {
            jsonStr: jsonStr,
            state: 3,
            auditState: -1
          }).then(() => {
             that.loading = false
             that.reset();
             that.$emit("success");
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleAuditNo () {
      let that = this
      this.$confirm({
        title: '确定审核不通过此记录?',
        content: '当您点击确定按钮后，此记录将审核不通过',
        centered: true,
        onOk () {
           that.setFields()
          let jsonStr = JSON.stringify({...that.dcaBSciencepublish})
          that.loading = true
          that.$post('dcaBDocSciencepublish/updateNew', {
            jsonStr: jsonStr,
            state: 2,
            auditState: 0
          }).then(() => {
            that.loading = false
            that.reset();
            that.$emit("success");
          }).catch(() => {
            that.loading = false
          })
        },
        onCancel () {
        }
      })
    },
    handleSubmit(state) {
        this.form.validateFields((err, values) => {
          if (!err) {
            this.setFields();
            
            this.$post("dcaBDocSciencepublish/update", {
              ...this.dcaBSciencepublish,
              state: state,
              auditState: 0,
            })
              .then((r) => {
                this.reset();
                this.$emit("success");
              })
              .catch(() => {
                this.loading = false;
              });
          }
        });
    },
    setFields() {
      let values = this.form.getFieldsValue([
        "paperName",
        "journalName",
        "journalCode",
        "paperPublishdate",
        "wzlx",
        "djzz",
        "qkjb",
        "cdzs",
        "paperShoulu",
        "paperCause",
        "isBest",
        "authorRank",
        "otherTimes",
        "xsd",
        "isPermit",
        "sciValue",
        "rankValue",
        "isJxzcsb",
        "isLczcsb",
        "auditQkjb",
        "jxzcsl",
        "lczcsl",
        "auditTotalnum",
        "auditIsfirst",
        "isUse",
        "auditSuggestion",
        "rankSear",
        "firstUnitAuthor",
        "state"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.dcaBSciencepublish[_key] = values[_key];
        });
      }
    },
    setFormValues({ ...dcaBSciencepublish }) {
    
      let fields = [
        "userAccountName",
        "userAccount",
        "paperName",
        "journalName",
        "journalCode",
        "paperPublishdate",
        "wzlx",
        "djzz",
        "qkjb",
        "cdzs",
        "paperShoulu",
        "paperCause",
        "xsd",
        "authorRank",
        "otherTimes",
        "isPermit",
        "sciValue",
        "rankValue",
        "isBest",
         "isJxzcsb",
        "isLczcsb",
        "auditQkjb",
        "jxzcsl",
        "lczcsl",
        "auditTotalnum",
        "auditIsfirst",
        "isUse",
        "auditSuggestion",
        "rankSear",
        "firstUnitAuthor"
      ];
      let fieldDates = ["paperPublishdate"];
      Object.keys(dcaBSciencepublish).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBSciencepublish[key] !== "") {
              obj[key] = moment(dcaBSciencepublish[key]);
            } else {
              obj[key] = "";
            }
          } else {
            if (key == "authorRank") {
              if (
                dcaBSciencepublish[key] !== null &&
                dcaBSciencepublish[key] !== ""
              ) {
                
                if(dcaBSciencepublish[key].indexOf("]")>0){
                  obj[key]= JSON.parse(dcaBSciencepublish[key].toString())
                }
                else{
                  obj[key]= dcaBSciencepublish[key].toString().split(",")
                }
                
              }
            } else {
              obj[key] = dcaBSciencepublish[key];
            }
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBSciencepublish.id = dcaBSciencepublish.id;
      this.state= dcaBSciencepublish.state;
      this.dcaBSciencepublish.state = dcaBSciencepublish.state;
    
      let that = this;
      setTimeout(() => {
        that.$refs.fileagent3.setForm(dcaBSciencepublish.fileId);
      }, 500);
       setTimeout(()=>{
          that.$refs.seek.editSearch(dcaBSciencepublish.paperName)
        },500)
    },
   
    getSearchValue(dcaSearch) {

    },
  },
};
</script>
<style lang="less" scoped >
</style>
