/**
 * 用户界面，展示用户列表，可以新增、修改、删除用户，查询时，无法查询到自身
 */
<template>
  <a-card :bordered="false" class="card-area">
    <div>
      <!-- 搜索区域 -->
      <a-form layout="horizontal">
        <a-row >
        <div class="fold">
            <a-col :md="12" :sm="24" >
              <a-form-item
                label="用户名"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <a-input v-model="queryParams.name"/>
              </a-form-item>
            </a-col>
            <a-col :md="12" :sm="24" >
              <a-form-item
                label="部门"
                :labelCol="{span: 4}"
                :wrapperCol="{span: 18, offset: 2}">
                <dept-input-tree @change="handleDeptChange"
                                 ref="deptTree">
                </dept-input-tree>
              </a-form-item>
            </a-col>
        </div>
          <span style="float: right; margin-top: 3px;">
            <a-button type="primary" @click="search">查询</a-button>
            <a-button style="margin-left: 8px" @click="reset">重置</a-button>
          </span>
        </a-row>
      </a-form>
    </div>
    <div>
      <div class="operator">
        <a-button type="primary" ghost @click="add" v-action="'user:add'">新增</a-button>
        <a-button @click="batchDelete" v-action="'user:delete'">删除</a-button>
        <a-dropdown v-hasAnyPermission="'user:reset','user:export'">
          <a-menu slot="overlay">
            <a-menu-item v-action="'user:reset'" key="password-reset" @click="resetPassword">密码重置</a-menu-item>
            <a-menu-item v-action="'user:export'" key="export-data" @click="exportExcel">导出Excel</a-menu-item>
          </a-menu>
        </a-dropdown>
      </div>
      <!-- 表格区域 -->
      <a-table ref="TableInfo"
              rowKey="id"
               :columns="columns"
               :dataSource="dataSource"
               :pagination="pagination"
               :loading="loading"
               :rowSelection="{selectedRowKeys: selectedRowKeys, onChange: onSelectChange}"
               :scroll="{ x: 900 }"
               @change="handleTableChange">
        <template slot="email" slot-scope="text">
          <a-popover placement="topLeft">
            <template slot="content">
              <div>{{text}}</div>
            </template>
            <p style="width: 150px;margin-bottom: 0">{{text}}</p>
          </a-popover>
        </template>
        <template slot="operation" slot-scope="text, record">
          <a-icon v-action="'user:update'" type="setting" theme="twoTone" twoToneColor="#4a9ff5" @click="edit(record)" title="修改用户"></a-icon>
          &nbsp;
          <a-icon v-action="'user:view'" type="eye" theme="twoTone" twoToneColor="#42b983" @click="view(record)" title="查看"></a-icon>
        </template>
      </a-table>
    </div>
      <!-- 用户信息查看 -->
      <user-info
        ref="userInfo"
        :userInfoData="userInfo.data"
        @close="handleUserInfoClose">
      </user-info>
      
    <!-- 新增用户 -->
    <user-add
      v-if="userAdd.visiable"
      @close="handleUserAddClose"
      @success="handleUserAddSuccess"
      :userAddVisiable="userAdd.visiable">
    </user-add>
    <!-- 修改用户 -->
    <user-edit
      ref="userEdit"
      v-if="userEdit.visiable"
      @close="handleUserEditClose"
      @success="handleUserEditSuccess"
      :userEditVisiable="userEdit.visiable">
    </user-edit>

     


  </a-card>
</template>


<script>
import UserInfo from './user_info'
import DeptInputTree from '@/components/sys/dept_input_tree'
import UserAdd from './user_add'
import UserEdit from './user_edit'
import basemixin from '@/mixins/base'
import { mapState, mapActions } from 'vuex'
import { getModel } from '@/api/base'

