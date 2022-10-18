import axios from 'axios'
import {
  message,
  Modal,
  notification
} from 'ant-design-vue'
import moment from 'moment'

moment.locale('zh-cn')

// 统一配置
let FEBS_REQUEST = axios.create({
  baseURL: 'https://rclw.hbwsrc.cn:5999/',
  //baseURL: 'http://localhost:1099/',
  responseType: 'json',
  validateStatus(status) {
    // 200 外的状态码都认定为失败
    return status === 200
  }
})


// 拦截请求
FEBS_REQUEST.interceptors.request.use((config) => {
  return config
}, (error) => {
  return Promise.reject(error)
})

// 拦截响应
FEBS_REQUEST.interceptors.response.use((config) => {
  return config
}, (error) => {
  console.info(error,'3333333333')
  if (error.response) {
    let errorMessage = error.response.data === null ? '系统内部异常，请联系网站管理员' : error.response.data.message
    switch (error.response.status) {
      case 404:
        notification.error({
          message: '系统提示',
          description: '很抱歉，资源未找到',
          duration: 4
        })
        break
      case 403:
      case 401:
        notification.warn({
          message: '系统提示',
          description: '很抱歉，您无法访问该资源，可能是因为没有相应权限或者登录已失效',
          duration: 4
        })
        break
      default:
        notification.error({
          message: '系统提示',
          description: errorMessage,
          duration: 4
        })
        break
    }
  }
  notification.warn({
    message: '系统提示',
    description: '很抱歉，智能搜索网络不通，请手工录入或者联系管理员',
    duration: 4
  })
  return Promise.reject(error)
})
const request = {
  baseURL: 'https://whuhhrmapi.asclepius.whxh.com.cn/',
  post(url, params, token) {
    return FEBS_REQUEST.post(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
        'token': token==undefined ?'': token
      }
    })
  },
  put(url, params, token ) {
    return FEBS_REQUEST.put(url, params, {
      transformRequest: [(params) => {
        let result = ''
        Object.keys(params).forEach((key) => {
          if (!Object.is(params[key], undefined) && !Object.is(params[key], null)) {
            result += encodeURIComponent(key) + '=' + encodeURIComponent(params[key]) + '&'
          }
        })
        return result
      }],
      headers: {
        'Content-Type': 'application/x-www-form-urlencoded',
         'token': token==undefined?'':token
      }
    })
  },
  get(url, params,token) {
    let _params
    if (Object.is(params, undefined)) {
      _params = ''
    } else {
      _params = '?'
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`
        }
      }
    }
      return FEBS_REQUEST.get(`${url}${_params}`,{headers: {
         'token': token==undefined?'':token
      }})
  },
  delete(url, params) {
    let _params
    if (Object.is(params, undefined)) {
      _params = ''
    } else {
      _params = '?'
      for (let key in params) {
        if (params.hasOwnProperty(key) && params[key] !== null) {
          _params += `${key}=${params[key]}&`
        }
      }
    }
    return FEBS_REQUEST.delete(`${url}${_params}`)
  },
}

export default request
