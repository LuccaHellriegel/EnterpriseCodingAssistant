const firstLowerCase = (str) => str[0].toLowerCase() + str.slice(1);

const addEmptySpace = (str, amount = 1) =>
  str
    .split("\n")
    .map((s) => " ".repeat(amount) + s)
    .join("\n");

module.exports = {firstLowerCase, addEmptySpace};
