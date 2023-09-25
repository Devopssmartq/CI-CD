
/* groovylint-disable CompileStatic */
// Uses Declarative syntax to run commands inside a container.

def checkoutWebORT(branch) {
    echo "Checkout webort"
    echo "${branch}"   
        sh "mkdir -p webort"
        dir('webort') {
            git branch: "${branch}",
            credentialsId: 'serviceaccount',
            url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/web-ort.git'
            sh 'ls'
        }
}
def buildWebORT() {
    container('nodejs') {
        sh 'echo ${WORKSPACE}'
        sh 'echo $BUILD_NUMBER'
            dir('webort') {
                sh 'ls'
                sh 'npm install --force'
                sh 'CI=false npm run build'
                    dir ('${WORKSPACE}/webort/build') {
                        sh 'ls'
                        sh 'echo changing to directory'
                    }
                }
            }
}
def uploadWebOrtToGCS(ENVIRONMENT) {
   echo "Upload to GCS"  
   echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {
            dir('webort') {
                sh 'ls'
                sh "gcloud storage cp ${WORKSPACE}/webort/*.yaml gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/web-ort/webort@$VERSION_NUMBER --recursive"
            }
            sh "gcloud storage cp ${WORKSPACE}/webort/build/* gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/web-ort/webort@$VERSION_NUMBER/build --recursive"
        }
}

def checkoutControlDesk(branch) {
    echo "Checkout Controldesk"
        sh "mkdir -p controldesk"
        echo "test controldesk"
        echo "My Branch ${branch}"
            dir('controldesk') {
                git branch: "${branch}",
                credentialsId: 'serviceaccount',
                url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/control-desk.git'
                sh 'ls'
            }

}
def buildControlDesk() {
    echo "Build Control Desk"  
        container('nodejs') {
            sh 'echo ${WORKSPACE}'
            sh 'ls'
                dir('controldesk') {
                    sh 'npm install --force'
                    sh 'CI=false npm run build'
                        dir ('${WORKSPACE}/controldesk/build') {
                            sh 'echo changing to directory'
                            sh 'ls'
                        
                        }
                    }
                }
    
}
def uploadControlDeskToGCS(ENVIRONMENT) {
    echo "Upload Control Desk to GCS" 
    echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {
            dir('controldesk') {
                sh 'ls'
                sh "gcloud storage cp ${WORKSPACE}/controldesk/*.yaml gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/controldesk/controldesk@$VERSION_NUMBER --recursive"
            }
            sh "gcloud storage cp ${WORKSPACE}/controldesk/build/* gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/controldesk/controldesk@$VERSION_NUMBER/build --recursive"
        }   
}

def checkoutEWallet(branch) {
    echo "Checkout ewallet"
        sh "mkdir -p ewallet"
            dir('ewallet') {
                git branch: "${branch}",
                credentialsId: 'serviceaccount', 
                url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/ewallet.git' 
            }
}
def buildEWallet() {
    echo "Building eWallet"
        dir('ewallet'){
            container('nodejs') {
                sh 'npm install'
                sh 'CI=false npm run build'
                dir ('build') {
                    sh 'echo changing to directory'
                    sh 'ls -l'
                }
            }
        }
}
def uploadEWalletToGCS(ENVIRONMENT) {
    echo "Upload eWallet to GCS"
    echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {
            sh "gcloud storage cp ${WORKSPACE}/ewallet/build/* gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/ewallet/ewallet@$VERSION_NUMBER --recursive"
        }

            
}

def checkouttime2eatweb(branch) {
    echo "Checkout PWA"
        sh "mkdir -p time2eatweb"
            dir('time2eatweb') {
                git branch: "${branch}",
                credentialsId: 'serviceaccount',
                url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/time2eatweb.git'
            }

}
def buildtime2eatweb() {
    echo "Build PWA"
        container('nodejs') {
             sh 'echo ${WORKSPACE}'
                dir('time2eatweb') {
                    sh 'npm install --force'
                    sh 'CI=false npm run build:app1'
                    sh 'CI=false npm run build:app2'
                    sh 'CI=false npm run build:app3'
                    sh 'CI=false npm run build:app4'
                    sh 'CI=false npm run build:app5'
                        dir ('${WORKSPACE}/time2eatweb/build') {
                            sh 'echo changing to directory'
                       }
                    }
                }

}
def uploadtime2eatwebToGCS(ENVIRONMENT) {
    echo "Upload time2eatweb to GCS"
    echo "My ENVIRONMENT ${ENVIRONMENT}"
    container('gcloud') {
        dir('time2eatweb') {
            sh 'ls'
            sh "gcloud storage cp ${WORKSPACE}/time2eatweb/*.yaml gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/time2eat/time2eat@$VERSION_NUMBER --recursive"
        }
        sh "gcloud storage cp ${WORKSPACE}/time2eatweb/build/* gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/time2eat/time2eat@$VERSION_NUMBER/build --recursive"
    }

}

