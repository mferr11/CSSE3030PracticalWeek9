# local-maven-repo

This is a small, project-local Maven repository containing a custom build of
Ekstazi (`org.ekstazi:ekstazi-maven-plugin` and `org.ekstazi:org.ekstazi.core`,
version `5.3.0-jdk17`).

**Why this exists:** the latest version of Ekstazi published to Maven Central
is `5.3.0`, from 2018. Ekstazi's GitHub repository has since added support for
Java 11/17/21 (see commits `7ac45e0` and `16fffb9` on
https://github.com/gliga/ekstazi), but this has never been published as a new
Maven Central release. The old `5.3.0` jar silently fails to record most test
dependencies when run on Java 17 — it will *look* like it's working, but
regression test selection will be wrong (usually under-selecting: it misses
tests it should re-run). This repo builds Ekstazi from its current GitHub
source instead, under the distinct version `5.3.0-jdk17` so it can never be
confused with (or silently overwritten by) the real `5.3.0` release in your
`~/.m2` cache.

Because it's referenced via `pluginRepositories` with a `file://` URL relative
to the project directory, this works out of the box on a fresh clone -- no
one-time build step needed, no internet access required for this specific
dependency.

**Do not delete this directory.** If you ever need to rebuild it:

```
git clone https://github.com/gliga/ekstazi
cd ekstazi
mvn org.codehaus.mojo:versions-maven-plugin:2.16.2:set -DnewVersion=5.3.0-jdk17 -DgenerateBackupPoms=false
JAVA_TOOL_OPTIONS="-Djdk.attach.allowAttachSelf=true" mvn -DskipTests install
```

then re-deploy the three needed artifacts (`org.ekstazi.parent`,
`org.ekstazi.core`, `ekstazi-maven-plugin`) from your local `~/.m2` cache into
this directory with `mvn deploy:deploy-file -Durl=file:///path/to/this/dir ...`
for each.