export default {
  name: 'Users',
  mixins: [basemixin],
  components: {UserInfo, UserAdd, UserEdit, DeptInputTree},
  data () {
    return {
      userInfo: {
        visiable: true,
        data: {}
      },
      userAdd: {
        visiable: false
      },
      userEdit: {
        visiable: false
      },
      selectedRowKeys: [],
    }
  },
  computed: {
    columns () {
      let { sortedInfo, filteredInfo } = this
      sortedInfo = sortedInfo || {}
      filteredInfo = filteredInfo || {}
      return [{
        title: '用户名',
        dataIndex: 'name',
        sorter: true,
        sortOrder: sortedInfo.columnKey === 'name' && sortedInfo.order
      }, {
        title: '性别',
        dataIndex: 'sex',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return '男'
            case 0:
              return '女'
            case 2:
              return '保密'
            default:
              return text
          }
        },
        filters: [
          { text: '男', value: 1 },
          { text: '女', value: 0 },
          { text: '保密', value: 2 }
        ],
        filterMultiple: false,
        filteredValue: filteredInfo.sex || null,
        onFilter: (value, record) => record.sex.includes(value)
      }, {
        title: '邮箱',
        dataIndex: 'email',
        scopedSlots: { customRender: 'email' },
        width: 100
      }, {
        title: '部门',
        dataIndex: 'deptName'
      }, {
        title: '电话',
        dataIndex: 'mobile'
      }, {
        title: '状态',
        dataIndex: 'status',
        customRender: (text, row, index) => {
          switch (text) {
            case 1:
              return <a-tag color="red">锁定</a-tag>
            case 0:
              return <a-tag color="cyan">有效</a-tag>
            default:
              return text
          }
        },
        filters: [
          { text: '有效', value: 0 },
          { text: '锁定', value: 1 }
        ],
        filterMultiple: false,
        filteredValue: filteredInfo.status || null,
        onFilter: (value, record) => record.status.includes(value)
      }, {
        title: '操作',
        dataIndex: 'operation',
        scopedSlots: { customRender: 'operation' }
      }]
    }
  },
  created () {
    this.modelname = 'users'
  },
  mounted () {
  },
  methods: {
    ...mapActions(['getRolePage']),
    onSelectChange (selectedRowKeys) {
      this.selectedRowKeys = selectedRowKeys
    },
    getUser(id){
      return getModel(this.modelname, id)
    },
    view (record) {
      // 根据id查询用户详情
      this.getUser(record.id).then( user => {
        this.userInfo.data = user
        //this.userInfo.visiable = true
        this.$refs.userInfo.show()
      }).catch(err => {
        console.error(err)
        this.$message.error('查询用户失败')
      })
      
    },
    add () {
      this.userAdd.visiable = true
    },
    handleUserAddClose () {
      this.userAdd.visiable = false
    },
    handleUserAddSuccess () {
      this.userAdd.visiable = false
      this.$message.success('新增用户成功')
      this.search()
    },
    edit (record) {
      // 根据id查询用户详情
      this.getUser(record.id).then( user => {
        this.userEdit.visiable = true
        this.$nextTick(()=>{
          this.$refs.userEdit.setFormValues(user)
        })
      }).catch(err => {
        console.error(err)
        this.$message.error('查询用户失败')
      })
      
    },
    handleUserEditClose () {
      this.userEdit.visiable = false
    },
    handleUserEditSuccess () {
      this.userEdit.visiable = false
      this.$message.success('修改用户成功')
      this.search()
    },
    handleUserInfoClose () {
      this.userInfo.visiable = false
    },
    handleDeptChange (value) {
      this.queryParams.deptId = value || ''
    },
    handleDateChange (value) {
      if (value) {
        this.queryParams.createTimeFrom = value[0]
        this.queryParams.createTimeTo = value[1]
      }
    },
    batchDelete () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要删除的记录')
        return
      }
      let that = this
      this.$confirm({
        title: '确定删除所选中的记录?',
        content: '当您点击确定按钮后，这些记录将会被彻底删除',
        centered: true,
        onOk () {
          let userIds = []
          for (let key of that.selectedRowKeys) {
            userIds.push(that.dataSource[key].userId)
          }
          this.deleteAllById(userIds).then(() => {
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
    resetPassword () {
      if (!this.selectedRowKeys.length) {
        this.$message.warning('请选择需要重置密码的用户')
        return
      }
      let that = this
      this.$confirm({
        title: '确定重置选中用户密码?',
        content: '当您点击确定按钮后，这些用户的密码将会被重置！',
        centered: true,
        onOk () {
          let ids = []
          for (let key of that.selectedRowKeys) {
            ids.push(that.dataSource[key].id)
          }

          this.$post(`/${this.modelname}/adminresetpwd`, { ids })
            .then(result => {
            that.$message.success('重置用户密码成功，重置后的密码为:' + result.data)
            that.selectedRowKeys = []
          })
        },
        onCancel () {
          that.selectedRowKeys = []
        }
      })
    },
    reset () {
      // 取消选中
      this.selectedRowKeys = []
      // 重置分页
      this.$refs.TableInfo.pagination.current = this.pagination.defaultCurrent
      if (this.paginationInfo) {
        this.paginationInfo.current = this.pagination.defaultCurrent
        this.paginationInfo.pageSize = this.pagination.defaultPageSize
      }
      // 重置列过滤器规则
      this.filteredInfo = null
      // 重置列排序规则
      this.sortedInfo = null
      // 重置查询参数
      this.queryParams = {}
      // 清空部门树选择
      this.$refs.deptTree.reset()
      this.fetchPage()
    },
  }
}
</script>
<style lang="less" scoped>
</style>