def checkoutvendordashboard(branch) {
    echo "Checkout Vendordashboard"
        sh "mkdir -p unified_webstack"
            dir('unified_webstack') {
                git branch: "${branch}",
                credentialsId: 'serviceaccount',
                url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/unified_webstack.git'
            }

}
def buildvendordashboard() {
    echo "Buliding Vendordashboard"
        dir('unified_webstack') {         
            container('nodejs') {
                dir ('${WORKSPACE}/frontend') {
                    sh 'npm install -g gulp'
                    sh 'gulp build_vdashboard'
                        dir ('dist/vdashboard/admindashboard'){
                            sh 'ls *'
                    }
                }
            }             
        }      

}   
def uploadvendordashboardToGCS(ENVIRONMENT) {
    echo "Uploading to GCS Bucket"
    echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {
            sh "gcloud storage cp ${WORKSPACE}/unified_webstack/frontend/dist/vdashboard/admindashboard/* gs://sqinternational-cicd.appspot.com/$ENVIRONMENT/vendor-dashboard/vdashboard@$VERSION_NUMBER --recursive"
        }

}

def checkoutgenericadmindashboard(branch) {
    echo "Checkout generic-admin-dashboard"
    echo "${branch}"
        sh "mkdir -p generic-admin-dashboard"                
        dir('generic-admin-dashboard') {
            git branch: "${branch}",
            credentialsId: 'serviceaccount',
            url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/generic-admin-dashboard.git' 
            sh 'ls'
        }
}
def buildgenericadmindashboard() {
    echo"Building generic-admin-dashboard"
        dir('generic-admin-dashboard') {         
            container('nodejs') {
                sh 'npm install --force'
                sh 'CI=false npm run build'
                dir ('build') {
                    sh 'echo changing to directory'
                    sh 'ls -l'
                }
            }             
        }
}
def uploadgenericadmindashboardToGCS(ENVIRONMENT) {
    echo "Uploading generic-admin-dashboard to GCS Bucket"   
    echo "My ENVIRONMENT ${ENVIRONMENT}"     
    container('gcloud') {
        sh "gcloud storage cp ${WORKSPACE}/generic-admin-dashboard/build/* gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/generic-admin-dashboard/admindashboard@$VERSION_NUMBER --recursive"
        }
}

def checkoutsmartqcloudbackend(branch) {
    echo "Checkout smartq-cloud-backend"
        sh "mkdir -p smartq-cloud-backend"
            dir('smartq-cloud-backend') {                        
                git branch: "${branch}",
                credentialsId: 'serviceaccount',
                url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/smartq-cloud-backend.git'
                sh 'ls -lrt'
                sh "touch smartqcloudbackend.txt"
            }

}   
def uploadsmartqcloudbackendToGCS(ENVIRONMENT) {
    echo "Uploading smartq-backend to GCS"
    echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {                 
            sh "gcloud storage cp ${WORKSPACE}/smartq-cloud-backend/smartqcloudbackend.txt gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/smartq-cloud-backend/smartq-cloud-backend@$VERSION_NUMBER --recursive"
                  
        }

}

def checkoutsqmicroservicesbackend(branch) {
    echo "Checkout sqmicroservicesbackend"
    sh "mkdir -p sq_microservices_backend"
        dir('sq_microservices_backend') {                        
            git branch: "${branch}",
            credentialsId: 'serviceaccount',
            url: 'https://AbhijeetSawhney@bitbucket.org/bottlelabtech/sq_microservices_backend.git'
            sh 'ls -lrt'
            sh "touch sqmicroservicesbackend.txt"
        }

}
def uploadsqmicroservicesbackendToGCS(ENVIRONMENT) {
    echo "Uploading sq_Microservices_Backend to GCS Bucket"
    echo "My ENVIRONMENT ${ENVIRONMENT}"
        container('gcloud') {                   
            sh "gcloud storage cp ${WORKSPACE}/sq_microservices_backend/sqmicroservicesbackend.txt gs://sqinternational-cicd.appspot.com/${ENVIRONMENT}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/default --recursive"


        }

}

