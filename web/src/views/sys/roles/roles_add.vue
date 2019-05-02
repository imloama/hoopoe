<template>
  <a-drawer
    title="新增角色"
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
      <a-form-item label='角色描述'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['remark']"/>
      </a-form-item>
      <a-row class="ant-form-item">
        <a-col :span="3" class=" ant-form-item-label">权限：</a-col>
        <a-col :span="18">
          <menu-tree @check="onMenuTreeCheck" ref="menuTree" checkable :userMenuIds="userMenuIds"/>
        </a-col>
      </a-row>

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
import MenuTree from '@/components/sys/menu_tree'
import * as api from '@/api/base'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: {
    DeptInputTree,
    MenuTree
  },
  data () {
    return {
      loading: false,
      form: this.$form.createForm(this),
      formItemLayout,
      validateStatus: '',
      addVisiable: true,
      userMenuIds:[],
      checkedMenusIds: []
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
        const menuIds = this.checkedMenusIds
        const params = {...values, menuIds }
        api.createModel('roles', params)
          .then(data => {
            if(data !== null){
              this.reset()
              this.$emit('ok')
            }
          }).catch(err =>{
            this.$message.warning(err.message)
            this.loading = false
          })
      });
    },
    onMenuTreeCheck(checkedKeys){
      this.checkedMenusIds = checkedKeys
    }

  }
}
</script>

<style>

</style>
