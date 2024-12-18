"use client";
import { useEffect } from "react";
import Button from "./button";
import useGlobalStore from "@/lib/store/globalStore";

export default function Alert() {
  const { res, showAlert, setShowAlert, duration } = useGlobalStore();
  useEffect(() => {
    if (showAlert && duration > 0) {
      const timer = setTimeout(() => {
        setShowAlert(false);
      }, duration);

      return () => clearTimeout(timer);
    }
  }, [showAlert, duration, setShowAlert]);

  if (!showAlert) return null;

  return (
    <>
      {showAlert && (
        <div className="fixed inset-0 flex items-center justify-center bg-black bg-opacity-50 z-50">
          <div
            className={`p-6 rounded shadow text-black font-medium bg-white max-w-md w-full`}
          >
            <ul className="mb-4 space-y-2">
              {res.messages &&
                res.messages.length &&
                res.messages.map((m: string, index: number) => (
                  <li key={index}>{m}</li>
                ))}
            </ul>
            <div className="flex justify-end">
              <Button
                onClick={() => setShowAlert(false)}
                label="OK"
                className="ml-4 bg-white text-black rounded px-4 py-2"
              />
            </div>
          </div>
        </div>
      )}
    </>
  );
}
