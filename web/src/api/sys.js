import { axios } from '@/utils/request'

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
