"use client";

import Button from "@/components/button";
import Paging from "@/components/paging/paging";
import { MemberTypeEnum } from "@/enum/memberTypeEnum";
import { IMember } from "@/lib/api/member/types";
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
} from "@mui/material";

type RequestProp = {
  total: number;
  itemsPerPage: number;
  currentPage: number;
  onClick: (page: number) => void;
  members: IMember[];
  memberTypes: { [key: string]: string };
  handleTypeChange: (accountId: string, type: MemberTypeEnum) => void;
  fetchApprove: () => void;
};

export default function Request({
  total,
  itemsPerPage,
  currentPage,
  onClick,
  members,
  memberTypes,
  handleTypeChange,
  fetchApprove,
}: RequestProp): JSX.Element {
  return (
    <Box className="flex flex-col items-center justify-center min-h-screen p-4 bg-black">
      <TableContainer
        component={Paper}
        style={{
          maxWidth: 800,
          backgroundColor: "black",
          border: "1px solid #9c27b0",
        }}
      >
        <Table>
          <TableHead>
            <TableRow>
              <TableCell
                align="center"
                style={{
                  fontWeight: "bold",
                  color: "white",
                  border: "1px solid #9c27b0",
                }}
              >
                계정
              </TableCell>
              <TableCell
                align="center"
                style={{
                  fontWeight: "bold",
                  color: "white",
                  border: "1px solid #9c27b0",
                }}
              >
                요청일시
              </TableCell>
              <TableCell
                align="center"
                style={{
                  fontWeight: "bold",
                  color: "white",
                  border: "1px solid #9c27b0",
                }}
              >
                ADMIN / USER
              </TableCell>
              <TableCell
                align="center"
                style={{
                  fontWeight: "bold",
                  color: "white",
                  border: "1px solid #9c27b0",
                }}
              ></TableCell>
            </TableRow>
          </TableHead>
          <TableBody>
            {members.map((member) => (
              <TableRow
                key={member.accountId}
                sx={{
                  height: "30px",
                }}
              >
                <TableCell
                  align="center"
                  style={{
                    color: "white",
                    border: "1px solid #9c27b0",
                  }}
                >
                  {member.accountId}
                </TableCell>
                <TableCell
                  align="center"
                  style={{
                    color: "white",
                    border: "1px solid #9c27b0",
                  }}
                >
                  {member.createdAt}
                </TableCell>
                <TableCell
                  align="center"
                  style={{
                    color: "white",
                    border: "1px solid #9c27b0",
                  }}
                >
                  <Select
                    value={memberTypes[member.accountId] ?? MemberTypeEnum.USER}
                    onChange={(e) =>
                      handleTypeChange(
                        member.accountId,
                        e.target.value as MemberTypeEnum
                      )
                    }
                    style={{
                      minWidth: 120,
                      backgroundColor: "#9c27b0",
                      color: "black",
                    }}
                  >
                    <MenuItem value="ADMIN">ADMIN</MenuItem>
                    <MenuItem value="USER">USER</MenuItem>
                  </Select>
                </TableCell>
                <TableCell
                  align="center"
                  style={{
                    color: "white",
                    border: "1px solid #9c27b0",
                  }}
                >
                  <Button
                    label={"Permission"}
                    variant="contained"
                    onClick={() => fetchApprove()}
                    sx={{
                      backgroundColor: "#9c27b0",
                      width: "-webkit-fill-available",
                      color: "black",
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
      <Box mt={2}>
        <Paging
          total={total}
          itemsPerPage={itemsPerPage}
          currentPage={currentPage}
          onClick={onClick}
        />
      </Box>
    </Box>
  );
}
