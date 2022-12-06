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
        <a-row>
          <a-col :span="7">
            <a-form-item label="智能搜索相似度" :style="{ fontWeight: 'bold' }">
              <a-input
                :disabled="true"
                v-decorator="[
                  'xsd',
                  { rules: [{ required: true, message: '智能搜索相似度' }] },
                ]"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item
              label="是否智能搜索结果数据"
              :style="{ fontWeight: 'bold' }"
            >
              <a-input
                :disabled="true"
                v-decorator="[
                  'isPermit',
                  {
                    rules: [
                      { required: true, message: '是否智能搜索结果数据' },
                    ],
                  },
                ]"
              /> </a-form-item
          ></a-col>
        </a-row>
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
            <a-form-item label="是否SCI">
              <a-select
                style="width: 100%"
                placeholder="请输入是否SCI"
                @change="(e1, f) => inputCheckChange(e1, f, 'sciValue')"
                v-decorator="[
                  'sciValue',
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
            <a-form-item label="rank值(百分制)">
              <a-input-number
                placeholder="请输入rank值(百分制)"
                style="width: 100%"
                v-decorator="[
                  'rankValue',
                  {
                    rules: [],
                  },
                ]"
                :precision="3"
              >
              </a-input-number>
            </a-form-item> </a-col></a-row
        ><a-row
          ><a-col :span="7">
            <a-form-item label="他引次数">
              <a-input
                placeholder="请输入他引次数"
                v-decorator="['otherTimes']"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
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
          </a-col>
          <a-col :span="7" :offset="1">
            <a-form-item label="rank值原始值">
              <a-input
                placeholder="请输入rank值原始值"
                v-decorator="['rankSear']"
              /> </a-form-item></a-col></a-row
        ><a-row>
          <a-col :span="7">
            <a-form-item label="第几作者">
              <a-input
                placeholder="请输入第几作者"
                v-decorator="['djzz']"
              /> </a-form-item
          ></a-col>
          <a-col :span="7" :offset="1">
            <a-form-item label="第一或通讯作者">
              <a-select
                style="width: 100%"
                mode="multiple"
                v-decorator="[
                  'authorRank',
                  {
                    rules: [],
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
          <a-col :sm="7" :offset="1">
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
      <a-card
        title="审核信息"
        :headStyle="{
          fontWeight: 'bold',
          fontSize: '20px',
        }"
      >
        <a-row>
          <a-col :span="7">
            <a-form-item
              label="是否能用于教学职称申报"
              style="font-weight: bold"
            >
              <a-select
                style="width: 100%"
                placeholder="请输入是否能用于教学职称申报"
                @change="(e1, f) => inputCheckChange(e1, f, 'isJxzcsb')"
                v-decorator="[
                  'isJxzcsb',

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
            <a-form-item
              label="是否能用于临床职称申报"
              style="font-weight: bold"
            >
              <a-select
                style="width: 100%"
                placeholder="请输入是否能用于临床职称申报"
                @change="(e1, f) => inputCheckChange(e1, f, 'isLczcsb')"
                v-decorator="[
                  'isLczcsb',

                  {
                    rules: [],
                  },
                ]"
              >
                <a-select-option value="是">是</a-select-option>
                <a-select-option value="否">否</a-select-option>
              </a-select>
            </a-form-item></a-col
          >
          <a-col :span="7" :offset="1">
            <a-form-item label="期刊级别(审核)" style="font-weight: bold">
              <a-select
                placeholder="请输入期刊级别"
                v-decorator="[
                  'auditQkjb',
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
          >
        </a-row>
        <a-row
          ><a-col :span="7">
            <a-form-item
              label="第一作者或通讯作者共几人"
              style="font-weight: bold"
            >
              <a-input-number
                placeholder="请输入第一作者或通讯作者共几人"
                @blur="(e) => inputChange(e.target.value, 'auditTotalnum')"
                v-decorator="['auditTotalnum']"
                :precision="0"
              >
              </a-input-number> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="教学职称文章认定数量" style="font-weight: bold">
              <a-input
                placeholder="请输入教学职称文章认定数量"
                v-decorator="['jxzcsl']"
              /> </a-form-item></a-col
          ><a-col :span="7" :offset="1">
            <a-form-item label="临床职称文章认定数量" style="font-weight: bold">
              <a-input
                placeholder="请输入临床职称文章认定数量"
                v-decorator="['lczcsl']"
              /> </a-form-item
          ></a-col> </a-row
        ><a-row
          ><a-col :span="7">
            <a-form-item label="非第一作者或通讯作者" style="font-weight: bold">
              <a-checkbox
                v-decorator="[
                  'auditIsfirst',
                  {
                    valuePropName: 'checked',
                  },
                ]"
              ></a-checkbox></a-form-item
          ></a-col>
          <a-col :span="7" :offset="1">
            <a-form-item
              label="经审核是否构成职称晋升条件"
              style="font-weight: bold"
            >
              <!-- <a-checkbox v-decorator="['isUse',{
                    valuePropName: 'checked'
                }]"></a-checkbox> -->
              <a-select
                style="width: 100%"
                placeholder="经审核是否构成职称晋升条件"
                v-decorator="[
                  'isUse',
                  {
                    rules: [],
                  },
                ]"
              >
                <a-select-option :value="true">是</a-select-option>
                <a-select-option :value="false">否</a-select-option>
              </a-select>
            </a-form-item>
          </a-col>
          <a-col :span="7" :offset="1">
            <a-form-item label="发表年月" style="font-weight: bold">
              <a-date-picker
                v-decorator="[
                  'paperPublishdate',
                  { rules: [{ required: true, message: '发表年月不能为空' }] },
                ]"
              />
            </a-form-item>
          </a-col>
        </a-row>
        <a-row
          ><a-col :span="7">
            <a-form-item label="审核意见" style="font-weight: bold">
              <a-textarea
                placeholder="请输入审核意见"
                v-decorator="['auditSuggestion']"
              >
              </a-textarea> </a-form-item></a-col
        ></a-row>
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
      <a-button
        v-if="dcaBSciencepublish.state == 1"
        type="primary"
        @click="handleAuditNext()"
      >
        下一轮
      </a-button>

      <a-button
        v-if="dcaBSciencepublish.state == 1"
        type="primary"
        @click="handleSave()"
      >
        保存
      </a-button>
      <a-button
        v-if="dcaBSciencepublish.state == 1"
        type="primary"
        @click="handleAudit()"
      >
        通过
      </a-button>
      <a-button
        v-if="dcaBSciencepublish.state == 1"
        type="danger"
        @click="handleAuditNo()"
      >
        审核不通过
      </a-button>
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
      console.info(222);
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
    inputCheckChange(blFlag, f, filedName) {
      if (filedName == "isJxzcsb") {
        if (blFlag == "是") {
          this.form.getFieldDecorator("jxzcsl");
          if (
            this.form.getFieldValue("auditTotalnum") != "" &&
            this.form.getFieldValue("auditTotalnum") != null
          ) {
            var gjr = parseInt(this.form.getFieldValue("auditTotalnum"));
            var num = parseFloat(1 / gjr);
            this.form.setFieldsValue({ jxzcsl: num.toFixed(3) });
          }
        } else {
          // record["jxzcsl"] = ''
        }
      }
      if (filedName == "sciValue") {
        console.info(this.dcaBSciencepublish["codejb"], 9999);
        this.form.getFieldDecorator("auditQkjb");
        if (blFlag == "是") {
          if (this.form.getFieldValue("rankValue") != "") {
            var rankValue = parseFloat(this.form.getFieldValue("rankValue"));
            let auditQkjb = "";
            if (rankValue > 50) {
              auditQkjb = "C";
            } else if (rankValue > 20 && rankValue <= 50) {
              auditQkjb = "B";
            } else if (rankValue <= 20) {
              auditQkjb = "A";
              //record["auditQkjb"]= 'A'
            }
            if (this.dcaBSciencepublish["codejb"] > auditQkjb) {
              auditQkjb = this.dcaBSciencepublish["codejb"];
            }
            this.form.setFieldsValue({ auditQkjb: auditQkjb });
          }
        } else {
          this.form.setFieldsValue({
            auditQkjb: this.dcaBSciencepublish["namejb"],
          });
        }
      }
      if (filedName == "isLczcsb") {
        this.form.getFieldDecorator("lczcsl");
        console.info(blFlag);
        if (blFlag == "是") {
          this.form.setFieldsValue({ lczcsl: "1" });
        } else {
          this.form.setFieldsValue({ lczcsl: "" });
        }
      }
    },
    inputChange(value, filedName) {
      if (filedName == "auditTotalnum") {
        this.form.getFieldDecorator("jxzcsl");
        if (this.form.getFieldValue("isJxzcsb") == "是") {
          if (value != "") {
            console.log("value:" + value);
            var gjr = parseInt(value.trim());
            var num = parseFloat(1 / gjr);

            this.form.setFieldsValue({ jxzcsl: num.toFixed(3) });
          }
        } else {
          this.form.setFieldsValue({ jxzcsl: "" });
        }
      }
      if (filedName == "rankValue") {
        this.form.getFieldDecorator("auditQkjb");
        if (this.form.getFieldValue("sciValue") == "是") {
          var rankValue = parseFloat(value);
          let auditQkjb = "";
          if (rankValue > 50) {
            auditQkjb = "C";
          } else if (rankValue > 20 && rankValue <= 50) {
            auditQkjb = "B";
          } else if (rankValue <= 20) {
            auditQkjb = "A";
            //record["auditQkjb"]= 'A'
          }
          if (this.dcaBSciencepublish["codejb"] > auditQkjb) {
            auditQkjb = this.dcaBSciencepublish["codejb"];
          }

          this.form.setFieldsValue({ auditQkjb: auditQkjb });
        } else {
          this.form.setFieldsValue({
            auditQkjb: this.dcaBSciencepublish["namejb"],
          });
        }
      }
    },
    inputPermitCheckChange(e) {
      if (e == "是") {
        this.isNoEdit = true;
        this.setFormValues(this.dcaSearch);
      } else {
        this.isNoEdit = false;
      }
    },
    fetchJb() {
      this.$get("dcaBSciencepublish/jbLb", {}).then((r) => {
        this.jbLbList = r.data;
      });
    },
    handleAuditNext() {
      let that = this;
      that.setFields();
      let flag = 0;
      if (parseFloat(that.dcaBSciencepublish.jxzcsl) > 1) {
        flag = 1;
        that.$message.warning("教学职称认定数量不能大于1");
      }
      if (parseFloat(that.dcaBSciencepublish.lczcsl) > 1) {
        flag = 1;
        that.$message.warning("临床职称认定数量不能大于1");
      }
      if (flag == 0) {
        this.$confirm({
          title: "确定审核通过此记录?",
          content: "当您点击确定按钮后，此记录将进入下一个审核人",
          centered: true,
          onOk() {
            console.info(that.dcaBSciencepublish.auditState);
            let jsonStr = JSON.stringify({ ...that.dcaBSciencepublish });
            that.loading = true;
            that
              .$post("dcaBSciencepublish/updateNew", {
                jsonStr: jsonStr,
                state: 1,
                auditState: that.dcaBSciencepublish.auditState,
              })
              .then(() => {
                //this.reset()
                that.loading = false;
                that.reset();
                that.$emit("success");
              })
              .catch(() => {
                that.loading = false;
              });
          },
          onCancel() {},
        });
      }
    },
    handleSave() {
      let that = this;
      this.$confirm({
        title: "确定保存通过此记录?",
        content: "当您点击确定按钮后，此记录将保存",
        centered: true,
        onOk() {
          that.setFields();
          let jsonStr = JSON.stringify({ ...that.dcaBSciencepublish });
          that.loading = true;
          that
            .$post("dcaBSciencepublish/updateNew", {
              jsonStr: jsonStr,
              state: that.dcaBSciencepublish.state,
              auditState: -1,
            })
            .then(() => {
              that.loading = false;
              that.reset();
              that.$emit("success");
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleAudit() {
      let that = this;
      that.setFields();
      let flag = 0;
      if (parseFloat(that.dcaBSciencepublish.jxzcsl) > 1) {
        flag = 1;
        that.$message.warning("教学职称认定数量不能大于1");
      }
      if (parseFloat(that.dcaBSciencepublish.lczcsl) > 1) {
        flag = 1;
        that.$message.warning("临床职称认定数量不能大于1");
      }
      if (flag == 0) {
        this.$confirm({
          title: "确定审核通过此记录?",
          content: "当您点击确定按钮后，此记录将审核通过",
          centered: true,
          onOk() {
            // that.setFields()
            let jsonStr = JSON.stringify({ ...that.dcaBSciencepublish });
            that.loading = true;
            that
              .$post("dcaBSciencepublish/updateNew", {
                jsonStr: jsonStr,
                state: 3,
                auditState: -1,
              })
              .then(() => {
                that.loading = false;
                that.reset();
                that.$emit("success");
              })
              .catch(() => {
                that.loading = false;
              });
          },
          onCancel() {},
        });
      }
    },
    handleAuditNo() {
      let that = this;
      this.$confirm({
        title: "确定审核不通过此记录?",
        content: "当您点击确定按钮后，此记录将审核不通过",
        centered: true,
        onOk() {
          that.setFields();
          let jsonStr = JSON.stringify({ ...that.dcaBSciencepublish });
          that.loading = true;
          that
            .$post("dcaBSciencepublish/updateNew", {
              jsonStr: jsonStr,
              state: 2,
              auditState: 0,
            })
            .then(() => {
              that.loading = false;
              that.reset();
              that.$emit("success");
            })
            .catch(() => {
              that.loading = false;
            });
        },
        onCancel() {},
      });
    },
    handleSubmit(state) {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.setFields();
          let flag = 0;
          if (parseFloat(this.dcaBSciencepublish.jxzcsl) > 1) {
            flag = 1;
            this.$message.warning("教学职称认定数量不能大于1");
          }
          if (parseFloat(this.dcaBSciencepublish.lczcsl) > 1) {
            flag = 1;
            this.$message.warning("临床职称认定数量不能大于1");
          }
          if (flag == 0) {
            this.$post("dcaBSciencepublish/update", {
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
      ]);
      if (typeof values !== "undefined") {
        Object.keys(values).forEach((_key) => {
          this.dcaBSciencepublish[_key] = values[_key];
        });
      }
    },
    setFormValues({ ...dcaBSciencepublish }) {
      if (
        dcaBSciencepublish.isPermit == null ||
        dcaBSciencepublish.isPermit == ""
      ) {
        dcaBSciencepublish.isPermit = "否";
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
                if (dcaBSciencepublish[key].indexOf("]") > 0) {
                  obj[key] = JSON.parse(dcaBSciencepublish[key].toString());
                } else {
                  obj[key] = dcaBSciencepublish[key].toString().split(",");
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
      this.isNoEdit = dcaBSciencepublish.isPermit == "是" ? true : false;
      this.dcaBSciencepublish.auditState = dcaBSciencepublish.auditState;
      this.dcaBSciencepublish.state = dcaBSciencepublish.state;
      console.info(dcaBSciencepublish.codejb, "9999999999");
      this.dcaBSciencepublish["codejb"] = dcaBSciencepublish.codejb;
      this.dcaBSciencepublish["namejb"] = dcaBSciencepublish.namejb;
      let that = this;
      setTimeout(() => {
        that.$refs.fileagent3.setForm(dcaBSciencepublish.fileId);
      }, 500);
      setTimeout(() => {
        that.$refs.seek.editSearch(dcaBSciencepublish.paperName);
      }, 500);
    },

    getSearchValue(dcaSearch) {},
  },
};
</script>
<style lang="less" scoped >
</style>
