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
                    label="考核时间">
                        <a-input placeholder="请输入考核时间" v-decorator="['year', {rules: [{ required: true, message: '考核时间不能为空' }] }]" />
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
                    label="成果概述">
                        <a-input placeholder="请输入成果概述" v-decorator="['cggs', {rules: [{ required: true, message: '成果概述不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="思想政治表现">
                        <a-input placeholder="请输入思想政治表现" v-decorator="['sxzzbx', {rules: [{ required: true, message: '思想政治表现不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="考勤状况">
                        <a-input placeholder="请输入考勤状况" v-decorator="['kqzk', {rules: [{ required: true, message: '考勤状况不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="工作态度">
                        <a-input placeholder="请输入工作态度" v-decorator="['gztd', {rules: [{ required: true, message: '工作态度不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="工作进展">
                        <a-input placeholder="请输入工作进展" v-decorator="['gzjz', {rules: [{ required: true, message: '工作进展不能为空' }] }]" />
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
        name: 'DcaBDocAuditfivemonthAdd',
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
                dcaBDocAuditfivemonth: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBDocAuditfivemonth = {}
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
                    this.$post('dcaBDocAuditfivemonth', {
                        ...this.dcaBDocAuditfivemonth
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
                let values = this.form.getFieldsValue([                    'khjg'    ,                     'year'    ,                     'userAccountName'    ,                     'userAccount'    ,                     'cggs'    ,                     'sxzzbx'    ,                     'kqzk'    ,                     'gztd'    ,                     'gzjz'    ,                     'adContent'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBDocAuditfivemonth[_key] = values[_key] })
                }
            }
        }
    }
</script>
