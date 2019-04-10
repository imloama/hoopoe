<template>
  <div class="table-page-search-wrapper">
    <a-form layout="inline" :form="form" @submit="handleSubmit">
      <a-card>
        <template v-slot:title>
          <h4 style="text-align:left;margin:0;">
            {{ title }}
          </h4>
        </template>

        <template v-slot:extra>
          <a-row type="flex" justify="start" align="middle">
            <slot>
              <a-button type="primary" @click="handleSubmit">查询</a-button>
              <a-button style="margin-left: 8px" @click="resetSearchForm">重置</a-button>
            </slot>
            <slot name="extra"> </slot>
            <template v-if="maxItem < renderDataSource.length">
              <a-divider type="vertical" />
              <a @click="togglecollapsed" style="margin-left: 8px">
                {{ collapsed ? '收起' : '展开' }}
                <a-icon :type="collapsed ? 'up' : 'down'" />
              </a>
            </template>
          </a-row>
        </template>

        <a-row :gutter="gutter">
          <template v-for="(item, index) in renderDataSource">
            <field-render
              :SearchGlobalOptions="SearchGlobalOptions"
              :itemOptions="item"
              :key="item.fieldName"
              v-show="index < SearchGlobalOptions.maxItem || (index >= SearchGlobalOptions.maxItem && collapsed)"
            />
          </template>
        </a-row>
      </a-card>
    </a-form>
  </div>
</template>

<script>
import FieldRender from './FieldRender';
export default {
  components: {
    FieldRender
  },
  computed: {
    SearchGlobalOptions() {
      // 全局配置
      return {
        maxItem: this.maxItem,
        size: this.size,
        immediate: this.immediate,
        responsive: this.responsive
      };
    },
    renderDataSource() {
      // 重组传入的数据，合并全局配置，子项的配置优先全局
      return this.SearchDataSource.map(item => ({ ...this.SearchGlobalOptions, ...item }));
    }
  },
  props: {
    datetimeTotimeStamp: {
      // 是否把时间控件的返回值全部转为时间戳
      type: Boolean,
      default: false
    },
    maxItem: {
      // 超过多少个折叠
      type: Number,
      default: 3
    },
    gutter: {
      // 控件的间距
      type: Number,
      default: 48
    },
    size: {
      //  控件的尺寸
      type: String,
      default: 'small'
    },
    responsive: {
      type: Object,
      default: function() {
        return {
          xl: 8,
          md: 12,
          sm: 24
        };
      }
    },
    title: {
      type: String,
      default: '搜索条件区域'
    },
    SearchDataSource: {
      // 数据源
      type: Array,
      default: function() {
        return [
          {
            type: 'text', // 控件类型
            labelText: '控件名称', // 控件显示的文本
            fieldName: 'formField1',
            placeholder: '文本输入区域' // 默认控件的空值文本
          },
          {
            labelText: '数字输入框',
            type: 'number',
            fieldName: 'formField2',
            placeholder: '这只是一个数字的文本输入框'
          },
          {
            labelText: '单选框',
            type: 'radio',
            fieldName: 'formField3',
            defaultValue: '0',
            options: [
              {
                label: '选项1',
                value: '0'
              },
              {
                label: '选项2',
                value: '1'
              }
            ]
          },
          {
            labelText: '日期选择',
            type: 'datetime',
            fieldName: 'formField4',
            placeholder: '选择日期'
          },
          {
            labelText: '日期范围',
            type: 'datetimeRange',
            fieldName: 'formField5',
            placeholder: ['开始日期', '选择日期']
          },
          {
            labelText: '下拉框',
            type: 'select',
            fieldName: 'formField7',
            placeholder: '下拉选择你要的',
            options: [
              {
                label: 'text1',
                value: '0'
              },
              {
                label: 'text2',
                value: '0'
              }
            ]
          },
          {
            labelText: '联动',
            type: 'cascader',
            fieldName: 'formField6',
            placeholder: '级联选择',
            options: [
              {
                value: 'zhejiang',
                label: 'Zhejiang',
                children: [
                  {
                    value: 'hangzhou',
                    label: 'Hangzhou',
                    children: [
                      {
                        value: 'xihu',
                        label: 'West Lake'
                      },
                      {
                        value: 'xiasha',
                        label: 'Xia Sha',
                        disabled: true
                      }
                    ]
                  }
                ]
              },
              {
                value: 'jiangsu',
                label: 'Jiangsu',
                children: [
                  {
                    value: 'nanjing',
                    label: 'Nanjing',
                    children: [
                      {
                        value: 'zhonghuamen',
                        label: 'Zhong Hua men'
                      }
                    ]
                  }
                ]
              }
            ]
          }
        ];
      }
    }
  },
  data() {
    return {
      // 高级搜索 展开/关闭
      collapsed: false
    };
  },
  beforeCreate() {
    this.form = this.$form.createForm(this);
  },

  methods: {
    togglecollapsed() {
      this.collapsed = !this.collapsed;
    },
    handleParams(obj) {
      // 判断必须为obj
      if (!(Object.prototype.toString.call(obj) === '[object Object]')) {
        return {};
      }
      let tempObj = {};
      for (let [key, value] of Object.entries(obj)) {
        if (!value) continue;
        if (Array.isArray(value) && value.length <= 0) continue;
        if (Object.prototype.toString.call(value) === '[object Function]') continue;

        if (this.datetimeTotimeStamp) {
          // 若是为true,则转为时间戳
          if (Object.prototype.toString.call(value) === '[object Object]' && value._isAMomentObject) {
            // 判断moment
            value = value.valueOf();
          }
          if (Array.isArray(value) && value[0]._isAMomentObject && value[1]._isAMomentObject) {
            // 判断moment
            value = value.map(item => item.valueOf());
          }
        }
        // 若是为字符串则清除两边空格
        if (value && typeof value === 'string') {
          value = value.trim();
        }
        tempObj[key] = value;
      }

      return tempObj;
    },
    handleSubmit(e) {
      // 触发表单提交，也就是搜索按钮
      e.preventDefault();
      this.form.validateFields((err, values) => {
        if (!err) {
          console.log('处理前的表单数据', values);
          const queryParams = this.handleParams(values);

          this.$emit('change', queryParams);
        }
      });
    },
    resetSearchForm() {
      // 重置整个查询表单
      this.form.resetFields();
      this.$emit('change', null);
    }
  }
};
</script>

<style lang="sass">
.table-page-search-wrapper {
  .ant-form-inline {
    .ant-form-item {
      display: flex;
      margin-bottom: 24px;
      margin-right: 0;

      .ant-form-item-control-wrapper {
        flex: 1;
        display: inline-block;
        vertical-align: middle;
      }

      > .ant-form-item-label {
        line-height: 32px;
        padding-right: 8px;
        width: auto;
      }
      .ant-form-item-control {
        height: 32px;
        line-height: 32px;
        display: flex;
        justify-content: flex-start;
        align-items: center;
        .ant-form-item-children {
          min-width: 160px;
        }
      }
    }
  }

  .table-page-search-submitButtons {
    display: block;
    margin-bottom: 24px;
    white-space: nowrap;
  }
}
</style>

