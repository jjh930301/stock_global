import { ApiRes } from "@/lib/interfaces/apiRes";
import { restApi } from "../restApi";
import { UserInfo } from "./types";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export const postSignIn = async (
  accountId: string,
  accountPassword: string
): Promise<ApiRes<UserInfo>> => {
  return await restApi.post<ApiRes<UserInfo>>("/auth/login", {
    accountId,
    accountPassword,
  });
};

export const getUserInfo = async (): Promise<ApiRes<UserInfo>> => {
  return await restApi.get<ApiRes<UserInfo>>("/auth");
};

export const postSendEmail = async (
  email: string
): Promise<ApiRes<boolean>> => {
  return await restApi.post<ApiRes<boolean>>(`/auth/${email}`);
};

export const putMemberPermission = async ({
  email,
  type,
}: {
  email: string;
  type: MemberTypeEnum;
}): Promise<ApiRes<boolean>> => {
  return await restApi.put<ApiRes<boolean>>("/auth", {
    email,
    type,
  });
};

export const patchMemberPassword = async (body: {
  password: string;
  prevPassword: string;
  confirmPassword: string;
}) => {
  return await restApi.patch<ApiRes<boolean>>("/auth", body);
};
