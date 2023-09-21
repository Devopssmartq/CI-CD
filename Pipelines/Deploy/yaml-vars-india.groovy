import groovy.transform.Field
@Field def GCS_BUCKET_NAME;  
@Field def ENVIRONMENT;

@Field def CONTROLDESK_YAML;  
@Field def PWA_YAML; 
@Field def FOODBOOK_YAML;  
@Field def WEBORT_YAML;

@Field def APP_SERVICE_YAML;  
@Field def DEFAULT_SERVICE_YAML;
@Field def BACKGROUND_YAML;  
@Field def TASKQSERVICE_YAML;
@Field def REPORTS_YAML;  
@Field def BQREPORTS_YAML;

@Field def PY3_APPMS_YAML;
@Field def PY3_DASHBOARD_YAML;
@Field def PY3_SSO_YAML;
@Field def PY3_PRICEBOOK_YAML;

def setProperties() {
    GCS_BUCKET_NAME = "sqinternational-cicd.appspot.com"
    ENVIRONMENT = "SPRINT-DEMO" //"${params.RELEASE_SCOPE}" == "sprint" ? "SPRINT-DEMO" : "${params.RELEASE_SCOPE}" == "preprod" ? "RELEASE" : "MASTER"
    
    CONTROLDESK_YAML = 'app.yaml'
    PWA_YAML = 'smartq-pwa.yaml'
    FOODBOOK_YAML = 'foodbook.yaml'
    WEBORT_YAML = 'app.yaml'

    APP_SERVICE_YAML = 'time2eat_appservice.yaml'
    DEFAULT_SERVICE_YAML = 'app.yaml'
    BACKGROUND_YAML = 'background.yaml'
    TASKQSERVICE_YAML = 'taskqservice.yaml'
    REPORTS_YAML = 'reports.yaml'
    BQREPORTS_YAML = 'bqreports.yaml'

    PY3_APPMS_YAML = "${params.APP_ENGINE_PROJECT_ID}.yaml"
    PY3_DASHBOARD_YAML = "${params.APP_ENGINE_PROJECT_ID}.yaml"
    PY3_SSO_YAML = 'app.yaml'
    PY3_PRICEBOOK_YAML = 'app_db_demo.yaml'
}
return this