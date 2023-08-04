def call(string repoUrl){

    pipeline {
        agent any

            stages {
                stage('Hello') {
                    steps {
                        echo 'Hello, World!'
                    }
                }
            }
    }
}