"use client";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import Input from "./input";
import { Dispatch, SetStateAction } from "react";
import Button from "./button";

interface InputModalProps {
  open: boolean;
  value: string | number;
  setValue: Dispatch<SetStateAction<string | number>>;
  setOpen: Dispatch<SetStateAction<boolean>>;
  handleSubmit: () => void;
  inputLabel?: string;
  buttonLabel?: string;
}

export default function InputModel({
  open,
  value,
  setValue,
  setOpen,
  handleSubmit,
  inputLabel = "email",
  buttonLabel = "send",
}: InputModalProps) {
  return (
    <Modal
      open={open}
      onClose={() => setOpen(false)}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      className="flex items-center justify-center min-h-screen"
    >
      <Box className="flex items-center justify-center bg-orange-100 p-4 rounded shadow">
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
            value={value}
            label={inputLabel}
            variant="outlined"
            setValue={setValue}
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
            label={buttonLabel}
            sx={{
              width: "-webkit-fill-available",
              minWidth: "15vw",
              height: "4em",
            }}
            className="mt-1"
            onClick={handleSubmit}
          />
        </Box>
      </Box>
    </Modal>
  );
}
