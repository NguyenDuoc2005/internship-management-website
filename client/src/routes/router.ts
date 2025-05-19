import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import { useAuthStore } from '@/stores/auth'
import { createRouter, createWebHistory } from 'vue-router'
import type { RouteRecordRaw } from 'vue-router'

export const routes: RouteRecordRaw[] = [
  // Login route
  {
    path: ROUTES_CONSTANTS.LOGIN.path,
    name: ROUTES_CONSTANTS.LOGIN.name,
    component: () => import('@/pages/login/Login.vue')
  },
  {
    path: ROUTES_CONSTANTS.LOGIN_GOOGLE.path,
    name: ROUTES_CONSTANTS.LOGIN_GOOGLE.name,
    component: () => import('@/pages/login/LoginGoogle.vue')
  },
  {
    path: ROUTES_CONSTANTS.REGISTER_GOOGLE.path,
    name: ROUTES_CONSTANTS.REGISTER_GOOGLE.name,
    component: () => import('@/pages/login/RegisterGoogle.vue')
  },
  {
    path: '/',
    component: () => import('@/pages/login/Login.vue')
  },
  // đăng ký route
  {
    path: ROUTES_CONSTANTS.REGISTER.path,
    name: ROUTES_CONSTANTS.REGISTER.name,
    component: () => import('@/pages/login/Register.vue')
  },
  //  admin
  {
    path: ROUTES_CONSTANTS.ADMIN.path,
    redirect: `${ROUTES_CONSTANTS.ADMIN.path}/${ROUTES_CONSTANTS.ADMIN.children.ADMIN.path}`,
    component: () => import('@/layout/Admin.vue'),
    children: [

      {
        path: `${ROUTES_CONSTANTS.ADMIN.children.ADMIN.path}`,
        name: `${ROUTES_CONSTANTS.ADMIN.children.ADMIN.name}`,
        component: () => import('@/pages/admin/admin.vue')
      }
    ]
  },
  //  manage
  {
    path: ROUTES_CONSTANTS.MANAGE.path,
    name: ROUTES_CONSTANTS.MANAGE.name,
    component: () => import('@/layout/Manage.vue'),
    children: [
      //manage
      {
        path: ROUTES_CONSTANTS.MANAGE.children.MANAGE.name,
        name: ROUTES_CONSTANTS.MANAGE.children.MANAGE.path,
        component: () => import('@/pages/manage/manage.vue'),
        meta: {
          requiresRole: [ROLES.MANAGE],
          requiresAuth: true
        }
      },
      //intern
      {
        path: ROUTES_CONSTANTS.MANAGE.children.INTERN.path,
        name: ROUTES_CONSTANTS.MANAGE.children.INTERN.name,
        component: () => import('@/pages/manage/intern/Intern.vue'),
      },
      //meetings
      {
        path: ROUTES_CONSTANTS.MANAGE.children.MEETINGS.path,
        name: ROUTES_CONSTANTS.MANAGE.children.MEETINGS.name,
        component: () => import('@/pages/manage/meetings/Meetings.vue'),
      },
      // evaluation 
      {
        path: ROUTES_CONSTANTS.MANAGE.children.EVALUATION.path,
        name: ROUTES_CONSTANTS.MANAGE.children.EVALUATION.name,
        component: () => import('@/pages/manage/meetings/EvaluationUserModal.vue'),
      },
    ]
  },
  //  member
  {
    path: ROUTES_CONSTANTS.MEMBER.path,
    name: ROUTES_CONSTANTS.MEMBER.name,
    component: () => import('@/layout/Member.vue'),
    children: [
      {
        path: ROUTES_CONSTANTS.MEMBER.children.MEMBER.name,
        name: ROUTES_CONSTANTS.MEMBER.children.MEMBER.path,
        component: () => import('@/pages/member/member.vue')
      },
    ]
  },
  // Root route
  {
    path: '/',
    component: () => import('@/routes/guard/Landing.vue')
  },
  // Not Found route
  {
    path: ROUTES_CONSTANTS.NOT_FOUND.path,
    name: ROUTES_CONSTANTS.NOT_FOUND.name,
    component: () => import('@/pages/404/NotFound.vue')
  },
  // 403 route
  {
    path: ROUTES_CONSTANTS.FORBIDDEN.path,
    name: ROUTES_CONSTANTS.FORBIDDEN.name,
    component: () => import('@/pages/403/Forbidden.vue')
  },
  // 401 route
  {
    path: ROUTES_CONSTANTS.UNAUTHORIZED.path,
    name: ROUTES_CONSTANTS.UNAUTHORIZED.name,
    component: () => import('@/pages/401/Unauthorized.vue')
  },
  // Redirect route
  {
    path: ROUTES_CONSTANTS.REDIRECT.path,
    name: ROUTES_CONSTANTS.REDIRECT.name,
    component: () => import('@/routes/guard/Redirect.vue')
  }
]

export const router = createRouter({
  history: createWebHistory(),
  routes
})

router.beforeEach((to, _from, next) => {
  const authStore = useAuthStore()
  const requiresAuth = to.matched.some((record) => record.meta.requiresAuth)

  const requiresRole = to.meta.requiresRole as string | string[]

  console.log('requiresRole : ', requiresRole)

  const userRole = authStore?.user?.rolesCodes
  console.log('userRole', userRole)

  if (requiresAuth && !authStore.isAuthenticated) {
    next({ name: ROUTES_CONSTANTS.LOGIN.name })
  } else if (requiresRole) {
    if (Array.isArray(requiresRole)) {
      if (requiresRole.some((role) => userRole?.includes(role))) {
        next()
      } else {
        next({ name: ROUTES_CONSTANTS.FORBIDDEN.name })
      }
    } else {
      if (userRole?.includes(requiresRole)) {
        next()
      } else {
        next({ name: ROUTES_CONSTANTS.FORBIDDEN.name })
      }
    }
  } else {
    next()
  }
})
