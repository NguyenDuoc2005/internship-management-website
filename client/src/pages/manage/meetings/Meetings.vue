<template>
    <DivCustom label="Quản lý thực tập sinh">
        <InternFilter
            :searchQuery="state.searchQuery"
            @update:searchQuery="updateSearchQuery"
            :searchStatus="state.internStatus"
            @update:searchStatus="updateSearchStatus"
        />

        <InternTable
            :searchQuery="state.searchQuery"
            :interns="state.interns"
            :paginationParams="state.paginationParams"
            :totalItems="state.totalItems"
            @add="openAddModal"
            @view="openViewModal"
            @page-change="handlePageChange"
            @update-status="handleUpdateStatusClick"
        />
    </DivCustom>

    <InternModal
        :open="state.isModalOpen"
        :internId="state.id"
        :title="state.id ? 'Cập nhật thực tập sinh' : 'Thêm mới thực tập sinh'"
        @close="closeModal"
        @success="fetchInterns"
    />
</template>

<script setup lang="ts">
import { onMounted, reactive, watch } from 'vue'
import { debounce } from 'lodash'
import { toast } from 'vue3-toastify'

import DivCustom from '@/components/custom/Div/DivCustom.vue'
import InternFilter from './MeetingFilter.vue'
import InternTable from './MeetingsTable.vue'
import InternModal from './MeetingsModal.vue'

import {
    InternResponse,
    changeStatusIntern,
    getAllInterns,
    ParamsGetIntern
} from '@/services/api/intern.api'

const state = reactive({
    searchQuery: '',
    internStatus: null as number | null,
    isModalOpen: false,
    id: null as string | null,
    interns: [] as InternResponse[],
    paginationParams: { page: 1, size: 10 },
    totalItems: 0
})

const fetchInterns = async () => {
    try {
        const params: ParamsGetIntern = {
            page: state.paginationParams.page,
            size: state.paginationParams.size,
            internName: state.searchQuery || '',
            internStatus: state.internStatus ?? undefined
        }

        const response = await getAllInterns(params)
        state.interns = response?.data?.data || []
        state.totalItems = response?.data?.totalElements || 0
        console.log(response.data)
    } catch (error) {
        console.error('Lỗi khi tải danh sách thực tập sinh:', error)
    }
}

onMounted(fetchInterns)

const openAddModal = () => {
    state.id = null
    state.isModalOpen = true
}

const openViewModal = (id: string) => {
    state.id = id
    state.isModalOpen = true
}

const closeModal = () => {
    state.isModalOpen = false
}

const handlePageChange = ({ page, pageSize }: { page: number; pageSize?: number }) => {
    state.paginationParams.page = page
    if (pageSize) {
        state.paginationParams.size = pageSize
    }
    fetchInterns()
}

const handleUpdateStatus = async (id: string) => {
    try {
        await changeStatusIntern(id)
        toast.success('Cập nhật trạng thái thành công!')
        fetchInterns()
    } catch (error) {
        toast.error('Cập nhật trạng thái thất bại!')
    }
}

const debouncedFetchInterns = debounce(fetchInterns, 500)

const updateSearchQuery = (newQuery: string) => {
    state.searchQuery = newQuery.trim()
}

const updateSearchStatus = (newStatus: number) => {
    state.internStatus = newStatus
}

// Theo dõi sự thay đổi của bộ lọc
watch(() => state.searchQuery, () => {
    state.paginationParams.page = 1
    debouncedFetchInterns()
})

watch(() => state.internStatus, () => {
    state.paginationParams.page = 1
    debouncedFetchInterns()
})

const handleUpdateStatusClick = (id: string) => {
    handleUpdateStatus(id)
}
</script>
