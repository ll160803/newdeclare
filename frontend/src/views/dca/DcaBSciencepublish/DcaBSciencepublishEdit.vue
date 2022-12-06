<template>
  <a-drawer
    title="编辑查看"
    :maskClosable="false"
    width="90%"
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
          ref="seek"
        ></sci-seek>
      </a-col>
    </a-row>
    <a-card title="论文信息" v-if="isShow" :headStyle="{
        fontWeight: 'bold',
        fontSize: '20px'
      }">
      <a-form :form="form" layout="vertical">
        <a-row
          >
           <a-col :span="7"  >
            <a-form-item label="智能搜索相似度" :style="{fontWeight: 'bold'}">
            <a-input
            :disabled="true"
             v-decorator="[
                  'xsd',
                 
                ]"
          />
          </a-form-item></a-col
          ><a-col :span="7" :offset="1" >
            <a-form-item label="是否智能搜索结果" :style="{fontWeight: 'bold'}">
            <a-input
            :disabled="true"
             v-decorator="[
                  'isPermit',
                 
                ]"
          />
         
          </a-form-item></a-col
          ><a-col :span="7"  :offset="1">
            <a-form-item label="手工录入" :style="{fontWeight: 'bold'}">
              <a-button @click="handleLogin" type="primary" 
          >手工录入</a-button
        > <span style="color:red;font-size:20px;"> 如果没有智能搜索到，请点击手工录入按钮</span>
             </a-form-item></a-col
          >
         </a-row>
        <a-row
          ><a-col :span="23" >
            <a-form-item label="论文名">
              <a-input
                :style="{fontWeight: 'bold'}"
                placeholder="请输入论文名"
                :disabled="isNoEdit"
                v-decorator="[
                  'paperName',
                  { rules: [{ required: true, message: '论文名不能为空' }] },
                ]"
              /> </a-form-item></a-col
          ></a-row><a-row><a-col :span="7" >
            <a-form-item label="期刊名">
              <a-input
                placeholder="请输入期刊名"
                 :disabled="isNoEdit"
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
          ><a-col :span="7" :offset="1" >
            <a-form-item label="发表年月">
              <a-date-picker
               :disabled-date="disabledDate"
                v-decorator="[
                  'paperPublishdate',
                  { rules: [{ required: true, message: '发表年月不能为空' }] },
                ]"
              /> </a-form-item></a-col
          ></a-row><a-row><a-col :span="7" >
            <a-form-item label="期刊级别">
              <a-select
                placeholder="请输入期刊级别"
                 :disabled="isNoEdit"
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
                 :disabled="isNoEdit"
                v-decorator="[
                  'paperShoulu',
                 
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1" >
            <a-form-item label="影响因子">
              <a-input
                placeholder="请输入影响因子"
                 :disabled="isNoEdit"
                v-decorator="[
                  'paperCause',
                
                ]"
              /> </a-form-item></a-col
          ></a-row><a-row><a-col :span="7" >
            <a-form-item label="是否一流期刊" >
              <a-select
                style="width: 100%"
               :disabled="isNoEdit"
                placeholder="请输入是否一流期刊"
                v-decorator="[
                  'isBest',
                  
                  {
                    rules: [
                     
                    ]
                
                  },
                ]"
              >
              <a-select-option value="是">是</a-select-option>
              <a-select-option value="否">否</a-select-option>
              </a-select> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="是否SCI"  >
                <a-select
                style="width: 100%"
                :disabled="isNoEdit"
                placeholder="请输入是否SCI"
                v-decorator="[
                  'sciValue',
                  {
                    rules: [
                      
                    ]
                  },
                ]"
              >
                <a-select-option value="是">是</a-select-option>
              <a-select-option value="否">否</a-select-option>
              </a-select> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="rank值(百分制)">
              <a-input-number
                :disabled="isNoEdit"
                placeholder="请输入rank值(百分制)"
                style="width: 100%"
                v-decorator="[
                  'rankValue',
                  {
                    rules: [
                     
                    ],
                  },
                ]"
                :precision="3"
              >
              </a-input-number> </a-form-item>
            </a-col
          ></a-row><a-row>
            <a-col :span="7" >
            <a-form-item label="rank值原始值">
              <a-input
              :disabled="isNoEdit"
                placeholder="请输入rank值原始值"
                v-decorator="[
                  'rankSear',
                 
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="他引次数">
              <a-input
              :disabled="isNoEdit"
                placeholder="请输入他引次数"
                v-decorator="[
                  'otherTimes',
                 
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1" >
            <a-form-item label="期刊类型">
              <a-select
                placeholder="请输入期刊类型"
                v-decorator="[
                  'wzlx',
                   { rules: [{ required: true, message: '期刊类型不能为空' }] },
                ]"
                style="width: 100%"
              >
                <a-select-option value="科研"> 科研 </a-select-option>
                <a-select-option value="教学"> 教学 </a-select-option>
              </a-select>
            </a-form-item>
            </a-col
          ></a-row><a-row>
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
              </a-select>
            </a-form-item></a-col
          >
          <a-col :span="7" :offset="1">
            <a-form-item label="第几作者">
              <a-input
                placeholder="请输入第几作者"
                v-decorator="[
                  'djzz',
                 
                ]"
              /> </a-form-item
          ></a-col>
          
        </a-row>
        <a-row><a-col :sm="10" >
        <a-form-item label="附件材料">
          <upload-single-file
            ref="fileagent3"
            @uploadRemove="removeAgent_3"
            @uploadSuc="uploadAgent_3"
          >
          </upload-single-file>
          <span style="color:red">英文文章：上传正文+检索报告，中文文章：上传封面、目录、全文、封底，请合并为一个PDF文件</span>
        </a-form-item>
      </a-col></a-row>
      </a-form>
    </a-card>
    <div class="drawer-bootom-button" >
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: 3rem">取消</a-button>
      </a-popconfirm>
       <a-button @click="handleSubmit(0)" type="primary" v-if="state==0||state==2" :loading="loading"
        >保存草稿不提交</a-button
      >
      <a-button @click="handleSubmit(1)" type="primary" v-if="state==0||state==2" :loading="loading"
        >直接提交</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import SciSeek from "./SciSeek.vue";
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
      pulishDate: '',
      isShow: true,
      isNoEdit: true,
      id: '',
      state: 1
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
    handleLogin(){
       //this.dcaBSciencepublish.isPermit= '否';
       this.form.getFieldDecorator("isPermit");
       this.form.setFieldsValue({isPermit: '否'});
       this.dcaBSciencepublish.auditQkjb= ''
       this.isNoEdit = false;
    },
    reset() {
      this.loading = false;
      this.dcaBSciencepublish = {};
      this.dcaSearch = {};
      this.isShow =true;
      this.isNoEdit = true;
      console.info(222)
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
    inputPermitCheckChange(e){
       if(e=='是'){
        this.isNoEdit = true;
        this.setFormValues(this.dcaSearch);
       }
       else{
          this.isNoEdit = false;
       }
    },
    fetchJb() {
      this.$get("dcaBSciencepublish/jbLb", {}).then((r) => {
        this.jbLbList = r.data;
      });
    },
    handleSubmit(state) {
       if( this.dcaBSciencepublish.fileId=='' ||this.dcaBSciencepublish.fileId==null){
          this.$message.warning('请上传论文附件')
        return
      }
      else{
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          

          this.$post("dcaBSciencepublish/update", {
            ...this.dcaBSciencepublish,
            state: state,
            auditState: 0
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
      }
    },
    disabledDate(current){
      console.info(moment(this.pulishDate).endOf('month').format('YYYY-MM-DD'),'5555555')
       if(this.isNoEdit){
          return !(moment(current).format('YYYY-MM-DD')>=this.pulishDate && moment(current).format('YYYY-MM-DD') <= moment(this.pulishDate).endOf('month').format('YYYY-MM-DD'));
       }
       return false;
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
        "auditState",
        "rankSear"
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.dcaBSciencepublish[_key] = values[_key];
        });
      }
    },
    setFormValues({ ...dcaBSciencepublish }) {
      if(dcaBSciencepublish.isPermit==null ||dcaBSciencepublish.isPermit==''){
        dcaBSciencepublish.isPermit= '否';
      }
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
         "rankSear",
         "state"
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
            if(key=='authorRank'){
              if(dcaBSciencepublish[key]!==null && dcaBSciencepublish[key]!==''){
                 obj[key]= dcaBSciencepublish[key].split(",")
              }
              
            }
            else{
            obj[key] = dcaBSciencepublish[key];
            }
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBSciencepublish.id = dcaBSciencepublish.id;
      this.isNoEdit= dcaBSciencepublish.isPermit=='是'?true: false;
      this.pulishDate= dcaBSciencepublish.isPermit=='是'? dcaBSciencepublish.paperPublishdate: ''
      this.state= dcaBSciencepublish.state;
      this.dcaBSciencepublish.fileId = dcaBSciencepublish.fileId;
      this.dcaBSciencepublish.fileUrl = dcaBSciencepublish.fileUrl;
     let that= this;
      setTimeout(()=>{
        that.$refs.fileagent3.setForm(dcaBSciencepublish.fileId);
      },500)
    },
     setFormValues2({ ...dcaBSciencepublish }) {
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
         "rankSear"
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
            if(key=='authorRank'){
              if(dcaBSciencepublish[key]!==null && dcaBSciencepublish[key]!==''){
                if(dcaBSciencepublish[key].indexOf("]")>0){
                  obj[key]= JSON.parse(dcaBSciencepublish[key].toString())
                }
                else{
                  obj[key]= dcaBSciencepublish[key].toString().split(",")
                }
              }
              
            }
            else{
            obj[key] = dcaBSciencepublish[key];
            }
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.isNoEdit= dcaBSciencepublish.isPermit=='是'?true: false;
    },
    getSearchValue(dcaSearch) {
      // this.dcaBSciencepublish = {
      //    fileId: ''
      // };
      this.dcaSearch = {};
      this.isNoEdit = true;
      this.form.resetFields();
      this.isShow = true
      this.$refs.seek.setIsShow();
      if(dcaSearch.xsd!==''&&dcaSearch.xsd>0.9){
        this.dcaBSciencepublish.isPermit ='是';
        this.dcaBSciencepublish.xsd= dcaSearch.xsd;
        if(this.dcaSearch.qkjb!=''){
            this.dcaBSciencepublish.auditQkjb= dcaSearch.qkjb
          }
        this.pulishDate= dcaSearch.paperPublishdate;
        dcaSearch.isPermit  ='是';
        this.dcaSearch = { ...dcaSearch };
        this.setFormValues2(dcaSearch);
      }
      else{
         this.isNoEdit = false;
        
         this.dcaBSciencepublish.auditQkjb= ''
      }
      
    },
  },
};
</script>
<style lang="less" scoped >
   
   
</style>
