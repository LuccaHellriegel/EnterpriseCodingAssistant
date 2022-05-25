const {firstLowerCase} = require("../Util");

const repositoryName = (entityType) => `${entityType}Repository`;
const repositoryInstanceName = (entityType) =>
  firstLowerCase(repositoryName(entityType));
const repositoryProperty = (entityType) =>
  `private final ${repositoryName(entityType)} ${repositoryInstanceName(
    entityType
  )};`;

module.exports = {repositoryName, repositoryProperty, repositoryInstanceName};
