import { ApiRes } from "@/lib/interfaces/apiRes";
import { MembersResponse } from "./types";
import { restApi } from "../restApi";

export const getMembers = async (
  page: number,
  size: number
): Promise<ApiRes<MembersResponse>> => {
  return await restApi.get<ApiRes<MembersResponse>>(`/member`, { page, size });
};
