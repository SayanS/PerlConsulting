
def suites = "None\n" +
         "DefinitionTestSuiteAll\n" +
         "DefinitionTestSuiteCreateProfile\n" +
         "DefinitionTestSuiteCheckout\n" +
         "DefinitionTestSuiteMyPrescription\n" +
         "DefinitionTestSuitePDP\n" +
         "DefinitionTestSuiteProductsPage\n" +
         "DefinitionTestSuiteShoppingCart\n"

def     baseurls = "https://qa-lloydspharma-be:9002\n"+
                   "https://qa.lloydspharma.be\n"
pipeline {
    agent any
    options {
        buildDiscarder(logRotator(numToKeepStr: '10'))
        disableConcurrentBuilds()
    }
    parameters {
        string(defaultValue: '@RunAll', description: 'Tags to choice ', name: 'tags')
        choice(name: 'url', choices: "${baseurls}", description: 'select url')
        choice(name: 'tc_one', choices: "${suites}", description: 'Test suite 1')
        choice(name: 'tc_two', choices: "${suites}", description: 'Test suite 2')
        choice(name: 'tc_three', choices: "${suites}", description: 'Test suite 3')
        choice(name: 'tc_four', choices: "${suites}", description: 'Test suite 4')
    }
    environment {
        MVN_GOAL = "clean verify"
        CHOICE_SUITE = "-Dit.test="
        CHOICE_URL = "-Dbase.url="
        CHOICE_TESTS="\"-Dcucumber.options=--tags "
    }
    stages {
        stage('Tests execution') {
            parallel {
                stage('Execution of Test Suite 1') {
                    when {
                        expression { return params.tc_one != 'None' }
                    }
                    agent any
                    steps {
                        wrap([$class: 'Xvfb', screen: '1920x1080x16', autoDisplayName: true]) {
                            sh "cd lloydspharma && ${M2_HOME}/bin/mvn ${env.CHOICE_SUITE}${params.tc_one} ${env.MVN_GOAL}" +
                               " ${env.CHOICE_URL}${params.url}"+ " ${env.CHOICE_TESTS}${params.tags}\""
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
//                                    reportName           : 'Serenity Test Suite 1',
                                    reportName           : params.tc_one,
                                    reportDir            : 'lloydspharma/target/site/serenity',
                                    reportFiles          : 'index.html',
                                    keepAll              : true,
                                    alwaysLinkToLastBuild: true,
                                    allowMissing         : false
                            ])
                        }
                    }
                }
                stage('Execution of Test Suite 2') {
                    when {
                        expression { return params.tc_two != 'None' }
                    }
                    agent any
                    steps {
                        wrap([$class: 'Xvfb', screen: '1920x1080x16', autoDisplayName: true]) {
                        sh "cd lloydspharma && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_two}" +
                                " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
//                                    reportName           : 'Serenity Test Suite 2',
                                    reportName           : params.tc_two,
                                    reportDir            : 'lloydspharma/target/site/serenity',
                                    reportFiles          : 'index.html',
                                    keepAll              : true,
                                    alwaysLinkToLastBuild: true,
                                    allowMissing         : false
                            ])
                        }
                    }
                }
                stage('Execution of Test Suite 3') {
                    when {
                        expression { return params.tc_three != 'None' }
                    }
                    agent any
                    steps {
                        wrap([$class: 'Xvfb', screen: '1920x1080x16', autoDisplayName: true]) {
                        sh "cd lloydspharma && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_three}" +
                                " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
//                                    reportName           : 'Serenity Test Suite 3',
                                    reportName           : params.tc_three,
                                    reportDir            : 'lloydspharma/target/site/serenity',
                                    reportFiles          : 'index.html',
                                    keepAll              : true,
                                    alwaysLinkToLastBuild: true,
                                    allowMissing         : false
                            ])
                        }
                    }
                }
                stage('Execution of Test Suite 4') {
                    when {
                        expression { return params.tc_four != 'None' }
                    }
                    agent any
                    steps {
                        wrap([$class: 'Xvfb', screen: '1920x1080x16', autoDisplayName: true]) {
                        sh "cd lloydspharma && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_four}" +
                                " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
//                                    reportName           : 'Serenity Test Suite 4',
                                    reportName           : params.tc_four,
                                    reportDir            : 'lloydspharma/target/site/serenity',
                                    reportFiles          : 'index.html',
                                    keepAll              : true,
                                    alwaysLinkToLastBuild: true,
                                    allowMissing         : false
                            ])
                        }
                    }
                }
            }
        }
    }
}