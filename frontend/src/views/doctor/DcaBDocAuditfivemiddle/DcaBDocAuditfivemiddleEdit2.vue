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
    <a-form :form="form" layout=""horizontal>
      
     
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
            'khDate',
            { rules: [{ required: true, message: '填写日期不能为空' }] },
          ]"
        />
      </a-form-item>
      <a-form-item  label="出站考核总结">
        <a-textarea
          :rows="4"
          placeholder="请输入出站考核总结"
          v-decorator="[
            'czkhzj',
            { rules: [{ required: true, message: '出站考核总结不能为空' }] },
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
        >查看</a-button
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
  name: "DcaBDocAuditfivemiddleEdit",
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
      dcaBDocAuditfivemiddle: {},
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
    setFormValues({ ...dcaBDocAuditfivemiddle }) {
      let fields = [
        "khjg",
        "khtype",
      
        "userAccountName",
        "userAccount",
        "khDate",
        "czkhzj",
        "adContent",
      ];
      let fieldDates = ["khDate"];
      Object.keys(dcaBDocAuditfivemiddle).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocAuditfivemiddle[key] !== "") {
              obj[key] = moment(dcaBDocAuditfivemiddle[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = dcaBDocAuditfivemiddle[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBDocAuditfivemiddle.id = dcaBDocAuditfivemiddle.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocAuditfivemiddle = this.form.getFieldsValue();
          dcaBDocAuditfivemiddle.id = this.dcaBDocAuditfivemiddle.id;
          this.$put("dcaBDocAuditfivemiddle", {
            ...dcaBDocAuditfivemiddle,
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
