<template>
  <a-drawer
    title="修改"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;"
  >
    <a-form :form="form">
      <a-form-item
        v-bind="formItemLayout"
        label="人事编号"
      >
        <a-input
          placeholder="请输入人事编号"
          v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="开始时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'jzStartTime', {rules: [{ required: true, message: '开始时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="结束时间"
      >
        <a-date-picker
          showTime
          format='YYYY-MM-DD HH:mm:ss'
          v-decorator="[ 'jzEndTime', {rules: [{ required: true, message: '结束时间不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="工作内容"
      >
        <a-input
          placeholder="请输入工作内容"
          v-decorator="['jzContent', {rules: [{ required: true, message: '工作内容不能为空' }] }]"
        />
      </a-form-item>
      <a-form-item
        v-bind="formItemLayout"
        label="状态"
      >
        <a-input
          placeholder="请输入状态"
          v-decorator="['jzState', {rules: [{ required: true, message: '状态不能为空' }] }]"
        />
      </a-form-item>
    </a-form>
    <div class="drawer-bootom-button">
      <a-popconfirm
        title="确定放弃编辑？"
        @confirm="onClose"
        okText="确定"
        cancelText="取消"
      >
        <a-button style="margin-right: .8rem">取消</a-button>
      </a-popconfirm>
      <a-button
        @click="handleSubmit"
        type="primary"
        :loading="loading"
      >提交</a-button>
    </div>
  </a-drawer>
</template>
<script>
import moment from 'moment'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'DcaBParttimejobEdit',
  props: {
    editVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      dcaBParttimejob: {}
    }
  },
  methods: {
    reset () {
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    setFormValues ({ ...dcaBParttimejob }) {
      let fields = ['userAccount', 'jzStartTime', 'jzEndTime', 'jzContent', 'jzState']
      let fieldDates = ['jzStartTime', 'jzEndTime']
      Object.keys(dcaBParttimejob).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          if (fieldDates.indexOf(key) !== -1) {
            if (dcaBParttimejob[key] !== '') {
              obj[key] = moment(dcaBParttimejob[key])
            }
            else {
              obj[key] = ''
            }
          } else {
            obj[key] = dcaBParttimejob[key]
          }
          this.form.setFieldsValue(obj)
        }
      })
      this.dcaBParttimejob.id = dcaBParttimejob.id
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          let dcaBParttimejob = this.form.getFieldsValue()
          dcaBParttimejob.id = this.dcaBParttimejob.id                              
          this.$put('dcaBParttimejob', {
            ...dcaBParttimejob
          }).then(() => {
            this.reset()
            this.$emit('success')
          }).catch(() => {
            this.loading = false
          })
        }
      })
    }
  }
}
</script>
