<template>
  <div class="bg-gray-200 bg-cover min-h-screen flex justify-center items-center px-4 sm:px-6 lg:px-8"
    :style="{ backgroundImage: `url(${backgroundImage})` }">
    <div class="card border-0 w-full max-w-lg sm:max-w-xl md:max-w-2xl lg:max-w-3xl">
 
      <div class="flex flex-col sm:flex-row flex-wrap justify-center items-center gap-12">

        <!-- Admin -->
        <div class="flex flex-col items-center gap-3 w-full sm:w-[220px]">
          <img src="/src/assets/images/Admin.png" alt="Admin Icon"
            class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105 cursor-pointer"
            @click="handleRedirectLoginADMIN" />
          <button @click="handleRedirectLoginADMIN" :style="buttonStyleLogin"
            class="w-full px-4 py-3 rounded-md font-semibold text-white text-base hover:opacity-90">
            <font-awesome-icon :icon="faUser" class="mr-2" />
            <span>Admin</span>
          </button>
        </div>

          <!-- Admin -->
          <div class="flex flex-col items-center gap-3 w-full sm:w-[220px]">
          <img src="/src/assets/images/Admin.png" alt="Admin Icon"
            class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105 cursor-pointer"
            @click="handleRedirectLoginMANAGE" />
          <button @click="handleRedirectLoginMANAGE" :style="buttonStyleLogin"
            class="w-full px-4 py-3 rounded-md font-semibold text-white text-base hover:opacity-90">
            <font-awesome-icon :icon="faUser" class="mr-2" />
            <span>Quản lý </span>
          </button>
        </div>

          <!-- Admin -->
          <div class="flex flex-col items-center gap-3 w-full sm:w-[220px]">
          <img src="/src/assets/images/Admin.png" alt="Admin Icon"
            class="w-36 h-auto object-contain transition-transform duration-300 hover:scale-105 cursor-pointer"
            @click="handleRedirectLoginMEMBER" />
          <button @click="handleRedirectLoginMEMBER" :style="buttonStyleLogin"
            class="w-full px-4 py-3 rounded-md font-semibold text-white text-base hover:opacity-90">
            <font-awesome-icon :icon="faUser" class="mr-2" />
            <span>Thành Viên </span>
          </button>
        </div>

      </div>
    </div>

  </div>
</template>

<script setup>
import { ref, computed } from 'vue';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faGoogle } from '@fortawesome/free-brands-svg-icons';
import { URL_OAUTH2_GOOGLE_ADMIN, URL_OAUTH2_GOOGLE_MANAGE, URL_OAUTH2_GOOGLE_MEMBER } from '@/constants/url';
import backgroundImage from '/images/bg-simple.jpg';
import logo from '/images/Logo_FPT.png';
import { useRouter } from 'vue-router';
import { useAuthStore } from '@/stores/auth';
import { ACCOUNT_EXPIRED, ACCOUNT_NOT_ASSIGNED_TO_ANY_PROJECT, ACCOUNT_NOT_ASSIGNED_TO_ANY_PROJECT_MESSAGE, ACCOUNT_NOT_EXIST, ACCOUNT_NOT_EXIST_MESSAGE, ACCOUNT_NOT_HAVE_PERMISSION, ACCOUNT_NOT_HAVE_PERMISSION_MESSAGE, STUDENT_NEEDS_REGISTRATION, STUDENT_NEEDS_REGISTRATION_MESSAGE } from '@/constants/cookie.constants';
import { cookieStorageAction } from '@/utils/storage';
import { toast } from 'vue3-toastify'
import { onMounted } from 'vue';
import { faUser } from '@fortawesome/free-solid-svg-icons'; 

const isFacilityModalOpen = ref(false);
const loginType = ref('');


//authen
const authStore = useAuthStore()

//router
const router = useRouter()

const isRegisterModalOpen = ref(false)

onMounted(() => {
  const accountNotExistError = cookieStorageAction.get(ACCOUNT_NOT_EXIST)
  const accountNotHavePermission = cookieStorageAction.get(ACCOUNT_NOT_HAVE_PERMISSION)
  const accountNotAssignedToAnyProject = cookieStorageAction.get(ACCOUNT_NOT_ASSIGNED_TO_ANY_PROJECT)
  const accountExpried = cookieStorageAction.get(ACCOUNT_EXPIRED)
  const student_needs_registration = cookieStorageAction.get(STUDENT_NEEDS_REGISTRATION)
  if (accountNotExistError) {
    toast.error(ACCOUNT_NOT_EXIST_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_NOT_EXIST)
  }
  if (accountNotHavePermission) {
    toast.error(ACCOUNT_NOT_HAVE_PERMISSION_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_NOT_HAVE_PERMISSION)
  }
  if (accountNotAssignedToAnyProject) {
    toast.error(ACCOUNT_NOT_ASSIGNED_TO_ANY_PROJECT_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_NOT_ASSIGNED_TO_ANY_PROJECT)
  }
  if (accountExpried) {
    toast.error(ACCOUNT_EXPIRED_MESSAGE)
    cookieStorageAction.remove(ACCOUNT_EXPIRED)
  }
  // tài khoản student ko tồn tại 
  if (student_needs_registration) {
    isRegisterModalOpen.value = true
    toast.info(STUDENT_NEEDS_REGISTRATION_MESSAGE)
    cookieStorageAction.remove(STUDENT_NEEDS_REGISTRATION)
  }
})

const handleRedirectLoginADMIN = () => {
  window.location.href = URL_OAUTH2_GOOGLE_ADMIN();
};

const handleRedirectLoginMANAGE = () => {
  window.location.href = URL_OAUTH2_GOOGLE_MANAGE();
};

const handleRedirectLoginMEMBER = () => {
  window.location.href = URL_OAUTH2_GOOGLE_MEMBER();
};

const buttonStyleLogin = computed(() => ({
  backgroundColor: '#0a58ca',
  color: '#fff',
  borderRadius: '12px', 
  fontSize: '16px',
}));

</script>
