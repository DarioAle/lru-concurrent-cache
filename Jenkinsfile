pipeline {
    agent any
    parameters {
        string(name: 'buildType', defaultValue: 'maven', description: 'What type of build can be used, gradle or maven')
    }
    stages {
        stage('Build') {
            steps {
                echo "The build will be performed using ${params.buildType}"
                java ../src/com/module2/task/CacheNode.java
                echo "Compilation successfull"
            }
        }
    }
}
