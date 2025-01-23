"use client";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";
import { UserInfo } from "@/lib/api/auth/types";
import Link from "next/link";
import React from "react";

interface NavProps {
  userInfo: UserInfo;
}

export default function Nav({ userInfo }: NavProps) {
  const ID = 0;
  const accountId = userInfo?.accountId?.toString().split("@")?.[ID];
  return (
    <>
      <header
        className={`${
          userInfo
            ? "bg-gray-800 text-white p-4 fixed w-full top-0 left-0 z-50"
            : "invisible"
        }`}
      >
        <nav className="flex justify-between max-w-4xl mx-auto">
          <div className="text-lg font-bold">
            <Link href="/" className="hover:pointer-events-auto"></Link>
          </div>
          <div className="flex space-x-4 text-lg">
            {userInfo?.type === MemberTypeEnum.ADMIN ? (
              <Link href={`/admin`} className="hover:underline">
                admin
              </Link>
            ) : null}
            <Link href={`/my`} className="hover:underline">
              {accountId}
            </Link>
          </div>
        </nav>
      </header>
    </>
  );
}
