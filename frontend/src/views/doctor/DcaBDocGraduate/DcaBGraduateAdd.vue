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
                    label="博士在读人数">
                        <a-input placeholder="请输入博士在读人数" v-decorator="['doctorNumber', {rules: [{ required: true, message: '博士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="硕士在读人数">
                        <a-input placeholder="请输入硕士在读人数" v-decorator="['graduateNumber', {rules: [{ required: true, message: '硕士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="博士在读人数">
                        <a-input placeholder="请输入博士在读人数" v-decorator="['doctorDoneNumber', {rules: [{ required: true, message: '博士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="硕士在读人数">
                        <a-input placeholder="请输入硕士在读人数" v-decorator="['graduateDoneNumber', {rules: [{ required: true, message: '硕士在读人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item v-bind="formItemLayout"
                    label="已毕业获奖情况">
                        <a-input placeholder="请输入已毕业获奖情况" v-decorator="['prizeContent', {rules: [{ required: true, message: '已毕业获奖情况不能为空' }] }]" />
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
        name: 'DcaBGraduateAdd',
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
                dcaBGraduate: {}
            }
        },
        methods: {
            reset () {
                this.loading = false
                this.dcaBGraduate = {}
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
                    this.$post('dcaBDocGraduate', {
                        ...this.dcaBGraduate
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
                let values = this.form.getFieldsValue([                    'doctorNumber'    ,                     'graduateNumber'    ,                     'doctorDoneNumber'    ,                     'graduateDoneNumber'    ,                     'prizeContent'                   ])
                if (typeof values !== 'undefined') {
                    Object.keys(values).forEach(_key => { this.dcaBGraduate[_key] = values[_key] })
                }
            }
        }
    }
</script>
