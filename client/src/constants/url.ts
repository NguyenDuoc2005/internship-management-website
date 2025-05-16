import Register from '@/pages/login/Register.vue'
import { ROLES } from './roles'

export const { VITE_BASE_URL_SERVER } = import.meta.env || {}

export const { VITE_BASE_URL_CLIENT } = import.meta.env || {}

// DOMAIN
export const DOMAIN_BACKEND = `${VITE_BASE_URL_SERVER}` as string

export const DOMAIN_FRONTEND = `${VITE_BASE_URL_CLIENT}` as string

export const URL_FRONTEND = `${DOMAIN_FRONTEND}/redirect`

// export const URL_FRONTEND_ORGANIZATION = `${DOMAIN_FRONTEND}/organization`

//SUB_REDIRECT
export const SCREEN_ROLE_ADMIN = `&screen=${ROLES.ADMIN}`
export const SCREEN_ROLE_MANAGE = `&screen=${ROLES.MANAGE}`
export const SCREEN_ROLE_MEMBER = `&screen=${ROLES.MEMBER}`
export const REGISTER = `&register=true`
export const NOT_REGISTER = `&register=false`
export const URL_OAUTH2_GOOGLE_ADMIN = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_ADMIN}${NOT_REGISTER}` as string
export const URL_OAUTH2_GOOGLE_MANAGE = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_MANAGE}${NOT_REGISTER}` as string
export const URL_OAUTH2_GOOGLE_MEMBER = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_MEMBER}${NOT_REGISTER}` as string
// Register google 
export const URL_OAUTH2_GOOGLE_ADMIN_REGISTER = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_ADMIN}${REGISTER}` as string
export const URL_OAUTH2_GOOGLE_MANAGE_REGISTER = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_MANAGE}${REGISTER}` as string
export const URL_OAUTH2_GOOGLE_MEMBER_REGISTER = () =>
  `${DOMAIN_BACKEND}/oauth2/authorize/google?redirect_uri=${URL_FRONTEND}${SCREEN_ROLE_MEMBER}${REGISTER}` as string
export const API_URL = `${VITE_BASE_URL_SERVER}/api/v1` as string

// AUTH API
export const PREFIX_API_AUTH = `${API_URL}/auth` as string

// ADMIN API
export const PREFIX_API_ADMIN = `${API_URL}/admin` as string
// ADMIN API
export const PREFIX_API_MANAGE = `${API_URL}/manage` as string
export const PREFIX_API_INTERN_MANAGE = `${PREFIX_API_MANAGE}/intern` as string
export const PREFIX_API_MEETINGS_MANAGE = `${PREFIX_API_MANAGE}/meetings` as string
export const PREFIX_API_EVALUATION_MANAGE = `${PREFIX_API_MANAGE}/evaluation` as string

// MEMBER API
export const PREFIX_API_MEMBER = `${API_URL}/member` as string

// AUTH 
export const PREFIX_API_AUTH_LOGIN = `${PREFIX_API_AUTH}/login` as string
export const PREFIX_API_AUTH_REGISTER = `${PREFIX_API_AUTH}/register` as string
export const PREFIX_API_AUTH_ALL_ROLE = `${PREFIX_API_AUTH}/all-role` as string


