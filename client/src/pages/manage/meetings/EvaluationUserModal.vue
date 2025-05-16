<template>
  <div class="p-4">
    <h2 class="text-xl font-semibold mb-4">Đánh giá sinh viên</h2>

    <a-table :columns="columns" :data-source="evaluations" row-key="userId" bordered>
      <template #bodyCell="{ column, record }">
        <!-- Avatar -->
        <template v-if="column.dataIndex === 'picture'">
          <img :src="record.picture" class="w-10 h-10 rounded-full" />
        </template>

        <!-- Score -->
        <template v-else-if="column.dataIndex === 'score'">
          <a-input-number
            v-model:value="record.score"
            :disabled="record.isEvaluated"
            :min="0"
            :max="10"
            style="width: 100%;"
          />
        </template>

        <!-- Comment -->
        <template v-else-if="column.dataIndex === 'comment'">
          <a-textarea
            v-model:value="record.comment"
            :disabled="record.isEvaluated"
            auto-size
          />
        </template>

        <!-- Action -->
        <template v-else-if="column.dataIndex === 'action'">
          <template v-if="record.isEvaluated">
            <a-tag color="green">Đã đánh giá</a-tag>
          </template>
          <template v-else>
            <a-button type="primary" @click="submitEvaluation(record)">Lưu</a-button>
          </template>
        </template>
      </template>
    </a-table>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { useRoute } from 'vue-router'
import { getAllCheckUserEvaluation,  MAEvaluationResponse } from '@/services/api/evaluation.api'
import { toast } from 'vue3-toastify'

// Lấy meetingId từ URL
const route = useRoute()
const meetingId = route.params.id as string

// Danh sách sinh viên cần đánh giá
const evaluations = ref<MAEvaluationResponse[]>([])

// Lấy danh sách sinh viên cần đánh giá
const fetchEvaluations = async () => {
  try {
    const res = await getAllCheckUserEvaluation({ meetingId })
    evaluations.value = res.data.data
  } catch (err) {
    console.error(err)
    toast.error('Không thể lấy danh sách đánh giá!')
  }
}

// Gửi đánh giá 1 sinh viên
const submitEvaluation = async (student: MAEvaluationResponse) => {
  if (!student.score || !student.comment) {
    toast.warning('Vui lòng nhập đầy đủ điểm và nhận xét!')
    return
  }
  try {
    // await updateEvaluation(student.evaluationId, {
    //   score: student.score,
    //   comment: student.comment
    // })
    student.isEvaluated = true
    toast.success('Đánh giá thành công!')
  } catch (err) {
    console.error(err)
    toast.error('Không thể gửi đánh giá!')
  }
}

onMounted(() => {
  fetchEvaluations()
})

// Cấu hình table
const columns = [
  { title: 'Ảnh', dataIndex: 'picture', key: 'picture' },
  { title: 'Họ tên', dataIndex: 'userName', key: 'userName' },
  { title: 'Email', dataIndex: 'email', key: 'email' },
  { title: 'Điểm', dataIndex: 'score', key: 'score' },
  { title: 'Nhận xét', dataIndex: 'comment', key: 'comment' },
  { title: 'Thao tác', dataIndex: 'action', key: 'action' }
]
</script>
