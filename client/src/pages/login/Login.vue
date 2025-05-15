<template>
  <div class="min-h-screen bg-cover bg-center flex items-center justify-center px-4"
    style="background-image: url('https://i.pinimg.com/originals/c2/e9/02/c2e902e031e1d9d932411dd0b8ab5eef.jpg')">
    <div class="bg-white/90 backdrop-blur-sm shadow-2xl rounded-xl p-6 w-full max-w-sm relative">
      <h2 class="text-xl font-bold text-center mb-1 text-gray-800">Chào mừng bạn!</h2>
      <p class="text-center text-sm text-gray-500 mb-5">Đăng nhập để tiếp tục</p>

      <form @submit.prevent="handleLogin" class="space-y-3">
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1" for="email">Email</label>
          <input v-model="email" type="email" id="email" required
            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-400" />
        </div>
        <div>
          <label class="block text-sm font-medium text-gray-700 mb-1" for="password">Mật khẩu</label>
          <input v-model="password" type="password" id="password" required
            class="w-full px-3 py-2 border border-gray-300 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-400" />
          <p v-if="loginError" class="text-red-500 text-sm mt-1">{{ loginError }}</p>
        </div>


        <button type="submit"
          class="w-full bg-blue-500 hover:bg-blue-600 text-white py-2 rounded-lg font-semibold text-sm transition duration-200">
          Đăng nhập
        </button>

        <button type="button" @click="loginWithGoogle"
          class="w-full bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg font-semibold text-sm transition duration-200 flex items-center justify-center gap-2">
          <svg class="w-4 h-4 fill-white" viewBox="0 0 24 24">
            <path
              d="M21.35 11.1H12v2.8h5.35c-.23 1.27-1.34 3.7-5.35 3.7-3.22 0-5.85-2.63-5.85-5.85S8.78 5.9 12 5.9c1.84 0 3.08.79 3.79 1.47l2.6-2.52C17.22 3.28 14.83 2.3 12 2.3 6.78 2.3 2.6 6.48 2.6 11.7S6.78 21.1 12 21.1c6.29 0 10.45-4.42 10.45-10.65 0-.71-.1-1.24-.22-1.35z" />
          </svg>
          <span>Đăng nhập bằng Google</span>
        </button>
      </form>

      <div class="text-right mt-4 text-sm">
        <a href="/register" class="text-blue-600 hover:underline">Chưa có tài khoản? Đăng ký</a>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import { ACCESS_TOKEN_STORAGE_KEY, REFRESH_TOKEN_STORAGE_KEY, USER_INFO_STORAGE_KEY } from '@/constants/storagekey'
import { login } from '@/services/api/auth.api'
import { localStorageAction } from '@/utils/storage'
import { getUserInformation } from '@/utils/token.helper'
import { onMounted, ref } from 'vue'
const router = useRouter()
const email = ref('')
const password = ref('')
import { useRouter } from 'vue-router'

const loginError = ref('')

const handleLogin = async () => {
  
  loginError.value = '' // reset lỗi cũ
  try {

    const payload = {
      email: email.value,
      password: password.value
    }

    const res = await login(payload)
    const user = getUserInformation(res.data)
    localStorageAction.set(USER_INFO_STORAGE_KEY, user)
    localStorageAction.set(ACCESS_TOKEN_STORAGE_KEY, res.data)
    localStorageAction.set(REFRESH_TOKEN_STORAGE_KEY, res.data)
    console.log(res.data)
    if (user.rolesCodes.includes(ROLES.ADMIN) && user.roleScreen === ROLES.ADMIN) { 
       router.push({ name: ROUTES_CONSTANTS.ADMIN.children.ADMIN.name })
    
    } else if (user.rolesCodes.includes(ROLES.MANAGE) && user.roleScreen === ROLES.MANAGE) {
     
      router.push({ name: ROUTES_CONSTANTS.MANAGE.children.MANAGE.name })
       
    } else {  
      router.push({ name: ROUTES_CONSTANTS.MEMBER.children.MEMBER.name })
    }
  } catch (err: any) {
    loginError.value = err.response?.data?.message || 'Tài khoản hoặc mật khẩu không đúng'
  }
}

function loginWithGoogle() {
  router.push({ name: ROUTES_CONSTANTS.LOGIN_GOOGLE.name })
}

</script>