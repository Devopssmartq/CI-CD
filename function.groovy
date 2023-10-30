import groovy.transform.Field
@Field def IS_NODEAPPS_ENABLED
@Field def IS_PY2_ENABLED
@Field def IS_PY3_ENABLED
@Field def ARR_SELECTED_SERVICE
@Field def ARR_VERIFY_SERVICE = []
@Field def PROMOTED_SERVICES = []

def getURL_Status(Map args) {
    script {
        def service_url = sh(script: "gcloud app browse --service=${args.service_name} --version=${args.version_name} --no-launch-browser --format='value(url)'", returnStdout: true).trim()
        // must call trim() to remove the default trailing newline
        final status_code = sh(script: "curl --write-out %{http_code} --silent --output /dev/null '${service_url}/${args.url_suffix}'", returnStdout: true).trim()
         
        if (status_code == "200") {
            echo "Success with Status Code : ${status_code}" 
        }
        else { 
            echo "Failed with Status Code : ${status_code}" 
        }
        return status_code
    }
}
def get_Previous_Version(Map args) {
    container('gcloud') {
        try {
            //def previous_verison = ''
            // def output_status = sh ( 
            //     script:"gcloud app versions list --service='${args.service_name}' --limit=1 --format='value(VERSION.ID)' --filter='traffic_split>0'", 
            //     returnStatus: true
            // )// must call trim() to remove the default trailing newline
            // echo "output-status: ${output_status}"
            // if (output_status == 0) {
            //     previous_verison= sh ( 
            //         script:"gcloud app versions list --service='${args.service_name}' --limit=1 --format='value(VERSION.ID)' --filter='traffic_split>0'", 
            //         //returnStdout: true
            //     ).trim()
               
            // }
            
          
        }
        catch (Exception e) {
            // Handle the error gracefully, e.g., set previous_version to a default value
            //previous_version = ""
        }

        return ""
     }
}
def is_Version_Exists(Map args) {
    script {       
        def previous_verison = get_Previous_Version(service_name: "${args.service_name}")
        def new_version = get_AppEngine_Version(app_name: "${args.app_name}")
        echo "previous_verison - ${previous_verison}"
        echo "new_version - ${new_version}"   
        boolean version_Exists = (previous_verison == new_version) ? true : false
        echo "version_Exists - $version_Exists, skipping the deployment for ${args.service_name}"
        return version_Exists
    }
}
def get_Storage_Object_Source_Path(Map args) { 
    def source_path = sh (
        script: "gcloud storage ls  gs://${GCS_BUCKET_NAME}/${ENVIRONMENT}/${args.app_name} | sort -k 2,2 -r | head -n 1",
        returnStdout: true
    ).trim()
    return "${source_path}"
}
def get_AppEngine_Version(Map args) {
     container('gcloud') {
        def STORAGE_OBJECT_SOURCE_PATH = sh (
                script: "gcloud storage ls  gs://${GCS_BUCKET_NAME}/${ENVIRONMENT}/${args.app_name} | sort -k 2,2 -r | head -n 1",
                returnStdout: true
        ).trim()                                
        def APP_ENGINE_VERSION = sh (
                script: "basename ${STORAGE_OBJECT_SOURCE_PATH}",
                returnStdout: true
        ).trim()
        echo "App_Engine_Version for ${args.app_name} : ${APP_ENGINE_VERSION}" 
        newId = "${APP_ENGINE_VERSION}"
        return "${APP_ENGINE_VERSION}"
    }
}
def promote_Service(Map args) {
    def PROMOTED_SERVICE = sh (script: "gcloud app services set-traffic ${args.service_name} --splits ${args.version_name}=1 --quiet", returnStdout: true).trim()       
    return "${PROMOTED_SERVICE}"
}
def Write_To_File() {
    script {    
        def filePath = "${env.WORKSPACE}/release-${env.VERSION_NUMBER}.txt"                  
        def content = PROMOTED_SERVICES.join('\n')
        writeFile file: filePath, text: content                                            
        echo "Items written to ${filePath}"
        def data = readFile(file: "${filePath}")
        println(data)
        // container('gcloud') {
        //     dir("${env.WORKSPACE}") {
        //         sh "gcloud storage cp ${filePath} gs://sqinternational-cicd.appspot.com/release_summary/${APP_ENGINE_PROJECT_ID}/released --recursive"
        //     }
        // }
    }
}
//1. initialize the app that needs deployment
def nodeapps = [
            [key: 'controldesk'],
            [key: 'controldeskflutter'],
            [key: 'smartq-pwa'],
            [key: 'foodbook-web'],
            [key: 'time2eat'],
            [key: 'baweb'],
            [key: 'web-ort']
        ]
def py2_services = [
            [key: 'appservice'],
            [key: 'py2_default'],//india
            [key: 'backgroundtasks'],
            [key: 'taskqservice'],
            [key: 'reports'],
            [key: 'bqreports']
        ]
def py3_services = [
            [key: 'app'],
            [key: 'py3_default'], //international           
            [key: 'dashboard'],
            [key: 'sso'],
            [key: 'pricebook'],
            [key: 'integration']