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
              class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 bg-white/80 transition" />
          </div>
          <div>
            <label for="password" class="block text-xs font-semibold text-gray-600 mb-1">Mật khẩu</label>
            <input v-model="password" type="password" id="password" required
              class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-blue-400 bg-white/80 transition" />
            <p v-if="loginError" class="text-red-500 text-xs mt-1">{{ loginError }}</p>
          </div>

          <button type="submit"
            class="w-full bg-gradient-to-r from-pink-500 to-blue-500 hover:from-blue-600 hover:to-pink-600 text-white py-2 rounded-lg font-semibold text-sm transition shadow">
            Đăng nhập
          </button>

          <button type="button" @click="loginWithGoogle"
            class="w-full bg-white border border-gray-300 hover:bg-gray-100 text-gray-700 py-2 rounded-lg font-semibold text-sm transition flex items-center justify-center gap-2 shadow-sm">
            <!-- Google Icon SVG -->
            <svg class="w-5 h-5" viewBox="0 0 533.5 544.3" xmlns="http://www.w3.org/2000/svg">
              <path fill="#4285F4"
                d="M533.5 278.4c0-17.4-1.6-34.1-4.7-50.4H272v95.5h146.9c-6.3 34-25.1 62.9-53.7 82v68h86.7c50.7-46.7 79.6-115.4 79.6-194.9z" />
              <path fill="#34A853"
                d="M272 544.3c72.8 0 134-24.1 178.6-65.4l-86.7-68c-24.1 16.2-55 25.7-91.9 25.7-70.6 0-130.5-47.7-152-111.5H33.1v69.8C77.5 484.3 168.8 544.3 272 544.3z" />
              <path fill="#FBBC05"
                d="M120 321.1c-4.9-14.7-7.7-30.5-7.7-46.6s2.8-31.9 7.7-46.6v-69.8H33.1C12.1 214.3 0 243.7 0 273.9c0 30.1 12.1 59.6 33.1 83.5l86.9-69.8z" />
              <path fill="#EA4335"
                d="M272 107.7c39.7 0 75.2 13.7 103.3 40.7l77.4-77.4C396 24.1 335.1 0 272 0 168.8 0 77.5 60 33.1 154.8l86.9 69.8c21.5-63.7 81.4-111.5 152-111.5z" />
            </svg>
            <span>Đăng nhập với Google</span>
          </button>

          <button type="button" @click="loginWithGithub"
            class="w-full bg-white border border-gray-300 hover:bg-gray-100 text-gray-700 py-2 rounded-lg font-semibold text-sm transition flex items-center justify-center gap-2 shadow-sm">
            <!-- Github Icon SVG -->
            <svg class="w-5 h-5" viewBox="0 0 24 24" fill="currentColor" xmlns="http://www.w3.org/2000/svg">
              <path fill-rule="evenodd" clip-rule="evenodd"
                d="M12 2C6.477 2 2 6.484 2 12.017c0 4.423 2.865 8.18 6.839 9.504.5.092.682-.217.682-.482 0-.237-.009-.868-.013-1.703-2.782.605-3.369-1.342-3.369-1.342-.454-1.154-1.11-1.461-1.11-1.461-.908-.62.069-.608.069-.608 1.004.07 1.532 1.032 1.532 1.032.892 1.53 2.341 1.088 2.91.832.091-.647.35-1.088.636-1.338-2.22-.253-4.555-1.113-4.555-4.951 0-1.093.39-1.987 1.029-2.686-.103-.253-.446-1.27.098-2.647 0 0 .84-.27 2.75 1.025A9.564 9.564 0 0112 6.844c.85.004 1.705.115 2.504.337 1.909-1.296 2.747-1.025 2.747-1.025.546 1.377.202 2.394.1 2.647.64.7 1.028 1.593 1.028 2.686 0 3.847-2.339 4.695-4.566 4.943.359.309.678.92.678 1.855 0 1.338-.012 2.419-.012 2.75 0 .267.18.58.688.482A10.018 10.018 0 0022 12.017C22 6.484 17.523 2 12 2z" />
            </svg>
            <span>Đăng nhập với GitHub</span>
          </button>
        </form>

        <div class="text-center mt-6 text-xs">
          <span class="text-gray-500">Chưa có tài khoản?</span>
          <a href="/register" class="ml-1 text-pink-500 hover:text-blue-500 font-semibold transition-colors">Đăng ký</a>
        </div>
      </div>
    </div>

    <!-- Modal chọn vai trò google -->
    <a-modal v-model:open="showRoleModalGoogle" title="Chọn vai trò" :footer="null" centered :width="500"
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
    
    <!-- Modal chọn vai trò github  -->
    <a-modal v-model:open="showRoleModalGithub" title="Chọn vai trò" :footer="null" centered :width="500"
      bodyStyle="padding: 1.5rem 2rem;">
      <div class="flex flex-col items-center space-y-6">
        <p class="text-center text-gray-700 text-sm font-medium">
          Vui lòng chọn vai trò để tiếp tục đăng nhập bằng Github
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
          <a-button type="primary" :disabled="!selectedRole" @click="confirmRoleSelectionGithub">
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
import { login } from '@/services/api/manage/auth.api'
import { getUserInformation } from '@/utils/token.helper'
import { cookieStorageAction, localStorageAction } from '@/utils/storage'
import { ACCESS_TOKEN_STORAGE_KEY, REFRESH_TOKEN_STORAGE_KEY, USER_INFO_STORAGE_KEY } from '@/constants/storagekey'
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import { URL_OAUTH2_GITHUB_ADMIN, URL_OAUTH2_GITHUB_MANAGE, URL_OAUTH2_GITHUB_MEMBER, URL_OAUTH2_GOOGLE_ADMIN, URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MEMBER } from '@/constants/url'
import { ACCOUNT_EXIST, ACCOUNT_EXIST_MESSAGE, ACCOUNT_NOT_EXIST, ACCOUNT_NOT_EXIST_MESSAGE, Registered_Awaiting_Confirmation, Registered_Awaiting_Confirmation_MESSAGE, Unverified_Account, Unverified_Account_MESSAGE } from '@/constants/cookie.constants'
import { toast } from 'vue3-toastify'

