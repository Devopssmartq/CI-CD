
/* groovylint-disable CompileStatic Author : Ajith Vijayakumar  */
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
def uploadWebOrtToGCS(branch) {
   echo "Upload to GCS"  
   echo "My branch & GCS Bucket name is  ${branch}"
        container('gcloud') {
            dir('webort') {
                sh 'ls'
                sh "gcloud storage cp ${WORKSPACE}/webort/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/web-ort/webort@$VERSION_NUMBER --recursive"
            }
            sh "gcloud storage cp ${WORKSPACE}/webort/build/* gs://sqinternational-cicd.appspot.com/${branch}/web-ort/webort@$VERSION_NUMBER/build --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadControlDeskToGCS(branch) {
    echo "Upload Control Desk to GCS" 
    echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {
            dir('controldesk') {
                sh 'ls'
                sh "gcloud storage cp ${WORKSPACE}/controldesk/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/controldesk/controldesk@$VERSION_NUMBER --recursive"
            }
            sh "gcloud storage cp ${WORKSPACE}/controldesk/build/* gs://sqinternational-cicd.appspot.com/${branch}/controldesk/controldesk@$VERSION_NUMBER/build --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadEWalletToGCS(branch) {
    echo "Upload eWallet to GCS"
    echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {
            sh "gcloud storage cp ${WORKSPACE}/ewallet/build/* gs://sqinternational-cicd.appspot.com/${branch}/ewallet/ewallet@$VERSION_NUMBER --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadtime2eatwebToGCS(branch) {
    echo "Upload time2eatweb to GCS"
    echo "My branch & GCS Bucket name is ${branch}"
    container('gcloud') {
        dir('time2eatweb') {
            sh 'ls'
            sh "gcloud storage cp ${WORKSPACE}/time2eatweb/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/time2eat/time2eat@$VERSION_NUMBER --recursive"
        }
        sh "gcloud storage cp ${WORKSPACE}/time2eatweb/build/* gs://sqinternational-cicd.appspot.com/${branch}/time2eat/time2eat@$VERSION_NUMBER/build --recursive"
        CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadvendordashboardToGCS(branch) {
    echo "Uploading to GCS Bucket"
    echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {
            sh "gcloud storage cp ${WORKSPACE}/unified_webstack/frontend/dist/vdashboard/admindashboard/* gs://sqinternational-cicd.appspot.com/$branch/vendor-dashboard/vdashboard@$VERSION_NUMBER --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadgenericadmindashboardToGCS(branch) {
    echo "Uploading generic-admin-dashboard to GCS Bucket"   
    echo "My branch & GCS Bucket name is ${branch}"     
    container('gcloud') {
        sh "gcloud storage cp ${WORKSPACE}/generic-admin-dashboard/build/* gs://sqinternational-cicd.appspot.com/${branch}/generic-admin-dashboard/admindashboard@$VERSION_NUMBER --recursive"
        CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
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
def uploadsmartqcloudbackendToGCS(branch) {
    echo "Uploading smartq-backend to GCS"
    echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {                 
            sh "gcloud storage cp ${WORKSPACE}/smartq-cloud-backend/smartqcloudbackend.txt gs://sqinternational-cicd.appspot.com/${branch}/smartq-cloud-backend/smartq-cloud-backend@$VERSION_NUMBER --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
                  
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
def uploadsqmicroservicesbackendToGCS(branch) {
    echo "Uploading sq_Microservices_Backend to GCS Bucket"
    echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {                   
            sh "gcloud storage cp ${WORKSPACE}/sq_microservices_backend/sqmicroservicesbackend.txt gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/default --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"


        }

}

def checkoutcontroldeskflutter(branch) {
    echo "Checkout controldeskflutter"
    echo "${branch}"   
        sh "mkdir -p controldeskflutter"
        dir('controldeskflutter') {
            git branch: "${branch}",
            credentialsId: 'bitbucket api',
            url: 'https://yash123devops@bitbucket.org/bottlelabtech/control-desk-flutter.git'
            sh 'ls'
        }
}
def buildcontroldeskflutter() {
    container('flutter') {
        sh 'echo ${WORKSPACE}'
        sh 'echo $BUILD_NUMBER'
            dir('controldeskflutter') {
                sh 'flutter clean'
                sh 'flutter pub get'
                sh 'flutter build web --web-renderer canvaskit'
                sh 'ls'
                dir ('${WORKSPACE}/build/web') {
                    sh 'ls'
                    sh 'pwd'
                    //sh 'echo changing to directory'
                }
            }
    }
}
def uploadcontroldeskflutterToGCS(branch) {
  echo "Upload to GCS"  
  echo "My branch & GCS Bucket name is ${branch}"
        container('gcloud') {
            dir('controldeskflutter') {
                sh 'ls'
                sh "gcloud storage cp ${WORKSPACE}/controldeskflutter/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/controldeskflutter/controldeskflutter@$VERSION_NUMBER --recursive"
            }
            sh "gcloud storage cp ${WORKSPACE}/controldeskflutter/build/* gs://sqinternational-cicd.appspot.com/${branch}/controldeskflutter/controldeskflutter@$VERSION_NUMBER/build --recursive"
            CLOUD_URL = "https://console.cloud.google.com/storage/browser/sqinternational-cicd.appspot.com/${branch}/${bitBucketProject}"
        }
}

pipeline {
    agent {
    
        kubernetes {
            yaml '''
        apiVersion: v1
        kind: Pod
        branch:
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

          - name: flutter
            image: instrumentisto/flutter:latest
            command:
            - cat
            tty: true 
        '''        
        }
    }

    parameters {

    choice(name: 'BitBucketProject', choices: ['web-ort', 'control-desk', 'ewallet', 'time2eatweb', 'vendor-dashboard','generic-admin-dashboard', 'smartq-cloud-backend', 'sq_microservices_backend','controldeskflutter'], description: 'Select BitBucket Project')
   // choice(name: 'Branch', choices: ['cicd-sprint','Release','SPRINT','feature-master', 'master','feature/sprint-25apr15may/preprod-config'], description: 'Select Branch')
    string(name: 'Branch', description: 'Enter the branch name')
   
    
    }


        
    environment {
        //environment='SPRINT'
        def commitId = 'your-dummy-commit-id'
        def CLOUD_URL = 'Your_URL'
        VERSION_NUMBER = VersionNumber([
            versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMddhhmmss"}-${BUILDS_TODAY}-{commitId}',  
            worstResultForIncrement: 'SUCCESS'
        ])

}

    stages { 

        // stage('Send Email') {
        //     steps {
        //         script {
        //             emailext subject: "Pipeline Started - ${bitBucketProject}", 
        //                      body: "The pipeline ${bitBucketProject} has started.",
        //                      from: 'yashwanth.kp@thesmartq.com',
        //                      to: 'yashwanth.kp@thesmartq.com',
        //                      cc: 'Ajith.Vijayakumar@thesmartq.com',
        //                      attachLog: true
        //         }
        //     }
        // }
    
        stage('Building All Pipeline') {
            steps {
                script {
                    def bitBucketProject = params.BitBucketProject.toLowerCase()
                    def branch = params.Branch
                    echo "Selected BitBucket Project: ${bitBucketProject}"
                    echo "Selected Branch: ${branch}"
                    echo "Selected GCS Bucket: ${branch}"
                    def buildPipeline = false


                    if (bitBucketProject == 'web-ort') {
                        checkoutWebORT(branch)
                        buildWebORT()
                        uploadWebOrtToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'control-desk') {
                        checkoutControlDesk(branch)
                        buildControlDesk()
                        uploadControlDeskToGCS(branch)
                        buildPipeline = true 
                    } else if (bitBucketProject == 'ewallet') {
                        checkoutEWallet(branch)
                        buildEWallet()
                        uploadEWalletToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'time2eatweb') {
                        checkouttime2eatweb(branch)
                        buildtime2eatweb()
                        uploadtime2eatwebToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'vendor-dashboard') {
                        checkoutvendordashboard(branch)
                        buildvendordashboard()
                        uploadvendordashboardToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'generic-admin-dashboard') {
                        checkoutgenericadmindashboard(branch)
                        buildgenericadmindashboard()
                        uploadgenericadmindashboardToGCS(branch)
                        buildPipeline = true                    
                    } else if (bitBucketProject == 'smartq-cloud-backend') {
                        checkoutsmartqcloudbackend(branch)
                        uploadsmartqcloudbackendToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'sq_microservices_backend') {
                        checkoutsqmicroservicesbackend(branch)
                        uploadsqmicroservicesbackendToGCS(branch)
                        buildPipeline = true
                    } else if (bitBucketProject == 'controldeskflutter') {
                        checkoutcontroldeskflutter(branch)
                        buildcontroldeskflutter()
                        uploadcontroldeskflutterToGCS(branch)
                        buildPipeline = true  
                    }  

                    if (!buildPipeline) {
                        error("Invalid combination of BitBucket Project and Branch.")
                    } 
                }
            }
        }



    }

    post{

        always{

            script {
                
                mail bcc: '', body: "Build is running.\nJob Name: ${Job_Name}.\nBuild Status: ${currentBuild.currentResult}.\nJenkins Build Number: ${Build_Number}.\nApplication  Name: ${bitBucketProject}.\nGCP Cloud URL: ${CLOUD_URL}.\n Version Number: ${VERSION_NUMBER}",

                cc: 'Ajith.Vijayakumar@thesmartq.com, R.Prabhakaran@thesmartq.com , VenkateswaraReddi.P@thesmartq.com , abhijeet@thesmartq.com',

                from: '',

                replyTo: '',

                subject: "Build Status# ${currentBuild.currentResult}",

                to: 'yashwanth.kp@thesmartq.com'

            }

        }

    }

}

