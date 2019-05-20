/**
 * 在线用户
 */
/**
 * 运行系统
 */

<template>
  
    <a-card title="在线用户">
      <a-skeleton active :loading="loading" :paragraph="{rows: 8}">
        <a-table size="middle" rowKey="id" :columns="columns" :dataSource="loginusers">
          <template slot="operation" slot-scope="text, record, index">
            <a-popconfirm title="是否要删除此行？" @confirm="remove(record.username)">
              <a>删除</a>
            </a-popconfirm>
          </template>
        </a-table>
        
      </a-skeleton>
    </a-card>
  
</template>

<script>
import * as api from '@/api/base'
import moment from 'moment'
moment.locale('zh-cn')
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item
export default {
  components: {
    DetailList,
    DetailListItem
  },
  data () {
    return {
      time: '',
      loading: true,
      loginusers:[],
      columns:[
         {
          title: '用户名',
          dataIndex: 'username',
        },
         {
          title: '登陆时间',
          dataIndex: 'created',
          customRender: (text) => new Date(text).toLocaleString()
        },
        {
          title: '操作',
          key: 'action',
          scopedSlots: { customRender: 'operation' }
        }
      ]
    }
  },
  created () {
    this.reload()
   
  },
  methods: {
    reload(){
      this.loading = true
     
    },
    convert (value) {
      return Number(value * 100).toFixed(2)
    },
    bytesToMB(value){
      return ((Number(value)/1024)/1024).toFixed(2)
    }
  }
}
</script>

<style>

</style>
