import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export interface IUserInfo {
  accountId?: string;
  type?: MemberTypeEnum;
  accessToken?: string;
}

export type UserInfo = IUserInfo | null;
