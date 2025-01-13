import Button from "@/components/button";
import Input from "@/components/input";
import { Box } from "@mui/material";
import { Dispatch, SetStateAction } from "react";
type ChangePasswordProp = {
  prevPassword: string;
  setPrevPassword: Dispatch<SetStateAction<string>>;
  password: string;
  setPassword: Dispatch<SetStateAction<string>>;
  confirmPassword: string;
  setConfirmPassword: Dispatch<SetStateAction<string>>;
  onClick: () => void;
};
export default function ChangePassword({
  prevPassword,
  setPrevPassword,
  password,
  setPassword,
  confirmPassword,
  setConfirmPassword,
  onClick,
}: ChangePasswordProp): JSX.Element {
  return (
    <Box className="flex items-center justify-center min-h-screen">
      <Box
        className="bg-gray-800 p-2 rounded-md shadow-lg"
        sx={{
          width: "70vw",
          maxWidth: {
            xs: "70vw",
            sm: "40vw",
          },
          height: "auto",
        }}
      >
        <Input
          value={prevPassword}
          label="previous pw"
          type="password"
          color="primary"
          setValue={setPrevPassword}
          variant="filled"
        />
        <Input
          value={password}
          label="change pw"
          type="password"
          setValue={setPassword}
          variant="outlined"
        />
        <Input
          value={confirmPassword}
          label="confirm pw"
          type="password"
          setValue={setConfirmPassword}
          variant="outlined"
        />
        <Button
          label="📥"
          onClick={onClick}
          sx={{
            width: "-webkit-fill-available",
          }}
        />
      </Box>
    </Box>
  );
}
