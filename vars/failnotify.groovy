def call(String emailRecipient, String slackChannel, String slackTokenId) {
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

