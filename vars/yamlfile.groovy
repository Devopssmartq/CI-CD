def generatePodYaml() {
    return """
		pipeline {
    		agent {
        		kubernetes {
            		//label 'jenkins-private-cluster-0'
            		defaultContainer 'jnlp'
            		yaml '''
                		apiVersion: v1
                		kind: Pod
                		spec:
                		containers:
                		- name: gcloud
                    	  image: google/cloud-sdk:latest
                    	  command:
                    	  - sleep
                          args:
                    	  - 99d
                    	  tty: true
                          - name: net
                    	  image: mcr.microsoft.com/dotnet/sdk:latest
                    	  command:
                          - sleep
                          args:
                          - 99d
                          tty: true
                    '''
               	}
    		}
		}    
	"""
}
