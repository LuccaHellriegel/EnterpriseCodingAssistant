const {parseArgsBase, getEntityInfObj} = require("../Args");

const notFoundException = ({
  entityType,
}) => `public class ${entityType}NotFoundException extends BaseException {

    public ${entityType}NotFoundException(String id) {
      super("${entityType.toUpperCase()}_NOT_FOUND", "The ${entityType} with the id " + id + " was not found.",
          HttpStatus.NOT_FOUND);
    }
  
  }`;

const args = parseArgsBase();

console.log(args);
console.log("\n\n");

console.log(notFoundException(getEntityInfObj(args)));
