pipeline {
    agent any
    parameters {
        string(name: 'buildType', defaultValue: 'maven', description: 'What type of build can be used, gradle or maven')
    }
    stages {
        stage('Build') {
            steps {
                echo "The build will be performed using ${params.buildType}"
                sh 'javac src/*.java'

            }
        }
    }
}
