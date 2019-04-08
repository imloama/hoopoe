/**
 * base相关接口的封装
 */
import * as api from '@/api/base'

export default {
  data () {
    return {
      modelname: '',
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
      }).then((r) => {
        const data = r.data
        const pagination = { ...this.pagination }
        pagination.total = data.total
        this.dataSource = data.records
        this.pagination = pagination
        // 数据加载完毕，关闭loading
        this.loading = false
        return data
      })
    }, // end of fetchPage
    search () {
      const { sortedInfo, filteredInfo } = this
      let sortField, sortOrder
      // 获取当前列的排序和列的过滤规则
      if (sortedInfo) {
        sortField = sortedInfo.field
        sortOrder = sortedInfo.order
      }
      this.fetchPage(this.modelname, {
        orderby: sortField,
        asc: sortOrder,
        ...this.queryParams,
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
        asc: sortOrder,
        ...this.queryParams,
        ...filteredInfo
      })
    }
  }
}
