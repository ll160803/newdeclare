import Vue from 'vue'
import Router from 'vue-router'
import MenuView from '@/views/common/MenuView'
import PageView from '@/views/common/PageView'
import LoginView from '@/views/login/Common'
import CustomList from '@/views/login/CustomList'
import CustomStr from '@/views/login/CustomStr'
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
    path: '/customHan2/:id',
    name: '自定义数据2',
    component: CustomStr
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
            asyncRouter.forEach(item => {
              resetArr(item) 
            })
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
// 职称路由自动生成 （只争对职称管理，只需要years里面添加年份）
 function resetArr(arr) {
  if (!arr.children || arr.children.length == 0) return
    for (let i = 0; i < arr.children.length; i++) {
      const item = arr.children[i]
      if (item.name == '高级职称信息审核') {
        const years = ['2020', '2021', '2022']
        const object = {
          name: '高级职称',
          path: '/dca/AuditBaseInfoA',
          component: 'dca/AuditBaseInfoA'
        }
        const routes = createRoutes(years, object)
        item.children = []
        item.children.push(...routes)
      }
      if (item.name == '中初级职称信息审核') {
        const years = ['2019', '2020', '2021']
        const object = {
          name: '中初级职称',
          path: '/dca/AuditBaseInfoB',
          component: 'dca/AuditBaseInfoB'
        }
        const routes = createRoutes(years, object)
        item.children = []
        item.children.push(...routes)
      }
      if (item.name == '二三级职称信息审核') {
        const years = ['2020', '2021', '2022']
        const object = {
          name: '二三级职称',
          path: '/dca/AuditBaseInfoC',
          component: 'dca/AuditBaseInfoC'
        }
        const routes = createRoutes(years, object)
        item.children = []
        item.children.push(...routes)
      }
      resetArr(item)
    }
 }
 // 创建‘职称’路由
 function createRoutes(years, object) {
  const addNewRoutes = []
  years.forEach(item => {
    const routeObject = {
      name: item + '年' + object.name,
      path: object.path + '=' + item,
      // query: {year: item},
      component: object.component
    }
    addNewRoutes.push(routeObject)
  })
  return addNewRoutes;
 }

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
