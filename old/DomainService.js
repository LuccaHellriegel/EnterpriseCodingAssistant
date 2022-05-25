const {parseArgsBase, getEntityInfObj} = require("../Args");
const {repositoryProperty, repositoryInstanceName} = require("../Repository");
const {firstLowerCase} = require("../Util");

const domainService = ({entityType, entityTypePlural}) => {
  const repoInstance = repositoryInstanceName(entityType);

  return `@Service
@Slf4j
@AllArgsConstructor
public class ${entityType}Service {

  ${repositoryProperty(entityType)}

  public Mono<${entityType}> create${entityType}(${entityType} partial${entityType}) {
    return validate(partial${entityType})
        .flatMap(${repoInstance}::insert);
  }

  private Mono<${entityType}> validate(${entityType} ${firstLowerCase(
    entityType
  )}) {
      //TODO
    return Mono.just(${firstLowerCase(entityType)});
  
   }

  public Mono<${entityType}> get${entityType}ById(String id) {
    return ${repoInstance}.findById(id)
        .switchIfEmpty(Mono.error(new ${entityType}NotFoundException(id)));
  }

  public Flux<${entityType}> getAll${entityTypePlural}() {
    return ${repoInstance}.findAll();
  }

  public Mono<${entityType}> save${entityType}(${entityType} ${firstLowerCase(
    entityType
  )}) {
    return ${repoInstance}.save(${firstLowerCase(entityType)});
  }

}
`;
};

const args = parseArgsBase();

console.log(args);
console.log("\n\n");

console.log(domainService(getEntityInfObj(args)));
