import { mapActions } from 'vuex';
/**
 * 菜单树
 * 是否支持选择由参数传递
 */
<template>
  <a-tree
    showLine
    :checkable="checkable"
    @expand="onExpand"
    :expandedKeys="expandedKeys"
    :autoExpandParent="autoExpandParent"
    v-model="checkedKeys"
    @select="onSelect"
    :selectedKeys="selectedKeys"
    :treeData="treeData"
  />
</template>

<script>
import { mapState, mapActions } from 'vuex'

export default {
  name: 'MenuTree',
  props: {
    checkable:{
      type: Boolean,
      default: false
    }
  },
  data(){
    return {
      autoExpandParent: true,
      // 展开的key
      expandedKeys: [],
      checkedKeys: [],
      selectedKeys: [],
      treeData: []
    }
  },
  computed: {
    ...mapState({
      menuTree: state => state.sys.menuTree,
    })
  },
  created(){
    this.getMenuTree().then(()=>{
      this.$nextTick(()=>{
        this.rebuildMenuTree()
      })
    }).catch(err=>{
      console.error(err)
    })
  },
  methods: {
    ...mapActions(['getMenuTree']),
    rebuildMenuTree(){
      let children = this.menuTree.children
      if(!children)return
      this.treeData = children.map(item => this.transform(item))
    },
    transform(item){
      let titem = Object.assign({title: item.label}, item)
      let child = item.children
      if(child === null || typeof child === 'undefined' || child.length === 0)return titem;
      child = child.map( c => this.transform(c))
      titem.children = child
      return titem
    },
    onExpand (expandedKeys) {
      console.log('onExpand', expandedKeys)
      // if not set autoExpandParent to false, if children expanded, parent can not collapse.
      // or, you can remove all expanded children keys.
      this.expandedKeys = expandedKeys
      this.autoExpandParent = false
    },
    onCheck (checkedKeys) {
      console.log('onCheck', checkedKeys)
      this.checkedKeys = checkedKeys
      this.$emit('check', checkedKeys)
    },
    onSelect (selectedKeys, info) {
      console.log('onSelect', info)
      this.selectedKeys = selectedKeys
      this.$emit('select', selectedKeys)
    },
    // 设置当前选中的内容
    setCheckedKeys(checkedKeys){
      this.checkedKeys = checkedKeys;
    }
  }
}
</script>
