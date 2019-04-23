<template>
  <a-drawer
    title="新增部门"
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
      <a-form-item label='全称'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['fullname',{rules: [{ required: true, message: '用户名不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='父部门'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <dept-input-tree style="width:100%;" @change="handleDeptChange" ref="deptTree" />
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
import DeptInputTree from '@/components/sys/dept_input_tree'
import * as api from '@/api/base'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: {
    DeptInputTree
  },
  data () {
    return {
      loading: false,
      form: this.$form.createForm(this),
      formItemLayout,
      validateStatus: '',
      dept_id: null,
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
        const params = {...values, parentId: this.dept_id }
        api.createModel('depts', params)
          .then(res => {
            this.reset()
            this.$emit('success')
          }).catch(err =>{
            this.$message.warning(err.message)
            this.loading = false
          })
      });
    },
     handleDeptChange (value) {
      this.dept_id = value || ''
    },

  }
}
</script>

<style>

</style>
