<template>
    <a-drawer
            title="新增"
            :maskClosable="false"
            width=650
            placement="right"
            :closable="false"
            @close="onClose"
            :visible="addVisiable"
            style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
        <a-form :form="form">
                <a-form-item v-bind="formItemLayout"
                    label="考核结果">
                        <a-input placeholder="请输入考核结果" v-decorator="['khjg', {rules: [{ required: true, message: '考核结果不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="考核类型">
                        <a-input placeholder="请输入考核类型" v-decorator="['khtype', {rules: [{ required: true, message: '考核类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="考核时间">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'khdate', {rules: [{ required: true, message: '考核时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="考核日期">
                        <a-date-picker v-decorator="[ 'khDate', {rules: [{ required: true, message: '考核日期不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="出站考核总结">
                        <a-input placeholder="请输入出站考核总结" v-decorator="['czkhzj', {rules: [{ required: true, message: '出站考核总结不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['adContent', {rules: [{ required: true, message: '备注不能为空' }] }]" />
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
    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBDocAuditfivemiddleAdd',
        props: {
            addVisiable: {
                default: false
            }
        },
        data () {
            return {
                loading: false,
                formItemLayout,
                form: this.$form.createForm(this),
                dcaBDocAuditfivemiddle: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBDocAuditfivemiddle = {}
                this.form.resetFields()
            },
            onClose () {
                this.reset()
                this.$emit('close')
            },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    this.setFields()
                    this.$post('dcaBDocAuditfivemiddle', {
                        ...this.dcaBDocAuditfivemiddle
                    }).then(() => {
                        this.reset()
                        this.$emit('success')
                    }).catch(() => {
                        this.loading = false
                    })
                }
            })
            },
            setFields () {
                let values = this.form.getFieldsValue([                    'khjg'    ,                     'khtype'    ,                     'khdate'    ,                     'userAccountName'    ,                     'userAccount'    ,                     'khDate'    ,                     'czkhzj'    ,                     'adContent'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBDocAuditfivemiddle[_key] = values[_key] })
                }
            }
        }
    }
</script>
