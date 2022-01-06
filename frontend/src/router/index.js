import Vue from 'vue'
import Router from 'vue-router'
import MenuView from '@/views/common/MenuView'
import PageView from '@/views/common/PageView'
import LoginView from '@/views/login/Common'
import CustomList from '@/views/login/CustomList'
import EmptyPageView from '@/views/common/EmptyPageView'
import HomePageView from '@/views/HomePage'

import db from 'utils/localstorage'
import request from 'utils/request'

// 全局Router异常处理
const originalPush = Router.prototype.push
Router.prototype.push = function push(location) {
  return originalPush.call(this, location).catch(err => {
    if (typeof err !== 'undefined') console.log(err)
  })
}
Vue.use(Router)

let constRouter = [{
    path: '/customHan/:id',
    name: '自定义数据',
    component: CustomList
  },
  {
    path: '/login',
    name: '登录页',
    component: LoginView
  },
  {
    path: '/index',
    name: '首页',
    redirect: '/home'
  }
]

let router = new Router({
  routes: constRouter
})

const whiteList = ['/login','/customHan']

let asyncRouter

// 导航守卫，渲染动态路由
router.beforeEach((to, from, next) => {
  if (to.path.indexOf("customHan") > 0) {
    console.info(to.path)
    next()
    next(to.path)
  } else {
    if (whiteList.indexOf(to.path) !== -1) {
      next()
    }
    let token = db.get('USER_TOKEN')
    let user = db.get('USER')
    let userRouter = get('USER_ROUTER')
    if (token.length && user) {
      if (!asyncRouter) {
        if (!userRouter) {
          request.get(`menu/${user.username}`).then((res) => {
            asyncRouter = res.data
            save('USER_ROUTER', asyncRouter)
            go(to, next)
          }).catch(err => {
            console.error(err)
          })
        } else {
          asyncRouter = userRouter
          go(to, next)
        }
      } else {
        next()
      }
    } else {
      next('/login')
    }
  }
})

function go2(to, next) {
  router.addRoutes({
    path: '/customHan',
    name: '自定义数据',
    component: CustomList
  })
  next({
    ...to,
    replace: true
  })
}

function go(to, next) {
  asyncRouter = filterAsyncRouter(asyncRouter)
  console.info("go:")
  console.info(asyncRouter)
  router.addRoutes(asyncRouter)
  next({
    ...to,
    replace: true
  })
}

function save(name, data) {
  localStorage.setItem(name, JSON.stringify(data))
}

function get(name) {
  return JSON.parse(localStorage.getItem(name))
}

function filterAsyncRouter(routes) {
  console.info(routes)
  return routes.filter((route) => {
    let component = route.component
    if (component) {
      switch (route.component) {
        case 'MenuView':
          route.component = MenuView
          break
        case 'PageView':
          route.component = PageView
          break
        case 'EmptyPageView':
          route.component = EmptyPageView
          break
        case 'HomePageView':
          route.component = HomePageView
          break
        default:
          route.component = view(component)
      }
      if (route.children && route.children.length) {
        route.children = filterAsyncRouter(route.children)
      }
      return true
    }
  })
}

function view(path) {
  console.info(path)
  return function (resolve) {
    import(`@/views/${path}.vue`).then(mod => {
      resolve(mod)
    })
  }
}

export default router
