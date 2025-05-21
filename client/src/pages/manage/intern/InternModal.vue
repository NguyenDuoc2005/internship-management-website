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

      <a-form-item label="Số điện thoại" name="phoneNumber" :rules="rules.phoneNumber">
        <a-input v-model:value="intern.phoneNumber" />
      </a-form-item>

      <a-form-item label="Địa chỉ" name="address" :rules="rules.address">
        <a-input v-model:value="intern.address" />
      </a-form-item>

      <a-form-item label="Chuyên ngành" name="major" :rules="rules.major">
        <a-input v-model:value="intern.major" />
      </a-form-item>

      <a-form-item label="Ảnh đại diện" name="picture" :rules="rules.picture">
        <a-upload
          :beforeUpload="handleImageChange"
          :showUploadList="false"
          accept="image/*"
        >
          <a-button>Chọn ảnh</a-button>
        </a-upload>

        <!-- Spinner + Ảnh -->
        <a-spin :spinning="uploading" class="mt-2">
          <div v-if="intern.picture">
            <img
              :src="intern.picture"
              alt="Ảnh đại diện"
              style="max-width: 120px; border: 1px solid #ccc; padding: 4px; border-radius: 4px;"
            />
          </div>
        </a-spin>

        <!-- Tên ảnh -->
        <div v-if="intern.pictureName" class="mt-1 text-sm text-gray-500">
          Ảnh: {{ intern.pictureName }}
        </div>
      </a-form-item>
    </a-form>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { createIntern, getByIdIntern, updateIntern, uploadFile } from '@/services/api/intern.api'
import { toast } from 'vue3-toastify'

const props = defineProps<{ open: boolean; internId: string | null; title: string }>()
const emit = defineEmits(['close', 'success'])

const intern = ref({
  userName: '',
  email: '',
  picture: '',
  pictureName: '',
  phoneNumber: '',
  address: '',
  major: ''
})

const internForm = ref()
const uploading = ref(false)

const rules = {
  userName: [{ required: true, message: 'Tên đăng nhập không được để trống!' }],
  email: [{ required: true, message: 'Email không được để trống!' }],
  picture: [{ required: true, message: 'Ảnh đại diện không được để trống!' }],
  phoneNumber: [{ required: true, message: 'Số điện thoại không được để trống!' }],
  address: [{ required: true, message: 'Địa chỉ không được để trống!' }],
  major: [{ required: true, message: 'Chuyên ngành không được để trống!' }]
}

const handleImageChange = async (file: File) => {
  uploading.value = true
  try {
    const res = await uploadFile(file)
    intern.value.picture = res.data
    intern.value.pictureName = file.name
  } catch (error) {
    console.error('Lỗi upload ảnh:', error)
    toast.error('Tải ảnh thất bại!')
  } finally {
    uploading.value = false
  }
  return false // Ngăn upload mặc định
}

const fetchInternDetails = async (id: string) => {
  try {
    const response = await getByIdIntern(id)
    intern.value = {
      userName: response.data.userName,
      email: response.data.email,
      picture: response.data.picture,
      pictureName: 'Đã có ảnh',
      phoneNumber: response.data.phoneNumber,
      address: response.data.address,
      major: response.data.major
    }
  } catch (error) {
    console.error('Lỗi khi lấy thông tin thực tập sinh:', error)
  }
}

watch(
  () => props.open,
  async (newVal) => {
    if (newVal) {
      if (props.internId) {
        await fetchInternDetails(props.internId)
      } else {
        resetForm()
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
    pictureName: '',
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

    const formData = {
      userName: intern.value.userName,
      email: intern.value.email,
      phoneNumber: intern.value.phoneNumber,
      address: intern.value.address,
      major: intern.value.major,
      picture: intern.value.picture
    }

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
    console.error('Lỗi khi submit:', error)
  }
}
</script>
