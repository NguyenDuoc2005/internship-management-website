<template>
  <a-modal :open="open" title="Thêm thành viên vào cuộc họp" @cancel="closeModal" style="top: 20px" width="800px">
    <template #footer>
      <a-button @click="closeModal">Đóng</a-button>
    </template>

    <div style="display: flex; justify-content: space-between; margin-bottom: 16px;">
      <a-input
        v-model:value="searchText"
        placeholder="Tìm theo tên hoặc email"
        style="width: 300px"
        allow-clear
      />

      <!-- <a-select v-model:value="filterJoinStatus" style="width: 180px">
        <a-select-option :value="null">Tất cả</a-select-option>
        <a-select-option :value="true">Đã tham gia</a-select-option>
        <a-select-option :value="false">Chưa tham gia</a-select-option>
      </a-select> -->
    </div>

    <a-table :columns="columns" :data-source="members" :row-key="record => record.userId" bordered>
      <template #bodyCell="{ column, record }">
        <template v-if="column.key === 'picture'">
          <a-avatar :src="record.picture" />
        </template>

        <template v-else-if="column.key === 'evaluateAndAction'">
          <span style="margin-right: 8px; font-size: 18px;">
            {{ record.isEvaluated === true ? '✔️' : '❌' }}
          </span>
          <a-switch :checked="record.isEvaluated" @change="checked => handleToggleMember(record, checked)" />
        </template>
      </template>
    </a-table>
  </a-modal>
</template>

<script setup lang="ts">
import { ref, defineProps, defineEmits, watch } from 'vue'
import { getAllCheckUserEvaluation, joinMeetings, MAEvaluationResponse, outMeetings } from '../../../services/api/evaluation.api'

const props = defineProps<{
  open: boolean
  meetingId: string | null
}>()
const searchText = ref('')
const filterJoinStatus = ref('')
const emit = defineEmits(['close', 'member-added'])

const members = ref<MAEvaluationResponse[]>([])
const addedMembers = ref(new Set<string>())

const columns = [
  { title: 'Ảnh đại diện', key: 'picture', dataIndex: 'picture' },
  { title: 'Tên', key: 'userName', dataIndex: 'userName' },
  { title: 'Email', key: 'email', dataIndex: 'email' },
  {
    title: 'Tham gia',
    key: 'evaluateAndAction',
    dataIndex: 'evaluateAndAction',
  },
]

const fetchMembers = async () => {
  try {
    if (!props.meetingId) return
    const payload = {
      meetingId: props.meetingId,
      q:searchText.value
    }
    const response = await getAllCheckUserEvaluation(payload)
    members.value = response.data.data || []

    addedMembers.value = new Set(
      members.value
        .filter((m) => m.isAdded) 
        .map((m) => m.userId)
    )
  } catch (error) {
    console.error('Lỗi khi lấy danh sách người dùng:', error)
  }
}

const closeModal = () => {
  emit('close')
}

const handleToggleMember = async (member: MAEvaluationResponse, checked: boolean) => {
  const payload = {
    idUser: member.userId,
    meetingId: props.meetingId
  }
  if (checked) {
    await joinMeetings(payload)
  } else {
    await outMeetings(payload)
  }
  fetchMembers()
  emit('member-added', { member, added: checked })
}

watch(
  () => props.open,
  async (newVal) => {
    if (newVal) {
      await fetchMembers()
    } else {
      addedMembers.value.clear()
      members.value = []
    }
  },
  { immediate: true }
)
watch(
  () => searchText.value,
  async (newVal) => {
    fetchMembers()
  }
)
</script>
