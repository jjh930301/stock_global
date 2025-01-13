import { useEffect, useRef } from "react";

interface InfiniteScrollProps<T> {
  data: T[]; // 현재 로드된 데이터
  total: number; // 총 데이터 개수
  currentPage: number; // 현재 페이지
  loadMore: (nextPage: number) => void; // 추가 데이터를 불러오는 함수
  pageHandler: (page: number) => void; // 페이지 전환 핸들러
}

export default function InfiniteScroll<T>({
  data,
  total,
  currentPage,
  loadMore,
  pageHandler,
}: InfiniteScrollProps<T>): JSX.Element {
  const observerRef = useRef<HTMLDivElement | null>(null);
  const isLoadingRef = useRef(false); // 로딩 상태 관리

  useEffect(() => {
    if (!observerRef.current || data.length >= total) return;

    const observer = new IntersectionObserver(
      (entries) => {
        const [entry] = entries;
        if (
          entry.isIntersecting &&
          data.length < total &&
          !isLoadingRef.current
        ) {
          isLoadingRef.current = true;
          const nextPage = currentPage + 1;
          loadMore(nextPage);
          pageHandler(nextPage);
        }
      },
      {
        root: null,
        rootMargin: "100px",
        threshold: 1.0,
      }
    );

    const target = observerRef.current;
    if (target) observer.observe(target);

    return () => {
      if (target) observer.unobserve(target);
    };
  }, [data.length, total, currentPage, loadMore, pageHandler]);

  useEffect(() => {
    isLoadingRef.current = false; // 데이터가 갱신되면 로딩 상태 해제
  }, [data]);

  return (
    <>
      {data.length < total && (
        <div
          ref={observerRef}
          style={{ height: "20px", background: "transparent" }}
        />
      )}
    </>
  );
}
