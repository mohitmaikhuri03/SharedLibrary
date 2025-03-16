
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

def pass(String reports, String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
        attachmentsPattern: "${reports}/*.txt",
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
        attachmentsPattern: "${reports}/*.txt",
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
    unittest()
    artifact()
    pass()
    fail()
}
