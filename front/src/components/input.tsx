import { SxProps, TextField, TextFieldVariants, Theme } from "@mui/material";
import React, {
  ChangeEvent,
  ChangeEventHandler,
  Dispatch,
  HTMLInputTypeAttribute,
  KeyboardEventHandler,
  SetStateAction,
} from "react";
import Stack from "@mui/material/Stack";

type InputProps<T> = {
  value: T;
  setValue: Dispatch<SetStateAction<T>>;
  type?: HTMLInputTypeAttribute;
  sx?: SxProps<Theme>;
  label?: string;
  variant?: TextFieldVariants;
  color?: "primary" | "secondary" | "error" | "info" | "success" | "warning";
  onKeyDown?: KeyboardEventHandler;
  onChange?: ChangeEventHandler;
  helperText?: React.ReactNode;
  className?: string;
  error?: boolean;
};

export default function Input<T extends string | number>({
  value,
  setValue,
  type,
  sx = {},
  label,
  variant,
  color,
  onKeyDown,
  onChange,
  helperText,
  className,
  error,
}: InputProps<T>) {
  return (
    <Stack spacing={2} direction={"row"} className={className}>
      <TextField
        value={value}
        type={type ?? "text"}
        onChange={(event: ChangeEvent<HTMLInputElement>) => {
          setValue(event.target.value as T);
          if (onChange) {
            onChange(event);
          }
        }}
        helperText={helperText}
        color={color ?? "secondary"}
        error={error}
        variant={variant ?? "standard"}
        label={label ?? null}
        onKeyDown={onKeyDown}
        className={className ?? ""}
        sx={{
          "& .MuiInputBase-input": {
            color: "white", // 텍스트 색을 하얀색으로 설정
          },
          "& .MuiInputLabel-root": {
            color: "white", // 레이블 텍스트 색을 하얀색으로 설정
          },
          ...sx,
        }}
      />
    </Stack>
  );
}
