"use client";
import Nav from "@/components/nav";
import useAuthStore from "@/lib/store/auth/auth-store";

export default function DefaultLayout({
  children,
}: Readonly<{ children: React.ReactNode }>) {
  const { userInfo } = useAuthStore();

  return (
    <>
      <Nav userInfo={userInfo} />
      <main className={userInfo ? "pt-16" : "pt-0"}>{children}</main>{" "}
    </>
  );
}
