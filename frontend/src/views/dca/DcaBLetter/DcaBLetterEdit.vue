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
                        label="通讯评审时间">
                        <a-date-picker v-decorator="[ 'letterDate', {rules: [{ required: true, message: '通讯评审时间不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="通讯评审结果">
                        <a-input placeholder="请输入通讯评审结果" v-decorator="['letterJl', {rules: [{ required: true, message: '通讯评审结果不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="有效期">
                        <a-date-picker v-decorator="[ 'validDate', {rules: [{ required: true, message: '有效期不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="">
                        <a-input placeholder="请输入" v-decorator="['auditState', {rules: [{ required: true, message: '不能为空' }] }]" />
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
        name: 'DcaBLetterEdit',
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
            dcaBLetter: {}
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
            setFormValues ({...dcaBLetter}) {
                let fields = [    'letterDate'    , 'letterJl'    , 'validDate'     , 'auditState'               ]
                let fieldDates = [     'letterDate'        , 'validDate'                 ]
                Object.keys(dcaBLetter).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBLetter[key]!=='') {
                            obj[key] = moment(dcaBLetter[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBLetter[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBLetter. id                                       = dcaBLetter. id                                                  },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBLetter = this.form.getFieldsValue()
                    dcaBLetter. id                                      = this.dcaBLetter.id
                    this.$put('dcaBLetter', {
                        ...dcaBLetter
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
