
/* groovylint-disable CompileStatic */
// Uses Declarative syntax to run commands inside a container.

def checkoutWebORT(branch) {
    echo "Checkout webort - In method"
    echo "${branch}"
    
        sh "mkdir -p webort"
        dir('webort') {
            git branch: "${branch}",
            credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
            url: 'https://vrp63531@bitbucket.org/bottlelabtech/web-ort.git'
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
                      //  sh 'echo webhook is triggered'
                    }
                }
            }
}
def uploadWebOrtToGCS(branch) {
   echo "Upload to GCS"  
   def timestamp = currentBuild.startTimeInMillis
   def formattedTimestamp = new Date(timestamp).format("yyyyMMdd-HHmmss")


            container('gcloud') {
                dir('webort') {
                    sh 'ls'
                    sh "gcloud storage cp ${WORKSPACE}/webort/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/web-ort/webort@${formattedTimestamp}-$VERSION_NUMBER --recursive"
                }
                sh "gcloud storage cp ${WORKSPACE}/webort/build/* gs://sqinternational-cicd.appspot.com/${branch}/web-ort/webort@${formattedTimestamp}-$VERSION_NUMBER/build --recursive"
            }
}

def checkoutControlDesk(branch) {
            sh "mkdir -p controldesk"
            echo "test controldesk"
            echo "My Branch ${branch}"
            dir('controldesk') {
                git branch: "${branch}",
                credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
                url: 'https://vrp63531@bitbucket.org/bottlelabtech/control-desk.git'
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
            container('gcloud') {
                //sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'
                dir('controldesk') {
                    sh 'ls'
                    sh "gcloud storage cp ${WORKSPACE}/controldesk/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/controldesk/controldesk@$VERSION_NUMBER --recursive"
                }
                sh "gcloud storage cp ${WORKSPACE}/controldesk/build/* gs://sqinternational-cicd.appspot.com/${branch}/controldesk/controldesk@$VERSION_NUMBER/build --recursive"
            }   
}
def checkoutEWallet(branch) {
    //stage("Checkout eWallet") {
     //    steps {
           echo "Checkout ewallet"
           sh "mkdir -p ewallet"
            dir('ewallet') {
                git branch: "${branch}",
                    credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556', 
                    url: 'https://vrp63531@bitbucket.org/bottlelabtech/ewallet.git' 
                }
    //        }
     //   }
}
def buildEWallet() {
 //   stage("Build eWallet") {
 //       steps {
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
      //      }
     //   }
}
def uploadEWalletToGCS(branch) {
     stage("Upload eWallet to GCS") {
      //   steps {
             container('gcloud') {
                 //sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'
                 sh "gcloud storage cp ${WORKSPACE}/ewallet/build/* gs://sqinternational-cicd.appspot.com/${branch}/ewallet/ewallet@$VERSION_NUMBER --recursive"
                }
        //     }
            }
}

def checkouttime2eatweb(branch) {
 //   stage("Checkout time2eatweb") {
    //    steps {
            echo "Checkout PWA"
                sh "mkdir -p time2eatweb"
                dir('time2eatweb') {
                    git branch: 'feature/sprint-april25lnp/preprod',
                    credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
                    url: 'https://vrp63531@bitbucket.org/bottlelabtech/time2eatweb.git'
            }
    //    }
//    }
}
def buildtime2eatweb() {
   // stage("Build time2eatweb") {
    //    steps {
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
     //   }
//    }
}
def uploadtime2eatwebToGCS(branch) {
 //   stage("Upload to GCS") {
  //      steps {
            container('gcloud') {
                    dir('time2eatweb') {
                        sh 'ls'
                        sh 'gcloud storage cp ${WORKSPACE}/time2eatweb/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/time2eat/time2eat@$VERSION_NUMBER --recursive'
                    }
                    sh 'gcloud storage cp ${WORKSPACE}/time2eatweb/build/* gs://sqinternational-cicd.appspot.com/${branch}/time2eat/time2eat@$VERSION_NUMBER/build --recursive'
                }
   //     }
//    }
}

def checkoutvendordashboard(branch) {
  //  stage("Build Platform 1") {
 //       steps {
            sh "mkdir -p unified_webstack"
            dir('unified_webstack') {
                git branch: "${branch}",
                credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
                url: 'https://vrp63531@bitbucket.org/bottlelabtech/unified_webstack.git'
            }
  //      }
 //   }
}
def buildvendordashboard() {
  //      steps {
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
       //     }
        }
    
def uploadvendordashboardToGCS(branch) {
  //  stage('Upload to GCS') {
   //     steps {
            container('gcloud') {
                //sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'                    
                sh "gcloud storage cp ${WORKSPACE}/unified_webstack/frontend/dist/vdashboard/admindashboard/* gs://sqinternational-cicd.appspot.com/${branch}/vendor-dashboard/vdashboard@$VERSION_NUMBER --recursive"
                }
      //      }
  //      }
}
def checkoutsmartqcloudbackend(branch) {
   // stage('Git CheckOut - smartq-backend') { 
   //     steps { 
                sh "mkdir -p smartq-cloud-backend"
                dir('smartq-cloud-backend') {                        
                    git branch: "${branch}",
                    credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
                    url: 'https://vrp63531@bitbucket.org/bottlelabtech/smartq-cloud-backend.git'
                }
   //         } 
    //    }
}
    
def uploadsmartqcloudbackendToGCS(branch) {
 //   stage('Upload to GCS') {
  //      steps {
                container('gcloud') {
                    //sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'                    
                  //  sh "gcloud storage cp ${WORKSPACE}/smartq-cloud-backend/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/smartq-cloud-backend/smartq-cloud-backend@$VERSION_NUMBER --recursive"
                  sh "gcloud storage  gs://sqinternational-cicd.appspot.com/${branch}/smartq-cloud-backend/smartq-cloud-backend@$VERSION_NUMBER --recursive"
                }
   //         }
 //       }
}
def checkoutsqmicroservicesbackend(branch) {
    
    echo "Checkout sqmicroservicesbackend"
    sh "mkdir -p sq_microservices_backend"
        dir('sq_microservices_backend') {                        
            git branch: "${branch}",
            credentialsId: '205fee1d-5909-4587-abe4-8c50bdc37556',
            url: 'https://vrp63531@bitbucket.org/bottlelabtech/sq_microservices_backend.git'
            sh 'ls -lrt'
        }

}
def uploadsqmicroservicesbackendToGCS(branch) {
 //   stage('Upload to GCS') {
   //     steps {
           container('gcloud') {
               // sh 'gcloud config set project $APP_ENGINE_PROJECT_ID'                    
                // sh "gcloud storage cp ${WORKSPACE}/sq_microservices_backend/appms/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/default --recursive"
                // sh "gcloud storage cp ${WORKSPACE}/sq_microservices_backend/dashboardms/*.yaml gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/dashboard --recursive"
                // sh "gcloud storage cp ${WORKSPACE}/sq_microservices_backend/sso_flex/app.yaml gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/sso/ --recursive"
                sh "gcloud storage gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/default --recursive"
                sh "gcloud storage gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/dashboard --recursive"
                sh "gcloud storage gs://sqinternational-cicd.appspot.com/${branch}/sq_microservices_backend/sq_microservices_backend@$VERSION_NUMBER/sso/ --recursive"
            }
   //         }
  //      }
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

    choice(name: 'BitBucketProject', choices: ['web-ort', 'control-desk', 'ewallet', 'time2eatweb', 'vendor-dashboard', 'smartq-cloud-backend', 'sq_microservices_backend'], description: 'Select BitBucket Project')
    choice(name: 'Branch', choices: ['cicd-sprint','ccid-sprint','release','feature-master', 'master','feature/sprint-25apr15may/preprod-config'], description: 'Select Branch')
    
    }


        
    environment {
        //APP_ENGINE_PROJECT_ID = 'sqpreprod-us'
        //BRANCH = params.Branch
        // Get the current timestamp
        VERSION_NUMBER = VersionNumber([
            versionNumberString: '${BUILD_DATE_FORMATTED, "yyyyMMddhhmmss"}-${BUILDS_TODAY}', 
            //versionPrefix: 'v1.0.', 
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
                    } else if (bitBucketProject == 'smartq-cloud-backend') {
                        checkoutsmartqcloudbackend(branch)
                        uploadsmartqcloudbackendToGCS(branch)
                        buildPipeline = true
                    }  else if (bitBucketProject == 'sq_microservices_backend') {
                        checkoutsqmicroservicesbackend(branch)
                        uploadsqmicroservicesbackendToGCS(branch)
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

