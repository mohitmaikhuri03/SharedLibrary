def call(String reports) {
    archiveArtifacts artifacts: "${reports}/*.txt", fingerprint: true
}
