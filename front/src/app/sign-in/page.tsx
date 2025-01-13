"use client";
import { useEffect, useState } from "react";
import { getUserInfo, postSendEmail, postSignIn } from "@/lib/api/auth";
import useAuthStore from "@/lib/store/auth/auth-store";
import useGlobalStore from "@/lib/store/global-store";
import { useRouter } from "next/navigation";
import SignIn from "./sign-in";

export default function Page(): JSX.Element {
  const router = useRouter();
  const { userInfo, setUserInfo } = useAuthStore();
  const [accountId, setAccountId] = useState<string>("");
  const [email, setEmail] = useState<string | number>("");
  const [accountPassword, setAccountPassword] = useState<string>("");
  const [open, setOpen] = useState<boolean>(false);
  const { setShowAlert, setRes, setOnClick, setDuration, setLabel } =
    useGlobalStore();
  const MIN_LENGTH = 8;
  const ID = 0;
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

    if (result.payload.accessToken) {
      localStorage.setItem("key", result.payload.accessToken);
      setUserInfo(result.payload);
    }

    if (result.payload.history) {
      setRes({
        messages: ["change your password"],
      });
      setOnClick(function () {
        const accountId = result?.payload?.accountId?.toString().split("@")?.[
          ID
        ];
        router.push(`/my/${accountId}`);
      });
      setLabel("move");
      setDuration(1000 * 60);
      setShowAlert(true);
    }
    await router.push("/");
  };

  const handleSubmit = () => {
    fetchSignIn();
  };
  const sendEmail = async () => {
    if (!email || email.toString().trim() !== "") {
      setOpen(false);
      setShowAlert(true);
      setRes({
        messages: ["enter email"],
      });
      return;
    }
    const result = await postSendEmail(String(email));
    setShowAlert(true);
    setRes(result);
    setEmail("");
    setOpen(false);
  };

  useEffect(() => {
    if (userInfo && userInfo?.accountId) {
      const fetchMemberInfo = async () => {
        const result = await getUserInfo();
        if (!result.payload) {
          localStorage.removeItem("key");
          setUserInfo(null);
          return;
        }
        router.push("/");
        return;
      };
      fetchMemberInfo();
      return;
    }
  }, [userInfo, setUserInfo, router]);

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
