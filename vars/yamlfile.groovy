def call(String repoUrl) {
    //def pipelineParams= [:]
    //body.resolveStrategy = Closure.DELEGATE_FIRST
    //body.delegate = pipelineParams
    //body()

    //pipeline {
        //agent {
            //kubernetes {
                //label 'jenkins-private-cluster'
                //defaultContainer 'jnlp'
                //yaml '''
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
                //'''
            //}
        //}
    //}
}    