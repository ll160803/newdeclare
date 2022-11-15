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
                        label="考核结果">
                        <a-input placeholder="请输入考核结果" v-decorator="['khjg', {rules: [{ required: true, message: '考核结果不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="起止年度">
                        <a-input placeholder="请输入起止年度" v-decorator="['year', {rules: [{ required: true, message: '起止年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="姓名">
                        <a-input placeholder="请输入姓名" v-decorator="['userAccountName', {rules: [{ required: true, message: '姓名不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="人事编号">
                        <a-input placeholder="请输入人事编号" v-decorator="['userAccount', {rules: [{ required: true, message: '人事编号不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="政治表现">
                        <a-input placeholder="请输入政治表现" v-decorator="['zzbx', {rules: [{ required: true, message: '政治表现不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="职业道德">
                        <a-input placeholder="请输入职业道德" v-decorator="['zydd', {rules: [{ required: true, message: '职业道德不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="廉洁自律">
                        <a-input placeholder="请输入廉洁自律" v-decorator="['ljzl', {rules: [{ required: true, message: '廉洁自律不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="业务能力工作态度总结">
                        <a-input placeholder="请输入业务能力工作态度总结" v-decorator="['ywnlgztd', {rules: [{ required: true, message: '业务能力工作态度总结不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="个人科研进展较为详细的成果总结">
                        <a-input placeholder="请输入个人科研进展较为详细的成果总结" v-decorator="['grkyjz', {rules: [{ required: true, message: '个人科研进展较为详细的成果总结不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="下年度科研课题较为详细计划">
                        <a-input placeholder="请输入下年度科研课题较为详细计划" v-decorator="['xndkykt', {rules: [{ required: true, message: '下年度科研课题较为详细计划不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
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
    import moment from 'moment'

    const formItemLayout = {
        labelCol: { span: 3 },
        wrapperCol: { span: 18 }
    }
    export default {
        name: 'DcaBDocAuditfiveEdit',
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
            dcaBDocAuditfive: {}
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
            setFormValues ({...dcaBDocAuditfive}) {
                let fields = [   'khjg'    , 'year'     , 'userAccountName'    , 'userAccount'    , 'zzbx'    , 'zydd'    , 'ljzl'    , 'ywnlgztd'    , 'grkyjz'    , 'xndkykt'    , 'adContent'                ]
                let fieldDates = [                          ]
                Object.keys(dcaBDocAuditfive).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBDocAuditfive[key]!=='') {
                            obj[key] = moment(dcaBDocAuditfive[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBDocAuditfive[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBDocAuditfive. id                                                     = dcaBDocAuditfive. id                                                                },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBDocAuditfive = this.form.getFieldsValue()
                    dcaBDocAuditfive. id                                                    = this.dcaBDocAuditfive.id
                    this.$put('dcaBDocAuditfive', {
                        ...dcaBDocAuditfive
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
