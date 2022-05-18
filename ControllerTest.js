const {CONTEXT_BASE_PATH} = require("./APIPath");
const {parseArgsBase} = require("./Args");
const {BUILDER, NEW_INSTANCE} = require("./Class");
const {PROPERTY_NEW, PROPERTY_MOCK} = require("./Property");
const {TEST} = require("./Test");
const {firstLowerCase, addEmptySpace} = require("./Util");

const controllerName = (entityType) => entityType + "Controller";

const GIVEN_CONTROLLER = ({entityType}) =>
  `
private WebTestClientRequestSpecification givenController() {
  return given().standaloneSetup(${firstLowerCase(
    controllerName(entityType)
  )}, new RestExceptionHandler(new ObjectMapper())).contentType(ContentType.JSON);
}
`.trim();

const createValidMethod = (entityType) => `createValid${entityType}`;

const CREATE_VALID = (
  {entityType},
  bodyFunc
) => `public ${entityType} ${createValidMethod(entityType)}(){
${addEmptySpace(bodyFunc(), 2)}
}`;

const CREATE_VALID_ENTITY_BUILDER = (props) =>
  CREATE_VALID(props, () => "return " + BUILDER(props.entityType));

const CREATE_VALID_ENTITY_INSTANCE = (props, postFix = "") =>
  CREATE_VALID({entityType: props.entityType + postFix}, () =>
    [
      NEW_INSTANCE(props.entityType + postFix),
      `return ${firstLowerCase(props.entityType + postFix)};`,
    ].join("\n")
  );

const CREATE_VALID_ENTITY_DTO = (props) =>
  CREATE_VALID_ENTITY_INSTANCE(props, "Dto");

const CREATE_VALID_CREATE_DTO = (props) =>
  CREATE_VALID_ENTITY_INSTANCE(props, "CreateDto");

const CREATE_VALID_DELTA_DTO = (props) =>
  CREATE_VALID_ENTITY_INSTANCE(props, "DeltaDto");

const CONTROLLER = ({entityType}, deps) =>
  PROPERTY_NEW(controllerName(entityType), deps);

const CREATE_METHODS = (props) => [
  CREATE_VALID_ENTITY_BUILDER(props),
  "",
  CREATE_VALID_ENTITY_DTO(props),
  "",
  CREATE_VALID_CREATE_DTO(props),
  "",
  CREATE_VALID_DELTA_DTO(props),
];

const CREATE_VALID_VARIABLE = (props, postFix = "") =>
  "var " +
  firstLowerCase(props.entityType + postFix) +
  " = " +
  createValidMethod(props.entityType + postFix) +
  "();";

const entityId = (props) => firstLowerCase(props.entityType) + "Id";
const ENTITY_ID = (props) =>
  "var " + entityId(props) + " = " + '"' + entityId(props) + '"';

const GIVEN_CONTROLLER_THEN = (verb, path, status, extra = "", body) =>
  `givenController()${
    body ? `.body(${body})` : ""
  }.when().${verb}(BASE_PATH + ${path}).then().status(HttpStatus.${status})${extra};`;

const GIVEN_CONTROLLER_GET_ALL_THEN = ({entityType, entityTypePlural}) =>
  GIVEN_CONTROLLER_THEN(
    "get",
    CONTEXT_BASE_PATH({entityTypePlural}),
    "OK",
    `.extract().as(${entityType + "Dto"}[].class)`
  );

const GIVEN_CONTROLLER_CREATE_THEN = ({entityType, entityTypePlural}) =>
  GIVEN_CONTROLLER_THEN(
    "post",
    CONTEXT_BASE_PATH({entityTypePlural}),
    "CREATED",
    `.header("location", BASE_PATH + ${CONTEXT_BASE_PATH({
      entityTypePlural,
    })} + ${firstLowerCase(entityType) + "Id"})`,
    firstLowerCase(entityType) + "CreateDto"
  );

const GIVEN_CONTROLLER_GET_THEN = ({entityType, entityTypePlural}) =>
  GIVEN_CONTROLLER_THEN(
    "get",
    CONTEXT_BASE_PATH({entityTypePlural}) +
      " + " +
      firstLowerCase(entityType) +
      "Id",
    "OK",
    `.extract().as(${entityType + "Dto"}.class)`
  );

const GIVEN_CONTROLLER_PATCH_THEN = ({entityType, entityTypePlural}) =>
  GIVEN_CONTROLLER_THEN(
    "patch",
    CONTEXT_BASE_PATH({entityTypePlural}) +
      " + " +
      firstLowerCase(entityType) +
      "Id",
    "OK",
    `.extract().as(${entityType + "Dto"}.class)`,
    firstLowerCase(entityType) + "DeltaDto"
  );

const MOCKITO_WHEN = (instanceName, methodName, args = "", returnArg = "") =>
  `when(${instanceName}.${methodName}(${args})).thenReturn(${returnArg});`;

const BASE_TESTS = (props) => {
  const entityLowerCase = firstLowerCase(props.entityType);
  const serviceName = entityLowerCase + "Service";

  return [
    TEST(
      "gettingAll" + props.entityTypePlural + "ShouldReturn200",
      MOCKITO_WHEN(
        serviceName,
        "getAll" + props.entityType,
        undefined,
        "List.of()"
      ),
      undefined,
      GIVEN_CONTROLLER_GET_ALL_THEN(props)
    ),
    "",
    TEST(
      "creating" + props.entityType + "ShouldReturn201",
      [
        CREATE_VALID_VARIABLE(props, "CreateDto"),
        CREATE_VALID_VARIABLE(props),
        MOCKITO_WHEN(
          serviceName,
          "create" + props.entityType,
          "any()",
          "Mono.just(" + entityLowerCase + ")"
        ),
      ].join("\n"),
      undefined,
      GIVEN_CONTROLLER_CREATE_THEN(props)
    ),
    "",
    TEST(
      "getting" + props.entityType + "ShouldReturn200",
      [
        ENTITY_ID(props),
        CREATE_VALID_VARIABLE(props),
        MOCKITO_WHEN(
          serviceName,
          "get" + props.entityType,
          entityId(props),
          entityLowerCase
        ),
      ].join("\n"),
      undefined,
      GIVEN_CONTROLLER_GET_THEN(props)
    ),
    "",
    TEST(
      "updating" + props.entityType + "ShouldReturn200",
      [
        ENTITY_ID(props),
        CREATE_VALID_VARIABLE(props, "DeltaDto"),
        CREATE_VALID_VARIABLE(props),
        MOCKITO_WHEN(
          serviceName,
          "update" + props.entityType,
          "any(),any()",
          entityLowerCase
        ),
      ].join("\n"),
      undefined,
      GIVEN_CONTROLLER_PATCH_THEN(props)
    ),
  ];
};

const CONTROLLER_PROPERTIES = (props, deps) =>
  [
    `private final ${props.entityType}Mapper mapper = new ${props.entityType}MapperImpl();`,
    PROPERTY_MOCK(props.entityType + "Service"),
    ...deps.map((dep) => PROPERTY_MOCK(dep)),
    CONTROLLER(
      props,
      ["mapper", props.entityType + "Service", ...deps].map(firstLowerCase)
    ),
    "",
    GIVEN_CONTROLLER(props),
    "",
    ...CREATE_METHODS(props),
    "",
    ...BASE_TESTS(props),
  ].join("\n");

const args = parseArgsBase(["d"]);
const deps = args["d"];
const entityType = args["e"];
const entityTypePlural = args["ep"];
console.log(CONTROLLER_PROPERTIES({entityType, entityTypePlural}, deps));
