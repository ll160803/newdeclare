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
                        label="申报年度">
                        <a-input placeholder="请输入申报年度" v-decorator="['dcaYear', {rules: [{ required: true, message: '申报年度不能为空' }] }]" />
                </a-form-item>
                <a-form-item
                        v-bind="formItemLayout"
                        label="岗位等级">
                        <a-input placeholder="请输入岗位等级" v-decorator="['gwdj', {rules: [{ required: true, message: '岗位等级不能为空' }] }]" />
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
        name: 'DcaDYearsettingEdit',
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
            dcaDYearsetting: {}
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
            setFormValues ({...dcaDYearsetting}) {
                let fields = [  'dcaYear'    , 'gwdj'          ]
                let fieldDates = [         ]
                Object.keys(dcaDYearsetting).forEach((key) => {
                    if (fields.indexOf(key) !== -1) {
                    this.form.getFieldDecorator(key)
                    let obj = {}
                    if(fieldDates.indexOf(key)!==-1)
                    {
                        if(dcaDYearsetting[key]!=='') {
                            obj[key] = moment(dcaDYearsetting[key])
                        }
                        else
                        {
                            obj[key] =''
                        }
                    } else {
                        obj[key] = dcaDYearsetting[key]
                    }
                    this.form.setFieldsValue(obj)
                }
            })
                this.dcaDYearsetting. id                   = dcaDYearsetting. id                              },
            handleSubmit () {
                this.form.validateFields((err, values) => {
                    if (!err) {
                    let dcaDYearsetting = this.form.getFieldsValue()
                    dcaDYearsetting. id                  = this.dcaDYearsetting.id
                    this.$put('dcaDYearsetting', {
                        ...dcaDYearsetting
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
