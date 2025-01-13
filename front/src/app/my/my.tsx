"use client";

import MenuItem from "@/components/menu-item";
import { UserInfo } from "@/lib/api/auth/types";
import { Box } from "@mui/material";

type MyProp = {
  userInfo: UserInfo;
  onLogout: () => void;
};

export default function My({ userInfo, onLogout }: MyProp): JSX.Element {
  const ID = 0;
  return (
    <Box className="flex items-center justify-center min-h-screen">
      <Box
        className="p-4 rounded-md shadow-lg"
        sx={{
          width: "790vw",
          maxWidth: {
            xs: "90vw",
            sm: "40vw",
          },
          height: "auto",
        }}
      >
        <Box className="flex flex-col space-y-4">
          <MenuItem
            href={`/my/${userInfo?.accountId?.split("@")[ID]}`}
            emoji="👤"
            value="account management"
          />
          <MenuItem
            href="/"
            emoji="⏻"
            value="logout"
            onClick={() => {
              onLogout();
            }}
          />
        </Box>
      </Box>
    </Box>
  );
}
