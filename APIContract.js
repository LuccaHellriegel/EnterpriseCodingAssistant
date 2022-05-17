const CONTEXT_BASE_PATH = ({entityTypePlural}) =>
  `"/${entityTypePlural.toLowerCase()}/":`;

const GET_ALL = ({entityType, entityTypePlural}) =>
  `
get:
  operationId: getAll${entityType}
  responses:  
    200:  
      description: All ${entityTypePlural} that were found, might be an empty array  
      content:  
        application/json:  
          schema:  
            type: array  
            items:  
              $ref: "#/components/schemas/${entityType}"`.trim();

const POST_ENTITY = ({entityType}, requestBodySchemaPostfix) =>
  `
post:  
  operationId: create${entityType}
  requestBody:  
    required: true  
    content:  
      application/json:  
        schema:  
          $ref: "#/components/schemas/${entityType}${requestBodySchemaPostfix}"  
  responses:  
    200:  
      description: ${entityType} was successfully created  
      content:  
        application/json:  
          schema:  
            $ref: "#/components/schemas/${entityType}"  
    400:  
      description: Invalid ${entityType} create-request  
      content:  
        application/json:  
          schema:  
            $ref: "commons.yaml#/components/schemas/RequestError"`.trim();

const POST_ENTITY_CREATE = ({entityType}) =>
  POST_ENTITY({entityType}, "Create");

const CONTEXT_ENTITY_PATH = ({entityTypePlural}, idDescriptor = "id") =>
  `
"/${entityTypePlural.toLowerCase()}/{${idDescriptor}}":
  parameters:
  - name: ${idDescriptor}
    in: path
    required: true
    schema:
      type: string`.trim();

const GET_ENTITY = ({entityType}) =>
  `
get:
  operationId: get${entityType}
  responses:
    200:
      description: ${entityType} was found
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/${entityType}"
    404:
      description: No ${entityType} with given id
      content:
        application/json:
          schema:
            $ref: "commons.yaml#/components/schemas/RequestError"`.trim();

const PATCH_ENTITY = ({entityType}, patchSchemaPostfix) =>
  `
patch:
  operationId: update${entityType}
  requestBody:
    required: true
    content:
      application/json:
        schema:
          $ref: "#/components/schemas/${entityType}${patchSchemaPostfix}"
  responses:
    200:
      description: ${entityType} was successfully updated
      content:
        application/json:
          schema:
            $ref: "#/components/schemas/${entityType}"
    404:
      description: No ${entityType} with given id
      content:
        application/json:
          schema:
            $ref: "commons.yaml#/components/schemas/RequestError"
    400:
      description: invalid ${entityType} update-request
      content:
        application/json:
          schema:
            $ref: "commons.yaml#/components/schemas/RequestError"
`.trim();

const PATCH_ENTITY_DELTA = ({entityType}) =>
  PATCH_ENTITY({entityType}, "Delta");

const DELETE_ENTITY = ({entityType}, deletion400Desc = "can not be deleted") =>
  `
delete:
  operationId: delete${entityType}
  responses:
    204:
      description: ${entityType} was deleted
    400:
      description: ${entityType} ${deletion400Desc}
      content:
        application/json:
          schema:
            $ref: "commons.yaml#/components/schemas/RequestError"    
    404:
      description: No ${entityType} with the given ID
      content:
        application/json:
          schema:
            $ref: "commons.yaml#/components/schemas/RequestError"`.trim();

const addEmptySpace = (str, amount = 1) =>
  str
    .split("\n")
    .map((s) => " ".repeat(amount) + s)
    .join("\n");

const addTwoEmptySpace = (str) => addEmptySpace(str, 2);

const cominbePathWithVerbs = (path, verbs) =>
  path.split("\nparameters:") + "\n" + verbs.map(addTwoEmptySpace).join("\n");

//p=e e=Component ep=Components id=componentId v=g,ga,d,po,pa
//p=b

const args = process.argv
  .slice(2)
  .map((arg) => arg.split("="))
  .reduce((prev, entry) => {
    prev[entry[0]] = entry[1];
    return prev;
  }, {});

if (args["v"]) {
  args["v"] = args["v"].split(",");
}
console.log(args);
console.log("\n\n");

const pathFunctions = {e: CONTEXT_ENTITY_PATH, b: CONTEXT_BASE_PATH};
let path;
if (args["p"]) {
  path = pathFunctions[args["p"]]({entityTypePlural: args["ep"]}, args["id"]);
}

const verbFunctions = {
  g: GET_ENTITY,
  ga: GET_ALL,
  d: DELETE_ENTITY,
  po: POST_ENTITY_CREATE,
  pa: PATCH_ENTITY_DELTA,
};
let verbs;
if (args["v"]) {
  verbs = args["v"].map((verb) =>
    verbFunctions[verb]({entityType: args["e"], entityTypePlural: args["ep"]})
  );
}

if (path && verbs && verbs.length > 0) {
  console.log(cominbePathWithVerbs(path, verbs));
} else if (path) {
  console.log(path);
} else if (verbs && verbs.length > 0) {
  console.log(verbs.join("\n"));
}
