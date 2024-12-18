"use client";
import { useEffect, useState } from "react";
import { getUserInfo, postSendEmail, postSignIn } from "@/lib/api/auth";
import useAuthStore from "@/lib/store/auth/authStore";
import useGlobalStore from "@/lib/store/globalStore";
import { useRouter } from "next/navigation";
import SignIn from "./sign-in";

export default function Page(): JSX.Element {
  const router = useRouter();
  const { userInfo, setUserInfo } = useAuthStore();
  const [accountId, setAccountId] = useState<string>("");
  const [email, setEmail] = useState<string | number>("");
  const [accountPassword, setAccountPassword] = useState<string>("");
  const [open, setOpen] = useState<boolean>(false);
  const { setShowAlert, setRes } = useGlobalStore();
  const MIN_LENGTH = 8;
  const fetchSignIn = async () => {
    if (accountId.length < MIN_LENGTH || accountPassword.length < MIN_LENGTH) {
      setShowAlert(true);
      setRes({
        messages: [
          `required min length 8 
          accountId or accountPassword `,
        ],
      });
      return;
    }
    const result = await postSignIn(accountId, accountPassword);
    if (!result.payload) {
      setShowAlert(true);
      setRes({
        messages: result.messages,
      });
      return;
    }

    if (result.payload.accessToken)
      localStorage.setItem("key", result.payload.accessToken);
    setUserInfo(result.payload);
    router.push("/");
  };

  const handleSubmit = () => {
    fetchSignIn();
  };
  const sendEmail = async () => {
    if (!email) {
      setShowAlert(true);
      setRes({
        messages: ["enter email"],
      });
    }
    const result = await postSendEmail(String(email));
    setShowAlert(true);
    setRes(result);
    setEmail("");
    setOpen(false);
  };

  useEffect(() => {
    if (userInfo?.accountId) {
      const fetchMemberInfo = async () => {
        const result = await getUserInfo();
        if (!result.payload) {
          localStorage.removeItem("key");
          setUserInfo(null);
        }
      };
      fetchMemberInfo();
      router.push("/");
    }
  }, [router, setUserInfo, userInfo]);

  return (
    <SignIn
      userInfo={userInfo}
      accountId={accountId}
      accountPassword={accountPassword}
      fetchSignIn={fetchSignIn}
      handleSubmit={handleSubmit}
      setAccountId={setAccountId}
      setAccountPassword={setAccountPassword}
      open={open}
      setOpen={setOpen}
      sendEmail={sendEmail}
      email={email}
      setEmail={setEmail}
    />
  );
}
