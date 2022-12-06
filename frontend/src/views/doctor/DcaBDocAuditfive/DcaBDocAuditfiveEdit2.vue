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
    <a-form :form="form" layout="horizontal" >
      <a-form-item  label="考核结果">
        <a-input
          placeholder="请输入考核结果"
          v-decorator="[
            'khjg',
          
          ]"
        />
      </a-form-item>
      <a-form-item  label="考核年度">
        <a-input
          placeholder="请输入考核年度"
          v-decorator="[
            'year',
        
          ]"
        />
      </a-form-item>
    
      <a-form-item  label="政治表现、职业道德、廉洁自律、业务能力工作态度总结">
        <a-textarea
          :rows="4"
          placeholder="请输入政治表现"
          v-decorator="[
            'zzbx',
           
          ]"
        />
      </a-form-item>
      <!-- <a-form-item  label="职业道德">
        <a-textarea
          placeholder="请输入职业道德"
          v-decorator="[
            'zydd',
           
          ]"
        />
      </a-form-item>
      <a-form-item  label="廉洁自律">
        <a-textarea
          placeholder="请输入廉洁自律"
          v-decorator="[
            'ljzl',
           
          ]"
        />
      </a-form-item>
      <a-form-item  label="业务能力工作态度总结">
        <a-textarea
          placeholder="请输入业务能力工作态度总结"
          v-decorator="[
            'ywnlgztd',
            {
              rules: [
               
              ],
            },
          ]"
        />
      </a-form-item> -->
      <a-form-item
        
        label="个人科研进展较为详细的成果总结"
      >
        <a-textarea
        :rows="4"
          placeholder="请输入个人科研进展较为详细的成果总结"
          v-decorator="[
            'grkyjz',
            {
              rules: [
                {
               
                },
              ],
            },
          ]"
        />
      </a-form-item>
      <a-form-item  label="下年度科研课题较为详细计划">
        <a-textarea
        :rows="4"
          placeholder="请输入下年度科研课题较为详细计划"
          v-decorator="[
            'xndkykt',
            {
              rules: [
                {
                
                },
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
  name: "DcaBDocAuditfiveEdit",
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
      dcaBDocAuditfive: {},
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
    setFormValues({ ...dcaBDocAuditfive }) {
      let fields = [
        "khjg",
        "year",
        "userAccountName",
        "userAccount",
        "zzbx",
        "zydd",
        "ljzl",
        "ywnlgztd",
        "grkyjz",
        "xndkykt",
        "adContent",
      ];
      let fieldDates = [];
      Object.keys(dcaBDocAuditfive).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key);
          let obj = {};
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBDocAuditfive[key] !== "") {
              obj[key] = moment(dcaBDocAuditfive[key]);
            } else {
              obj[key] = "";
            }
          } else {
            obj[key] = dcaBDocAuditfive[key];
          }
          this.form.setFieldsValue(obj);
        }
      });
      this.dcaBDocAuditfive.id = dcaBDocAuditfive.id;
    },
    handleSubmit() {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBDocAuditfive = this.form.getFieldsValue();
          dcaBDocAuditfive.id = this.dcaBDocAuditfive.id;
          this.$put("dcaBDocAuditfive", {
            ...dcaBDocAuditfive,
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
