<script setup lang="ts">
const props = defineProps({
  customClasses: String,
  customClassesLabel: String,
  label: String
})
</script>

<template>
  <div
    class="col-span-12 rounded-sm border border-stroke bg-white px-5 pt-5 pb-5 shadow-default dark:border-strokedark dark:bg-boxdark sm:px-7.5 xl:col-span-8"
    :class="props.customClasses"
  >
    <div class="flex items-center justify-between mb-3">
      <h1
        v-if="props.label"
        class="text-xl font-bold text-graydark"
        :class="props.customClassesLabel"
      >
        {{ props.label }}
      </h1>
      <div>
        <slot name="extra" class="px-3"></slot>
      </div>
    </div>
    <slot></slot>
  </div>
</template>
