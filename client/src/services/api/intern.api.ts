import { PREFIX_API_INTERN_MANAGE } from "@/constants/url";
import request from "@/services/request";
import { AxiosResponse } from "axios";
import {
    DefaultResponse,
    PaginationParams,
    PaginationResponse,
    ResponseList,
} from "@/types/api.common";

// Định nghĩa phản hồi (Response) cho intern
export type InternResponse = ResponseList & {
    id: string;
    code: string;
    userName: string;
    email: string;
    passWord: string;
    picture: string;
    phoneNumber: string;
    address: string;
    major: string;
    createdDate: number;
    status: number;
    isEvaluated: boolean;
    position: string;
};

export interface ParamsGetIntern extends PaginationParams {
    q?: string;
    internName?: string;
    internStatus?: number;
}

export interface MACreateUpdateInternRequest {
    userName: string;
    email: string;
    passWord: string;
    picture: string;
    phoneNumber: string;
    address: string;
    major: string;
}

export const getAllInterns = async (params: ParamsGetIntern) => {
    const res = (await request({
        url: `${PREFIX_API_INTERN_MANAGE}`,
        method: "GET",
        params: params,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<InternResponse>>>>;
    return res.data;
};

export const createIntern = async (data: MACreateUpdateInternRequest) => {
    const res = (await request({
        url: `${PREFIX_API_INTERN_MANAGE}/add`,
        method: "POST",
        data: data,
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};

export const updateIntern = async (data: MACreateUpdateInternRequest, id: string) => {
    const res = (await request({
        url: `${PREFIX_API_INTERN_MANAGE}/update/${id}`,
        method: "PUT",
        data: data,
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};

export const changeStatusIntern = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_INTERN_MANAGE}/${id}/change-status`,
        method: "PUT",
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};

export const getByIdIntern = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_INTERN_MANAGE}/detail/${id}`,
        method: "GET",
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};
