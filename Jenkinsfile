pipeline {
    agent any
     
    parameters {
        choice(name: 'Branch', choices: ['', 'python-version', 'bucket-list'], description: 'Select Branch')
    }
    
    stages {
        stage('test') {
            steps {
                echo "email is working fine"
            }
        }
    }
    post {
        always{
		    script{
			    mail bcc: '', body: "Testcases is working\nBuild Status: ${currentBuild.currentResult}.\nJenkins Build URL: ${BUILD_URL}.\nJob Name: ${Job_Name}", 
                cc: '', 
                from: 'pipeline-pantry@thesmartq.com', 
                replyTo: '', 
                subject: "Build Status# ${currentBuild.currentResult}", 
                to: 'yashwanth.kp@thesmartq.com'
		    }
	    }
        // success {
        //     script {
        //         def branchName = params.Branch
                
        //         if (branchName != '') {
        //             build job: branchName
        //         } else {
        //             echo "No specific job selected to trigger"
        //         }
        //     }
        // }
    }
}