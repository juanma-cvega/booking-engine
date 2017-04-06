node {
    def artifactId
    def version
    stage('Build') {
        docker.image('maven:3.3.9-jdk-8').inside {
            git 'git@github.com:juanma-cvega/bookingservice.git'
            sh 'mvn clean install'
            def pom = readMavenPom file: 'pom.xml'
            artifactId = pom.artifactId
            version = pom.version
        }
    }
    stage('Results') {
        junit '**/target/surefire-reports/TEST-*.xml'
        archive 'target/*.jar'
    }
    stage('Create image') {
        echo 'Deleting image ' + artifactId + ':' + version
        def image = docker.build artifactId + ':' + version
    }
    stage('Deploy image') {
        echo 'Deploying image'
        docker.image(artifactId + ':' + version).inside {
            echo 'Inside image'
        }
    }
    stage('Clean up') {
        sh 'docker rmi ' + artifactId + ':' + version
    }
}
