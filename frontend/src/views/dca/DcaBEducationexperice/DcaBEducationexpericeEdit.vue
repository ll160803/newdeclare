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
                        label="自何年月">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'expStartTime', {rules: [{ required: true, message: '自何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="至何年月">
                        <a-date-picker showTime format='YYYY-MM-DD HH:mm:ss' v-decorator="[ 'expEndTime', {rules: [{ required: true, message: '至何年月不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="何地">
                        <a-input placeholder="请输入何地" v-decorator="['expAddress', {rules: [{ required: true, message: '何地不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="何学校">
                        <a-input placeholder="请输入何学校" v-decorator="['expSchool', {rules: [{ required: true, message: '何学校不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="何单位职位">
                        <a-input placeholder="请输入何单位职位" v-decorator="['expPosition', {rules: [{ required: true, message: '何单位职位不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="证明人">
                        <a-input placeholder="请输入证明人" v-decorator="['expCertifier', {rules: [{ required: true, message: '证明人不能为空' }] }]" />
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
        name: 'DcaBEducationexpericeEdit',
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
            dcaBEducationexperice: {}
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
            setFormValues ({...dcaBEducationexperice}) {
                let fields = [   'expStartTime'    , 'expEndTime'    , 'expAddress'    , 'expSchool'    , 'expPosition'    , 'expCertifier'          ]
                let fieldDates = [    'expStartTime'       , 'expEndTime'              ]
                Object.keys(dcaBEducationexperice).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaBEducationexperice[key]!=='') {
                            obj[key] = moment(dcaBEducationexperice[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaBEducationexperice[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaBEducationexperice. id                             = dcaBEducationexperice. id                                        },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaBEducationexperice = this.form.getFieldsValue()
                    dcaBEducationexperice. id                            = this.dcaBEducationexperice.id
                    this.$put('dcaBEducationexperice', {
                        ...dcaBEducationexperice
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
