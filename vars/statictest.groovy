def call(String projectKey, String sonar, String credentialsId ) {
    withSonarQubeEnv(sonar) { 
        withCredentials([string(credentialsId: credentialsId, variable: 'SONARQUBE_TOKEN')]) {
            sh """
            mvn sonar:sonar \
            -Dsonar.projectKey=${projectKey} \
            -Dsonar.login=${SONARQUBE_TOKEN} \
            -Dsonar.projectName='${projectKey}' \
            """
        }
    }
}
