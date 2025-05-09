<script setup lang="ts">
import { ROUTES_CONSTANTS } from '@/constants/path'
import { ref } from 'vue'
import { useRoute } from 'vue-router'

const isCollapsed = ref(false)
const isMobileMenuOpen = ref(false)

const route = useRoute()

const menuItems = ref([
  {
    icon: `<svg class="fill-current" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 576 512" width="18" height="18"><path d="M64 32C46.3 32 32 46.3 32 64v240v48v80c0 26.5 21.5 48 48 48h416c26.5 0 48-21.5 48-48V256V104.2c0-18.2-19.4-29.7-35.4-21.1L352 215.4v-63.2c0-18.2-19.4-29.7-35.4-21.1L160 215.4V64c0-17.7-14.3-32-32-32H64z"/></svg>`,
    label: 'Cuộc họp của tôi',
    routeName: ROUTES_CONSTANTS.MEMBER.children.MEMBER.name,
  },
])
</script>

<template>
  <!-- MOBILE MENU BUTTON -->
  <button v-if="!isMobileMenuOpen" @click="isMobileMenuOpen = true"
    class="fixed top-4 left-4 z-50 flex items-center justify-center w-10 h-10 rounded-full hover:bg-gray-400 lg:hidden">
    <svg class="w-6 h-6 fill-current text-gray-800" xmlns="http://www.w3.org/2000/svg" viewBox="0 0 24 24">
      <path d="M4 6h16M4 12h16M4 18h16" />
    </svg>
  </button>

  <!-- SIDEBAR -->
  <aside
    class="fixed inset-0 lg:inset-y-0 lg:left-0 z-40 flex h-screen flex-col overflow-hidden bg-white transition-transform duration-300 ease-in-out lg:relative lg:translate-x-0"
    :class="{
      'hidden lg:block': !isMobileMenuOpen, /* Mobile: hidden when closed */
      'translate-x-0': isMobileMenuOpen, /* Mobile: shown when open */
      'w-20': isCollapsed && !isMobileMenuOpen, /* Large screen: collapsed */
      'w-64': !isCollapsed && !isMobileMenuOpen, /* Large screen: expanded */
    }">
    <!-- SIDEBAR HEADER -->
    <div class="flex items-center justify-between px-4 py-4 bg-white">
      <img src="../../../assets/images/logo-udpm-dark.png" alt="Logo" class="transition-all duration-300"
        :class="{ hidden: isCollapsed }" style="width: 180px; height: auto;" />
      <!-- Collapse/Expand button -->
      <button v-if="!isMobileMenuOpen" @click="isCollapsed = !isCollapsed"
        class="hidden lg:flex items-center justify-center w-8 h-8 rounded-full hover:bg-gray-300">
        <svg class="w-6 h-6 text-gray-600" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
            stroke="currentColor" stroke-width="2">
            <path v-if="isCollapsed" d="M6 5l7 7-7 7" />
            <path v-else d="M18 19l-7-7 7-7" />
          </svg>
      </button>

      <!-- Close button (only on mobile) -->
      <button v-if="isMobileMenuOpen" @click="isMobileMenuOpen = false"
          class="lg:hidden flex items-center justify-center w-8 h-8">
          <svg class="w-6 h-6 text-black-2" xmlns="http://www.w3.org/2000/svg" fill="none" viewBox="0 0 24 24"
            stroke="currentColor" stroke-width="2">
            <path stroke-linecap="round" stroke-linejoin="round" d="M15 19l-7-7 7-7" />
          </svg>
        </button>
    </div>

    <!-- SIDEBAR MENU -->
    <div class="px-4 py-2 ">
      <nav>
        <ul>
          <li v-for="(menuItem, index) in menuItems" :key="index">
            <router-link :to="{ name: menuItem.routeName }"
              class="flex items-center gap-4 p-3 rounded-lg hover:bg-gray-100 transition-all duration-200"
              :class="{ 'bg-gray-100': route.name === menuItem.routeName }">

              <!-- ICON -->
              <span v-html="menuItem.icon" class="w-6 h-6" :class="{
                'text-yellow-500': route.name === menuItem.routeName,
                'text-gray-400': route.name !== menuItem.routeName
              }"></span>

              <!-- LABEL -->
              <span v-if="!isCollapsed" class="text-sm" :class="{
                'text-black font-bold': route.name === menuItem.routeName,
                'text-gray-700 font-normal': route.name !== menuItem.routeName
              }">
                {{ menuItem.label }}
              </span>
            </router-link>
          </li>
        </ul>
      </nav>
    </div>
  </aside>
</template>

<style scoped>

/* aside {
    transition: transform 0.2s ease-out, width 0.2s ease-out;
    will-change: transform, width, height;
  } */

</style>