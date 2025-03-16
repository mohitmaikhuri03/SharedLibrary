def call(String emailRecipient, String slackChannel) {
    post {
        success {
            emailext(
                body: 'Build failure', 
                subject: "Job Name: ${env.JOB_NAME}, Build Number: ${env.BUILD_NUMBER}", 
                to: emailRecipient
            )
            slackSend(
                channel: slackChannel, 
                message: "Build Failure: JOB-Name:- ${env.JOB_NAME} Build_No.:- ${env.BUILD_NUMBER} & Build-URL:- ${env.BUILD_URL}", 
                tokenCredentialId: 'slack'
            )
        }
    }
}
