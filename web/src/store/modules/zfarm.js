
import * as base from '@/api/base'

export const SET_ZFARM_INFO = 'SET_ZFARM_INFO'
export const SET_ZFARM_PAGE = 'SET_ZFARM_PAGE'

const state = {
  zfarms: {},
  pages: {}
}

const actions = {
  // 查询zfarm信息
  async zfarmInfo ({ commit, state }, name) {
    const result = await base.getZFarm(name)
    commit(SET_ZFARM_INFO, name, result)
  },
  // 查询分页信息
  async pageInfo ({ commit, state }, { name, params }) {
    const result = await base.getModelPage(name, params)
    commit(SET_ZFARM_PAGE, name, params, result)
  }
  // 根据id查询模型信息

  // 新建对象

  // 修改对象

  // 删除多个对象

  // 执行对象的action

}

const mutations = {
  [SET_ZFARM_INFO] (state, name, result) {
    state.zfarms[name] = result.data
  },
  [SET_ZFARM_PAGE] (state, name, params, result) {
    const page = result.data
    state.pages[name] = page
  }
}

export default {
  state,
  actions,
  mutations
}
