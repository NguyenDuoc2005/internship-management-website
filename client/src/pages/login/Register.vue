<template>
    <div class="min-h-screen bg-cover bg-center flex items-center justify-center px-4"
        style="background-image: url('https://i.pinimg.com/originals/c2/e9/02/c2e902e031e1d9d932411dd0b8ab5eef.jpg');">
        <div class="bg-white bg-opacity-90 shadow-lg rounded-2xl p-6 w-full max-w-sm">
            <h2 class="text-xl font-bold text-center mb-2">Tạo tài khoản</h2>
            <p class="text-center text-sm text-gray-500 mb-4">Đăng ký để bắt đầu sử dụng</p>

            <form @submit.prevent="handleRegister" class="space-y-3">
                <div>
                    <label for="fullname" class="block text-sm font-medium text-gray-700 mb-1">Họ và tên</label>
                    <input v-model="fullname" id="fullname" type="text" required
                        class="w-full max-w-full px-3 py-1.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 text-sm" />
                </div>
                <div>
                    <label for="email" class="block text-sm font-medium text-gray-700 mb-1">Email</label>
                    <input v-model="email" id="email" type="email" required
                        class="w-full max-w-full px-3 py-1.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 text-sm" />
                </div>
                <div>
                    <label for="password" class="block text-sm font-medium text-gray-700 mb-1">Mật khẩu</label>
                    <input v-model="password" id="password" type="password" required
                        class="w-full max-w-full px-3 py-1.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 text-sm" />
                </div>
                <div>
                    <label for="confirmPassword" class="block text-sm font-medium text-gray-700 mb-1">Xác nhận mật
                        khẩu</label>
                    <input v-model="confirmPassword" id="confirmPassword" type="password" required
                        class="w-full max-w-full px-3 py-1.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 text-sm" />
                    <p v-if="confirmPasswordError" class="text-red-500 text-xs mt-1">{{ confirmPasswordError }}</p>
                </div>


                <div>
                    <label for="role" class="block text-sm font-medium text-gray-700 mb-1">Vai trò</label>
                    <select v-model="role" id="role" required
                        class="w-full max-w-full px-3 py-1.5 border border-gray-300 rounded-lg focus:outline-none focus:ring-2 focus:ring-green-400 text-sm">
                        <option v-for="role in allRole" :key="role.id" :value="role.id">
                            {{ role.name }}
                        </option>
                    </select>
                </div>

                <button type="submit"
                    class="w-full bg-green-500 hover:bg-green-600 text-white py-2 rounded-lg font-semibold transition duration-200">
                    Đăng ký
                </button>

                <button type="button" @click="registerWithGoogle"
                    class="w-full bg-red-500 hover:bg-red-600 text-white py-2 rounded-lg font-semibold transition duration-200 flex items-center justify-center space-x-2">
                    <svg class="w-5 h-5 fill-current text-white" viewBox="0 0 24 24">
                        <path
                            d="M21.35 11.1H12v2.8h5.35c-.23 1.27-1.34 3.7-5.35 3.7-3.22 0-5.85-2.63-5.85-5.85S8.78 5.9 12 5.9c1.84 0 3.08.79 3.79 1.47l2.6-2.52C17.22 3.28 14.83 2.3 12 2.3 6.78 2.3 2.6 6.48 2.6 11.7S6.78 21.1 12 21.1c6.29 0 10.45-4.42 10.45-10.65 0-.71-.1-1.24-.22-1.35z" />
                    </svg>
                    <span>Đăng ký bằng Google</span>
                </button>
            </form>

            <div class="text-right mt-4 text-sm">
                <a href="/login" class="text-green-600 hover:underline">Đã có tài khoản? Đăng nhập</a>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import { ACCESS_TOKEN_STORAGE_KEY, REFRESH_TOKEN_STORAGE_KEY, USER_INFO_STORAGE_KEY } from '@/constants/storagekey'
import { getAllRole, login, register, RoleResponse } from '@/services/api/auth.api'
import { localStorageAction } from '@/utils/storage'
import { getUserInformation } from '@/utils/token.helper'
import { onMounted, ref } from 'vue'
import { useRouter } from 'vue-router'
const router = useRouter()
const fullname = ref('')
const email = ref('')
const password = ref('')
const confirmPassword = ref('')
const role = ref('user')
const allRole = ref<RoleResponse[]>([])
const confirmPasswordError = ref('')

const handleRegister = async () => {
    if (password.value !== confirmPassword.value) {
        confirmPasswordError.value = 'Mật khẩu xác nhận không khớp!'
        return
    } else {
        confirmPasswordError.value = ''
    }

    const payload = {
        userName: fullname.value,
        email: email.value,
        password: password.value,
        idRole: role.value
    }
    
    try {
        await register(payload)
        console.log('Đăng ký thành công')
        const res = await login({email: email.value, password: password.value})

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

    } catch (error) {
        console.error('Lỗi khi đăng ký:', error)
    }
}

function registerWithGoogle() {
    router.push({ name: ROUTES_CONSTANTS.REGISTER_GOOGLE.name })
}

const fetchRole = async () => {
    try {
        const response = await getAllRole()
        allRole.value = response.data
        console.log(role.value)
    } catch (error) {
        console.error('Lỗi khi lấy danh sách cột:', error)
    }
}
onMounted(fetchRole)
</script>