const router = useRouter()
const email = ref('')
const password = ref('')
const loginError = ref('')
const showRoleModalGoogle = ref(false)
const showRoleModalGithub = ref(false)
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
  showRoleModalGoogle.value = true
}

function loginWithGithub() {
  showRoleModalGithub.value = true
}

function cancelRoleSelection() {
  showRoleModalGoogle.value = false
  showRoleModalGithub.value = false
  selectedRole.value = ''
  
}

function confirmRoleSelection() {
  if (!selectedRole.value) return

  showRoleModalGoogle.value = false

  if (selectedRole.value === 'ADMIN') {
    window.location.href = URL_OAUTH2_GOOGLE_ADMIN();
  } else if (selectedRole.value === 'MANAGE') {
    window.location.href = URL_OAUTH2_GOOGLE_MANAGE();
  } else if (selectedRole.value === 'MEMBER') {
    window.location.href = URL_OAUTH2_GOOGLE_MEMBER();
  }
}
function confirmRoleSelectionGithub() {
  if (!selectedRole.value) return

  showRoleModalGithub.value = false

  if (selectedRole.value === 'ADMIN') {
    window.location.href = URL_OAUTH2_GITHUB_ADMIN();
  } else if (selectedRole.value === 'MANAGE') {
    window.location.href = URL_OAUTH2_GITHUB_MANAGE();
  } else if (selectedRole.value === 'MEMBER') {
    window.location.href = URL_OAUTH2_GITHUB_MEMBER();
  }
}
onMounted(() => {

  const accountNotExistError = cookieStorageAction.get(ACCOUNT_NOT_EXIST)

  const accountExistError = cookieStorageAction.get(ACCOUNT_EXIST)

  const unverified_Account = cookieStorageAction.get(Unverified_Account)

  const registered_Awaiting_Confirmation = cookieStorageAction.get(Registered_Awaiting_Confirmation)

  if (accountNotExistError) {
    toast.error(ACCOUNT_NOT_EXIST_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_NOT_EXIST)
  }
  if (accountExistError) {
    toast.error(ACCOUNT_EXIST_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_EXIST)
  }
  if (unverified_Account) {
    toast.warn(Unverified_Account_MESSAGE)
    cookieStorageAction.remove(Unverified_Account)
  }
  if (registered_Awaiting_Confirmation) {
    toast.info(Registered_Awaiting_Confirmation_MESSAGE)
    cookieStorageAction.remove(Registered_Awaiting_Confirmation)
  }
})
</script>
