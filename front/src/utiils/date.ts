export const convertUTCLocalDate = (date: string | Date): string => {
  if (typeof date === "string") {
    const d = new Date(date);
    d.setMinutes(d.getMinutes() - d.getTimezoneOffset());
    return d.toLocaleString();
  }
  return date
    .setMinutes(date.getMinutes() - date.getTimezoneOffset())
    .toLocaleString();
};
