<script setup lang="ts">
import HeaderArea from '@/components/ui/Header/HeaderArea.vue'
import AdminSidebar from '@/components/custom/Sidebar/AdminSidebar.vue'
import MemberSidebar from '@/components/custom/Sidebar/MemberSidebar.vue';
</script>

<template>
  <!-- ===== Page Wrapper Start ===== -->
  <div class="flex h-screen overflow-hidden">
    <!-- ===== Sidebar Start ===== -->
    <MemberSidebar />
    <!-- ===== Sidebar End ===== -->

    <!-- ===== Content Area Start ===== -->
    <div class="relative flex flex-1 flex-col overflow-y-auto overflow-x-hidden">
      <!-- ===== Header Start ===== -->
      <HeaderArea />
      <!-- ===== Header End ===== -->

      <!-- ===== Main Content Start ===== -->
      <main>
        <!-- <div class="p-4 md:p-6 2xl:p-10"> -->
          <router-view></router-view>
        <!-- </div> -->
      </main>
      <!-- ===== Main Content End ===== -->
    </div>
  </div>
  <!-- ===== Page Wrapper End ===== -->
</template>
