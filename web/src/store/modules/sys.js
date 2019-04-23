/**
 * sys store
 */
import * as sysapi from '@/api/sys'

export const GET_DEPT_TREE = 'GET_DEPT_TREE'
export const GET_ROLES_PAGE = 'GET_ROLES_PAGE'

const state = {
  deptTree: null,
  // role page
  rolePage: null
}

const actions = {
  async getDeptTree ({ commit, state }, params = { query: [] }) {
    const data = await sysapi.getDepts(params)
    commit(GET_DEPT_TREE, data ? data.children ? data.children : [] : [])
  },
  async getRolePage ({ commit, state }, params) {
    const page = await sysapi.getRoles(params)
    commit(GET_ROLES_PAGE, page)
  }
}

const mutations = {
  [GET_DEPT_TREE] (state, tree) {
    state.deptTree = tree
  },
  [GET_ROLES_PAGE] (state, page) {
    state.rolePage = page
  }
}

export default {
  state,
  actions,
  mutations
}
