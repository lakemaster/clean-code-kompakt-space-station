pipeline {
    agent any 

    options {
        gitLabConnection('viadee GitLab')
        skipStagesAfterUnstable()
        timestamps()
    }
   
    triggers {
        gitlab(
            branchFilterType: 'All',
            triggerOnPush: true,
            triggerOnMergeRequest: true,
            triggerOpenMergeRequestOnPush: "never",
            triggerOnNoteRequest: true,
            noteRegex: "Jenkins please retry a build",
            skipWorkInProgressMergeRequest: true,
            secretToken: "2cb3e70320bf196c927fce3fd87ff5b3",
            ciSkip: true,
            setBuildDescription: true,
            addNoteOnMergeRequest: true,
            addCiMessage: true,
            addVoteOnMergeRequest: true,
            acceptMergeRequestOnSuccess: false
        )
    }
   
    tools { 
        maven 'apache-maven-3.3.3' 
        jdk 'jdk8_40' 
    }
    
    stages {
    	 stage('clean') {
            steps {
                sh 'mvn clean'
            }
        }

        stage('build') {
            steps {
                sh 'mvn clean package -DskipTests'
            }
        }
        
        stage('test') {
            steps {
                sh 'mvn test'
                dependencyCheckAnalyzer datadir: '/data/jenkins-settings/owasp/nvd', hintsFile: '', includeCsvReports: false, includeHtmlReports: false, includeJsonReports: false, includeVulnReports: false, isAutoupdateDisabled: true, outdir: '', scanpath: '', skipOnScmChange: false, skipOnUpstreamChange: false, suppressionFile: 'dependency-check-suppression.xml', zipExtensions: ''  
            }
            post {
                always {
                    junit(testResults: '**/target/surefire-reports/TEST-*.xml', allowEmptyResults: true)
                    dependencyCheckPublisher canComputeNew: false, shouldDetectModules: true, defaultEncoding: '', healthy: '', pattern: '', unHealthy: ''
                }
            }
        }
        
        stage('SonarQube analysis') {
            steps {
                withSonarQubeEnv('sonarqube viadee') {
                    sh 'mvn sonar:sonar'
                }
                timeout(time: 1, unit: 'HOURS') { // Just in case something goes wrong, pipeline will be killed after a timeout
                    waitForQualityGate abortPipeline: true
                }
            }
        }
            
    }  
    
    post {
        success {
            archiveArtifacts artifacts: 'backend/target/spring-angular-example*.jar', fingerprint: true                        
        }

    	unstable {
            script {
                if(authorlist()) {
                    mail to: authorlist(),
                         subject: "Build unstable: ${currentBuild.fullDisplayName}",
                         body: "Check console output at ${env.BUILD_URL} to view the results."                    
                }
            }
        }

        failure {
            script {
                if(authorlist()) {
                    mail to: authorlist(),
                         subject: "Build failed: ${currentBuild.fullDisplayName}",
                         body: "Check console output at ${env.BUILD_URL} to view the results."
                }
            }
        }
        
        fixed {
            script {
                if(authorlist()) {
                    mail to: authorlist(),
                         subject: "Build is back to normal: ${currentBuild.fullDisplayName}",
                         body: "${env.BUILD_URL} - SUCCESSFUL!"
                }
            }
        }
    }

}

@NonCPS  // Necessary to allow .each to work.
def authorlist() {
   def authors = []
    currentBuild.changeSets.each { set ->
        set.each { entry ->
            authors << "${entry.author.id}@viadee.de"
        }
    }
    authors.join(',').trim()
}
