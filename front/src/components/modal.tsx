"use client";
import Modal from "@mui/material/Modal";
import Box from "@mui/material/Box";
import { Dispatch, ReactNode, SetStateAction } from "react";

interface CustomModalProp {
  open: boolean;
  setOpen: Dispatch<SetStateAction<boolean>>;
  children: ReactNode;
}

export default function CustomModal({
  open,
  setOpen,
  children,
}: CustomModalProp) {
  return (
    <Modal
      open={open}
      onClose={() => setOpen(false)}
      aria-labelledby="modal-modal-title"
      aria-describedby="modal-modal-description"
      className="flex items-center justify-center min-h-screen"
    >
      <Box className="flex items-center bg-black justify-center p-4 rounded shadow">
        {children}
      </Box>
    </Modal>
  );
}
