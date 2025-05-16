<template>
  <a-modal :open="open" :title="title" @cancel="closeModal" style="top: 20px">
    <template #footer>
      <a-popconfirm
        title="Bạn có chắc chắn muốn lưu thay đổi?"
        @confirm="handleSubmit"
        ok-text="Đồng ý"
        cancel-text="Huỷ"
      >
        <a-button type="primary">Xác nhận</a-button>
      </a-popconfirm>
      <a-button @click="closeModal">Huỷ</a-button>
    </template>

    <a-form
      :model="meetings"
      ref="meetingsForm"
      name="meetingsForm"
      autocomplete="off"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
    >
      <a-form-item label="Mã cuộc họp" name="code" :rules="rules.code">
        <a-input v-model:value="meetings.code" />
      </a-form-item>

      <a-form-item label="Tiêu đề" name="title" :rules="rules.title">
        <a-input v-model:value="meetings.title" />
      </a-form-item>

      <a-form-item label="Nội dung" name="content" :rules="rules.content">
        <a-textarea v-model:value="meetings.content" />
      </a-form-item>

      <a-form-item label="Địa điểm" name="location" :rules="rules.location">
        <a-input v-model:value="meetings.location" />
      </a-form-item>

      <a-form-item label="Thời gian bắt đầu" name="startTime" :rules="rules.startTime">
        <a-date-picker v-model:value="meetings.startTime" show-time format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
      </a-form-item>

      <a-form-item label="Thời gian kết thúc" name="endTime" :rules="rules.endTime">
        <a-date-picker v-model:value="meetings.endTime" show-time format="YYYY-MM-DD HH:mm:ss" style="width: 100%;" />
      </a-form-item>

      <a-form-item label="Link họp" name="link" :rules="rules.link">
        <a-input v-model:value="meetings.link" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits } from 'vue'
import dayjs from 'dayjs'
import { toast } from 'vue3-toastify'
import { createMeetings, getMeetingsById, updateMeetings } from '@/services/api/meetings.api';

const props = defineProps<{ open: boolean; meetingId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const meetings = ref({
  code: '',
  title: '',
  content: '',
  location: '',
  startTime: null as any,
  endTime: null as any,
  link: ''
})

const meetingsForm = ref()

const rules = {
  code: [{ required: true, message: 'Mã cuộc họp không được để trống!' }],
  title: [{ required: true, message: 'Tiêu đề không được để trống!' }],
  content: [{ required: true, message: 'Nội dung không được để trống!' }],
  location: [{ required: true, message: 'Địa điểm không được để trống!' }],
  startTime: [{ required: true, message: 'Thời gian bắt đầu không được để trống!' }],
  endTime: [{ required: true, message: 'Thời gian kết thúc không được để trống!' }],
  link: [{ required: true, message: 'Link họp không được để trống!' }]
}

const fetchMeetingDetails = async (id: string) => {
  try {
    const response = await getMeetingsById(id)
    const data = response.data
    meetings.value = {
      code: data.code,
      title: data.title,
      content: data.content,
      location: data.location,
      startTime: dayjs(data.startTime),
      endTime: dayjs(data.endTime),
      link: data.link
    }
  } catch (error) {
    console.error('Lỗi khi lấy thông tin cuộc họp:', error)
  }
}

watch(
  () => props.open,
  async (newVal) => {
    if (newVal) {
      if (props.meetingId) {
        await fetchMeetingDetails(props.meetingId)
      } else {
        resetForm()
      }
    }
  },
  { immediate: true }
)

const resetForm = () => {
  meetings.value = {
    code: '',
    title: '',
    content: '',
    location: '',
    startTime: null,
    endTime: null,
    link: ''
  }
  meetingsForm.value?.resetFields()
}

const closeModal = () => {
  resetForm()
  emit('close')
}

const handleSubmit = async () => {
  try {
    await meetingsForm.value.validate()

    const formData = {
      ...meetings.value,
      startTime: meetings.value.startTime?.valueOf(),
      endTime: meetings.value.endTime?.valueOf()
    }

    if (props.meetingId) {
      await updateMeetings(formData, props.meetingId)
      toast.success('Cập nhật cuộc họp thành công!')
    } else {
      await createMeetings(formData)
      toast.success('Thêm mới cuộc họp thành công!')
    }

    resetForm()
    closeModal()
    emit('success')
  } catch (error) {
  }
}

</script>
