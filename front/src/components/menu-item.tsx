import Link from "next/link";
import { MouseEventHandler } from "react";

type MenuItemProp = {
  href?: string;
  emoji: string;
  value: string;
  onClick?: MouseEventHandler<HTMLAnchorElement>;
};
export default function MenuItem({
  href = "",
  emoji,
  value,
  onClick,
}: MenuItemProp): JSX.Element {
  return (
    <Link
      href={href}
      className="flex items-center space-x-2 p-3 border rounded-md shadow-sm hover:bg-gray-500"
      onClick={onClick}
    >
      <span className="text-xl">{emoji}</span>
      <span className="font-medium">{value}</span>
    </Link>
  );
}
