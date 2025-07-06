<template>
    <div
        class="min-h-screen flex items-center justify-center bg-gradient-to-br from-green-500 via-white to-pink-200 px-2">
        <div class="relative w-full max-w-lg p-0">
            <!-- Glass morphism background layer -->
            <div
                class="absolute inset-0 z-0 rounded-2xl blur-xl opacity-60 bg-gradient-to-br from-green-300/60 via-white/50 to-pink-200/60">
            </div>
            <div
                class="relative z-10 bg-white/90 rounded-2xl shadow-lg p-7 sm:p-10 flex flex-col items-center backdrop-blur-xl border border-green-100 w-full">
                <div
                    class="mb-3 w-16 h-16 flex items-center justify-center bg-gradient-to-br from-green-400 to-pink-400 rounded-full shadow-md">
                    <svg class="w-8 h-8 text-white" fill="none" stroke="currentColor" stroke-width="2"
                        viewBox="0 0 24 24">
                        <path stroke-linecap="round" stroke-linejoin="round" d="M12 4v16m8-8H4" />
                    </svg>
                </div>
                <h2 class="text-2xl font-bold text-center mb-1 text-gray-800 select-none">
                    Tạo tài khoản
                </h2>
                <p class="text-center text-sm text-gray-500 mb-6 select-none">
                    Đăng ký để bắt đầu sử dụng
                </p>

                <form @submit.prevent="handleRegister" class="w-full space-y-4">
                    <!-- 2 hàng, mỗi hàng 2 input, responsive -->
                    <div class="flex flex-col sm:flex-row gap-4 w-full">
                        <div class="flex-1">
                            <label for="fullname" class="block text-xs font-semibold text-gray-600 mb-1">Họ và
                                tên</label>
                            <input v-model="fullname" id="fullname" type="text" required
                                class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
                        </div>
                        <div class="flex-1">
                            <label for="email" class="block text-xs font-semibold text-gray-600 mb-1">Email</label>
                            <input v-model="email" id="email" type="email" required
                                class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
                        </div>
                    </div>
                    <div class="flex flex-col sm:flex-row gap-4 w-full">
                        <div class="flex-1">
                            <label for="password" class="block text-xs font-semibold text-gray-600 mb-1">Mật
                                khẩu</label>
                            <input v-model="password" id="password" type="password" required
                                class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
                        </div>
                        <div class="flex-1">
                            <label for="confirmPassword" class="block text-xs font-semibold text-gray-600 mb-1">Xác nhận
                                mật khẩu</label>
                            <input v-model="confirmPassword" id="confirmPassword" type="password" required
                                class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition" />
                            <p v-if="confirmPasswordError" class="text-red-500 text-xs mt-1">
                                {{ confirmPasswordError }}
                            </p>
                        </div>
                    </div>
                    <!-- Vai trò: căn giữa, responsive, đồng bộ width -->
                    <div class="flex flex-col items-center w-full sm:w-1/2 mx-auto">
                        <label for="role" class="block text-xs font-semibold text-gray-600 mb-1 w-full">Vai trò</label>
                        <select v-model="role" id="role" required
                            class="w-full px-3 py-2 border border-gray-200 rounded-lg text-sm focus:outline-none focus:ring-2 focus:ring-pink-400 bg-white/80 transition">
                            <option v-for="role in allRole" :key="role.id" :value="role.id">
                                {{ role.name }}
                            </option>
                        </select>
                    </div>

                    <!-- Nút Đăng ký -->
                    <button type="submit"
                        class="w-full bg-gradient-to-r from-pink-500 to-green-500 hover:from-green-600 hover:to-pink-600 text-white py-2 rounded-lg font-semibold text-sm transition shadow">
                        Đăng ký
                    </button>

                    <!-- Nút Đăng ký với Google -->
                    <button type="button" @click="openRoleModal"
                        class="w-full bg-white border border-gray-200 hover:bg-gray-50 text-gray-800 py-2 rounded-lg font-medium text-sm transition flex items-center justify-center gap-2 shadow-sm mt-2">
                        <svg class="w-4 h-4" viewBox="0 0 533.5 544.3" xmlns="http://www.w3.org/2000/svg">
                            <path fill="#4285F4"
                                d="M533.5 278.4c0-17.4-1.6-34.1-4.7-50.4H272v95.5h146.9c-6.3 34-25.1 62.9-53.7 82v68h86.7c50.7-46.7 79.6-115.4 79.6-194.9z" />
                            <path fill="#34A853"
                                d="M272 544.3c72.8 0 134-24.1 178.6-65.4l-86.7-68c-24.1 16.2-55 25.7-91.9 25.7-70.6 0-130.5-47.7-152-111.5H33.1v69.8C77.5 484.3 168.8 544.3 272 544.3z" />
                            <path fill="#FBBC05"
                                d="M120 321.1c-4.9-14.7-7.7-30.5-7.7-46.6s2.8-31.9 7.7-46.6v-69.8H33.1C12.1 214.3 0 243.7 0 273.9c0 30.1 12.1 59.6 33.1 83.5l86.9-69.8z" />
                            <path fill="#EA4335"
                                d="M272 107.7c39.7 0 75.2 13.7 103.3 40.7l77.4-77.4C396 24.1 335.1 0 272 0 168.8 0 77.5 60 33.1 154.8l86.9 69.8c21.5-63.7 81.4-111.5 152-111.5z" />
                        </svg>
                        <span>Đăng ký với Google</span>
                    </button>

                    <!-- Nút Đăng ký với Github -->
                    <button type="button" @click="openRoleModalGithub"
                        class="w-full bg-white border border-gray-200 hover:bg-gray-50 text-gray-800 py-2 rounded-lg font-medium text-sm transition flex items-center justify-center gap-2 shadow-sm mt-2">
                        <svg class="w-4 h-4 text-gray-800" fill="currentColor" viewBox="0 0 24 24">
                            <path d="M12 .297c-6.63 0-12 5.373-12 12 0 5.303 
            3.438 9.8 8.205 11.387.6.113.82-.258.82-.577 
            0-.285-.01-1.04-.015-2.04-3.338.724-4.042-1.61-4.042-1.61C4.422 
            18.07 3.633 17.7 3.633 17.7c-1.087-.744.084-.729.084-.729 
            1.205.084 1.84 1.236 1.84 1.236 1.07 1.835 2.809 
            1.304 3.495.997.108-.776.418-1.305.762-1.605-2.665-.3-5.466-1.334-5.466-5.93 
            0-1.31.468-2.38 1.236-3.22-.124-.303-.536-1.523.117-3.176 
            0 0 1.008-.322 3.3 1.23a11.48 11.48 0 0 1 3-.404 
            11.48 11.48 0 0 1 3 .404c2.29-1.552 3.297-1.23 
            3.297-1.23.653 1.653.241 2.873.118 
            3.176.77.84 1.235 1.91 1.235 3.22 
            0 4.61-2.803 5.625-5.475 5.921.43.37.823 1.102.823 
            2.222 0 1.606-.015 2.896-.015 3.286 
            0 .32.216.694.825.576C20.565 22.092 
            24 17.592 24 12.297c0-6.627-5.373-12-12-12" />
                        </svg>
                        <span>Đăng ký với Github</span>
                    </button>


                </form>

                <div class="text-center mt-6 text-xs">
                    <span class="text-gray-500">Đã có tài khoản?</span>
                    <a href="/login"
                        class="ml-1 text-pink-500 hover:text-green-500 font-semibold transition-colors">Đăng nhập</a>
                </div>
            </div>
        </div>

        <!-- Modal  đăng ký với google  -->
        <a-modal v-model:open="showRoleModalGoogle" title="Chọn vai trò" :footer="null" centered :width="360"
            bodyStyle="padding: 1.5rem 2rem;">
            <div class="flex flex-col items-center space-y-6">
                <p class="text-center text-gray-700 text-sm font-medium">
                    Vui lòng chọn vai trò để tiếp tục đăng ký bằng Google
                </p>
                <div class="flex flex-col sm:flex-row justify-center gap-4 w-full">
                    <button :class="[
                        'flex flex-col items-center justify-center flex-1 min-w-[110px] h-20 rounded-lg border cursor-pointer transition-shadow',
                        selectedRole === 'MANAGE' ? 'border-green-500 shadow-md bg-green-50' : 'border-gray-300 hover:shadow-sm hover:bg-green-50',
                    ]" @click="selectedRole = 'MANAGE'" type="button">
                        <span class="text-xl mb-1">👨‍💼</span>
                        <span class="font-semibold text-sm text-center">Quản lý</span>
                    </button>

                    <button :class="[
                        'flex flex-col items-center justify-center flex-1 min-w-[110px] h-20 rounded-lg border cursor-pointer transition-shadow',
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
        <!-- Modal đăng ký với github  -->
        <a-modal v-model:open="showRoleModalGithub" title="Chọn vai trò" :footer="null" centered :width="360"
            bodyStyle="padding: 1.5rem 2rem;">
            <div class="flex flex-col items-center space-y-6">
                <p class="text-center text-gray-700 text-sm font-medium">
                    Vui lòng chọn vai trò để tiếp tục đăng ký bằng Github
                </p>
                <div class="flex flex-col sm:flex-row justify-center gap-4 w-full">
                    <button :class="[
                        'flex flex-col items-center justify-center flex-1 min-w-[110px] h-20 rounded-lg border cursor-pointer transition-shadow',
                        selectedRole === 'MANAGE' ? 'border-green-500 shadow-md bg-green-50' : 'border-gray-300 hover:shadow-sm hover:bg-green-50',
                    ]" @click="selectedRole = 'MANAGE'" type="button">
                        <span class="text-xl mb-1">👨‍💼</span>
                        <span class="font-semibold text-sm text-center">Quản lý</span>
                    </button>

                    <button :class="[
                        'flex flex-col items-center justify-center flex-1 min-w-[110px] h-20 rounded-lg border cursor-pointer transition-shadow',
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
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ROLES } from '@/constants/roles'
import {
    ACCESS_TOKEN_STORAGE_KEY,
    REFRESH_TOKEN_STORAGE_KEY,
    USER_INFO_STORAGE_KEY,
} from '@/constants/storagekey'
import { URL_OAUTH2_GITHUB_MANAGE_REGISTER, URL_OAUTH2_GITHUB_MEMBER_REGISTER, URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MANAGE_REGISTER, URL_OAUTH2_GOOGLE_MEMBER, URL_OAUTH2_GOOGLE_MEMBER_REGISTER } from '@/constants/url'
import { getAllRole, login, register, RoleResponse } from '@/services/api/manage/auth.api'
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

