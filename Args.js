const {firstLowerCase, firstUpperCase} = require("./Util");

const parseArgsBase = (splitables = []) => {
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

const getEntityType = (args) => args["e"];
const getEntityTypePlural = (args) => args["ep"] ?? args["e"] + "s";

const getEntityInfObj = (args) => ({
  entityType: getEntityType(args),
  entityTypePlural: getEntityTypePlural(args),
});

const replacementArgs = (args) => ({
  $project$: args["pro"],
  $company$: args["com"],
  $context$: args["con"],
  $ENTITY$: getEntityType(args).toUpperCase(),
  $Entity$: getEntityType(args),
  $entity$: firstLowerCase(getEntityType(args)),
  $EntityPlural$: getEntityTypePlural(args),
  $entityPlural$: firstLowerCase(getEntityTypePlural(args)),
  $api$: firstUpperCase(getEntityTypePlural(args).toLowerCase()) + "Api",
  $apicontext$: getEntityType(args).toLowerCase(),
});

module.exports = {
  parseArgsBase,
  getEntityType,
  getEntityTypePlural,
  getEntityInfObj,
  replacementArgs,
};
