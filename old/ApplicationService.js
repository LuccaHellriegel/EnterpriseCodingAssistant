const {parseArgsBase, getEntityInfObj} = require("../Args");
const {firstLowerCase} = require("../Util");

const applicationService = ({entityType}) => {
  const serviceName = `${firstLowerCase(entityType)}Service`;

  return `@Service
@AllArgsConstructor
@Slf4j
public class ${entityType}ApplicationService {

  private final ${entityType}Service ${serviceName};

  public Mono<${entityType}> update${entityType}(${entityType}DeltaDto delta, String ${firstLowerCase(
    entityType
  )}Id) {
    return validateDelta(delta)
        .then(${serviceName}.get${entityType}(${firstLowerCase(entityType)}Id))
        .flatMap(${firstLowerCase(
          entityType
        )} -> apply${entityType}Delta(${firstLowerCase(entityType)}, delta))
        .flatMap(${serviceName}::save${entityType});
  }

  private Mono<${entityType}DeltaDto> validateDelta(${entityType}DeltaDto delta) {
    //TODO
    return Mono.just(delta);
  }

  private Mono<${entityType}> apply${entityType}Delta(${entityType} ${firstLowerCase(
    entityType
  )}, ${entityType}DeltaDto delta) {
    //TODO
    applyDelta(${firstLowerCase(entityType)}, delta, List.of());
    return Mono.just(${firstLowerCase(entityType)});
  }
}`;
};

const args = parseArgsBase();

console.log(args);
console.log("\n\n");

console.log(applicationService(getEntityInfObj(args)));
