"use client";

import Button from "@/components/button";
import InfiniteScroll from "@/components/paging/infinite-scroll";
// import Paging from "@/components/paging/paging";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";
import { IMember } from "@/lib/api/member/types";
import { convertUTCLocalDate } from "@/utiils/date";
import {
  Box,
  Table,
  TableBody,
  TableCell,
  TableContainer,
  TableHead,
  TableRow,
  Paper,
  Select,
  MenuItem,
  useMediaQuery,
} from "@mui/material";
import { CSSProperties } from "react";

type RequestProp = {
  total: number;
  currentPage: number;
  members: IMember[];
  memberTypes: { [key: string]: string };
  fetchMembers: (page: number) => void;
  handleTypeChange: (accountId: string, type: MemberTypeEnum) => void;
  fetchPermission: (accountId: string) => void;
  pageHandler: (page: number) => void; // 페이지 전환 핸들러
};

export default function Request({
  total,
  currentPage,
  members,
  memberTypes,
  fetchMembers,
  handleTypeChange,
  fetchPermission,
  pageHandler,
}: RequestProp): JSX.Element {
  const isMobile = useMediaQuery("(max-width: 600px)"); // 모바일 여부 확인

  const tableCellCss: CSSProperties = {
    color: "white",
    border: "1px solid black",
  };

  return (
    <Box
      className="flex flex-col items-center justify-center min-h-screen p-4 bg-black"
      style={{ overflowX: "auto" }} // 스크롤 가능
    >
      <TableContainer
        component={Paper}
        style={{
          maxWidth: isMobile ? "100%" : 800, // 모바일에서는 전체 화면 사용
          backgroundColor: "black",
          border: "1px solid black",
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell
                align="center"
                style={{
                  ...tableCellCss,
                  fontSize: isMobile ? 12 : 14, // 모바일에서는 폰트 크기 조정
                }}
              >
                ACCOUNT
              </TableCell>
              <TableCell
                align="center"
                style={{
                  ...tableCellCss,
                  fontSize: isMobile ? 12 : 14,
                }}
              >
                REQUEST DATE
              </TableCell>
              <TableCell
                align="center"
                style={{
                  ...tableCellCss,
                  fontSize: isMobile ? 12 : 14,
                }}
              >
                ROLE
              </TableCell>
              <TableCell
                align="center"
                style={{
                  ...tableCellCss,
                }}
              ></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {members.map((member) => (
              <TableRow
                key={member.accountId}
                sx={{
                  height: isMobile ? "20px" : "30px", // 모바일에서는 셀 높이 줄이기
                }}
              >
                <TableCell align="center" style={tableCellCss}>
                  {member.accountId}
                </TableCell>
                <TableCell align="center" style={tableCellCss}>
                  {convertUTCLocalDate(member.createdAt)}
                </TableCell>
                <TableCell align="center" style={tableCellCss}>
                  <Select
                    value={memberTypes[member.accountId] ?? MemberTypeEnum.USER}
                    onChange={(e) =>
                      handleTypeChange(
                        member.accountId,
                        e.target.value as MemberTypeEnum
                      )
                    }
                    style={{
                      minWidth: isMobile ? 90 : 120, // 모바일에서 드롭다운 크기 조정
                      backgroundColor: "#9c27b0",
                      color: "black",
                    }}
                  >
                    <MenuItem value="ADMIN">ADMIN</MenuItem>
                    <MenuItem value="USER">USER</MenuItem>
                  </Select>
                </TableCell>
                <TableCell align="center" style={tableCellCss}>
                  <Button
                    label={"Permission"}
                    variant="contained"
                    onClick={() => fetchPermission(member.accountId)}
                    sx={{
                      backgroundColor: "#9c27b0",
                      width: isMobile ? "80%" : "-webkit-fill-available",
                      color: "black",
                      fontSize: isMobile ? "0.8rem" : "1rem", // 모바일에서 버튼 폰트 크기 조정
                      "&:hover": {
                        backgroundColor: "#d4d4d4",
                      },
                    }}
                  />
                </TableCell>
              </TableRow>
            ))}
          </TableBody>
        </Table>
      </TableContainer>
      <InfiniteScroll<IMember>
        data={members}
        total={total}
        currentPage={currentPage}
        pageHandler={pageHandler}
        loadMore={(nextPage) => {
          fetchMembers(nextPage);
        }}
      />
    </Box>
  );
}
