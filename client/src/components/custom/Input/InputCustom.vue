<script lang="ts">
import { defineComponent } from 'vue'

export default defineComponent({
  props: {
    label: String,
    type: String,
    placeholder: String,
    customClasses: String,
    customClassesInput: String,
    required: {
      type: Boolean,
      default: false
    },
    modelValue: String
  },
  emits: ['update:modelValue']
})
</script>

<template>
  <div :class="customClasses">
    <label class="block text-black font-medium dark:text-white">
      {{ label }}
      <span v-if="required" class="text-meta-1">*</span>
    </label>
    <a-input
      :type="type"
      :placeholder="placeholder"
      :value="modelValue"
      size="large"
      @input="$emit('update:modelValue', $event.target.value)"
      :class="[
        'w-full rounded-md border-[1.5px] text-black border-stroke bg-transparent py-3 px-5 font-normal outline-none transition hover:border-stroke focus:border-primary active:border-primary disabled:cursor-default disabled:bg-whiter dark:text-white dark:border-form-strokedark dark:bg-form-input dark:focus:border-primary',
        customClassesInput
      ]"
      allowClear
    />
  </div>
</template>
