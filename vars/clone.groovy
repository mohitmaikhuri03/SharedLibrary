def call(String branch, String url, String credentialsId) {
    git branch: branch, url: url  , credentialsId: credentialsId
}
