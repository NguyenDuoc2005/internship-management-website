<template>
  <div class="min-h-screen flex items-center justify-center bg-gradient-to-br from-blue-500 via-white to-pink-200 px-2">
    <div class="relative w-full max-w-xs sm:max-w-sm p-0">
      <!-- Card shadow layer for glass morphism -->
      <div
        class="absolute inset-0 z-0 rounded-2xl blur-xl opacity-60 bg-gradient-to-br from-blue-300/60 via-white/50 to-pink-200/60">
      </div>

      <div
        class="relative z-10 bg-white/90 rounded-2xl shadow-lg p-7 sm:p-9 flex flex-col items-center backdrop-blur-xl border border-blue-100">
        <div
          class="mb-3 w-16 h-16 flex items-center justify-center bg-gradient-to-br from-blue-400 to-pink-400 rounded-full shadow-md">
          <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" stroke-width="2" viewBox="0 0 24 24">
            <path stroke-linecap="round" stroke-linejoin="round" d="M5 13l4 4L19 7" />
          </svg>
        </div>
        <h2 class="text-2xl font-bold text-center mb-1 text-gray-800 select-none">
          Đăng nhập tài khoản
        </h2>
        <p class="text-center text-sm text-gray-500 mb-6 select-none">
          Chào mừng bạn đã quay trở lại!
        </p>

        <form @submit.prevent="handleLogin" class="w-full space-y-4">
          <div>
            <label for="email" class="block text-xs font-semibold text-gray-600 mb-1">Email</label>
            <input v-model="email" type="email" id="email" required
              class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
          </div>
          <div>
            <label for="password" class="block text-xs font-semibold text-gray-600 mb-1">Mật khẩu</label>
            <input v-model="password" type="password" id="password" required
              class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
            <p v-if="loginError" class="text-red-500 text-xs mt-1">{{ loginError }}</p>
          </div>

          <button type="submit"
            class="w-full bg-gradient-to-r from-pink-500 to-blue-500 hover:from-blue-600 hover:to-pink-600 text-white py-2 rounded-lg font-semibold text-sm transition shadow">
            Đăng nhập
          </button>

          <button type="button" @click="loginWithGoogle"
            class="w-full bg-white border border-gray-300 hover:bg-gray-100 text-gray-700 py-2 rounded-lg font-semibold text-sm transition flex items-center justify-center gap-2 shadow-sm">
            <svg class="w-4 h-4" viewBox="0 0 24 24">
              <path fill="currentColor"
                d="M21.35 11.1H12v2.8h5.35c-.23 1.27-1.34 3.7-5.35 3.7-3.22 0-5.85-2.63-5.85-5.85S8.78 5.9 12 5.9c1.84 0 3.08.79 3.79 1.47l2.6-2.52C17.22 3.28 14.83 2.3 12 2.3 6.78 2.3 2.6 6.48 2.6 11.7S6.78 21.1 12 21.1c6.29 0 10.45-4.42 10.45-10.65 0-.71-.1-1.24-.22-1.35z" />
            </svg>
            <span>Đăng nhập với Google</span>
          </button>
        </form>

        <div class="text-center mt-6 text-xs">
          <span class="text-gray-500">Chưa có tài khoản?</span>
          <a href="/register" class="ml-1 text-pink-500 hover:text-blue-500 font-semibold transition-colors">Đăng ký</a>
        </div>
      </div>
    </div>

    <!-- Modal chọn vai trò -->
    <a-modal v-model:open="showRoleModal" title="Chọn vai trò" :footer="null" centered :width="500"
      bodyStyle="padding: 1.5rem 2rem;">
      <div class="flex flex-col items-center space-y-6">
        <p class="text-center text-gray-700 text-sm font-medium">
          Vui lòng chọn vai trò để tiếp tục đăng nhập bằng Google
        </p>
        <div class="flex justify-center gap-6 w-full">
          <!-- Trong modal chọn vai trò -->
          <button :class="[
            'flex flex-col items-center justify-center w-24 h-20 rounded-lg border cursor-pointer transition-shadow',
            selectedRole === 'ADMIN' ? 'border-green-500 shadow-md bg-green-50' : 'border-gray-300 hover:shadow-sm hover:bg-green-50',
          ]" @click="selectedRole = 'ADMIN'" type="button">
            <span class="text-xl mb-1">🧑‍⚖️</span>
            <span class="font-semibold text-sm text-center">Giám đốc</span>
          </button>

          <button :class="[
            'flex flex-col items-center justify-center w-24 h-20 rounded-lg border cursor-pointer transition-shadow',
            selectedRole === 'MANAGE' ? 'border-green-500 shadow-md bg-green-50' : 'border-gray-300 hover:shadow-sm hover:bg-green-50',
          ]" @click="selectedRole = 'MANAGE'" type="button">
            <span class="text-xl mb-1">👨‍💼</span>
            <span class="font-semibold text-sm text-center">Quản lý</span>
          </button>

          <button :class="[
            'flex flex-col items-center justify-center w-24 h-20 rounded-lg border cursor-pointer transition-shadow',
            selectedRole === 'MEMBER' ? 'border-green-500 shadow-md bg-green-50' : 'border-gray-300 hover:shadow-sm hover:bg-green-50',
          ]" @click="selectedRole = 'MEMBER'" type="button">
            <span class="text-xl mb-1">👤</span>
            <span class="font-semibold text-sm text-center">Thành viên</span>
          </button>
        </div>


        <div class="flex justify-end gap-3 w-full">
          <a-button @click="cancelRoleSelection">Hủy</a-button>
          <a-button type="primary" :disabled="!selectedRole" @click="confirmRoleSelection">
            Xác nhận
          </a-button>
        </div>
      </div>
    </a-modal>
  </div>
