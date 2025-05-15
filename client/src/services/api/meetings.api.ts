import { PREFIX_API_MEETINGS_MANAGE } from "@/constants/url";
import request from "@/services/request";
import { AxiosResponse } from "axios";
import {
    DefaultResponse,
    PaginationParams,
    PaginationResponse,
    ResponseList,
} from "@/types/api.common";

export type MAMeetingResponse = ResponseList &{
  id: string;
  code: string;
  title: string;
  content: string;
  location: string;
  startTime: number;
  endTime: number;
  memberCount: number;
  status: number;
  link: string;
}

export interface ParamsGetMeeting extends PaginationParams {
    q?: string;
    status?: number;
}

export interface MACreateUpdateMeetingsRequest {
  code: string;
  title: string;
  content: string;
  location: string;
  startTime: number;
  endTime: number;
  link: string;
}

export const getAllMeetings = async (params: ParamsGetMeeting) => {
    const res = (await request({
        url: `${PREFIX_API_MEETINGS_MANAGE}`,
        method: "GET",
        params: params,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<MAMeetingResponse>>>>;
    return res.data;
};

export const createMeetings = async (data: MACreateUpdateMeetingsRequest) => {
    const res = (await request({
        url: `${PREFIX_API_MEETINGS_MANAGE}/add`,
        method: "POST",
        data: data,
    })) as AxiosResponse<DefaultResponse<MAMeetingResponse>>;
    return res.data;
};

export const updateMeetings = async (data: MACreateUpdateMeetingsRequest, id: string) => {
    const res = (await request({
        url: `${PREFIX_API_MEETINGS_MANAGE}/update/${id}`,
        method: "PUT",
        data: data,
    })) as AxiosResponse<DefaultResponse<MAMeetingResponse>>;
    return res.data;
};

export const changeStatusMeeting = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_MEETINGS_MANAGE}/${id}/change-status`,
        method: "PUT",
    })) as AxiosResponse<DefaultResponse<MAMeetingResponse>>;
    return res.data;
};

export const getMeetingsById = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_MEETINGS_MANAGE}/detail/${id}`,
        method: "GET",
    })) as AxiosResponse<DefaultResponse<MAMeetingResponse>>;
    return res.data;
};
