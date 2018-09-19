def suites = "None\n" +
        "DefinitionTestSuiteAll\n" +
        "DefinitionTestSuiteCreateProfile\n" +
        "DefinitionTestSuiteCheckout"

def baseurls = "https://qa.vitusapotek.no\n"+
               "https://qa2.vitusapotek.no"

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
        CHOICE_TESTS = "\"-Dcucumber.options=--tags "
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
                            sh "cd vitusapotek && ${M2_HOME}/bin/mvn ${env.CHOICE_SUITE}${params.tc_one} ${env.MVN_GOAL}" +
                                    " ${env.CHOICE_URL}${params.url}" + " ${env.CHOICE_TESTS}${params.tags}\""
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
//                                  reportName           : 'Serenity Test Suite 1',
                                    reportName           : params.tc_one,
                                    reportDir            : 'vitusapotek/target/site/serenity',
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
                            sh "cd vitusapotek && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_two}" +
                                    " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
                                    //     reportName           : 'Serenity Test Suite 2',
                                    reportName           : params.tc_two,
                                    reportDir            : 'vitusapotek/target/site/serenity',
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
                            sh "cd vitusapotek && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_three}" +
                                    " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
                                    //   reportName           : 'Serenity Test Suite 3',
                                    reportName           : params.tc_three,
                                    reportDir            : 'vitusapotek/target/site/serenity',
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
                            sh "cd vitusapotek && ${M2_HOME}/bin/mvn ${env.MVN_GOAL} ${env.CHOICE_SUITE}${params.tc_four}" +
                                    " ${env.CHOICE_URL}${params.url}"
                        }
                    }
                    post {
                        always {
                            publishHTML(target: [
                                    //reportName           : 'Serenity Test Suite 4',
                                    reportName           : params.tc_four,
                                    reportDir            : 'vitusapotek/target/site/serenity',
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