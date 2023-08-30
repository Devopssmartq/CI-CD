def call() {
    return [
        APP_ENGINE_PROJECT_ID: 'sqpreprod-us',
        GCS_BUCKET_NAME: 'sqinternational-cicd.appspot.com',
        ENVIRONMENT: 'MASTER',
        YAML: 'app.yaml',
        YAML1: 'time2eat.yaml',
        YAML2: 'baweb.yaml',
        // ... define other variables ...
        SSO_APP_ENGINE_VERSION: 'sso-v1'
    ]
}