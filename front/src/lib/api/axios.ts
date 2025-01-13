import axios from "axios";

const API_URL = "http://localhost:9090/api/v1";
const restApi = axios.create({
  baseURL: API_URL,
  withCredentials: true,
  headers: { "Content-Type": "application/json" },
});

restApi.interceptors.request.use(
  (config) => {
    const accessToken = localStorage.getItem("key");
    if (accessToken) {
      config.headers["Authorization"] = `Bearer ${accessToken}`;
    }
    config.withCredentials = true;
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

export { restApi };
