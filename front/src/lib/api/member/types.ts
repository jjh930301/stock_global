import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export interface IMember {
  accountId: string;
  createdAt: string;
  type: MemberTypeEnum;
}

export interface MembersResponse {
  members: IMember[];
  total: number;
}
