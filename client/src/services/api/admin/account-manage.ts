import { PREFIX_API_ACCOUNT_MANAGE_ADMIN } from "@/constants/url";
import request from "@/services/request";
import { AxiosResponse } from "axios";
import {
    DefaultResponse,
    PaginationParams,
    PaginationResponse,
    ResponseList,
} from "@/types/api.common";

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

export const getAccountManage = async (params: ParamsGetIntern) => {
    const res = (await request({
        url: `${PREFIX_API_ACCOUNT_MANAGE_ADMIN}`,
        method: "GET",
        params: params,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<InternResponse>>>>;
    return res.data;
};

export const changeStatusAccountManage = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_ACCOUNT_MANAGE_ADMIN}/${id}/change-status`,
        method: "PUT",
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};

export const getByIdIntern = async (id: string) => {
    const res = (await request({
        url: `${PREFIX_API_ACCOUNT_MANAGE_ADMIN}/detail/${id}`,
        method: "GET",
    })) as AxiosResponse<DefaultResponse<InternResponse>>;
    return res.data;
};