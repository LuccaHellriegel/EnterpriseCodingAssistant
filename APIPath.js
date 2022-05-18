const CONTEXT_BASE_PATH = ({entityTypePlural}) =>
  `"/${entityTypePlural.toLowerCase()}/"`;

const CONTEXT_BASE_ENTITY_PATH = ({entityTypePlural}, idDescriptor = "id") =>
  `"/${entityTypePlural.toLowerCase()}/{${idDescriptor}}"`;

module.exports = {CONTEXT_BASE_PATH, CONTEXT_BASE_ENTITY_PATH};
