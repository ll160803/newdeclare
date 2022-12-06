<template>
  <a-drawer
    title="查看"
    :maskClosable="false"
    width="80%"
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px); overflow: auto; padding-bottom: 53px"
  >
    <a-form :form="form" layout="horizontal">
      <!-- <a-form-item  label="考核结果">
        <a-input
          placeholder="请输入考核结果"
          v-decorator="[
            'khjg',
            { rules: [{ required: true, message: '考核结果不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="考核类型">
        <a-input
          placeholder="请输入考核类型"
          v-decorator="[
            'khtype',
            { rules: [{ required: true, message: '考核类型不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="考核时间">
        <a-date-picker
          format="YYYY-MM-DD"
          v-decorator="[
            'khdate',
            { rules: [{ required: true, message: '考核时间不能为空' }] },
          ]"
        />
      </a-form-item> -->
      <a-form-item  label="姓名">
        <a-input
          placeholder="请输入姓名"
          v-decorator="[
            'userAccountName',
            { rules: [{ required: true, message: '姓名不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="人事编号">
        <a-input
          placeholder="请输入人事编号"
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人事编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="填写日期">
        <a-date-picker
          v-decorator="[
            'txDate',
            { rules: [{ required: true, message: '填写日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="成果概述">
        <a-textarea
          :rows="4"
          placeholder="请输入成果概述"
          v-decorator="[
            'cggs',
          
          ]"
        />
      </a-form-item>
      <a-form-item  label="主持研究的项目名称及研究计划">
        <a-textarea
        :rows="4"
          placeholder="请输入主持研究的项目名称及研究计划"
          v-decorator="[
            'zcyjxmyj',
            {
              rules: [
                {
                
                },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item  label="博士后本人中期工作小结">
        <a-textarea
        :rows="4"
          placeholder="请输入博士后本人中期工作小结"
          v-decorator="[
            'bshbrzqxj',
            {
              rules: [
                { required: true, message: '博士后本人中期工作小结不能为空' },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item  label="备注">
        <a-textarea
        :rows="4"
          placeholder="请输入备注"
          v-decorator="[
            'adContent',
            { rules: [{ required: true, message: '备注不能为空' }] },
          ]"
        />
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      
      <a-button @click="onClose" type="primary" :loading="loading"
        >关闭</a-button
      >
    </div>
  </a-drawer>
</template>
<script>
import moment from "moment";

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 },
};
export default {
  name: "DcaBDocAuditfiveotherEdit",
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
      dcaBDocAuditfiveother: {},
    };
  },
  methods: {
    reset() {
      this.loading = false;
      this.form.resetFields();
    },
    onClose() {
      this.reset();
      this.$emit("close");
    },
    setFormValues({ ...dcaBDocAuditfiveother }) {
      let fields = [
        "khjg",
        "khtype",
        "khdate",
        "userAccountName",
        "userAccount",
        "txDate",
        "cggs",
        "zcyjxmyj",
        "bshbrzqxj",
        "adContent",
      ];
      let fieldDates = ["khdate", "txDate"];
      Object.keys(dcaBDocAuditfiveother).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocAuditfiveother[key] !== "") {
              obj[key] = moment(dcaBDocAuditfiveother[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = dcaBDocAuditfiveother[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBDocAuditfiveother.id = dcaBDocAuditfiveother.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocAuditfiveother = this.form.getFieldsValue();
          dcaBDocAuditfiveother.id = this.dcaBDocAuditfiveother.id;
          this.$put("dcaBDocAuditfiveother", {
            ...dcaBDocAuditfiveother,
          })
            .then(() => {
              this.reset();
              this.$emit("success");
            })
            .catch(() => {
              this.loading = false;
            });
        }
      });
    },
  },
};
</script>
