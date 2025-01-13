import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export interface IUserInfo {
  accountId?: string;
  type?: MemberTypeEnum;
  accessToken?: string;
  history?: boolean;
}

export type UserInfo = IUserInfo | null;