</template>


<script setup lang="ts">
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
import { login } from '@/services/api/auth.api'
import { getUserInformation } from '@/utils/token.helper'
import { cookieStorageAction, localStorageAction } from '@/utils/storage'
import { ACCESS_TOKEN_STORAGE_KEY, REFRESH_TOKEN_STORAGE_KEY, USER_INFO_STORAGE_KEY } from '@/constants/storagekey'
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import { URL_OAUTH2_GOOGLE_ADMIN, URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MEMBER } from '@/constants/url'
import { ACCOUNT_EXIST, ACCOUNT_EXIST_MESSAGE, ACCOUNT_NOT_EXIST, ACCOUNT_NOT_EXIST_MESSAGE } from '@/constants/cookie.constants'
import { toast } from 'vue3-toastify'

const router = useRouter()
const email = ref('')
const password = ref('')
const loginError = ref('')
const showRoleModal = ref(false)
const selectedRole = ref('')

const handleLogin = async () => {
  loginError.value = ''
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
  showRoleModal.value = true
}

function cancelRoleSelection() {
  showRoleModal.value = false
  selectedRole.value = ''
}

function confirmRoleSelection() {
  if (!selectedRole.value) return

  showRoleModal.value = false

  if (selectedRole.value === 'ADMIN') {
    window.location.href = URL_OAUTH2_GOOGLE_ADMIN();
  } else if (selectedRole.value === 'MANAGE') {
    window.location.href = URL_OAUTH2_GOOGLE_MANAGE();
  } else if (selectedRole.value === 'MEMBER') {
    window.location.href = URL_OAUTH2_GOOGLE_MEMBER();
  }
}
onMounted(() => {

  const accountNotExistError = cookieStorageAction.get(ACCOUNT_NOT_EXIST)

  const accountExistError = cookieStorageAction.get(ACCOUNT_EXIST)

  if (accountNotExistError) {
    toast.error(ACCOUNT_NOT_EXIST_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_NOT_EXIST)
  }
  if (accountExistError) {
    toast.error(ACCOUNT_EXIST_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_EXIST)
  }
})
</script>
