"use client";
import { UserInfo } from "@/lib/api/auth/types";
import Button from "@/components/button";
import Input from "@/components/input";
import { Box } from "@mui/material";
import { Dispatch, SetStateAction } from "react";
import SendAccountModal from "@/components/modal";

type SignInProp = {
  userInfo: UserInfo;
  accountId: string;
  setAccountId: Dispatch<SetStateAction<string>>;
  accountPassword: string;
  setAccountPassword: Dispatch<SetStateAction<string>>;
  fetchSignIn: () => void;
  handleSubmit: () => void;
  sendEmail: () => void;
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  email: string | number;
  setEmail: Dispatch<SetStateAction<string | number>>;
};

export default function SignIn({
  userInfo,
  accountId,
  accountPassword,
  setAccountId,
  setAccountPassword,
  fetchSignIn,
  handleSubmit,
  sendEmail,
  open,
  setOpen,
  email,
  setEmail,
}: SignInProp) {
  return (
    <>
      {userInfo?.accountId ? (
        <></>
      ) : (
        <Box className="flex items-center justify-center min-h-screen">
          <Box
            className="bg-gray-800 p-2 rounded-md shadow-lg"
            sx={{
              width: "30vw",
              minWidth: {
                xs: "50vw",
                sm: "30vw",
              },
              height: "auto",
            }}
          >
            <Input
              value={accountId}
              label="ID"
              variant="outlined"
              setValue={setAccountId}
              className="mb-1"
              sx={{
                width: "-webkit-fill-available",
              }}
            />
            <Input
              value={accountPassword}
              label="Password"
              type="password"
              variant="outlined"
              className="mt-1"
              setValue={setAccountPassword}
              sx={{
                width: "-webkit-fill-available",
              }}
              onKeyDown={(e) => {
                if (e.key === "Enter") {
                  fetchSignIn();
                }
              }}
            />
          </Box>
          <Box
            sx={{
              height: "-webkit-fill-available",
              paddingLeft: "5px",
            }}
          >
            <Button
              label="Login"
              sx={{
                width: "-webkit-fill-available",
                minWidth: "15vw",
                height: "4em",
              }}
              className="mb-1"
              onClick={handleSubmit}
            />
            <Button
              label="Sign up"
              sx={{
                width: "-webkit-fill-available",
                minWidth: "15vw",
                height: "4em",
              }}
              className="mt-1"
              onClick={() => setOpen(true)}
            />
          </Box>
        </Box>
      )}
      <SendAccountModal open={open} setOpen={setOpen}>
        <Box
          className="bg-black p-2 rounded-md shadow-lg h-1/2"
          sx={{
            width: "30vw",
            minWidth: {
              xs: "50vw",
              sm: "30vw",
            },
            height: "auto",
          }}
        >
          <Input
            value={email}
            label="email"
            variant="outlined"
            setValue={setEmail}
            className="mb-1"
            sx={{
              width: "-webkit-fill-available",
            }}
          />
        </Box>
        <Box
          sx={{
            height: "-webkit-fill-available",
            paddingLeft: "5px",
          }}
        >
          <Button
            label="📥"
            sx={{
              width: "-webkit-fill-available",
              minWidth: "15vw",
              height: "4em",
            }}
            className="mt-1"
            onClick={sendEmail}
          />
        </Box>
      </SendAccountModal>
    </>
  );
}
