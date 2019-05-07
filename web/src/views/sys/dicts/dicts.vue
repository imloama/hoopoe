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
        </a-form-item>
      </a-form>
    </div>
    <div>
      <a-table rowKey="key" :columns="columns" size="middle" 
        :dataSource="dataSource" 
        :pagination="pagination"
        :loading="loading"
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        @change="handleTableChange"
        >
          <template slot="operation" slot-scope="text, record">
            <a-icon type="edit" theme="twoTone" @click="edit(record)" title="修改"></a-icon>
            &nbsp;
            <a-icon type="info-circle" theme="twoTone" @click="view(record)" title="查看"></a-icon>
          </template>
        </a-table>
    </div>
    <model-add v-if="addViewVisable" @close="onAddViewClose" @ok="onAddViewOK"/>
    <model-edit ref="refEdit" v-if="editViewVisable" @close="onEditViewClose" @ok="onEditViewOK"/>
    <model-info ref="refInfo" v-if="infoViewVisable" />
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import * as sysapi from '@/api/sys'
import * as api from '@/api/base'
import ModelAdd from './dicts_add'
import ModelEdit from './dicts_edit'
export default {
  components: {
    ModelAdd,
    ModelEdit,
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
          title: '备注',
          dataIndex: 'memo'
        },{
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
      infoViewVisable: false,
      modelname: 'dicts',
       // 分页数据
      paginationInfo: null,
      // 分页配置
      pagination: {
        pageSizeOptions: ['10', '20', '30', '50', '100'],
        defaultCurrent: 1,
        defaultPageSize: 50,
        showQuickJumper: true,
        showSizeChanger: true,
        showTotal: (total, range) => `显示 ${range[0]} ~ ${range[1]} 条记录，共 ${total} 条记录`
      }
    }
  },
  computed:{
    ...mapState({
    }),
  },
  created () {
    this.fetchData()
  },
  methods: {
    // 表格翻页
    handleTableChange (pagination, filters, sorter) {
      // 将这三个参数赋值给Vue data，用于后续使用
      this.paginationInfo = pagination
      this.filteredInfo = filters
      this.sortedInfo = sorter
      this.userInfo.visiable = false
      this.fetchPage({
        orderby: sorter.field,
        asc: sorter.order === 'ascend',
        query: this.getQueryItems(),
        ...filters
      })
    },
    fetchData(params = { query: [] }){
      this.loading = true
      api.getModelPage(this.modelname, params).then(() => {
        this.loading = false
        
      }).catch(err=>{
        console.log(err)
        this.loading = false
      })
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
    view(record){

    },
    edit(record){
      api.getModel(this.modelname,record.key)
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
      return api.delAllModel(this.modelname, ids)
    },
    exportExcel(){
      api.downExcel(this.modelname, {
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