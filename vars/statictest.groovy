def call(String projectKey, String credentialsId = 'sonar') {
    withSonarQubeEnv('sonar') { 
        withCredentials([string(credentialsId: credentialsId, variable: 'SONARQUBE_TOKEN')]) {
            sh """
            mvn sonar:sonar \
            -Dsonar.projectKey=${projectKey} \
            -Dsonar.login=${SONARQUBE_TOKEN} \
            -Dsonar.projectName='${projectKey}' \
            -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml \
            -Dsonar.qualitygate.wait=true 
            """
        }
    }
}
