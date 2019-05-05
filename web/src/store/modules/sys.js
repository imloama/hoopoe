/**
 * sys store
 */
import * as sysapi from '@/api/sys'
import * as base from '@/api/base'
import { getRemainingRequest } from 'loader-utils';

export const GET_DEPT_TREE = 'GET_DEPT_TREE'
export const GET_ROLES_PAGE = 'GET_ROLES_PAGE'
export const GET_MENU_TREE = 'GET_MENU_TREE'

const state = {
  deptTree: null,
  // role page
  rolePage: null,
  // 当前所有的菜单项
  menuTree: null
}

const actions = {
  async getDeptTree ({ commit, state }, params = { query: [] }) {
    const data = await sysapi.getDepts(params)
    commit(GET_DEPT_TREE, data ? data.children ? data.children : [] : [])
  },
  async getRolePage ({ commit, state }, params) {
    const page = await sysapi.getRoles(params)
    commit(GET_ROLES_PAGE, page)
  },
  async getMenuTree ({ commit, state }, params = { query: [] }) {
    const data = await sysapi.getMenuTree(params)
    commit(GET_MENU_TREE, data)
  }

}

const mutations = {
  [GET_DEPT_TREE] (state, tree) {
    state.deptTree = tree
  },
  [GET_ROLES_PAGE] (state, page) {
    state.rolePage = page
  },
  [GET_MENU_TREE] (state, data) {
    state.menuTree = data
  }
}

export default {
  state,
  actions,
  mutations
}
