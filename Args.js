const parseArgsBase = (splitables) => {
  const args = process.argv
    .slice(2)
    .map((arg) => arg.split("="))
    .reduce((prev, entry) => {
      prev[entry[0]] = entry[1];
      return prev;
    }, {});
  splitables.forEach((splitable) => {
    if (args[splitable]) {
      args[splitable] = args[splitable].split(",");
    }
  });
  return args;
};

module.exports = {parseArgsBase};
