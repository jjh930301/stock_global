"use client";
import { UserInfo } from "@/lib/api/auth/types";
interface NavProps {
  userInfo: UserInfo;
}

export default function Nav({ userInfo }: NavProps) {
  return (
    <header className={userInfo ? "bg-gray-800 text-white p-4" : "invisible"}>
      <nav className="flex justify-between max-w-4xl mx-auto">
        <div className="text-lg font-bold">
          <a href="/" className="hover:pointer-events-auto">
            Needsss
          </a>
        </div>
        <div className="flex space-x-4 text-lg">
          <a className="hover:underline"></a>
          <a href={`/my/${userInfo?.accountId}`} className="hover:underline">
            {userInfo?.accountId}
          </a>
        </div>
      </nav>
    </header>
  );
}
