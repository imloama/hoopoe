<template>
  <a-drawer
    title="新增字典"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="addVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='编码'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['code',{rules: [{ required: true, message: '编码不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='名称'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['name',{rules: [{ required: true, message: '名称不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='值'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-textarea v-decorator="['v',{rules: [{ required: true, message: '值不能为空'}]}]" 
          placeholder="输入'1=状态1,2=状态2'类似的内容" :rows="4"/>
      </a-form-item>
      <a-form-item label='备注'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['memo']"/>
      </a-form-item>

      <div class="drawer-bootom-button">
        <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
          <a-button style="margin-right: .8rem">取消</a-button>
        </a-popconfirm>
        <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
      </div>
      
    </a-form>
   </a-drawer>
</template>

<script>
import * as api from '@/api/base'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: {
  },
  data () {
    return {
      loading: false,
      form: this.$form.createForm(this),
      formItemLayout,
      validateStatus: '',
      addVisiable: true,
    }
  },
  methods: {
    onClose(){
      this.reset()
      this.$emit('close')
    },
    reset () {
      this.validateStatus = ''
      this.loading = false
      this.form.resetFields()
    },
    handleSubmit(){
      this.form.validateFields((err, values) => {
        if(err)return
        this.loading = true
        const params = {...values }
        api.createModel('dicts', params)
          .then(res => {
            this.reset()
            this.$emit('ok')
          }).catch(err =>{
            this.$message.warning(err.message)
            this.loading = false
          })
      });
    },

  }
}
</script>

<style>

</style>
