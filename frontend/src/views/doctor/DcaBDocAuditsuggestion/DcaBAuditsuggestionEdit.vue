<template>
    <a-drawer
            title="修改"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="editVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item
                        v-bind="formItemLayout"
                        label="附件">
                        <a-input placeholder="请输入附件" v-decorator="['fileId', {rules: [{ required: true, message: '附件不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核步骤">
                        <a-input placeholder="请输入审核步骤" v-decorator="['AuditStep', {rules: [{ required: true, message: '审核步骤不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="拟聘岗位工作思路及预期目标">
                        <a-input placeholder="请输入拟聘岗位工作思路及预期目标" v-decorator="['preGoal', {rules: [{ required: true, message: '拟聘岗位工作思路及预期目标不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核人">
                        <a-input placeholder="请输入审核人" v-decorator="['auditMan', {rules: [{ required: true, message: '审核人不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核人姓名">
                        <a-input placeholder="请输入审核人姓名" v-decorator="['auditManName', {rules: [{ required: true, message: '审核人姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'auditDate', {rules: [{ required: true, message: '审核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="审核意见">
                        <a-input placeholder="请输入审核意见" v-decorator="['auditSuggestion', {rules: [{ required: true, message: '审核意见不能为空' }] }]" />
                </a-form-item>
        </a-form>
        <div class="drawer-bootom-button">
            <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
                <a-button style="margin-right: .8rem">取消</a-button>
            </a-popconfirm>
            <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
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
        name: 'DcaBAuditsuggestionEdit',
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
            dcaBAuditsuggestion: {}
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
            setFormValues ({...dcaBAuditsuggestion}) {
                let fields = [  'fileId'    , 'AuditStep'      , 'preGoal'          , 'auditMan'    , 'auditManName'    , 'auditDate'    , 'auditSuggestion'    ]
                let fieldDates = [                'auditDate'     ]
                Object.keys(dcaBAuditsuggestion).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBAuditsuggestion[key]!=='') {
                            obj[key] = moment(dcaBAuditsuggestion[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBAuditsuggestion[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBAuditsuggestion. id                                 = dcaBAuditsuggestion. id                                            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBAuditsuggestion = this.form.getFieldsValue()
                    dcaBAuditsuggestion. id                                = this.dcaBAuditsuggestion.id
                    this.$put('dcaBDocAuditsuggestion', {
                        ...dcaBAuditsuggestion
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
