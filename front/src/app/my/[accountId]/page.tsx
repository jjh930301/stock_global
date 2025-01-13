"use client";
import { useState } from "react";
import ChangePassword from "./change-password";
import useGlobalStore from "@/lib/store/global-store";
import { patchMemberPassword } from "@/lib/api/auth";
import { useRouter } from "next/navigation";

export default function Page(): JSX.Element {
  const router = useRouter();
  const [prevPassword, setPrevPassword] = useState<string>("");
  const [password, setPassword] = useState<string>("");
  const [confirmPassword, setConfirmPassword] = useState<string>("");
  const { setRes, setShowAlert } = useGlobalStore();
  const fetchPassword = async () => {
    if (password !== confirmPassword) {
      setShowAlert(true);
      setRes({
        messages: ["not match passwords"],
      });
      setConfirmPassword("");
      setPassword("");
      return;
    }
    const response = await patchMemberPassword({
      prevPassword,
      password,
      confirmPassword,
    });
    setShowAlert(true);
    setRes(response);
    router.push("/");
  };
  return (
    <ChangePassword
      confirmPassword={confirmPassword}
      setConfirmPassword={setConfirmPassword}
      password={password}
      setPassword={setPassword}
      prevPassword={prevPassword}
      setPrevPassword={setPrevPassword}
      onClick={fetchPassword}
    />
  );
}
