export interface ApiRes<T> {
  payload?: T | null;
  messages?: string[];
  status?: number;
}
