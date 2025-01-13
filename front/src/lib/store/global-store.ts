import { create } from "zustand";
import { persist } from "zustand/middleware";
import { ApiRes } from "../interfaces/apiRes";

interface Global {
  showAlert: boolean;
  res: ApiRes<unknown>;
  duration: number;
  onClick: (() => void) | null;
  label: string;
}

interface GlobalStore extends Global {
  setShowAlert: (showAlert: boolean) => void;
  setRes: (res: ApiRes<unknown>) => void;
  setDuration: (duration: number) => void;
  setOnClick: (onClick: () => void) => void;
  setLabel: (label: string) => void;
}

const globalStore = create(
  persist<GlobalStore>(
    (set) => ({
      showAlert: false,
      setShowAlert: (showAlert: boolean) => set({ showAlert }),
      res: {
        payload: null,
        messages: [],
        status: 0,
      },
      setRes: (res: ApiRes<unknown>) => set({ res }),
      duration: 5000,
      setDuration: (duration: number) => set({ duration }),
      onClick: null,
      setOnClick: (onClick: () => void) => set({ onClick }),
      label: "ok",
      setLabel: (label: string) => set({ label }),
    }),
    { name: "globalStore" }
  )
);
export default globalStore;
