import { SxProps, Theme } from "@mui/material";
import MuiButton from "@mui/material/Button";
import Stack from "@mui/material/Stack";

type ButtonProp = {
  label: string;
  variant?: "text" | "contained" | "outlined";
  sx?: SxProps<Theme>;
  onClick: () => void;
  className?: string;
  color?: "primary" | "secondary" | "error" | "info" | "success" | "warning";
};

export default function Button({
  label,
  variant,
  sx = {},
  onClick,
  className,
  color,
}: ButtonProp) {
  return (
    <Stack spacing={2} direction={"row"} className={className ?? "m-1"}>
      <MuiButton
        variant={variant ?? "contained"}
        onClick={onClick}
        color={color ?? "secondary"}
        sx={sx ?? null}
      >
        {label}
      </MuiButton>
    </Stack>
  );
}
