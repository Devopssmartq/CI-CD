def call(){
        sh "npm install"
        sh "CI=false npm run build"
}