pipeline {
    agent {
    
        kubernetes {
            yaml '''
        apiVersion: v1
        kind: Pod
        environment:
            name: "NODE_OPTIONS"
            value: "--max-old-space-size=4096"
        spec:
          containers:
          - name: jnlp  
            resources:
              requests:
                memory: "2048Mi"
                cpu: "500m"
              limits:
                memory: "4096Mi"
                cpu: "1000m"
                
          - name: nodejs
            image: node:18-alpine
            command:
            - cat
            tty: true

          - name: gcloud
            image: google/cloud-sdk:latest
            command:
            - cat
            tty: true
        '''        
        }
    }

    parameters {

    choice(name: 'BitBucketProject', choices: ['web-ort', 'control-desk', 'ewallet', 'time2eatweb', 'vendor-dashboard','generic-admin-dashboard', 'smartq-cloud-backend', 'sq_microservices_backend'], description: 'Select BitBucket Project')
    choice(name: 'Branch', choices: ['cicd-sprint','release/release-v1.0','release-v1.0','feature-master', 'master','feature/sprint-25apr15may/preprod-config'], description: 'Select Branch')
    choice(name: 'ENVIRONMENT', choices: ['SPRINT', 'MASTER', 'RELEASE'], description: 'Select ENVIRONMENT which will copy to the respective folder inside the GCS Bucket')
    
    }


        
    environment {
        //ENVIRONMENT='SPRINT'
        VERSION_NUMBER = VersionNumber([
            versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMddhhmmss"}-${BUILDS_TODAY}',  
            worstResultForIncrement: 'SUCCESS'
        ])
}

    stages {      
        stage('Building All Pipeline') {
            steps {
                script {
                    def bitBucketProject = params.BitBucketProject.toLowerCase()
                    def branch = params.Branch
                    echo "Selected BitBucket Project: ${bitBucketProject}"
                    echo "Selected Branch: ${branch}"
                    def buildPipeline = false

                    if (bitBucketProject == 'web-ort') {
                        checkoutWebORT(branch)
                        buildWebORT()
                        uploadWebOrtToGCS(ENVIRONMENT)
                        buildPipeline = true
                    } else if (bitBucketProject == 'control-desk') {
                        checkoutControlDesk(branch)
                        buildControlDesk()
                        uploadControlDeskToGCS(ENVIRONMENT)
                        buildPipeline = true 
                    } else if (bitBucketProject == 'ewallet') {
                        checkoutEWallet(branch)
                        buildEWallet()
                        uploadEWalletToGCS(ENVIRONMENT)
                        buildPipeline = true
                    } else if (bitBucketProject == 'time2eatweb') {
                        checkouttime2eatweb(branch)
                        buildtime2eatweb()
                        uploadtime2eatwebToGCS(ENVIRONMENT)
                        buildPipeline = true
                    } else if (bitBucketProject == 'vendor-dashboard') {
                        checkoutvendordashboard(branch)
                        buildvendordashboard()
                        uploadvendordashboardToGCS(ENVIRONMENT)
                        buildPipeline = true
                    } else if (bitBucketProject == 'generic-admin-dashboard') {
                        checkoutgenericadmindashboard(branch)
                        buildgenericadmindashboard()
                        uploadgenericadmindashboardToGCS(ENVIRONMENT)
                        buildPipeline = true                    
                    }   else if (bitBucketProject == 'smartq-cloud-backend') {
                        checkoutsmartqcloudbackend(branch)
                        uploadsmartqcloudbackendToGCS(ENVIRONMENT)
                        buildPipeline = true
                    }  else if (bitBucketProject == 'sq_microservices_backend') {
                        checkoutsqmicroservicesbackend(branch)
                        uploadsqmicroservicesbackendToGCS(ENVIRONMENT)
                        buildPipeline = true
                    }  
                    if (!buildPipeline) {
                        error("Invalid combination of BitBucket Project and Branch.")
                    } 
                }
            }
        }


    }

}

