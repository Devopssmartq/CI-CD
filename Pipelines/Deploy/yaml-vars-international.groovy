import groovy.transform.Field
@Field def GCS_BUCKET_NAME;  
@Field def ENVIRONMENT;

@Field def CONTROLDESK_YAML;  
@Field def PWA_YAML; 
@Field def FOODBOOK_YAML;
@Field def TIME2EAT_YAML;
@Field def BAWEB_YAML;  
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
    ENVIRONMENT = RELEASE_SCOPE == "sprint" ? "SPRINT-DEMO" : RELEASE_SCOPE == "preprod" ? "RELEASE-DEMO" : "MASTER"
    // To-do : change the following to appropriate branch names, once release brnach is created
    GIT_BRANCH = RELEASE_SCOPE == "sprint" ? "cicd-sprint" : RELEASE_SCOPE == "preprod" ? "cicd-sprint" : "master"
     /***************** INTERNATIONAL deployment *********/
//frontend service  yamls
    CONTROLDESK_YAML = RELEASE_SCOPE == "beta" ? 'beta-app.yaml' : 'app.yaml'
    
    PWA_YAML = ''// not for INTERNATIONAL. for india only
    FOODBOOK_YAML = ''// not for INTERNATIONAL. for india only
    TIME2EAT_YAML = RELEASE_SCOPE == "beta" ? '' : 'time2eat.yaml'
    BAWEB_YAML = RELEASE_SCOPE == "beta" ? '' : 'baweb.yaml'

    WEBORT_YAML = RELEASE_SCOPE == "beta" ? 'beta_app.yaml' : 'app.yaml'
//py2 service yamls
    APP_SERVICE_YAML = RELEASE_SCOPE == "beta" ? '' : 'time2eat_appservice.yaml'
    DEFAULT_SERVICE_YAML = ''// not for INTERNATIONAL. for india only
    BACKGROUND_YAML = RELEASE_SCOPE == "beta" ? 'background_betaapp.yaml' : 'background.yaml'
    TASKQSERVICE_YAML = RELEASE_SCOPE == "beta" ? 'taskqservice_betaapp.yaml' : 'taskqservice.yaml'
    REPORTS_YAML = ''// not for INTERNATIONAL. for india only
    BQREPORTS_YAML = RELEASE_SCOPE == "beta" ? 'bqreports_betaapp.yaml' : 'bqreports.yaml'
//py3 service yamls
    PY3_APPMS_YAML = RELEASE_SCOPE == "beta" ? "betaapp-${params.APP_ENGINE_PROJECT_ID}.yaml" : "${params.APP_ENGINE_PROJECT_ID}.yaml"
    PY3_DASHBOARD_YAML = RELEASE_SCOPE == "beta" ? "betaapp-${params.APP_ENGINE_PROJECT_ID}.yaml" : "${params.APP_ENGINE_PROJECT_ID}.yaml"
    PY3_SSO_YAML = RELEASE_SCOPE == "beta" ? '' : 'app.yaml'
    PY3_PRICEBOOK_YAML = ''// not for INTERNATIONAL. for india only
}
return this