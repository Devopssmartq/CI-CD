def call(String repoUrl) {
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
                
          - name: flutter
            image: instrumentisto/flutter:latest
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