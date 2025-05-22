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

                    <button type="submit"
                        class="w-full bg-gradient-to-r from-pink-500 to-green-500 hover:from-green-600 hover:to-pink-600 text-white py-3 rounded-lg font-semibold text-base transition shadow">
                        Đăng ký
                    </button>

                    <button type="button" @click="openRoleModal"
                        class="w-full bg-white border border-gray-300 hover:bg-gray-100 text-gray-700 py-3 rounded-lg font-semibold text-base transition flex items-center justify-center gap-2 shadow-sm">
                        <svg class="w-5 h-5" viewBox="0 0 24 24">
                            <path fill="currentColor"
                                d="M21.35 11.1H12v2.8h5.35c-.23 1.27-1.34 3.7-5.35 3.7-3.22 0-5.85-2.63-5.85-5.85S8.78 5.9 12 5.9c1.84 0 3.08.79 3.79 1.47l2.6-2.52C17.22 3.28 14.83 2.3 12 2.3 6.78 2.3 2.6 6.48 2.6 11.7S6.78 21.1 12 21.1c6.29 0 10.45-4.42 10.45-10.65 0-.71-.1-1.24-.22-1.35z" />
                        </svg>
                        <span>Đăng ký với Google</span>
                    </button>
                </form>

                <div class="text-center mt-6 text-xs">
                    <span class="text-gray-500">Đã có tài khoản?</span>
                    <a href="/login"
                        class="ml-1 text-pink-500 hover:text-green-500 font-semibold transition-colors">Đăng nhập</a>
                </div>
            </div>
        </div>

        <!-- Modal chọn vai trò giữ nguyên như trước -->
        <a-modal v-model:open="showRoleModal" title="Chọn vai trò" :footer="null" centered :width="360"
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
import { URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MANAGE_REGISTER, URL_OAUTH2_GOOGLE_MEMBER, URL_OAUTH2_GOOGLE_MEMBER_REGISTER } from '@/constants/url'
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
const showRoleModal = ref(false)
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
    showRoleModal.value = true
}

function cancelRoleSelection() {
    showRoleModal.value = false
    selectedRole.value = ''
}

function confirmRoleSelection() {
    if (!selectedRole.value) return

    showRoleModal.value = false

    if (selectedRole.value === 'MANAGE') {
        window.location.href = URL_OAUTH2_GOOGLE_MANAGE_REGISTER()
    } else if (selectedRole.value === 'MEMBER') {
        window.location.href = URL_OAUTH2_GOOGLE_MEMBER_REGISTER()
    }
}
</script>
