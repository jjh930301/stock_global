import { create } from "zustand";
import { UserInfo } from "@/lib/api/auth/types";
import { persist } from "zustand/middleware";

interface Auth {
  userInfo: UserInfo | null;
}

interface AuthStore extends Auth {
  setUserInfo: (info: UserInfo) => void;
}

const useAuthStore = create(
  persist<AuthStore>(
    (set) => ({
      userInfo: null,
      setUserInfo: (userInfo: UserInfo) => set({ userInfo }),
    }),
    { name: "useAuthStore" }
  )
);

export default useAuthStore;
