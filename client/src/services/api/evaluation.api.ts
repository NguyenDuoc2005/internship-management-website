import { PREFIX_API_EVALUATION_MANAGE } from "@/constants/url";
import request from "@/services/request";
import { AxiosResponse } from "axios";
import {
    DefaultResponse,
    PaginationParams,
    PaginationResponse,
    ResponseList,
} from "@/types/api.common";

export interface MAEvaluationResponse {
  userId: string;
  userName: string;
  email: string;
  picture: string;

  evaluationId: string;
  score: number;
  comment: string;
  isEvaluated: boolean;
}

export interface MAEvaluationRequest extends PaginationParams {
    internName?: string;
    internStatus?: string;
    meetingId?: string;
    evaluatedOnly?: boolean;
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

export interface MAJoinOutMeetingsRequest {
  idUser: string;
  meetingId: string;
}
export interface MAUpdateEvaluationRequest {
  evaluations: EvaluationUpdateItem[];
}

export interface EvaluationUpdateItem {
  meetingId: string;
  userId: string;
  score: number;
  comment: string;
}
export const getAllCheckUserEvaluation = async (params: MAEvaluationRequest) => {
    const res = (await request({
        url: `${PREFIX_API_EVALUATION_MANAGE}/check-user`,
        method: "GET",
        params: params,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<MAEvaluationResponse>>>>;
    return res.data;
};

export const joinMeetings = async (data: MAJoinOutMeetingsRequest) => {
    const res = (await request({
        url: `${PREFIX_API_EVALUATION_MANAGE}/join-meetings`,
        method: "PUT",
        data: data,
    })) as AxiosResponse<DefaultResponse<null>>;
    return res.data;
};

export const outMeetings = async (data: MAJoinOutMeetingsRequest) => {
    const res = (await request({
        url: `${PREFIX_API_EVALUATION_MANAGE}/out-meetings`,
        method: "PUT",
        data: data,
    })) as AxiosResponse<DefaultResponse<null>>;
    return res.data;
};
export const getAllEvaluation = async (params: MAEvaluationRequest) => {
    const res = (await request({
        url: `${PREFIX_API_EVALUATION_MANAGE}`,
        method: "GET",
        params: params,
    })) as AxiosResponse<DefaultResponse<PaginationResponse<Array<MAEvaluationResponse>>>>;
    return res.data;
};
export const updateEvaluation = async (data: MAUpdateEvaluationRequest) => {
  const res = (await request({
    url: `${PREFIX_API_EVALUATION_MANAGE}/update-evaluation`,
    method: "PUT",
    data: data,
  })) as AxiosResponse<DefaultResponse<null>>;
  return res.data;
};