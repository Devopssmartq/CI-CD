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