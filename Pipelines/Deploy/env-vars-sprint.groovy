import groovy.transform.Field
@Field def APP_ENGINE_PROJECT_ID;
@Field def GCS_BUCKET_NAME;  
@Field def ENVIRONMENT;    
def setProperties() {
    GCS_BUCKET_NAME = "sqinternational-cicd.appspot.com"
    ENVIRONMENT = "SPRINT-DEMO"
}
return this