var publishCmd = `
./gradlew uploadAllPublicationsToMavenCentralNexus releaseStagingRepositoryOnMavenCentral || exit 3
./gradlew publishJsPackageToNpmjsRegistry || exit 4
./gradlew publishKotlinMultiplatformPublicationToGithubRepository || true
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
