const fs = require("fs");
const {replacementArgs, parseArgsBase} = require("./Args");

function escapeRegExp(string) {
  return string.replace(/[.*+?^${}()|[\]\\]/g, "\\$&"); // $& means the whole matched string
}

function replaceAll(str, find, replace) {
  return str.replace(new RegExp(escapeRegExp(find), "g"), replace);
}

const replace = (args, str) => {
  return Object.entries(replacementArgs(args)).reduce(
    (p, c) => replaceAll(p, c[0], c[1]),
    str
  );
};

const readTemplate = (tmpl) =>
  fs.readFileSync(`./templates/${tmpl}.java`, "utf8");

const renderTemplate = () => {
  const args = parseArgsBase();
  const tmpl = args["t"];
  const template = readTemplate(tmpl);
  return replace(args, template);
};

module.exports = {replace, renderTemplate};
