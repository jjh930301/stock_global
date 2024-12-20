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
        console.log(await response.json());
        if (response.status === 401) {
          localStorage.removeItem("key");
          location.href = "/sign-in";
        }
        const errorData = await response.json(); // 서버에서 반환된 에러 메시지 읽기
        // sign-in을 제외한 나머지는 sign-in으로 보내줌
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
    // return this.handleResponse<T>(response);
  }

  async put<T>(endpoint: string, data?: unknown): Promise<T> {
    return this.handleFetch<T>(
      `${this.baseUrl}${endpoint}`,
      "PUT",
      data ?? JSON.stringify(data)
    );
  }

  async post<T>(endpoint: string, data?: unknown): Promise<T> {
    return this.handleFetch<T>(
      `${this.baseUrl}${endpoint}`,
      "POST",
      data ?? JSON.stringify(data)
    );
  }

  async delete<T>(endpoint: string): Promise<T> {
    return this.handleFetch<T>(`${this.baseUrl}${endpoint}`, "DELETE");
  }
}

export const restApi = new RestApi("http://localhost:8090");
