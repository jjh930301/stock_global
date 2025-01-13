"use client";

import Request from "./request";
import { useCallback, useEffect, useMemo, useRef, useState } from "react";
import { useParams } from "next/navigation";
import { IMember, MembersResponse } from "@/lib/api/member/types";
import { getMembers } from "@/lib/api/member";
import { ApiRes } from "@/lib/interfaces/apiRes";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";
import useGlobalStore from "@/lib/store/global-store";
import { putMemberPermission } from "@/lib/api/auth";

export default function Page(): JSX.Element {
  const params = useParams();
  // const router = useRouter();
  const { page } = params;
  const [currentPage, setCurrentPage] = useState<number>(Number(page) || 1);
  const [total, setTotal] = useState<number>(0);
  const [members, setMembers] = useState<IMember[]>([]);
  const [memberTypes, setMemberTypes] = useState<{
    [key: string]: MemberTypeEnum;
  }>({});
  const isFetching = useRef(false);
  const { setShowAlert, setRes } = useGlobalStore();

  const date = useMemo(() => new Date(), []);

  const itemPerPage = 10;

  const pageHandler = (page: number) => {
    if (page !== currentPage) {
      setCurrentPage(page);
    }
  };

  const handleTypeChange = (accountId: string, type: MemberTypeEnum) => {
    setMemberTypes((prev) => ({
      ...prev,
      [accountId]: type,
    }));
  };

  const fetchPermission = async (email: string) => {
    const response: ApiRes<boolean> = await putMemberPermission({
      email,
      type: memberTypes[email],
    });
    if (response.status === 401) return;
    setShowAlert(true);
    setRes(response);
    return;
  };

  const fetchMembers = useCallback(
    async (page: number) => {
      if (isFetching.current || isNaN(page)) return;
      isFetching.current = true;

      const response: ApiRes<MembersResponse> = await getMembers(
        page,
        itemPerPage,
        date
      );

      if (response) {
        setMembers((prevMembers) => [
          ...prevMembers,
          ...(response.payload?.members ?? []),
        ]);
        setTotal(response.payload?.total ?? 0);

        setMemberTypes((prev) => {
          const updatedTypes = { ...prev };
          (response.payload?.members ?? []).forEach((member) => {
            updatedTypes[member.accountId] = member.type ?? MemberTypeEnum.USER;
          });
          return updatedTypes;
        });
      }

      isFetching.current = false;
    },
    [itemPerPage, date]
  );

  useEffect(() => {
    fetchMembers(currentPage);
  }, [currentPage, fetchMembers]);

  return (
    <Request
      total={total}
      currentPage={currentPage}
      members={members}
      memberTypes={memberTypes}
      handleTypeChange={handleTypeChange}
      fetchPermission={fetchPermission}
      fetchMembers={fetchMembers}
      pageHandler={pageHandler}
    />
  );
}
