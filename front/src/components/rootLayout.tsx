"use client";
import Nav from "@/components/nav";
import useAuthStore from "@/lib/store/auth/authStore";

export default function DefaultLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const { userInfo } = useAuthStore();

  return (
    <>
      <Nav userInfo={userInfo} />
      {children}
    </>
  );
}
