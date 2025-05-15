import { PREFIX_API_AUTH_ALL_ROLE, PREFIX_API_AUTH_LOGIN, PREFIX_API_AUTH_REGISTER } from "@/constants/url";
import request from "@/services/request";
import {DefaultResponse, PaginationParams, PaginationResponse, ResponseList} from "@/types/api.common"
import { AxiosResponse } from "axios";

export interface ParamsGetMajorFacility extends PaginationParams {
    q?: string | '',
    majorName: string | null,
}

export type MajorFacilityResponse = ResponseList & {
    majorFacilityId: string,
    majorName: string,
    majorId: string,
    majorCode: string
}

export interface LoginRequest{
    password: string,
    email: string
}
export interface RegisterRequest{
    password: string,
    email: string
    userName:string
    idRole:string
}
export interface RoleResponse{
    id: string,
    name: string
}

export const login = async (data: LoginRequest) => {
    const res = (await request({
      url: `${PREFIX_API_AUTH_LOGIN}`,
      method: 'POST',
      data: data
    })) as AxiosResponse<DefaultResponse<String>>
  
    return res.data
}

export const getAllRole = async () => {
    const res = (await request({
      url: `${PREFIX_API_AUTH_ALL_ROLE}`,
      method: 'GET'
    })) as AxiosResponse<DefaultResponse<Array<RoleResponse>>>
  
    return res.data
}

export const register = async (data: RegisterRequest) => {
    const res = (await request({
      url: `${PREFIX_API_AUTH_REGISTER}`,
      method: 'PUT',
      data: data
    })) as AxiosResponse<DefaultResponse<String>>
  
    return res.data
}