"use client";
import useAuthStore from "@/lib/store/auth/auth-store";
import { useRouter } from "next/navigation";
import { useCallback, useEffect, useMemo, useState } from "react";

interface IndexCandle {
  symbol: number;
  date: string;
  open: string;
  high: string;
  low: string;
  close: string;
  volume: number;
}

export default function Home() {
  const router = useRouter();
  const { userInfo } = useAuthStore();
  const [messages, setMessages] = useState<IndexCandle[]>([]);
  const [ws, setWs] = useState<WebSocket | null>(null);
  let tries: number = useMemo(() => 0, []);
  const MAX_TRIES: number = 5;

  useEffect(() => {
    if (!userInfo && router) {
      router.push("/sign-in");
    }
  }, [router, userInfo]);

  const createWebSocket = useCallback(() => {
    if (!userInfo?.accessToken) return;

    const wsConnect = new WebSocket(
      `ws://localhost:8100/${userInfo.accessToken}`
    );

    wsConnect.onopen = (event) => {
      console.log(event);
      tries = 0;
    };

    wsConnect.onmessage = (event) => {
      const data = JSON.parse(event.data) as [IndexCandle];
      if (data[0]) {
        setMessages((prev) => [...prev, data[0]]);
      }
    };

    wsConnect.onclose = (event) => {
      console.log("WebSocket Disconnected, reconnecting...", event.reason);
      if (MAX_TRIES > tries) {
        setTimeout(() => {
          createWebSocket();
        }, 3000);
      }
    };

    wsConnect.onerror = (error) => {
      tries += 1;
      console.log("WebSocket error:", error);
      wsConnect.close();
    };

    setWs(wsConnect);
  }, [userInfo?.accessToken]);

  useEffect(() => {
    if (userInfo?.accessToken) {
      createWebSocket();
    }

    // Cleanup 함수: 컴포넌트가 언마운트되거나 userInfo가 변경될 때 WebSocket 종료
    return () => ws?.close();
  }, [userInfo?.accessToken, createWebSocket]); // userInfo?.accessToken이 변경될 때만 실행

  return (
    <>
      <div className="grid grid-rows-[20px_1fr_20px] items-center justify-items-center min-h-screen p-8 pb-20 gap-16 sm:p-20 font-[family-name:var(--font-geist-sans)]">
        <main className="flex flex-col gap-8 row-start-2 items-center sm:items-start">
          <div>
            <ul>
              {messages.map((msg, index) => (
                <div key={index}>{msg.close}</div>
              ))}
            </ul>
          </div>
        </main>
      </div>
    </>
  );
}
