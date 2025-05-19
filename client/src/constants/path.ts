import Member from '@/pages/member/member.vue'
import { Components } from 'ant-design-vue/es/date-picker/generatePicker'

export const ROUTES_CONSTANTS = {
  LOGIN: {
    path: '/login',
    name: 'login'
  },
  LOGIN_GOOGLE: {
    path: '/login-google',
    name: 'login-google'
  },
  REGISTER: {
    path: '/register',
    name: 'register'
  },
  REGISTER_GOOGLE: {
    path: '/register-google',
    name: 'register-google'
  },
  // ADMIN
  ADMIN: {
    path: '/admin',
    name: 'admin',
    children: {
      ADMIN: {
        path: 'admin',
        name: 'admin'
      },

    }
  },
  // MANAGE
  MANAGE: {
    path: '/manage',
    name: 'manage',
    children: {
      MANAGE: {
        path: 'mn',
        name: 'mn'
      },
      INTERN: {
        path: 'intern',
        name: 'intern'
      },
      MEETINGS: {
        path: 'meetings',
        name: 'meetings'
      },
      EVALUATION: {
        path: 'evaluation-intern/:id',
        name: 'evaluation-intern'
      },
    }
  },
  // MEMBER
  MEMBER: {
    path: '/member',
    name: 'member',
    children: {
      MEMBER: {
        path: 'mb',
        name: 'mb'
      }
    }
  },
  FORBIDDEN: {
    path: '/error/403',
    name: 'Forbidden'
  },

  UNAUTHORIZED: {
    path: '/error/401',
    name: 'Unauthorized'
  },

  NOT_FOUND: {
    path: '/:pathMatch(.*)*',
    name: 'NotFound'
  },

  REDIRECT: {
    path: '/redirect',
    name: 'redirect'
  }
}
