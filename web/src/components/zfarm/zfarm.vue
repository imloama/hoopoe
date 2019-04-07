/**
 * 实现内容参考List/TableList.vue实现
 */

<template>
  <div class="zfarm-wrapper">

    <!--1. 查询条件-->
    <div class="table-search-wrapper" v-if="searchFields.length>0">
      <a-form layout="inline">
        <a-row :gutter="48">
          <a-col :md="8" :sm="24" v-for="field in searchFields" :key="field.name">
            <a-form-item :label="field.label">
              <a-select v-if="field.type === 'select'" placeholder="请选择" v-model="queryParam[field.name]">
                <a-select-option v-for="op in field.options" :key="op.key" :value="op.key">{{op.value}}</a-select-option>
              </a-select>
              <a-input-number v-if="field.type === 'Number' || field.type === 'Integer' || field.type === 'Float'"    
                  v-model="queryParam[field.name]" />
              <a-range-picker v-model="queryParam[field.name]" v-if="field.type === 'date'" />
              <a-range-picker v-model="queryParam[field.name]" v-if="field.type === 'datetime'"
                :showTime="{
                  hideDisabledOptions: true, 
                  defaultValue: [moment('00:00:00', 'HH:mm:ss'), moment('11:59:59', 'HH:mm:ss')]
                }"
                format="YYYY-MM-DD HH:mm:ss"
                 />
              <a-input v-model="queryParam[field.name]" v-else />
            </a-form-item>
          </a-col>
          <a-col :md="8" :sm="24">
            <span class="table-page-search-submitButtons">
              <a-button type="primary" @click="$refs.table.refresh(true)">查询</a-button>
              <a-button style="margin-left: 8px" @click="() => queryParam = {}">重置</a-button>
            </span>
          </a-col>

        </a-row>
      </a-form>
    </div>

    <!--2. 操作按钮区-->
    <s-table  :rowKey="rowKey" :columns="columns" :data="loadTableData">  
      <template v-for="item in tableSlots">

      </template>
    </s-table>
    <!--3. 表格区 及每行数据操作-->

    <!--4. 新增-->

    <!--4. 修改-->
    

  </div>
</template>

<script>
import moment from 'moment'
import { mapState, mapActions } from 'vuex'
import { STable } from '@/components'
import DetailList from '@/components/tools/DetailList'
const DetailListItem = DetailList.Item
import * as base from '@/api/base'

export default {
  name: 'ZFarm',
  components: {
    DetailList,
    DetailListItem,
    STable
  },
  props: {
    name:{
      type:String,
      required: true
    }
  },
  data(){
    return {
      queryParam: {}
    }
  },
  computed:{
    ...mapState({
      zfarms: state.zfarm.zfarms,
      pages: state.zfarm.pages,
    }),
    farm(){
      return this.zfarms[this.name] || {}
    },
    fields(){
      return this.zfarms.fields || []
    },
    searchFields(){
      return this.fields.filter(item => item.search && item.type!=='none')
    },
    tableSlots(){
      return this.fields.filter(item=> item.type==='Select' || item.type ==='Ref'  || item.type ==='Date' || item.type ==='DateTime'  || item.type ==='Time')
    },
    rowKey(){
      return this.zfarms.primaryKey || "id"
    },
    // 表格列定义
    columns(){
      return []
    }

  },
  created () {
    this.init()
  },
  methods: {
    ...mapActions(['zfarmInfo','pageInfo','modelInfoById']),
    moment,
    init(){
      this.zfarmInfo(this.name).then(result => {}).catch(err => { 
        console.error(err)
        this.$notification.error('请求发生错误！')
      })
    },
    loadTableData(){
      // 加载表体数据，返回promise
    }
  }

}
</script>

<style>

</style>
