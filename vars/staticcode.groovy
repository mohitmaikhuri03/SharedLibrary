
def clean() { 
    cleanWs()
}

def clone(String branch, String url, String credentialsId = null) { 
    if (credentialsId) {
        git branch: branch, url: url, credentialsId: credentialsId
    } else {
        git branch: branch, url: url
    }
}

def statictest(String projectKey, String credentialsId ) {
    withSonarQubeEnv('sonar') { 
        withCredentials([string(credentialsId: credentialsId, variable: 'SONARQUBE_TOKEN')]) {
            sh """
            mvn clean verify sonar:sonar \
            -Dsonar.projectKey=${projectKey} \
            -Dsonar.login=${SONARQUBE_TOKEN} \
            -Dsonar.projectName='${projectKey}' \
            """
        }
    }
}



def pass(String reports, String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
       
        body: 'Build Success', 
        subject: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}", 
        to: emailRecipient
    )

    slackSend(
        channel: slackChannel, 
        tokenCredentialId: slackTokenId, 
        message: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER} - Build Success"
    )
}


def fail(String reports, String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
        
        body: 'Build Failure', 
        subject: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}", 
        to: emailRecipient
    )

    slackSend(
        channel: slackChannel, 
        tokenCredentialId: slackTokenId, 
        message: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER} - Build Success"
    )
}

def call() {
    clean()
    clone()
    statictest()
    pass()
    fail()
}
