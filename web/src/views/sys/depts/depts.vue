<template>
  <a-card :bordered="false"  class="depts-wrapper">
    <div class="m-b-1">
      <a-form layout="inline">
        <a-form-item>
          <a-input v-model="queryParams.code" placeholder="请输入编码"/>
        </a-form-item>
        <a-form-item>
          <a-input v-model="queryParams.name" placeholder="请输入名称"/>
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
        ></a-table>
    </div>
    <depts-add v-if="addViewVisable" @close="onAddViewClose" @ok="onAddViewOK"/>
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import * as sysapi from '@/api/sys'
import * as api from '@/api/base'
import DeptsAdd from './depts_add'
export default {
  components: {
    DeptsAdd,
  },
  data () {
    return {
      columns: [
        {
          title: '#',
          dataIndex: 'key'
        },
        {
          title: '编码',
          dataIndex: 'code',
        },
        {
          title: '名称',
          dataIndex: 'label',
        },
        {
          title: '全称',
          dataIndex: 'fullname',
        }
      ],
      loading: false,
      queryParams:{},
      dataSource: [],
      selectedRowKeys: [],
      addViewVisable: false,
    }
  },
  computed:{
    ...mapState({
      // deptTree: state => state.sys.deptTree
    }),
    // dataSource(){
    //   return this.deptTree.map(item => {
    //     return this.rebuildDeptTree(item)
    //   })
    // }
  },
  created () {
    this.fetchData()
  },
  methods: {
    ...mapActions(['getDeptTree']),
    fetchData(params = { query: [] }){
      this.loading = true
      sysapi.getDepts(params).then(data=>{
        if(data && data.children){
          this.dataSource = data.children.map(item => {
            return this.rebuildDeptTree(item)
          })
        }
        this.loading = false
      }).catch(err=>{
        console.log(err)
        this.loading = false
      })
    },
    rebuildDeptTree(item){
      let p = {
          key: item.key,
          label: item.label,
          leaf: item.leaf,
          parentKey: item.parentKey,
          code: item.source.code,
          fullname: item.source.fullname,
          createTime: item.source.createTime,
          name: item.source.name,
          value: item.value,
          children: item.children ? item.children.map(child => this.rebuildDeptTree(child)) : null
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
    exportExcel(){
      api.downExcel("depts", {
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
    }
  },
  
}
</script>