import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export interface IMember {
  accountId: string;
  createdAt: Date;
  type: MemberTypeEnum;
}

export interface MembersResponse {
  members: IMember[];
  total: number;
}
