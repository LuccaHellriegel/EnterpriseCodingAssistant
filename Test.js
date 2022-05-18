const {addEmptySpace} = require("./Util");

const TEST = (testName, givenBody = "", whenBody = "", thenBody = "") =>
  `
@Test
public void ${testName}() {
  // GIVEN
${addEmptySpace(givenBody, 2)}${
    whenBody ? "\n  // WHEN\n" + addEmptySpace(whenBody, 2) + "\n" : "\n"
  }
  // THEN
${addEmptySpace(thenBody, 2)}
}`.trim();

module.exports = {TEST};
