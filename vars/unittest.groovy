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
def reactunit(String Report) {
  sh """
      npm install --save-dev jest@23.6.0 babel-jest@23.6.0
      mkdir -p ${Report}
      npm test | tee ${Report}/unit.test.html
  """
}

def artifacts(String reports) { 
    archiveArtifacts artifacts: "${reports}/*.txt", fingerprint: true
}

def pass(String reports, String emailRecipient, String slackChannel, String slackTokenId) { 
    emailext(
        attachmentsPattern: "${reports}/*.html",
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
        attachmentsPattern: "${reports}/*.html",
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
    reactunit()
    artifact()
    pass()
    fail()
}
