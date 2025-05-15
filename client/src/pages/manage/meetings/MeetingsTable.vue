<template>
  <DivCustom label="Danh sách Cuộc Họp" customClasses="mt-5">
    <template #extra>
      <a-tooltip title="Thêm mới Cuộc Họp">
        <a-button type="primary" @click="handleAddClick" class="flex items-center justify-center px-4">
          Thêm Cuộc Họp
        </a-button>
      </a-tooltip>
    </template>

    <div class="min-h-[360px]">
      <a-table
        :columns="columns"
        :data-source="meetings"
        :pagination="{
          current: paginationParams.page,
          pageSize: paginationParams.size,
          total: totalItems,
          showSizeChanger: true,
          pageSizeOptions: ['10', '20', '30', '40', '50']
        }"
        :scroll="{ x: 1500, y: 400 }"
        @change="handlePageChange"
        rowKey="id"
      >
        <template #bodyCell="{ column, record, index }">
          <template v-if="column.key === 'orderNumber'">
            {{ (paginationParams.page - 1) * paginationParams.size + index + 1 }}
          </template>

          <template v-if="column.key === 'status'">
            <a-tag :color="record.status === 0 ? 'green' : 'red'">
              {{ record.status === 0 ? 'Hoạt động' : 'Ngừng hoạt động' }}
            </a-tag>
          </template>

          <template v-if="column.key === 'startTime'">
            {{ formatDate(record.startTime) }}
          </template>

          <template v-if="column.key === 'endTime'">
            {{ formatDate(record.endTime) }}
          </template>

          <template v-if="column.key === 'operation'">
            <div class="flex gap-1 justify-center">
              <a-tooltip title="Cập nhật">
                <a-icon @click="handleViewClick(record.id)">
                  <font-awesome-icon :icon="['fas', 'pen-to-square']" class="cursor-pointer p-2" style="font-size: 20px;" />
                </a-icon>
              </a-tooltip>

              <a-tooltip title="Thay đổi trạng thái">
                <a-icon @click="handleUpdateStatusClick(record.id)">
                  <font-awesome-icon :icon="['fas', 'rotate-right']" class="cursor-pointer p-2" style="font-size: 20px;" />
                </a-icon>
              </a-tooltip>
            </div>
          </template>
        </template>
      </a-table>
    </div>
  </DivCustom>
</template>

<script setup lang="ts">
import DivCustom from '@/components/custom/Div/DivCustom.vue'
import { TableColumnsType } from 'ant-design-vue'
import { faPenToSquare, faRotateRight } from '@fortawesome/free-solid-svg-icons'
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome'
import { library } from '@fortawesome/fontawesome-svg-core'
library.add(faPenToSquare, faRotateRight)

defineProps<{
  meetings: any[]
  paginationParams: { page: number; size: number }
  totalItems: number
}>()

const emit = defineEmits(['page-change', 'add', 'view', 'update-status'])

const columns: TableColumnsType = [
  { title: 'STT', key: 'orderNumber', width: 10, align: 'center' },
  { title: 'Mã', key: 'code', dataIndex: 'code', width: 15, align: 'center' },
  { title: 'Tiêu đề', key: 'title', dataIndex: 'title', width: 20, align: 'center' },
  { title: 'Nội dung', key: 'content', dataIndex: 'content', width: 25, align: 'center' },
  { title: 'Địa điểm', key: 'location', dataIndex: 'location', width: 20, align: 'center' },
  { title: 'Bắt đầu', key: 'startTime', dataIndex: 'startTime', width: 15, align: 'center' },
  { title: 'Kết thúc', key: 'endTime', dataIndex: 'endTime', width: 15, align: 'center' },
  { title: 'Link', key: 'link', dataIndex: 'link', width: 20, align: 'center' },
  { title: 'Trạng thái', key: 'status', dataIndex: 'status', width: 15, align: 'center' },
  { title: 'Hành động', key: 'operation', width: 10, align: 'center', fixed: 'right' }
]

const formatDate = (timestamp: number | null) => {
  if (!timestamp) return '---'
  const date = new Date(timestamp)
  return date.toLocaleString('vi-VN', {
    hour: '2-digit',
    minute: '2-digit',
    day: '2-digit',
    month: '2-digit',
    year: 'numeric'
  })
}

const handlePageChange = (pagination: any) => {
  emit('page-change', { page: pagination.current, pageSize: pagination.pageSize })
}
const handleAddClick = () => emit('add')
const handleViewClick = (id: string) => emit('view', id)
const handleUpdateStatusClick = (id: string) => emit('update-status', id)
</script>
