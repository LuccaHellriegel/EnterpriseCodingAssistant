const {firstLowerCase} = require("../Util");

const BUILDER = (entityType) => `${entityType}.builder().build();`;

const NEW_INSTANCE = (entityType) =>
  `var ${firstLowerCase(entityType)} = new ${entityType}();`;

module.exports = {BUILDER, NEW_INSTANCE};
