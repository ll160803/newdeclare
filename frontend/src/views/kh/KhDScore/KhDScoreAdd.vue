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
                    label="账号">
                        <a-input placeholder="请输入账号" v-decorator="['userAccount', {rules: [{ required: true, message: '账号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室名称">
                        <a-input placeholder="请输入科室名称" v-decorator="['deptName', {rules: [{ required: true, message: '科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="分组">
                        <a-input placeholder="请输入分组" v-decorator="['fenzu', {rules: [{ required: true, message: '分组不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="账号">
                        <a-input placeholder="请输入账号" v-decorator="['auditUserAccount', {rules: [{ required: true, message: '账号不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['auditUserAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="科室名称">
                        <a-input placeholder="请输入科室名称" v-decorator="['auditDeptName', {rules: [{ required: true, message: '科室名称不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="分组">
                        <a-input placeholder="请输入分组" v-decorator="['auditFenzu', {rules: [{ required: true, message: '分组不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="打分类型">
                        <a-input placeholder="请输入打分类型" v-decorator="['auditType', {rules: [{ required: true, message: '打分类型不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="入职年度">
                        <a-input placeholder="请输入入职年度" v-decorator="['year', {rules: [{ required: true, message: '入职年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="分数">
                        <a-input placeholder="请输入分数" v-decorator="['score', {rules: [{ required: true, message: '分数不能为空' }] }]" />
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
        name: 'KhDScoreAdd',
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
                khDScore: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.khDScore = {}
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
                    this.$post('khDScore', {
                        ...this.khDScore
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
                let values = this.form.getFieldsValue([                    'userAccount'    ,                     'userAccountName'    ,                     'deptName'    ,                     'fenzu'    ,                     'auditUserAccount'    ,                     'auditUserAccountName'    ,                     'auditDeptName'    ,                     'auditFenzu'    ,                     'auditType'    ,                     'year'    ,                     'score'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.khDScore[_key] = values[_key] })
                }
            }
        }
    }
</script>
