import { axios } from '@/utils/request'

export const MODEL_USERS = 'users'
export const MODEL_ROLES = 'roles'
export const MODEL_MENUS = 'menus'
export const MODEL_DICTS = 'dicts'
export const MODEL_DEPTS = 'depts'

// 查询所有的部门，树状结构
export function getDepts () {
  return axios({
    url: '/depts/tree',
    method: 'get'
  })
}

export function checkUsername (username) {
  return axios({
    method: 'get',
    url: `/users/check/${username}`
  })
}

// 查询
export function getRoles (params = { pageSize: 500 }) {
  return axios({
    method: 'post',
    url: `/roles/page`,
    data: params
  })
}

// 创建用户
export function createUser (params) {
  return axios({
    method: 'post',
    url: `/users/create`,
    data: params
  })
}

// 修改用户
export function updateUser (id, params) {
  return axios({
    method: 'post',
    url: `/users/update/${id}`,
    data: params
  })
}
