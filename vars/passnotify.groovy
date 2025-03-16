def call(String emailRecipient, String slackChannel,String slackTokenId) {
    post {
        success {
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
    }
}
