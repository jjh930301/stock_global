import { Stack } from "@mui/material";
import Button from "../button";

type PagingProp = {
  total: number;
  itemsPerPage: number;
  currentPage: number;
  onClick: (page: number) => void;
};

export default function Paging({
  total,
  itemsPerPage,
  currentPage,
  onClick,
}: PagingProp): JSX.Element {
  // 총 페이지 수
  const maxPage = Math.ceil(total / itemsPerPage);
  // 최대 버튼 표시 개수
  const maxButtonsToShow = 10;
  const pageNumbers: number[] = [];
  let startPage: number;
  let endPage: number;

  if (maxPage <= maxButtonsToShow) {
    // 전체 페이지 수가 10 이하인 경우
    startPage = 1;
    endPage = maxPage;
  } else {
    // 전체 페이지 수가 10 이상인 경우
    const half = Math.floor(maxButtonsToShow / 2);
    if (currentPage <= half) {
      startPage = 1;
      endPage = maxButtonsToShow;
    } else if (currentPage + half > maxPage) {
      startPage = maxPage - maxButtonsToShow + 1;
      endPage = maxPage;
    } else {
      startPage = currentPage - half;
      endPage = currentPage + half - 1;
    }
  }

  for (let i = startPage; i <= endPage; i += 1) {
    pageNumbers.push(i);
  }

  return (
    <Stack direction="row" spacing={1} alignItems="center">
      {/* 첫 페이지 버튼 */}
      <Button
        label={"<<"}
        onClick={() => onClick(1)}
        disabled={currentPage === 1}
      />

      {/* 이전 페이지 버튼 */}
      <Button
        label="<"
        onClick={() => onClick(currentPage - 1)}
        disabled={currentPage === 1}
      />

      {/* 페이지 번호 버튼 */}
      {pageNumbers.map((page) => (
        <Button
          key={page}
          label={page.toString()}
          onClick={() => onClick(page)}
          variant={page === currentPage ? "contained" : "outlined"}
        />
      ))}

      {/* 다음 페이지 버튼 */}
      <Button
        label={">"}
        onClick={() => onClick(currentPage + 1)}
        disabled={currentPage === maxPage}
      />

      {/* 마지막 페이지 버튼 */}
      <Button
        label={">>"}
        onClick={() => onClick(maxPage)}
        disabled={currentPage === maxPage}
      />
    </Stack>
  );
}
