<template>
  <a-drawer
    title="修改用户"
    :maskClosable="false"
    width=650
    placement="right"
    :closable="false"
    @close="onClose"
    :visible="userEditVisiable"
    style="height: calc(100% - 55px);overflow: auto;padding-bottom: 53px;">
    <a-form :form="form">
      <a-form-item label='用户名' v-bind="formItemLayout">
        <a-input readOnly v-decorator="['name']"/>
      </a-form-item>
      <a-form-item label='邮箱' v-bind="formItemLayout">
        <a-input
          v-decorator="[
          'email',
          {rules: [
            { type: 'email', message: '请输入正确的邮箱' },
            { max: 50, message: '长度不能超过50个字符'}
          ]}
        ]"/>
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
          @change="onRolesChange"
          v-decorator="[
            'roles',
            {rules: [{ required: true, message: '请选择角色' }]}
          ]">
          <a-select-option v-for="r in roleData" :value="r.id" :key="r.id">{{r.remark}}</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label='部门' v-bind="formItemLayout">
        <a-tree-select
          :allowClear="true"
          :dropdownStyle="{ maxHeight: '220px', overflow: 'auto' }"
          :treeData="deptTreeData"
          @change="onDeptChange"
          :value="userDept">
        </a-tree-select>
      </a-form-item>
      <a-form-item label='状态' v-bind="formItemLayout">
         <a-select
          :allowClear="true"
          style="width: 100%"
          v-decorator="[
            'status',
            {rules: [{ required: true, message: '请选择状态' }]}
          ]">
          <a-select-option value=0 >有效</a-select-option>
          <a-select-option value=1 >锁定</a-select-option>
        </a-select>
      </a-form-item>
      <a-form-item label='性别' v-bind="formItemLayout">
        <a-select
          :allowClear="true"
          style="width: 100%"
          v-decorator="[
            'sex',
            {rules: [{ required: true, message: '请选择性别' }]}
          ]">
          <a-select-option value=1 >男</a-select-option>
          <a-select-option value=0 >女</a-select-option>
          <a-select-option value=2 >保密</a-select-option>
        </a-select>
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
import {mapState, mapActions, mapMutations} from 'vuex'
import { updateUser } from '@/api/sys'

const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  name: 'UserEdit',
  props: {
    userEditVisiable: {
      default: false
    }
  },
  data () {
    return {
      formItemLayout,
      form: this.$form.createForm(this),
      deptTreeData: [],
      roleData: [],
      userDept: null,
      userRoles: [],
      userId: '',
      loading: false,
      tempUserData: {}
    }
  },
  computed: {
    ...mapState({
      currentUser: state => state.user.info,
      rolePage: state => state.sys.rolePage,
      deptTree: state => state.sys.deptTree,
    })
  },
  created(){
    Promise.all([this.getRolePage(), this.getDeptTree()])
      .then(r => {
        this.roleData = this.rolePage ? this.rolePage.records : []
        this.deptTreeData = this.deptTree
      })
      .catch(err=>{
        this.$message.warning(err.message)
      })
  },
  methods: {
    ...mapActions(['getDeptTree','getRolePage']),
    onClose () {
      this.loading = false
      this.form.resetFields()
      this.$emit('close')
    },
    setFormValues ({...user}) {
      this.tempUserData = user
      this.userId = user.id
      let fields = ['name', 'email', 'status', 'sex', 'mobile']
      Object.keys(user).forEach((key) => {
        if (fields.indexOf(key) !== -1) {
          this.form.getFieldDecorator(key)
          let obj = {}
          obj[key] = user[key]+''
          this.form.setFieldsValue(obj)
        }
      })

      if (user.roles) {
        this.form.getFieldDecorator('roles')
        let roleArr = user.roles.map(r => r.id)
        this.form.setFieldsValue({'roles': roleArr})
      }
      if (user.deptId) {
        this.userDept = user.deptId
      }
    },
    onRolesChange(value){
      this.userRoles = value
    },
    onDeptChange (value) {
      this.userDept = value
    },
    handleSubmit () {
      this.form.validateFields((err, values) => {
        if (!err) {
          this.loading = true
          let user = this.form.getFieldsValue()
          let roles = []
          for(let i=0,n=this.userRoles.length; i<n;i++){
            roles.push({id: this.userRoles[i]})
          }
          user.roles = roles 
          user.id = this.userId
          user.deptId = this.userDept
          updateUser(this.userId, { ...user})
            .then((r) => {
              this.loading = false
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