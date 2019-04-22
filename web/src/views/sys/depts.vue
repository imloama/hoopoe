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
          <a-button type="primary" @click="add" class="m-a-1" icon="plus">导出Excel</a-button>
        </a-form-item>
      </a-form>
    </div>
    <div>
      <a-table rowKey="key" :columns="columns" size="middle" :dataSource="dataSource" :loading="loading" defaultExpandAllRows></a-table>
    </div>
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import * as sysapi from '@/api/sys'
export default {
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
      dataSource: []
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
    fetchData(params){
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
    search(){
      const result = []
      for (const key in this.queryParams) {
        const value = this.queryParams[key]
        if (value === null || value === undefined || typeof value === 'undefined') continue
        const type = 'like'
        const item = { col: key, type, value }
        result.push(item)
      }
      const params = { query: result } 
      this.fetchData(params);
    },
    add(){

    },
    batchDelete(){

    }
  },
  
}
</script>