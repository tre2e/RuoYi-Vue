import request from '@/utils/request'

// 查询邀请码列表
export function listInvitecode(query) {
  return request({
    url: '/manage/invitecode/list',
    method: 'get',
    params: query
  })
}

// 查询邀请码详细
export function getInvitecode(id) {
  return request({
    url: '/manage/invitecode/' + id,
    method: 'get'
  })
}

// 新增邀请码
export function addInvitecode(data) {
  return request({
    url: '/manage/invitecode',
    method: 'post',
    data: data
  })
}

// 修改邀请码
export function updateInvitecode(data) {
  return request({
    url: '/manage/invitecode',
    method: 'put',
    data: data
  })
}

// 删除邀请码
export function delInvitecode(id) {
  return request({
    url: '/manage/invitecode/' + id,
    method: 'delete'
  })
}
