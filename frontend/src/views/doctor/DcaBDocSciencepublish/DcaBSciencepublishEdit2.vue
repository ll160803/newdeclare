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
          <a-col :span="7">
            <a-form-item label="排第几">
              <a-input
                placeholder="请输入排第几"
                v-decorator="['djzz']"
              /> </a-form-item
          ></a-col>
          <a-col :span="7" :offset="1">
           <a-form-item label="第一作者第一单位">
              <a-input
                placeholder="请输入第一作者第一单位"
                v-decorator="['firstUnitAuthor']"
              /> </a-form-item
          ></a-col
          >
           <a-col :span="7" :offset="1">
           <a-form-item label="第一或通讯作者">
              <a-input
                placeholder="请输入第一或通讯作者"
                v-decorator="['authorRank']"
              /> </a-form-item
          ></a-col
          ></a-row>
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
                
                  obj[key]= (dcaBSciencepublish[key].toString()).split(",")
                
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
