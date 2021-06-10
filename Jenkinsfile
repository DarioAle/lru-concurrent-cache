pipeline {
    agent any
    stages {
        stage('Build') {
            steps {
                sh 'echo "Hello World, now the build should trigger"'
                sh '''
                    echo "Multiline shell steps works too"
                    ls -lah
                '''
            }
        }
    }
}
