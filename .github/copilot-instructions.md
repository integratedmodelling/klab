# GitHub Copilot Instructions for k.LAB Repository

## File Link Provision Guidelines

When providing file references or links in responses:

1. **Do not guess the file path** - If you're uncertain about the exact location of a file, do not provide a guessed or assumed path.

2. **Provide information separately** - Instead of guessing, provide:
   - The file name (e.g., `Jenkinsfile`, `pom.xml`)
   - The repository structure or directory context separately

3. **Use searchable syntax** - When referencing files, use the `repo:user/repo` syntax to enable easy searching:
   - Example: `repo:integratedmodelling/klab filename.java`
   - This allows users to search for the file themselves without following potentially incorrect paths

## Example

❌ **Incorrect**: "You can find this in `/some/assumed/path/to/file.java`"

✅ **Correct**: "You can find this file named `file.java` in the repository. Use `repo:integratedmodelling/klab file.java` to search for it."

✅ **Also Correct**: "The file `file.java` is located in the klab.engine module. The repository structure follows a Maven multi-module layout."

## Rationale

This guideline ensures that:
- Users are not misled by incorrect or outdated path information
- Users can reliably locate files using GitHub's search functionality
- The repository structure remains flexible for reorganization without breaking documentation
