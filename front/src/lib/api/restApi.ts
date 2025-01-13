import { authStore } from "../store/auth/auth-store";

class RestApi {
  private baseUrl: string;
  constructor(baseUrl: string) {
    this.baseUrl = baseUrl;
  }

  private getHeaders() {
    const headers: HeadersInit = {
      "Content-Type": "application/json",
    };

    if (typeof window !== "undefined" && localStorage.getItem("key")) {
      headers.Authorization = `Bearer ${localStorage.getItem("key")}`;
    }
    return headers;
  }
  private async handleFetch<T>(uri: string, method: string, body?: unknown) {
    try {
      const requestInit: RequestInit = {
        headers: this.getHeaders(),
        method,
      };
      if (body) {
        requestInit.body = JSON.stringify(body);
      }
      const response = await fetch(`${uri}`, requestInit);
      if (!response.ok) {
        const errorData = await response.json();
        if (response.status === 401) {
          const { setUserInfo } = authStore();
          setUserInfo(null);
          localStorage.removeItem("key");
          location.href = "/sign-in";
        }
        return errorData;
      }
      return response.json() as Promise<T>;
      // eslint-disable-next-line @typescript-eslint/no-unused-vars
    } catch (e) {
      return {
        messages: ["try again later"],
        payload: null,
        status: 500,
      };
    }
  }

  async get<T>(
    endpoint: string,
    queryString?: Record<string, unknown>
  ): Promise<T> {
    const searchParams = new URLSearchParams();
    if (queryString && typeof queryString === "object") {
      Promise.all(
        Object.keys(queryString).map((key) => {
          const value = queryString[key];
          if (value) {
            searchParams.append(key, String(value));
          }
        })
      );
    }
    return this.handleFetch<T>(
      `${this.baseUrl}${endpoint}${
        searchParams.size === 0 ? "" : "?" + searchParams.toString()
      }`,
      "GET",
      null
    );
  }

  async put<T>(endpoint: string, data?: unknown): Promise<T> {
    return this.handleFetch<T>(`${this.baseUrl}${endpoint}`, "PUT", data);
  }

  async patch<T>(endpoint: string, data?: unknown): Promise<T> {
    return this.handleFetch<T>(`${this.baseUrl}${endpoint}`, "PATCH", data);
  }

  async post<T>(endpoint: string, data?: unknown): Promise<T> {
    return this.handleFetch<T>(`${this.baseUrl}${endpoint}`, "POST", data);
  }

  async delete<T>(endpoint: string): Promise<T> {
    return this.handleFetch<T>(`${this.baseUrl}${endpoint}`, "DELETE");
  }
}

export const restApi = new RestApi("http://localhost:8090");
