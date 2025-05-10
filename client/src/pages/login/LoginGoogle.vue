<template>
  <div class="bg-gray-200 bg-cover min-h-screen flex justify-center items-center px-4 sm:px-6 lg:px-8"
    :style="{ backgroundImage: `url(${backgroundImageUrl})` }">
    <div class="card border-0 w-full max-w-lg sm:max-w-xl md:max-w-2xl lg:max-w-3xl">

      <router-link :to="{ name: ROUTES_CONSTANTS.LOGIN.name }">
        <h1 class="text-3xl sm:text-4xl font-semibold text-white text-center mb-8">
          Đăng nhập
        </h1>
      </router-link>
      <div class="flex flex-col sm:flex-row flex-wrap justify-center items-center gap-12">
        <!-- Quản lý -->
        <div class="flex flex-col items-center gap-3 w-full sm:w-[220px]">
          <img :src="manageImageUrl" alt="Manage Icon"
            class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105 cursor-pointer"
            @click="handleRedirectLoginMANAGE" />
          <button @click="handleRedirectLoginMANAGE" :style="buttonStyleLogin"
            class="w-full px-4 py-3 rounded-lg font-semibold text-white text-base hover:bg-blue-600 transform transition duration-300 ease-in-out hover:scale-105">
            <font-awesome-icon :icon="faUser" class="mr-2" />
            <span>Quản lý</span>
          </button>
        </div>

        <!-- Thành viên -->
        <div class="flex flex-col items-center gap-3 w-full sm:w-[220px]">
          <img :src="memberImageUrl" alt="Member Icon"
            class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105 cursor-pointer"
            @click="handleRedirectLoginMEMBER" />
          <button @click="handleRedirectLoginMEMBER" :style="buttonStyleLogin"
            class="w-full px-4 py-3 rounded-lg font-semibold text-white text-base hover:bg-blue-600 transform transition duration-300 ease-in-out hover:scale-105">
            <font-awesome-icon :icon="faUser" class="mr-2" />
            <span>Thành Viên</span>
          </button>
        </div>

      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faGoogle } from '@fortawesome/free-brands-svg-icons';
import { URL_OAUTH2_GOOGLE_ADMIN, URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MEMBER } from '@/constants/url';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { toast } from 'vue3-toastify'
import { faUser } from '@fortawesome/free-solid-svg-icons';
import { ROUTES_CONSTANTS } from '@/constants/path';
import { cookieStorageAction } from '@/utils/storage';
import { ACCOUNT_EXIST, ACCOUNT_EXIST_MESSAGE, ACCOUNT_NOT_EXIST, ACCOUNT_NOT_EXIST_MESSAGE } from '@/constants/cookie.constants';

const backgroundImageUrl = 'https://i.pinimg.com/originals/c2/e9/02/c2e902e031e1d9d932411dd0b8ab5eef.jpg'; // URL của ảnh nền
const adminImageUrl = 'https://icon-library.com/images/admin-user-icon/admin-user-icon-3.jpg'; // URL ảnh cho Admin
const manageImageUrl = 'https://cdn-icons-png.flaticon.com/512/4873/4873262.png'; // URL ảnh cho Quản lý
const memberImageUrl = 'https://icon-library.com/images/admin-user-icon/admin-user-icon-3.jpg'; // URL ảnh cho Thành viên

const buttonStyleLogin = computed(() => ({
  backgroundColor: '#0a58ca',
  color: '#fff',
  borderRadius: '12px',
  fontSize: '16px',
}));

// Các hàm xử lý redirect
const handleRedirectLoginADMIN = () => {
  window.location.href = URL_OAUTH2_GOOGLE_ADMIN();
};

const handleRedirectLoginMANAGE = () => {
  window.location.href = URL_OAUTH2_GOOGLE_MANAGE();
};

const handleRedirectLoginMEMBER = () => {
  window.location.href = URL_OAUTH2_GOOGLE_MEMBER();
};
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
