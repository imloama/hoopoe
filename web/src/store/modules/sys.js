/**
 * sys store
 */
import * as sysapi from '@/api/sys'

export const GET_DEPT_TREE = 'GET_DEPT_TREE'

const state = {
  deptTree: null
}

const actions = {
  async getDeptTree ({ commit, state }) {
    const data = await sysapi.getDepts()
    commit(GET_DEPT_TREE, data.data)
  }
}

const mutations = {
  [GET_DEPT_TREE] (state, tree) {
    state.deptTree = tree
  }
}

export default {
  state,
  actions,
  mutations
}
