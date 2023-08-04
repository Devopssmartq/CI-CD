def call(body){

  pipeline {
    agent {
      kubernetes {
        label 'sample-app'
        defaultContainer 'jnlp'
      yaml '''
      apiVersion: v1
      kind: Pod
      spec:
        containers:
      - name: golang
        image: golang:latest
        command:
        - sleep
        args:
        - 99d
        tty: true
        '''
      }
    }
  }
}