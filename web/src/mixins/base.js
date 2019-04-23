/**
 * 界面相关接口封装，
 * 具体界面直接通过mixin引用，获取相应的实现
 */
import * as api from '@/api/base'

export default {
  data () {
    return {
      modelname: '',
      queryKeys: {},
      // 查询条件
      queryParams: {},
      filteredInfo: null,
      sortedInfo: null,

      loading: false,
      // 分页数据
      paginationInfo: null,
      dataSource: [],

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
  computed: {

  },
  mounted () {
    this.fetchPage()
  },
  methods: {
    $get: api.http.get,
    $post: api.http.post,
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
    // 查询分页数据
    fetchPage (params = {}) {
      // 显示loading
      this.loading = true
      if (this.paginationInfo) {
        // 如果分页信息不为空，则设置表格当前第几页，每页条数，并设置查询分页参数
        // this.$refs.TableInfo.pagination.current = this.paginationInfo.current
        // this.$refs.TableInfo.pagination.pageSize = this.paginationInfo.pageSize
        params.pageSize = this.paginationInfo.size
        params.pageNum = this.paginationInfo.current
      } else {
        // 如果分页信息为空，则设置为默认值
        params.pageSize = this.pagination.defaultPageSize
        params.pageNum = this.pagination.defaultCurrent
      }
      return api.getModelPage(this.modelname, {
        ...params
      }).then(data => {
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
        return data
      }).catch(err => {
        this.$message.error(err.message)
        this.loading = false
      })
    }, // end of fetchPage
    getQueryItems () {
      const result = []
      for (const key in this.queryParams) {
        const value = this.queryParams[key]
        if (value === null || value === undefined || typeof value === 'undefined') continue
        const type = this.queryKeys[key] ? this.queryKeys[key] : 'like'
        const item = { col: key, type, value }
        result.push(item)
      }
      return result
    },
    search () {
      const { sortedInfo, filteredInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetchPage({
        orderby: sortField,
        asc: sortOrder === 'ascend',
        query: this.getQueryItems(),
        ...filteredInfo
      })
    },
    exportExcel () {
      const { sortedInfo, filteredInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      api.downExcel(this.modelname, {
        orderby: sortField,
        asc: sortOrder === 'ascend',
        query: this.getQueryItems(),
        ...filteredInfo
      })
    },
    deleteAllById (ids) {
      return api.delAllModel(this.modelname, ids)
    }
  }
}
