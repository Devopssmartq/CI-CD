def call(){
    post{
	    always{
		    script{
			    mail bcc: '', body: "Testcases is working\nBuild Status: ${currentBuild.currentResult}.\nJenkins Build Number: ${Build_Number}.\nJenkins Build URL: ${BUILD_URL}.\nJob Name: ${Job_Name}", 
                cc: 'chiranjeevi.nr@thesmartq.com, srimathi.rb@thesmartq.com', 
                from: 'pipeline-pantry@thesmartq.com', 
                replyTo: '', 
                subject: "Build Status# ${currentBuild.currentResult}", 
                to: 'yashwanth.kp@thesmartq.com'
		    }
	    }
    }
}