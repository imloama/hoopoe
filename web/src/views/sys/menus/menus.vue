<template>
  <a-card :bordered="false"  class="menus-wrapper">
    <div class="m-b-1">
      <a-form layout="inline">
        <a-form-item>
          <a-input v-model="queryParams.name" placeholder="请输入名称"/>
        </a-form-item>
        <a-form-item>
          <a-input v-model="queryParams.path" placeholder="请输入地址"/>
        </a-form-item>
        <a-form-item>
          <a-button type="primary" @click="search" icon="search">搜索</a-button>
          <a-button type="primary" @click="add" class="m-a-1" icon="plus">新增</a-button>
          <a-button type="danger" @click="batchDelete" class="m-r-1" icon="delete">删除</a-button>
          <a-button type="primary" @click="exportExcel" class="m-a-1" icon="plus">导出Excel</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div>
      <a-table rowKey="key" :columns="columns" size="middle" 
        :dataSource="dataSource" :loading="loading" defaultExpandAllRows
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        >
          <template slot="icon" slot-scope="text">
            <a-icon :type="text" theme="twoTone" title="图标"></a-icon>
          </template>
          <template slot="operation" slot-scope="text, record">
            <a-icon type="edit" theme="twoTone" @click="edit(record)" title="修改"></a-icon>
          </template>
        </a-table>
    </div>
    <menus-add v-if="addViewVisable" @close="onAddViewClose" @ok="onAddViewOK"/>
    <menus-edit ref="refEdit" v-if="editViewVisable" @close="onEditViewClose" @ok="onEditViewOK"/>
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import * as sysapi from '@/api/sys'
import * as api from '@/api/base'
import MenusAdd from './menus_add'
import MenusEdit from './menus_edit'
export default {
  components: {
    MenusAdd,
    MenusEdit,
  },
  data () {
    return {
      columns: [
        {
          title: '编码',
          dataIndex: 'code',
        },
        {
          title: '名称',
          dataIndex: 'name',
        },
        {
          title: '地址',
          dataIndex: 'path',
        },
        {
          title: '图标',
          dataIndex: 'icon',
          scopedSlots: { customRender: 'icon' }
        },
         {
          title: '类型',
          dataIndex: 'type',
          customRender: (text, row, index) => {
            switch (text) {
              case '1':
                return '按钮'
              case '0':
                return '菜单'
              default:
                return text
            }
          }
        }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }
      ],
      loading: false,
      queryParams:{},
      dataSource: [],
      selectedRowKeys: [],
      addViewVisable: false,
      editViewVisable: false,
    }
  },
  computed:{
    ...mapState({
      menuTree: state => state.sys.menuTree
    }),
  },
  created () {
    this.fetchData()
  },
  methods: {
    ...mapActions(['getMenuTree']),
    fetchData(params = { query: [] }){
      this.loading = true
      this.getMenuTree(params).then(() => {
        this.$nextTick(()=>{
          this.loading = false
          if(this.menuTree && this.menuTree.children){
            this.dataSource = this.menuTree.children.map(item => {
              return this.rebuildTree(item)
            })
          }
        })
      }).catch(err=>{
        console.log(err)
        this.loading = false
      })
    },
    rebuildTree(item){
      let p = {
          key: item.key,
          label: item.label,
          leaf: item.leaf,
          parentKey: item.parentKey,
          code: item.source.code,
          path: item.source.path,
          type: item.source.type,
          icon: item.source.icon,
          createTime: item.source.createTime,
          name: item.source.name,
          value: item.value,
          children: item.children ? item.children.map(child => this.rebuildTree(child)) : null
        }
      return p
    },
    getQueryItems(){
      const result = []
      for (const key in this.queryParams) {
        const value = this.queryParams[key]
        if (value === null || value === undefined || typeof value === 'undefined') continue
        const type = 'like'
        const item = { col: key, type, value }
        result.push(item)
      }
      return result;
    },
    search(){
      const params = { query: this.getQueryItems() } 
      this.fetchData(params);
    },
    add(){
      this.addViewVisable = true
    },
    edit(record){
      api.getModel('menus',record.key)
        .then(dept => {
          this.editViewVisable = true
          this.$nextTick(()=>{
            this.$refs.refEdit.setFormData(dept);
          })
        })
        .catch(err => {
          this.$message.warning('请求服务器发生错误！'+ err.message)
        })
    },
    batchDelete(){
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除！',
        centered: true,
        onOk () {
          let ids = []
          for (let key of that.selectedRowKeys) {
            ids.push(that.dataSource[key-1].id)
          }
          that.deleteAllById(ids).then(() => {
            that.$message.success('删除成功')
            that.selectedRowKeys = []
            that.search()
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
     deleteAllById(ids){
      return api.delAllModel('menus', ids)
    },
    exportExcel(){
      api.downExcel("menus", {
        query: this.getQueryItems()
      })
    },
    onSelectChange(selectedRowKeys){
      this.selectedRowKeys = selectedRowKeys
    },
    onAddViewClose(){
      this.addViewVisable = false
    },
    onAddViewOK(){
      this.addViewVisable = false
      this.search()
    },
    onEditViewClose(){
      this.editViewVisable = false
    },
    onEditViewOK(){
      this.editViewVisable = false
      this.search()
    }
  },
  
}
</script>