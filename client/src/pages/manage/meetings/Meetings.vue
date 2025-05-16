<template>
    <BreadcrumbDefault label="Quản lý cuộc họp">
        <MeetingFilter
            :searchQuery="state.searchQuery"
            @update:searchQuery="updateSearchQuery"
            :searchStatus="state.meetingStatus"
            @update:searchStatus="updateSearchStatus"
        />

        <MeetingsTable
            :searchQuery="state.searchQuery"
            :meetings="state.meetings"
            :paginationParams="state.paginationParams"
            :totalItems="state.totalItems"
            @add="openAddModal"
            @view="openViewModal"
            @join-meeting="openJoinMeetingsModal"
            @page-change="handlePageChange"
            @update-status="handleUpdateStatusClick"
            @evaluation-user="openEvaluationUserModal"
        />
    </BreadcrumbDefault>

    <MeetingsModal
        :open="state.isModalOpen"
        :meetingId="state.id"
        :title="state.id ? 'Cập nhật cuộc họp' : 'Thêm mới cuộc họp'"
        @close="closeModal"
        @success="fetchMeetings"
    />
     <JoinMeetingsModal
        :open="state.isJoinMeetingsModalOpen"
        :meetingId="state.id"
        @close="closeModal"
        @success="fetchMeetings"
    />
   
</template>

<script setup lang="ts">
import { onMounted, reactive, watch } from 'vue'
import { debounce } from 'lodash'
import { toast } from 'vue3-toastify'

import DivCustom from '@/components/custom/Div/DivCustom.vue'


import MeetingFilter from './MeetingFilter.vue'
import MeetingsTable from './MeetingsTable.vue'
import MeetingsModal from './MeetingsModal.vue'
import { changeStatusMeeting, getAllMeetings, MAMeetingResponse, ParamsGetMeeting } from '@/services/api/meetings.api'
import BreadcrumbDefault from '@/components/custom/Div/BreadcrumbDefault.vue'
import JoinMeetingsModal from './JoinMeetingsModal.vue'
import EvaluationUserModal from './EvaluationUserModal.vue'

const state = reactive({
    searchQuery: '',
    meetingStatus: null as string | null,
    isModalOpen: false,
     isJoinMeetingsModalOpen: false,
       isEvaluationUserModalOpen: false,
    id: null as string | null,
    meetings: [] as MAMeetingResponse[],
    paginationParams: { page: 1, size: 10 },
    totalItems: 0
})

const fetchMeetings = async () => {
    try {
        const params: ParamsGetMeeting = {
            page: state.paginationParams.page,
            size: state.paginationParams.size,
            q: state.searchQuery || '',
            status: state.meetingStatus ?? undefined
        }

        const response = await getAllMeetings(params)
        state.meetings = response?.data?.data || []
        state.totalItems = response?.data?.totalElements || 0
        console.log(response.data)
    } catch (error) {
        console.error('Lỗi khi tải danh sách cuộc họp:', error)
    }
}

onMounted(fetchMeetings)

const openAddModal = () => {
    state.id = null
    state.isModalOpen = true
}

const openViewModal = (id: string) => {
    state.id = id
    state.isModalOpen = true
}

const openJoinMeetingsModal = (id: string) => {
    state.id = id
    state.isJoinMeetingsModalOpen = true
}
const openEvaluationUserModal = (id: string) => {
    state.id = id
    state.isEvaluationUserModalOpen = true
}

const closeModal = () => {
    state.isModalOpen = false
    state.isJoinMeetingsModalOpen = false
    state.isEvaluationUserModalOpen = false
}

const handlePageChange = ({ page, pageSize }: { page: number; pageSize?: number }) => {
    state.paginationParams.page = page
    if (pageSize) {
        state.paginationParams.size = pageSize
    }
    fetchMeetings()
}

const handleUpdateStatus = async (id: string) => {
    try {
        await changeStatusMeeting(id)
        toast.success('Cập nhật trạng thái thành công!')
        fetchMeetings()
    } catch (error) {
        toast.error('Cập nhật trạng thái thất bại!')
    }
}

const debouncedFetchMeetings = debounce(fetchMeetings, 500)

const updateSearchQuery = (newQuery: string) => {
    state.searchQuery = newQuery.trim()
}

const updateSearchStatus = (newStatus: string) => {
    state.meetingStatus = newStatus
}

watch(() => state.searchQuery, () => {
    state.paginationParams.page = 1
    debouncedFetchMeetings()
})

watch(() => state.meetingStatus, () => {
    state.paginationParams.page = 1
    debouncedFetchMeetings()
})

const handleUpdateStatusClick = (id: string) => {
    handleUpdateStatus(id)
}
</script>
