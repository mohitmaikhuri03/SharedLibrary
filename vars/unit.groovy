
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

def unittest(String reports) { 
    sh """
        mkdir -p ${reports}
        go mod tidy
        go test ./... -v > ${reports}/unit-test-report.txt
    """
}

def artifacts(String reports) { 
    archiveArtifacts artifacts: "${reports}/*.txt", fingerprint: true
}

def pass(String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
        body: 'Build Success', 
        subject: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}", 
        to: emailRecipient
    )
    slackSend(
        channel: slackChannel, 
        message: "Build Successful: JOB-Name:- ${env.JOB_NAME} Build_No.:- ${env.BUILD_NUMBER} & Build-URL:- ${env.BUILD_URL}", 
        tokenCredentialId: slackTokenId
    )
}

def fail(String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
        body: 'Build Failure', 
        subject: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}", 
        to: emailRecipient
    )
    slackSend(
        channel: slackChannel, 
        message: "Build Failure: JOB-Name:- ${env.JOB_NAME} Build_No.:- ${env.BUILD_NUMBER} & Build-URL:- ${env.BUILD_URL}", 
        tokenCredentialId: slackTokenId
    )
}
def call() {
    clean()
    clone()
    unittest()
    artifact()
    pass()
    fail()
}
