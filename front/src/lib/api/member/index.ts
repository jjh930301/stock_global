import { ApiRes } from "@/lib/interfaces/apiRes";
import { MembersResponse } from "./types";
import { restApi } from "../axios";

export const getMembers = async (
  page: number,
  size: number,
  date: Date
): Promise<ApiRes<MembersResponse>> => {
  const params = new URLSearchParams();
  params.set("page", page.toString());
  params.set("size", size.toString());
  params.set("date", date.toString());
  return await restApi.get<ApiRes<MembersResponse>>(
    `/member?${params.toString()}`
  );
};
