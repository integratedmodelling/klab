#!/usr/bin/env groovy

def kmodelers = [
  "unix": "org.integratedmodelling.klab.modeler.product-linux.gtk.x86_64.zip",
  "win": "org.integratedmodelling.klab.modeler.product-win32.win32.x86_64.zip",
  "macos": "org.integratedmodelling.klab.modeler.product-macosx.cocoa.x86_64.zip",
]

pipeline {
    agent { label "klab-agent-jdk17"}
    options { skipDefaultCheckout(true) }
    environment {
        VERSION_DATE = sh(
            script: "date '+%Y-%m-%dT%H:%M:%S'",
            returnStdout: true).trim()
        MAVEN_OPTS="--illegal-access=permit"
        REGISTRY = "registry.integratedmodelling.org"
        STAT_CONTAINER = "stat-server-17"
        ENGINE_CONTAINER = "engine-server-17"
        HUB_CONTAINER = "hub-server-17"
        NODE_CONTAINER = "node-server-17"
        BASE_CONTAINER = "klab-base-17:04da07762c87f77f2a3c04c880815327f94643c3"
        PRODUCTS_GEN = shouldPushProducts(env.BRANCH_NAME)
        TAG = "${env.BRANCH_NAME.replace('/','-')}"
        MINIO_HOST = "http://192.168.250.224:9000"
        MINIO_CREDENTIALS = "bc42afcf-7037-4d23-a7cb-6c66b8a0aa45"
        REGISTRY_CREDENTIALS = "83f9fb8b-e503-4566-9784-e80f2f2d7c64"
        GIT_CREDENTIALS = "2f30d924-29e5-4235-b61f-a0dbe2bb7783"
    }

    stages {
        /*
        We pull the repo to start, this also is used as a for web hook monitoring by jenkins
        and github.
        */
        stage ('Clone Repo') {
            steps {
                checkout scm

                withCredentials(
                    [usernamePassword(
                        credentialsId: "${env.MINIO_CREDENTIALS}",
                        passwordVariable: 'SECRETKEY',
                        usernameVariable: 'ACCESSKEY'
                        )
                    ]){ sh 'mc alias set minio $MINIO_HOST $ACCESSKEY $SECRETKEY' }

                script {

              		env.SNAPSHOT = sh(
                        returnStdout: true,
                        script: './mvnw org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate ' +
                                '-Dexpression=project.version -q -DforceStdout ' +
                                '--batch-mode -U -e -Dsurefire.useFile=false'
                        ).trim()

                    env.CURRENT_COMMIT = sh (
                            script: 'git rev-parse --verify HEAD',
                            returnStdout: true).trim()

                    env.LATEST_TAGGED_COMMIT = sh (
                            script: 'git describe --tags `git rev-list --tags --max-count=1` |  xargs git rev-list -n 1',
                            returnStdout: true).trim()

                    env.LATEST_TAG = sh (
                            script: 'git describe --tags `git rev-list --tags --max-count=1`',
                            returnStdout: true).trim()

                    currentBuild.description = "${env.BRANCH_NAME} build with container tag: ${env.TAG}"
                    echo "${env.BRANCH_NAME} build with container tag: ${env.TAG} and products generations is ${env.PRODUCTS_GEN}"
                }

            }
        }


        stage ('Update Version.java') {
            steps {
                sh "cd api/org.integratedmodelling.klab.api/src/org/integratedmodelling/klab && " +
                        "sed -i 's;\"VERSION_BRANCH;\"${env.BRANCH_NAME};g' Version.java &&" +
                        "sed -i 's;\"VERSION_COMMIT;\"${env.CURRENT_COMMIT};g' Version.java &&" +
                        "sed -i 's;\"VERSION_BUILD;\"${env.BUILD_NUMBER};g' Version.java && " +
                        "sed -i 's;\"VERSION_DATE;\"${env.VERSION_DATE};g' Version.java"
            }

        }

        stage('Maven install with jib') {
            steps {
                withCredentials([usernamePassword(credentialsId: "${env.REGISTRY_CREDENTIALS}", passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                    sh './mvnw -U clean install -DskipTests jib:build -Djib.httpTimeout=60000'
                    sh './mvnw -pl :klab.ogc test -Dtest="*STAC*"'
                }
            }
        }

        stage('Maven deploy') {
            when {
                anyOf { branch 'develop'; branch 'master' }
            }
            steps {
                configFileProvider([configFile(fileId: '1f5f24a2-9839-4194-b2ad-0613279f9fba', variable: 'MAVEN_SETTINGS_XML')]) {
                    sh './mvnw --settings $MAVEN_SETTINGS_XML deploy -pl :api -DskipTests'
                }
            }
        }

        stage('Push products') {
            when {
                expression { env.PRODUCTS_GEN == "yes" }
            }
            steps {
                pushProducts(productsFolderName(env.BRANCH_NAME), kmodelers)
            }
        }
    }
}

def shouldPushProducts(branchName) {
    return branchName == 'master' || branchName == 'develop' ? 'yes' : 'no'
}

def productsFolderName(branchName) {
    return branchName == 'master'  ? 'latest' : branchName
}

def pushProducts(destination, kmodelers) {
    prepareCliUpload(destination)
    prepareKmodelersUpload(kmodelers,destination)
    uploadProducts(destination)
}


def prepareCliUpload(destination) {
    sh """mkdir -p ${WORKSPACE}/minio/${destination}/cli
          cp products/cli/target/cli-*-SNAPSHOT.jar ${WORKSPACE}/minio/${destination}/cli
          cp -r products/cli/target/lib ${WORKSPACE}/minio/${destination}/cli
          cd ${WORKSPACE}/minio/${destination}/cli
          touch product.properties
          echo "klab.product.name=k.LAB Engine" >>  product.properties
          echo "klab.product.description=The k.LAB modeling engine" >>  product.properties
          echo "klab.product.type=JAR" >>  product.properties
          echo "klab.product.build.version=${env.SNAPSHOT}" >>  product.properties
          echo "klab.product.build.time=${VERSION_DATE}" >>  product.properties
          echo "klab.product.build.main=org.integratedmodelling.klab.k.Main" >> product.properties
          md5sum `find . -type f -print` > filelist.txt
      """
}


def uploadProducts(destination){
    sh """
       mc rm --recursive --force minio/products/klab/${destination} || echo "${destination} does not exists"
       mc cp --recursive  ${WORKSPACE}/minio/${destination} minio/products/klab/
       """
}


def prepareKmodelersUpload(list, destination) {
    dir ("${WORKSPACE}/minio/${destination}/kmodeler/") {
        sh """touch product.properties
            rm product.properties
            touch product.properties
            echo "klab.product.name=k.LAB Modeler" >> product.properties
            echo "klab.product.description=The k.LAB modeler interface" >> product.properties
            echo "klab.product.type=ECLIPSE" >> product.properties
            echo "klab.product.osspecific=true" >> product.properties
            echo "klab.product.build.version=${env.SNAPSHOT}" >> product.properties
            echo "klab.product.build.time=${VERSION_DATE}" >> product.properties
        """
    }
    list.each { element ->
        dir ("${WORKSPACE}/minio/${destination}/kmodeler/${element.key}") {
            unzip zipFile: "${WORKSPACE}/ide/org.integratedmodelling.klab.ide.repository/target/products/${element.value}", quiet: true
            if(element.key != "macos") {
                sh """cd kmodeler && md5sum `find . -type f` > filelist.txt && mv * .."""
                sh """rm -r kmodeler"""
            } else {
                sh """md5sum `find . -type f` > filelist.txt"""
            }
        }
    }
}
