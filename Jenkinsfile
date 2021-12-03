#!/usr/bin/env groovy

def kmodelers = [
  "unix": "org.integratedmodelling.klab.modeler.product-linux.gtk.x86_64.zip",
  "win": "org.integratedmodelling.klab.modeler.product-win32.win32.x86_64.zip",
  "macos": "org.integratedmodelling.klab.modeler.product-macosx.cocoa.x86_64.zip",
]

pipeline {
    agent { label "mvn-java-agent"}

    parameters {
        string(name: 'BRANCH',
            defaultValue: '',
            description :'Which branch should be used for the build?\n' + 
            'Empty is the default value and will generate build on the latest commit'
        )

        string(name: 'TAG',
            defaultValue: '',
            description: 'Variable used for tagging the container images or generating ' +
            'a build based on a tagged commit.  Default is empty and the tag is determined ' +
            'by the most recent commit.'
        )   

        string(name: 'MINIO_HOST',
            defaultValue: '',
            description: 'Minio host used to archive files'
        )

        string(name: 'MINIO_CREDENTIALS',
            defaultValue: '',
            description: 'Minio credentials used to be used by job'
        )
            
        string(name: 'REGISTRY_CREDENTIALS',
            defaultValue: '',
            description: 'Docker registry credentials used to be used by job'
        ) 
            
        string(
            name: 'GIT_CREDENTIALS',
            defaultValue: '',
            description: 'Git Credentials'
        )
    }

    environment {
        VERSION_DATE = sh(
            script: "date '+%Y-%m-%dT%H:%M:%S'",
            returnStdout: true).trim()
        MAVEN_OPTS="--illegal-access=permit"
        REGISTRY = "registry.integratedmodelling.org"
        STAT_CONTAINER = "stat-server-16"
        ENGINE_CONTAINER = "engine-server-16"
        HUB_CONTAINER = "hub-server-16"
        NODE_CONTAINER = "node-server-16"
        BASE_CONTAINER = "klab-base-16"
        MAIN = "master"
        DEVELOP = "develop"
    }

    stages {
        /*
        We pull the repo to start, this also is used as a for web hook monitoring by jenkins
        and github.
        */
        stage ('Clone Repo') {
            steps {

                checkout([
                    $class: 'GitSCM',
                    extensions: [],
                    userRemoteConfigs: [[
                        credentialsId: "${params.GIT_CREDENTIALS}", 
                        url: 'git@github.com:integratedmodelling/klab.git'
                    ]]
                ])

                withCredentials(
                    [usernamePassword(
                        credentialsId: "${params.MINIO_CREDENTIALS}",
                        passwordVariable: 'SECRETKEY',
                        usernameVariable: 'ACCESSKEY'
                        )
                    ]){ sh 'mc alias set minio $MINIO_HOST $ACCESSKEY $SECRETKEY' }

                script {
                    
                    if  (TAG.isEmpty() == false) {
                        echo "tag paramatized"
                        sh "git checkout tags/${TAG} -b latest"
                        BRANCH = MAIN
                        env.TAG = TAG
                    }
                    //paramatized branch checkout
                    if (BRANCH.isEmpty() ==  false && TAG.isEmpty() == true ) {
                        echo "branch paramatized"
                        sh "git checkout ${BRANCH}"
                        if (BRANCH.equalsIgnoreCase(MAIN)) {
                            echo "latest"
                            env.TAG = "latest"
                        }
                        if (BRANCH.equalsIgnoreCase(env.DEVELOP)) {
                            env.TAG = BRANCH
                        }
                    }
                    //unparamatized checkout of latest commit
                    if (BRANCH.isEmpty() == true && TAG.isEmpty() == true ){
                        echo "unparamatized"
                        BRANCH = sh(
                            returnStdout: true,
                            script: 'git for-each-ref --count=1 --sort=-committerdate --format="%(refname:short)"'
                        ).trim().replace("origin/", "")
                        sh "git checkout ${BRANCH}"
                        env.TAG = BRANCH.replace("/","-")
                        if (BRANCH == MAIN) {
                            env.TAG = "latest"
                        }
                        if (BRANCH == DEVELOP) {
                            env.TAG = BRANCH
                        }
                    }

                    env.SNAPSHOT = sh(
                        returnStdout: true, 
                        script: 'mvn org.apache.maven.plugins:maven-help-plugin:3.1.0:evaluate ' +
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

                    if (env.CURRENT_COMMIT == env.LATEST_TAGGED_COMMIT) {
                        echo "Tagged commit build"
                        env.TAG == LATEST_TAGGED_COMMIT
                    }

                    currentBuild.description = "${BRANCH} build with container tag: ${env.TAG}"
                    
                }

            }
        }
        

        stage ('Update Version.java') {
            steps {
                sh "cd api/org.integratedmodelling.klab.api/src/org/integratedmodelling/klab && " +
                        "sed -i 's;\"VERSION_BRANCH;\"${env.BRANCH};g' Version.java &&" +
                        "sed -i 's;\"VERSION_COMMIT;\"${env.CURRENT_COMMIT};g' Version.java &&" +
                        "sed -i 's;\"VERSION_BUILD;\"${env.BUILD_NUMBER};g' Version.java && " +
                        "sed -i 's;\"VERSION_DATE;\"${env.VERSION_DATE};g' Version.java"
            }

        }

        stage('Maven install with jib') {
            when { expression { env.TAG.isEmpty() == false } }
            steps {
                withCredentials([usernamePassword(credentialsId: "${params.REGISTRY_CREDENTIALS}", passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
                    sh 'export JAVA_HOME=/opt/java16/openjdk && mvn clean install -U -DskipTests jib:build -Djib.httpTimeout=60000'
                }
            }
        }
        
        stage('Maven install') {
            when { expression { env.TAG.isEmpty() == true} }
            steps {
                sh 'export JAVA_HOME=/opt/java16/openjdk && mvn clean install -U -DskipTests'
            }
        }

        stage('Push latest products') {
            when {
                expression { return env.BRANCH.equalsIgnoreCase(env.MAIN) }
            }
            steps {
                pushProducts("latest", kmodelers)
            }
        }

        stage('Push develop products') {
            when {
                expression { return env.BRANCH.equalsIgnoreCase(env.DEVELOP) }
            }
            steps {
                pushProducts(DEVELOP, kmodelers)
            }
        }

        stage ('Push tagged products') {
            when {
                expression { env.LATEST_COMMIT == env.LATEST_TAGGED_COMMIT }
            }
            steps {
                pushProducts(env.TAG, kmodelers)
            }
        }
    }
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

