<template>
  <div class="p-4">
    <h2 class="text-xl font-semibold mb-4">Đánh giá sinh viên</h2>

    <!-- Nút Lưu toàn bộ -->
    <a-button type="primary" @click="submitAllEvaluations" class="mb-4">
      Lưu toàn bộ đánh giá
    </a-button>

    <a-table :columns="columns" :data-source="evaluations" row-key="userId" bordered>
      <template #bodyCell="{ column, record }">
        <!-- Avatar -->
        <template v-if="column.dataIndex === 'picture'">
          <img :src="record.picture" class="w-10 h-10 rounded-full" />
        </template>

        <!-- Score -->
        <template v-else-if="column.dataIndex === 'score'">
          <a-input-number v-model:value="record.score" :min="0" :max="10" style="width: 100%;" />
        </template>

        <!-- Comment -->
        <template v-else-if="column.dataIndex === 'comment'">
          <a-textarea v-model:value="record.comment" auto-size />
        </template>
        
        <!-- Action -->
        <template v-else-if="column.dataIndex === 'action'">
          <a-tag v-if="record.score" color="green">Đã đánh giá</a-tag>
          <a-tag v-else color="red">Chưa đánh giá</a-tag>
        </template>

      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getAllEvaluation, MAEvaluationResponse, MAUpdateEvaluationRequest, updateEvaluation } from '@/services/api/evaluation.api'
import { toast } from 'vue3-toastify'

const route = useRoute()
const meetingId = route.params.id as string
const evaluations = ref<MAEvaluationResponse[]>([])

// Lấy danh sách đánh giá
const fetchEvaluations = async () => {
  try {
    const res = await getAllEvaluation({ meetingId })
    evaluations.value = res.data.data
  } catch (err) {
    console.error(err)
    toast.error('Không thể lấy danh sách đánh giá!')
  }
}

// Gửi toàn bộ đánh giá
const submitAllEvaluations = async () => {
  const payload: MAUpdateEvaluationRequest = {
    evaluations: evaluations.value
      .filter(e => e.score != null )
      .map(e => ({
        meetingId: meetingId,
        userId: e.userId,
        score: e.score,
        comment: e.comment
      }))
  }
  console.log("payload ", payload)
  if (payload.evaluations.length === 0) {
    toast.warning("Chưa có đánh giá nào hợp lệ để gửi!")
    return
  }

  try {
    await updateEvaluation(payload)
    toast.success("Đã lưu toàn bộ đánh giá thành công!")

    // Đánh dấu đã đánh giá
    payload.evaluations.forEach(item => {
      const found = evaluations.value.find(e => e.userId === item.userId)
      if (found) found.isEvaluated = true
    })
  } catch (err) {
    console.error(err)
    toast.error("Lỗi khi lưu đánh giá!")
  }
}

onMounted(() => {
  fetchEvaluations()
})

// Cột bảng
const columns = [
  { title: 'Ảnh', dataIndex: 'picture', key: 'picture' },
  { title: 'Họ tên', dataIndex: 'userName', key: 'userName' },
  { title: 'Email', dataIndex: 'email', key: 'email' },
  { title: 'Điểm', dataIndex: 'score', key: 'score' },
  { title: 'Nhận xét', dataIndex: 'comment', key: 'comment' },
  { title: 'Trạng thái', dataIndex: 'action', key: 'action' }
]
</script>
