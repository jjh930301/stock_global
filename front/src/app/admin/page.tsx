"use client";
import Request from "./request";
import { useEffect, useState } from "react";
import { useParams, useRouter } from "next/navigation";
import { IMember, MembersResponse } from "@/lib/api/member/types";
import { getMembers } from "@/lib/api/member";
import { ApiRes } from "@/lib/interfaces/apiRes";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";

export default function Page(): JSX.Element {
  const params = useParams();
  const router = useRouter();
  const { page } = params;
  const [currentPage, setCurrentPage] = useState<number>(Number(page) || 1);
  const [total, setTotal] = useState<number>(0);
  const [members, setMembers] = useState<IMember[]>([]);
  const [memberTypes, setMemberTypes] = useState<{ [key: string]: string }>(
    members.reduce((acc, member) => {
      acc[member.accountId] = member.type ?? "USER";
      return acc;
    }, {} as { [key: string]: string })
  );

  const itemPerPage = 10;

  const pageHandler = (page: number) => {
    if (page !== currentPage) {
      setCurrentPage(page);
      const searchParams = new URLSearchParams();
      searchParams.append("page", String(page));
      if (!isNaN(page)) router.push(`?${searchParams.toString()}`);
    }
  };

  const handleTypeChange = (accountId: string, type: MemberTypeEnum) => {
    setMemberTypes((prev) => ({
      ...prev,
      [accountId]: type,
    }));
  };

  const fetchApprove = async () => {};

  const fetchMembers = async (page: number) => {
    if (!isNaN(page)) {
      const response: ApiRes<MembersResponse> = await getMembers(
        page,
        itemPerPage
      );
      if (response) {
        setMembers(response.payload?.members ?? []);
        setTotal(response.payload?.total ?? 0);
      }
    }
  };

  useEffect(() => {
    fetchMembers(currentPage);
  }, [currentPage]);

  useEffect(() => {
    setCurrentPage(Number(page));
  }, [page]);
  return (
    <Request
      total={total}
      currentPage={currentPage}
      itemsPerPage={itemPerPage}
      onClick={pageHandler}
      members={members}
      memberTypes={memberTypes}
      handleTypeChange={handleTypeChange}
      fetchApprove={fetchApprove}
    />
  );
}
