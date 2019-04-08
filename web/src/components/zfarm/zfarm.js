/**
 * 从前台定义zfarm数据
 */
export const FIELDTYPE_NONE = 'None'
export const FIELDTYPE_String = 'String'
export const FIELDTYPE_Email = 'Email'
export const FIELDTYPE_Password = 'Password'
export const FIELDTYPE_Telphone = 'Telphone'
export const FIELDTYPE_IP = 'IP'
export const FIELDTYPE_URL = 'URL'
export const FIELDTYPE_Image = 'Image'
export const FIELDTYPE_File = 'File'
export const FIELDTYPE_Number = 'Number'
export const FIELDTYPE_Integer = 'Integer'
export const FIELDTYPE_Float = 'Float'
export const FIELDTYPE_Date = 'Date'
export const FIELDTYPE_DateTime = 'DateTime'
export const FIELDTYPE_Time = 'Time'
export const FIELDTYPE_Ref = 'Ref'
export const FIELDTYPE_Select = 'Select'

class ZFarm {
  constructor(){
    this.apiprefix = null
    this.primaryKey = 'id'
    this.tree = false
    this.parentKey = null
    this.fields = [] // ZField[]
    this.actions = [] // ZAction[]
  }
}



class ZField {
 
  constructor(){
    this.type = FIELDTYPE_String
    this.search = true
    this.show = true //是否展示
    this.edit = true;//是否可编辑
    this.name;
    this.label;
    this.showLabel = true;
    this.format;//格式化输出
    this.rules = Lists.newArrayList();
    this.children = Lists.newArrayList();
    // 可能的选项
    this.options;
     /**
   * 参加类型时
   */
    this.ref;
  }

 
}

/**
 * 一行数据的按钮
 */
class ZAction {
  constructor(){
    this.name = null
    this.label = null
    this.filter = null
  }
}


 /*
 export default class ZFarm {
  constructor (name, data) {
    // zfarm 前缀,组成/api/v1/${name}
    this.name = name
    
    this.fields = data.fields

    // 查询条件
    this.searchs = []
    // 表格字段
    this.tableFields = []
    // 分页数据
    // 新增form字段
    // 修改form字段
  }

  /**
   * 异步加载数据
   */
  load () {
    
  }

}
*/