// Modal và chọn vai trò Google OAuth
const showRoleModalGoogle = ref(false)
const showRoleModalGithub = ref(false)
const selectedRole = ref('')

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
        idRole: role.value,
    }

    try {
        await register(payload)
        console.log('Đăng ký thành công')
        const res = await login({ email: email.value, password: password.value })

        const user = getUserInformation(res.data)
        localStorageAction.set(USER_INFO_STORAGE_KEY, user)
        localStorageAction.set(ACCESS_TOKEN_STORAGE_KEY, res.data)
        localStorageAction.set(REFRESH_TOKEN_STORAGE_KEY, res.data)

        if (user.rolesCodes.includes(ROLES.ADMIN) && user.roleScreen === ROLES.ADMIN) {
            router.push({ name: ROUTES_CONSTANTS.ADMIN.children.ADMIN.name })
        } else if (
            user.rolesCodes.includes(ROLES.MANAGE) &&
            user.roleScreen === ROLES.MANAGE
        ) {
            router.push({ name: ROUTES_CONSTANTS.MANAGE.children.MANAGE.name })
        } else {
            router.push({ name: ROUTES_CONSTANTS.MEMBER.children.MEMBER.name })
        }
    } catch (error) {
        console.error('Lỗi khi đăng ký:', error)
    }
}

const fetchRole = async () => {
    try {
        const response = await getAllRole()
        allRole.value = response.data
    } catch (error) {
        console.error('Lỗi khi lấy danh sách vai trò:', error)
    }
}
onMounted(fetchRole)

// Mở modal khi nhấn nút Đăng ký bằng Google
const openRoleModal = () => {
    showRoleModalGoogle.value = true
}
const openRoleModalGithub = () => {
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

    if (selectedRole.value === 'MANAGE') {
        window.location.href = URL_OAUTH2_GOOGLE_MANAGE_REGISTER()
    } else if (selectedRole.value === 'MEMBER') {
        window.location.href = URL_OAUTH2_GOOGLE_MEMBER_REGISTER()
    }
}
function confirmRoleSelectionGithub() {
    if (!selectedRole.value) return
    showRoleModalGithub.value = false

    if (selectedRole.value === 'MANAGE') {
        window.location.href = URL_OAUTH2_GITHUB_MANAGE_REGISTER()
    } else if (selectedRole.value === 'MEMBER') {
        window.location.href = URL_OAUTH2_GITHUB_MEMBER_REGISTER()
    }
}
</script>
