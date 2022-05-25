const {firstLowerCase} = require("../Util");

const PROPERTY_MOCK = (className) =>
  `private final ${className} ${firstLowerCase(
    className
  )} = mock(${className}.class);`;

const PROPERTY_NEW = (className, deps = []) =>
  `private final ${className} ${firstLowerCase(
    className
  )} = new ${className}(${deps.join(", ")}));`;

module.exports = {PROPERTY_MOCK, PROPERTY_NEW};
