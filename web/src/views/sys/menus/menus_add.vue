<template>
  <a-drawer
    title="新增菜单"
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
      <a-form-item label='路径'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['path',{rules: [{ required: true, message: '用户名不能为空'}]}]"/>
      </a-form-item>
      <a-form-item label='类型'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-radio-group
          v-decorator="['type',{rules: [{ required: true, message: '请选择状态'}]}]">
          <a-radio value="0">按钮</a-radio>
          <a-radio value="1">菜单</a-radio>
        </a-radio-group>
      </a-form-item>
      <a-form-item label='图标'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <a-input v-decorator="['icon',{rules: [{ required: true, message: '用户名不能为空'}]}]">
          <a-icon slot="addonAfter" type="setting" @click="showIconSelector"/>
        </a-input>
      </a-form-item>
      <a-form-item label='父菜单'
                   v-bind="formItemLayout"
                   :validateStatus="validateStatus">
        <menu-input-tree style="width:100%;" @change="handleSelectChange" ref="refTree" />
      </a-form-item>

      <div class="drawer-bootom-button">
        <a-popconfirm title="确定放弃编辑？" @confirm="onClose" okText="确定" cancelText="取消">
          <a-button style="margin-right: .8rem">取消</a-button>
        </a-popconfirm>
        <a-button @click="handleSubmit" type="primary" :loading="loading">提交</a-button>
      </div>
      <a-modal v-model="iconSelectorVisable">
        <icon-selector @change="handleIconChange"/>
      </a-modal>
      
    </a-form>
   </a-drawer>
</template>

<script>
import MenuInputTree from '@/components/sys/menu_input_tree'
import IconSelector from '@/components/IconSelector'
import * as api from '@/api/base'
const formItemLayout = {
  labelCol: { span: 3 },
  wrapperCol: { span: 18 }
}
export default {
  components: {
    MenuInputTree,
    IconSelector
  },
  data () {
    return {
      loading: false,
      form: this.$form.createForm(this),
      formItemLayout,
      validateStatus: '',
      selected_id: null,
      addVisiable: true,
      iconSelectorVisable: false,
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
            this.$emit('ok')
          }).catch(err =>{
            this.$message.warning(err.message)
            this.loading = false
          })
      });
    },
    handleSelectChange (value) {
      this.selected_id = value || ''
    },
    showIconSelector(){
      this.iconSelectorVisable = true
    },
    handleIconChange(value){

      this.iconSelectorVisable = false
    }

  }
}
</script>

<style>

</style>
