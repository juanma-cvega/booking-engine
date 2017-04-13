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
        echo 'Creating image ' + artifactId + ':' + version
        def image = docker.build artifactId + ':' + version
    }
    stage('Test image') {
        echo 'Deploying image'
        def app = docker.image(artifactId + ':' + version).run('-p 8080:8080')
        docker.image('maven:3.3.9-jdk-8').inside {
            echo 'Running integration tests'
            git 'git@github.com:juanma-cvega/bookingservice.git'
            sh 'cd test && mvn integration-test verify -Pintegration-tests'
        }
        app.stop()
        app.rm()
    }
    stage('Clean up') {
        echo 'Deleting image ' + artifactId + ':' + version'
        sh 'docker rmi ' + artifactId + ':' + version'
    }
}
