import type { NextConfig } from "next";

const nextConfig: NextConfig = {
  async rewrites() {
    return [
      {
        source: "/api/:path*", // Next.js에서 프록시할 경로
        destination: "http://localhost:8090/:path*", // Spring Boot 백엔드 주소
      },
    ];
  },
};

export default nextConfig;
