def call() {
    return [
        name: 'gcloud',
        image: 'google/cloud-sdk:latest',
        command: 'sleep',
        args: 'infinity'
    ]
}
