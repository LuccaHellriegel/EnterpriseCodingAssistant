const {parseArgsBase} = require("../Args");
const {repositoryName} = require("../Repository");

const repository = ({entityType}) => `public interface ${repositoryName(
  entityType
)} extends ReactiveMongoRepository<${entityType}, String> {
  }`;

const args = parseArgsBase();

console.log(args);
console.log("\n\n");

console.log(repository({entityType: args["e"]}));
