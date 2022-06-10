var publishCmd = `
git tag -a -f \${nextRelease.version} \${nextRelease.version} -F CHANGELOG.md || exit 1
git push --force origin \${nextRelease.version} || exit 2
./gradlew releaseKotlinMultiplatformOnMavenCentralNexus || exit 3
./gradlew releaseJvmOnMavenCentralNexus || exit 4
./gradlew releaseJsOnMavenCentralNexus || exit 5
./gradlew releaseNativeOnMavenCentralNexus || exit 6
./gradlew releaseWasm32OnMavenCentralNexus || exit 7
./gradlew releaseKotlinMavenOnMavenCentralNexus || exit 8
./gradlew publishJsPackageToNpmjsRegistry || exit 9
./gradlew publishKotlinMavenPublicationToGithubRepository || true
`
var config = require('semantic-release-preconfigured-conventional-commits');
config.plugins.push(
    [
        "@semantic-release/exec",
        {
            "publishCmd": publishCmd,
        }
    ],
    "@semantic-release/github",
    "@semantic-release/git",
)
module.exports = config
