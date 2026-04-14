pipeline {
    agent any

    options {
        skipDefaultCheckout(true)
        timestamps()
    }

    parameters {
        string(name: 'GIT_URL', defaultValue: 'https://github.com/ttodoshi/sportly-testing.git', description: 'Git repository URL')
        string(name: 'GIT_BRANCH', defaultValue: 'main', description: 'Branch to run tests from')
        string(name: 'GIT_CREDENTIALS_ID', defaultValue: '', description: 'Jenkins credentials ID (optional, for private repo)')
        string(name: 'MAVEN_TOOL_NAME', defaultValue: 'mvn', description: 'Jenkins Maven tool name from Manage Jenkins -> Tools')
        string(name: 'ALLURE_TOOL_NAME', defaultValue: '', description: 'Optional: Jenkins Allure Commandline name from Manage Jenkins -> Tools')
        string(name: 'TEST_SCOPE', defaultValue: '', description: 'Optional: ALL | GUI | API. Empty = ALL')
        string(name: 'FEATURES_PATH', defaultValue: '', description: 'Optional legacy override. Supports only classpath:features/gui or classpath:features/api (mapped to tags).')
        string(name: 'CUCUMBER_TAGS', defaultValue: '', description: 'Optional tag expression, e.g. @smoke and not @wip')
    }

    environment {
        MAVEN_OPTS = '-Dmaven.repo.local=.m2/repository'
    }

    stages {
        stage('Init Tools') {
            steps {
                script {
                    if (!params.MAVEN_TOOL_NAME?.trim()) {
                        error("MAVEN_TOOL_NAME is required")
                    }
                    def mavenHome = tool name: params.MAVEN_TOOL_NAME.trim(), type: 'maven'
                    env.PATH = "${mavenHome}/bin:${env.PATH}"
                    echo "Using Jenkins Maven tool: ${params.MAVEN_TOOL_NAME.trim()}"
                }
            }
        }

        stage('Check Tools') {
            steps {
                script {
                    if (sh(returnStatus: true, script: 'command -v mvn >/dev/null 2>&1') != 0) {
                        error("Maven is not available. Configure Manage Jenkins -> Tools -> Maven and set MAVEN_TOOL_NAME parameter.")
                    }
                }
                sh 'mvn -version'
            }
        }

        stage('Checkout') {
            steps {
                script {
                    if (!params.GIT_URL?.trim()) {
                        error("Parameter GIT_URL is required")
                    }
                    if (!params.GIT_BRANCH?.trim()) {
                        error("Parameter GIT_BRANCH is required")
                    }

                    def remoteConfig = [url: params.GIT_URL]
                    if (params.GIT_CREDENTIALS_ID?.trim()) {
                        remoteConfig.credentialsId = params.GIT_CREDENTIALS_ID.trim()
                    }

                    def branchRef = params.GIT_BRANCH.trim()
                    if (!branchRef.startsWith('refs/heads/') && !branchRef.startsWith('*/')) {
                        branchRef = "*/${branchRef}"
                    }

                    checkout([
                        $class: 'GitSCM',
                        branches: [[name: branchRef]],
                        userRemoteConfigs: [remoteConfig]
                    ])
                }
            }
        }

        stage('Run Cucumber Tests') {
            steps {
                script {
                    def normalizedScope = (params.TEST_SCOPE ?: '').trim().toUpperCase()
                    if (!normalizedScope) {
                        normalizedScope = 'ALL'
                    }
                    if (!(normalizedScope in ['ALL', 'GUI', 'API'])) {
                        error("TEST_SCOPE must be ALL, GUI or API")
                    }

                    def scopeTags = ''
                    if (normalizedScope == 'GUI') {
                        scopeTags = '@ui'
                    } else if (normalizedScope == 'API') {
                        scopeTags = '@api'
                    }
                    // JUnit 5 Platform + Cucumber should not use cucumber.features.
                    // Keep FEATURES_PATH as a legacy input by translating common values to scope tags.
                    if (params.FEATURES_PATH?.trim()) {
                        def normalizedFeaturesPath = params.FEATURES_PATH.trim().toLowerCase()
                        if (normalizedFeaturesPath.startsWith('classpath:features/gui')) {
                            scopeTags = '@ui'
                            echo "FEATURES_PATH mapped to @ui tag selection"
                        } else if (normalizedFeaturesPath.startsWith('classpath:features/api')) {
                            scopeTags = '@api'
                            echo "FEATURES_PATH mapped to @api tag selection"
                        } else {
                            error("FEATURES_PATH='${params.FEATURES_PATH.trim()}' is not supported with JUnit Platform. Use TEST_SCOPE (ALL|GUI|API) and/or CUCUMBER_TAGS.")
                        }
                    }

                    def effectiveTags = ''
                    if (scopeTags && params.CUCUMBER_TAGS?.trim()) {
                        effectiveTags = "(${scopeTags}) and (${params.CUCUMBER_TAGS.trim()})"
                    } else if (scopeTags) {
                        effectiveTags = scopeTags
                    } else if (params.CUCUMBER_TAGS?.trim()) {
                        effectiveTags = params.CUCUMBER_TAGS.trim()
                    }

                    def cmd = "mvn -B clean test"
                    if (effectiveTags) {
                        cmd += " -Dcucumber.filter.tags=\"${effectiveTags}\""
                    }

                    sh cmd
                }
            }
        }
    }

    post {
        always {
            junit testResults: 'target/surefire-reports/*.xml', allowEmptyResults: true
            script {
                try {
                    def allureConfig = [results: [[path: 'target/allure-results']]]
                    if (params.ALLURE_TOOL_NAME?.trim()) {
                        allureConfig.commandline = params.ALLURE_TOOL_NAME.trim()
                    }
                    allure allureConfig
                } catch (err) {
                    echo "Allure publisher unavailable or incompatible: ${err}"
                    archiveArtifacts artifacts: 'target/allure-results/**', allowEmptyArchive: true
                }
            }
        }
    }
}
