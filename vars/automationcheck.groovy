def call() {
    pipeline {
    agent {
    kubernetes {
      yaml '''
        apiVersion: v1
        kind: Pod
        metadata:
          labels:
            some-label: some-exp
        spec:
          containers:
          - name: katalonstudio
            image: katalonstudio/katalon
            command:
            - sleep
            args:
            - 99d
            tty: true
          - name: gcloud
            image: google/cloud-sdk:latest
            command:
            - sleep
            args:
            - 99d
            tty: true    
        '''
      retries 2
    }
  }
    environment {    
    APP_ENGINE_PROJECT_ID = 'sqinternational-cicd'
    ENVIRONMENT='KATALON'
    }
    stages {
        stage('pull') { 
            steps { 
                git branch: 'Jayaram-B',
                credentialsId: 'abhigit',
                url: 'https://github.com/Bottle-Lab-Technologies-PVT-LTD/Automation-PWA.git'
                sh 'ls -lrt'
                dir ('Test Suites'){
                    sh 'ls'
                }
            }                       
        }
        stage('Run katalon') {
            steps {
                container('katalonstudio') {
                    
                    sh 'pwd'
                    sh 'cd /home/jenkins/agent/workspace/'
                    sh 'ls'
                    sh 'katalonc -noSplash -runMode=console -projectPath="/home/jenkins/agent/workspace/Katalona-test/My First Web UI Project.prj" -retry=0 -testSuitePath="Test Suites/simpleloginsuite" -browserType="Chrome (headless)" -executionProfile="default" -apiKey="657a0519-3dae-4354-a9ce-55de5167d269" -orgID=41312 -licenseRelease=true --config -proxy.auth.option=NO_PROXY -proxy.system.option=NO_PROXY -proxy.system.applyToDesiredCapabilities=true -webui.autoUpdateDrivers=true'
                    sh 'ls'
                    dir ('Reports'){
                        sh 'ls'
                        sh 'pwd'
                    }
                }
            }
        }
        // stage('Upload to GCS') {
        //     steps {
        //         container('gcloud') {
        //             sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'
        //             dir('/home/jenkins/agent/workspace/Katalona-test/Reports') {
        //                 sh 'ls'
        //                 sh 'gcloud storage cp /home/jenkins/agent/workspace/Katalona-test/Reports/* gs://sqinternational-cicd.appspot.com/$ENVIRONMENT/PWA --recursive'
        //             }
        //             //sh 'gcloud storage cp ${WORKSPACE}/controldesk/build/* gs://sqinternational-cicd.appspot.com/$ENVIRONMENT/controldesk/controldesk-$VERSION_NUMBER/build --recursive'
        //         }
        //     }
        // }
    }
    }

}