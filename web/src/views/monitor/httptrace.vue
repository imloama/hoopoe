/**
 * 用户请求链路展示
 */

<template>
  
    <a-card :title="`共追踪到 ${dataSource.length} 条近期HTTP请求记录`">
      <a-button :loading="loading" slot="extra" @click="reload">刷新</a-button>
      <a-skeleton active :loading="loading" :paragraph="{rows: 8}">
        <a-table size="middle" :columns="columns" :dataSource="dataSource">
          
        </a-table>
        
      </a-skeleton>
    </a-card>
  
</template>

<script>
import * as api from '@/api/base'
import moment from 'moment'
moment.locale('zh-cn')
export default {
  components: {
  },
  data () {
    return {
      loading: false,
      dataSource:[],
      columns: [{
        title: '请求时间',
        dataIndex: 'timestamp',
        customRender: (text, row, index) => {
          return moment(text).format('YYYY-MM-DD HH:mm:ss')
        }
      }, {
        title: '请求方法',
        dataIndex: 'request.method',
        customRender: (text, row, index) => {
          switch (text) {
            case 'GET':
              return <a-tag color="#87d068">{text}</a-tag>
            case 'POST':
              return <a-tag color="#2db7f5">{text}</a-tag>
            case 'PUT':
              return <a-tag color="#ffba5a">{text}</a-tag>
            case 'DELETE':
              return <a-tag color="#f50">{text}</a-tag>
            default:
              return text
          }
        },
        filters: [
          { text: 'GET', value: 'GET' },
          { text: 'POST', value: 'POST' },
          { text: 'PUT', value: 'PUT' },
          { text: 'DELETE', value: 'DELETE' }
        ],
        filterMultiple: true,
        onFilter: (value, record) => record.request.method.includes(value)
      }, {
        title: '请求URL',
        dataIndex: 'request.uri',
        customRender: (text, row, index) => {
          return text.split('?')[0]
        }
      }, {
        title: '响应状态',
        dataIndex: 'response.status',
        customRender: (text, row, index) => {
          if (text < 200) {
            return <a-tag color="pink">{text}</a-tag>
          } else if (text < 201) {
            return <a-tag color="green">{text}</a-tag>
          } else if (text < 399) {
            return <a-tag color="cyan">{text}</a-tag>
          } else if (text < 403) {
            return <a-tag color="orange">{text}</a-tag>
          } else if (text < 501) {
            return <a-tag color="red">{text}</a-tag>
          } else {
            return text
          }
        }
      }, {
        title: '请求耗时',
        dataIndex: 'timeTaken',
        customRender: (text, row, index) => {
          if (text < 500) {
            return <a-tag color="green">{text} ms</a-tag>
          } else if (text < 1000) {
            return <a-tag color="cyan">{text} ms</a-tag>
          } else if (text < 1500) {
            return <a-tag color="orange">{text} ms</a-tag>
          } else {
            return <a-tag color="red">{text} ms</a-tag>
          }
        }
      }]
    }
  },
  created () {
    this.reload()
   
  },
  methods: {
    reload(){
      this.loading = true
      api.http.get('actuator/httptrace').then(data => {
        this.loading = false
        let filterData = []
        for (let d of data.traces) {
          if (d.request.method !== 'OPTIONS' &&
            d.request.uri.indexOf('httptrace') === -1) {
            filterData.push(d)
          }
        }
        this.dataSource = filterData
      }).catch(err => {
        this.loading = false
        console.error(err)
        this.$message.error(`获取数据失败，${err.message}。`)
      })
    },
  }
}
</script>

<style>

</style>
