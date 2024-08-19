# Code Owners Java

A set of APIs and utils for resolving code ownership within a monorepo at runtime.

The key features include:
- An `@Owner` annotation for defining code ownership at a class or package level
- An `OwnerResolver` API for resolving ownership at runtime, including a reflection-based implementation
- A log4j2 JSON template field resolver to inject code owner information into log events

See module README files for more information on each module.