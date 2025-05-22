<template>
  <DivCustom label="Danh sách Tài khoản cần phê duyệt " customClasses="mt-5">
    <template #icon>
  <CheckCircleOutlined />
</template>

    <div class="min-h-[360px]">
      <a-table
        :columns="columns"
        :data-source="interns"
        :pagination="{
          current: paginationParams.page,
          pageSize: paginationParams.size,
          total: totalItems,
          showSizeChanger: true,
          pageSizeOptions: ['10', '20', '30', '40', '50']
        }"
       :scroll="{ x: 1500 }"  
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

          <template v-if="column.key === 'createdDate'">
            {{ formatDate(record.createdDate) }}
          </template>



          <template v-if="column.key === 'operation'">
            <div class="flex gap-1 justify-center">
              <a-tooltip title="Thay đổi trạng thái">
                <a-icon @click="handleUpdateStatusClick(record.id)">
                  <font-awesome-icon :icon="['fas', 'rotate-right']" class="cursor-pointer p-2 text-yellow-500" style="font-size: 20px;" />
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
import { CheckCircleOutlined } from '@ant-design/icons-vue'
library.add(faPenToSquare, faRotateRight)

defineProps<{
  interns: any[]
  paginationParams: { page: number; size: number }
  totalItems: number
}>()

const emit = defineEmits(['page-change', 'add', 'view', 'update-status'])

const columns: TableColumnsType = [
  { title: 'STT', key: 'orderNumber', width: 10, align: 'center',fixed: 'left' },
  { title: 'Mã', key: 'code', dataIndex: 'code', width: 10, align: 'center' },
  { title: 'Tên', key: 'userName', dataIndex: 'userName', width: 15, align: 'center' },
  { title: 'Email', key: 'email', dataIndex: 'email', width: 20, align: 'center' },
  { title: 'SĐT', key: 'phoneNumber', dataIndex: 'phoneNumber', width: 12, align: 'center' },
  { title: 'Địa chỉ', key: 'address', dataIndex: 'address', width: 15, align: 'center' },
  { title: 'Ngày tạo', key: 'createdDate', dataIndex: 'createdDate', width: 15, align: 'center' },
  { title: 'Trạng thái', key: 'status', dataIndex: 'status', width: 15, align: 'center' },
  { title: 'Hành động', key: 'operation', width: 10, align: 'center', fixed: 'right' }
]

const formatDate = (timestamp: number | null) => {
  if (!timestamp) return '---'
  const date = new Date(timestamp)
  return date.toLocaleDateString('vi-VN')
}

const handlePageChange = (pagination: any) => {
  emit('page-change', { page: pagination.current, pageSize: pagination.pageSize })
}

const handleUpdateStatusClick = (id: string) => emit('update-status', id)

</script>
