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
      <a-table rowKey="id" :columns="columns" size="middle" 
        :pagination="pagination"
        :dataSource="dataSource" :loading="loading" defaultExpandAllRows
        :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
        >
          <template slot="operation" slot-scope="text, record">
            <a-icon type="edit" theme="twoTone" @click="edit(record)" title="修改"></a-icon>
             &nbsp;
            <a-icon type="info-circle" theme="twoTone" @click="view(record)" title="查看"></a-icon>
          </template>
        </a-table>
    </div>
    <roles-add v-if="addViewVisable" @close="onAddViewClose" @ok="onAddViewOK"/>
    <roles-edit ref="modelEdit" v-if="editViewVisable" @close="onEditViewClose" @ok="onEditViewOK"/>
    <roles-info v-if="infoViewVisable" ref="modelInfo" @close="onInfoViewClose"></roles-info>
  </a-card>
</template>
<script>
import { mapState, mapActions } from 'vuex'
import * as sysapi from '@/api/sys'
import * as api from '@/api/base'
import RolesAdd from './roles_add'
import RolesEdit from './roles_edit'
import RolesInfo from './roles_info'
export default {
  components: {
    RolesAdd,
    RolesEdit,
    RolesInfo,
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
          dataIndex: 'remark',
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
      infoViewVisable: false,
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
      // deptTree: state => state.sys.deptTree
      rolePage: state => state.sys.rolePage,
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
    ...mapActions(['getRolePage']),
    fetchData(params = { query: [] }){
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        params.pageSize = this.paginationInfo.size
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      this.getRolePage(params).then(data=>{
        this.$nextTick(()=>{
          const pagination = { ...this.pagination }
          const page = this.rolePage
          pagination.total = page.total
          this.dataSource = page.records
          this.pagination = pagination
        })        
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
    edit(record){
      api.http.get('/roles/'+record.id+'/info')
        .then(data => {
          this.editViewVisable = true
          this.$nextTick(()=>{
            this.$refs.modelEdit.setFormData(data);
          })
        })
        .catch(err => {
          this.$message.warning('请求服务器发生错误！'+ err.message)
        })
    },
    view(){
      api.http.get('/roles/'+record.id+'/info')
        .then(data => {
          this.infoViewVisable = false
          this.$nextTick(()=>{
            this.$refs.infoEdit.setData(data);
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
    },
    onEditViewClose(){
      this.editViewVisable = false
    },
    onEditViewOK(){
      this.editViewVisable = false
      this.search()
    },
    onInfoViewClose(){
      this.infoViewVisable = false
    }
  },
  
}
</script>