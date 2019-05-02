/**
 * 修改，并包括相应的权限管理操作
 */
<template>
  <a-drawer
    title="修改角色"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="editVisiable"
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
import { colorList } from '@/components/tools/setting';
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'RolesEdit',
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
      editVisiable: true,
      menus: null,
      mid: null,
      userMenuIds: [],
      checkedMenusIds: []
    }
  },
  methods: {
    onClose(){
      this.reset()
      this.$emit('close')
    },
    setFormData(data){
      const role = data.role
      this.mid = role.id
      this.menus = data.menus.children
      let fields = ['name', 'code', 'remark']
      Object.keys(role).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = role[key]+''
          this.form.setFieldsValue(obj)
        }
      })
      const checkedKeys = this.menus.map(m =>this.getMenuIds(m)).reduce((a,b)=> a.concat(b))
      this.userMenuIds = checkedKeys
      this.checkedMenusIds = checkedKeys
      
    },
    getMenuIds(item){
      let child = item.children
      if(child === null || typeof child === 'undefined' || child.length === 0)return [item.key]
      let keys = child.map(c => this.getMenuIds(c)).reduce((a,b)=> a.concat(b))
      return [item.key, ...keys]
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
        const params = {...values, id: this.mid, menuIds }
        api.updateModel('roles', this.mid, params)
          .then(data => {
            this.loading = false
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
