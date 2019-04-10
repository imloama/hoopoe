<template>
  <a-drawer
    title="新增用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userAddVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='用户名'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus"
                   :help="help">
        <a-input v-decorator="['name',{rules: [{ required: true, message: '用户名不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='密码' v-bind="formItemLayout">
         <a-input type='password' readOnly v-decorator="['password',{rules: [{ required: true, message: '密码不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='邮箱' v-bind="formItemLayout">
        <a-input
          v-model="user.email"
          v-decorator="['email',{rules: [
            { type: 'email', message: '请输入正确的邮箱' },
            { max: 50, message: '长度不能超过50个字符'}
          ]}]"/>
      </a-form-item>
      <a-form-item label="手机" v-bind="formItemLayout">
        <a-input
          v-decorator="['mobile', {rules: [
            { pattern: '^0?(13[0-9]|15[012356789]|17[013678]|18[0-9]|14[57])[0-9]{8}$', message: '请输入正确的手机号'}
          ]}]"/>
      </a-form-item>
      <a-form-item label='角色' v-bind="formItemLayout">
        <a-select
          mode="multiple"
          :allowClear="true"
          style="width: 100%"
          v-decorator="['roles',{rules: [{ required: true, message: '请选择角色' }]}]">
          <a-select-option v-for="r in roleData" :key="r.id">{{r.name}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label='部门' v-bind="formItemLayout">
        <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="deptTreeData"
          v-decorator="['deptId']">
        </a-tree-select>
      </a-form-item>
      <a-form-item label='状态' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="['status',{rules: [{ required: true, message: '请选择状态'}]}]">
          <a-radio value="0">有效</a-radio>
          <a-radio value="1">锁定</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label='性别' v-bind="formItemLayout">
        <a-radio-group
          v-decorator="['sex',{rules: [{ required: true, message: '请选择性别' }]}]">
          <a-radio value="1">男</a-radio>
          <a-radio value="0">女</a-radio>
          <a-radio value="2">保密</a-radio>
        </a-radio-group>
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
import { mapState, mapActions } from 'vuex'
import { createUser } from '@/api/sys'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'UserAdd',
  props: {
    userAddVisiable: {
      default: false
    }
  },
  data () {
    return {
      loading: false,
      formItemLayout,
      form: this.$form.createForm(this),
      validateStatus: '',
      help: ''
    }
  },
  computed:{
    ...mapState({
      roleData: state => state.sys.rolePage.records,
      deptTreeData: state => state.sys.deptTree
    })
  },
  created(){
    Promise.all([this.getRolePage(), this.getDeptTree()])
      .then(r => {})
      .catch(err=>{
        this.$message.warning(err.message)
      })
  },
  methods: {
    ...mapActions(['getDeptTree','getRolePage']),
    reset () {
      this.validateStatus = ''
      this.help = ''
      this.loading = false
      this.form.resetFields()
    },
    onClose () {
      this.reset()
      this.$emit('close')
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if(err)return
        this.loading = true
        const roles = values.roles.map(id => {id})
        let user = Object.assign({ ...values }, {roles})
        createUser(user).then((r) => {
          this.reset()
          this.$emit('success')
        }).catch(err => {
          this.$message.warning(err.message)
          this.loading = false
        })
      })
    }
  }
}
</script>