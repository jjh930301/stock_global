import { IMember } from "@/lib/api/member/types";
import { create } from "zustand";
import { persist } from "zustand/middleware";

interface Admin {
  members: IMember[];
}

interface AdminStore extends Admin {
  setMembers: (members: IMember[]) => void;
}

const useAdminStore = create(
  persist<AdminStore>(
    (set) => ({
      members: [],
      setMembers: (members: IMember[]) => set({ members }),
    }),
    { name: "useAdminStore" }
  )
);
export const adminStore = useAdminStore.getState;
export default useAdminStore;
