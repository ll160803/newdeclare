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
      <a-form-item label="考核结果">
        <a-input
          placeholder="请输入考核结果"
          v-decorator="[
            'khjg',
            { rules: [{ required: true, message: '考核结果不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="考核时间">
        <a-input
          placeholder="请输入考核时间"
          v-decorator="[
            'year',
            { rules: [{ required: true, message: '考核时间不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="姓名">
        <a-input
          placeholder="请输入姓名"
          v-decorator="[
            'userAccountName',
            { rules: [{ required: true, message: '姓名不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="人事编号">
        <a-input
          placeholder="请输入人事编号"
          v-decorator="[
            'userAccount',
            { rules: [{ required: true, message: '人事编号不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="成果概述">
        <a-textarea
        :rows="4"
          placeholder="请输入成果概述"
          v-decorator="[
            'cggs',
            { rules: [{ required: true, message: '成果概述不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="思想政治表现">
        <a-textarea
        :rows="4"
          placeholder="请输入思想政治表现"
          v-decorator="[
            'sxzzbx',
            { rules: [{ required: true, message: '思想政治表现不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="考勤状况">
        <a-textarea
        :rows="4"
          placeholder="请输入考勤状况"
          v-decorator="[
            'kqzk',
            { rules: [{ required: true, message: '考勤状况不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="工作态度">
        <a-textarea
        :rows="4"
          placeholder="请输入工作态度"
          v-decorator="[
            'gztd',
            { rules: [{ required: true, message: '工作态度不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="工作进展">
        <a-textarea
        :rows="4"
          placeholder="请输入工作进展"
          v-decorator="[
            'gzjz',
            { rules: [{ required: true, message: '工作进展不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item label="备注">
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
  name: "DcaBDocAuditfivemonthEdit",
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
      dcaBDocAuditfivemonth: {},
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
    setFormValues({ ...dcaBDocAuditfivemonth }) {
      let fields = [
        "khjg",
        "year",
        "userAccountName",
        "userAccount",
        "cggs",
        "sxzzbx",
        "kqzk",
        "gztd",
        "gzjz",
        "adContent",
      ];
      let fieldDates = [];
      Object.keys(dcaBDocAuditfivemonth).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocAuditfivemonth[key] !== "") {
              obj[key] = moment(dcaBDocAuditfivemonth[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = dcaBDocAuditfivemonth[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBDocAuditfivemonth.id = dcaBDocAuditfivemonth.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocAuditfivemonth = this.form.getFieldsValue();
          dcaBDocAuditfivemonth.id = this.dcaBDocAuditfivemonth.id;
          this.$put("dcaBDocAuditfivemonth", {
            ...dcaBDocAuditfivemonth,
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
