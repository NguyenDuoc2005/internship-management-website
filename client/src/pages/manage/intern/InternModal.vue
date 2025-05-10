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
      :model="intern"
      ref="internForm"
      name="internForm"
      autocomplete="off"
      :label-col="{ span: 6 }"
      :wrapper-col="{ span: 18 }"
    >
      <a-form-item label="Tên đăng nhập" name="userName" :rules="rules.userName">
        <a-input v-model:value="intern.userName" />
      </a-form-item>

      <a-form-item label="Email" name="email" :rules="rules.email">
        <a-input v-model:value="intern.email" />
      </a-form-item>

      <a-form-item label="Ảnh đại diện" name="picture" :rules="rules.picture">
        <a-input v-model:value="intern.picture" />
      </a-form-item>

      <a-form-item label="Số điện thoại" name="phoneNumber" :rules="rules.phoneNumber">
        <a-input v-model:value="intern.phoneNumber" />
      </a-form-item>

      <a-form-item label="Địa chỉ" name="address" :rules="rules.address">
        <a-input v-model:value="intern.address" />
      </a-form-item>

      <a-form-item label="Chuyên ngành" name="major" :rules="rules.major">
        <a-input v-model:value="intern.major" />
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, watch, defineProps, defineEmits, nextTick } from 'vue'
import { createIntern, getByIdIntern, updateIntern } from '@/services/api/intern.api'
import { toast } from 'vue3-toastify'

const props = defineProps<{ open: boolean; internId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const intern = ref({
  userName: '',
  email: '',
  picture: '',
  phoneNumber: '',
  address: '',
  major: ''
})

const internForm = ref()

const rules = {
  userName: [{ required: true, message: 'Tên đăng nhập không được để trống!' }],
  email: [{ required: true, message: 'Email không được để trống!' }],
  picture: [{ required: true, message: 'Ảnh đại diện không được để trống!' }],
  phoneNumber: [{ required: true, message: 'Số điện thoại không được để trống!' }],
  address: [{ required: true, message: 'Địa chỉ không được để trống!' }],
  major: [{ required: true, message: 'Chuyên ngành không được để trống!' }]
}

const fetchInternDetails = async (id: string) => {
  try {
    const response = await getByIdIntern(id)
    intern.value = {
      userName: response.data.userName,
      email: response.data.email,
      picture: response.data.picture,
      phoneNumber: response.data.phoneNumber,
      address: response.data.address,
      major: response.data.major
    }
    console.log(intern.value)
  } catch (error) {
    console.error('Lỗi khi lấy thông tin thực tập sinh:', error)
  }
}

watch(
  () => props.open,
  async (newVal) => {
    if (newVal) {
      // Khi modal được mở, kiểm tra internId và lấy thông tin
      if (props.internId) {
        await fetchInternDetails(props.internId)
      } else {
        resetForm() // Nếu không có internId, reset form
      }
    }
  },
  { immediate: true }
)

const resetForm = () => {
  intern.value = {
    userName: '',
    email: '',
    picture: '',
    phoneNumber: '',
    address: '',
    major: ''
  }
  internForm.value?.resetFields()
}

const closeModal = () => {
  resetForm()
  emit('close')
}

const handleSubmit = async () => {
  try {
    await internForm.value.validate()
    const formData = { ...intern.value }
    if (props.internId) {
      await updateIntern(formData, props.internId)
      toast.success('Cập nhật thành công!')
    } else {
      await createIntern(formData)
      toast.success('Thêm mới thành công!')
    }

    resetForm()
    closeModal()
    emit('success')
  } catch (error) {
    // toast.error('Lưu thất bại, vui lòng kiểm tra lại dữ liệu!')
  }
}
</script>
