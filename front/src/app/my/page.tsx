"use client";
import My from "./my";
import useAuthStore from "@/lib/store/auth/auth-store";

export default function Page(): JSX.Element {
  const { userInfo, setUserInfo } = useAuthStore();
  const onLogout = () => {
    setUserInfo(null);
    localStorage.removeItem("key");
    return;
  };
  return <My userInfo={userInfo} onLogout={onLogout} />;
}
