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
                        label="讲授课程名称及其它教学任务">
                        <a-input placeholder="请输入讲授课程名称及其它教学任务" v-decorator="['teachtaletName', {rules: [{ required: true, message: '讲授课程名称及其它教学任务不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="自何年月">
                        <a-date-picker v-decorator="[ 'teachtalentStartDate', {rules: [{ required: true, message: '自何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="至何年月">
                        <a-date-picker v-decorator="[ 'teachtalentEndDate', {rules: [{ required: true, message: '至何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="学生人数">
                        <a-input placeholder="请输入学生人数" v-decorator="['studentNumber', {rules: [{ required: true, message: '学生人数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="周学时数">
                        <a-input placeholder="请输入周学时数" v-decorator="['weekTime', {rules: [{ required: true, message: '周学时数不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="总学时">
                        <a-input placeholder="请输入总学时" v-decorator="['totalTime', {rules: [{ required: true, message: '总学时不能为空' }] }]" />
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
                <a-form-item
                        v-bind="formItemLayout"
                        label="经审核是否构成职称晋升条件">
                        <a-input placeholder="请输入经审核是否构成职称晋升条件" v-decorator="['IsUse', {rules: [{ required: true, message: '经审核是否构成职称晋升条件不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="备注">
                        <a-input placeholder="请输入备注" v-decorator="['note', {rules: [{ required: true, message: '备注不能为空' }] }]" />
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
        name: 'DcaBTeachtalentEdit',
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
            dcaBTeachtalent: {}
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
            setFormValues ({...dcaBTeachtalent}) {
                let fields = [      'teachtaletName'    , 'teachtalentStartDate'    , 'teachtalentEndDate'    , 'studentNumber'    , 'weekTime'    , 'totalTime'          , 'auditMan'    , 'auditManName'    , 'auditDate'    , 'auditSuggestion'    , 'IsUse'    , 'note'    ]
                let fieldDates = [        'teachtalentStartDate'       , 'teachtalentEndDate'                  , 'auditDate'       ]
                Object.keys(dcaBTeachtalent).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBTeachtalent[key]!=='') {
                            obj[key] = moment(dcaBTeachtalent[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBTeachtalent[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBTeachtalent. id                                               = dcaBTeachtalent. id                                                          },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBTeachtalent = this.form.getFieldsValue()
                    dcaBTeachtalent. id                                              = this.dcaBTeachtalent.id
                    this.$put('dcaBTeachtalent', {
                        ...dcaBTeachtalent
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
