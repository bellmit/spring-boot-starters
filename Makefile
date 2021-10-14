##################################################################
#          Project Convenience Makefile Wrapper for Maven        #
##################################################################

# This makefile is just a convenience wrapper for the Maven
# program. The actual building rules for this project may
# be found in the Maven "pom.xml" file located in this folder.

######################### DEFINITIONS ############################

# Define the commandline invocation of Maven if necessary:
ifeq ($(MVN),)
    MVN  := mvn
endif

ifeq ($(GIT),)
    GIT  := git
endif


ifeq ($(RELEASE_VERSION),)
    RELEASE_VERSION  := $(shell xmllint --xpath "/*[local-name() = 'project']/*[local-name() = 'version']/text()" pom.xml | perl -pe 's/-SNAPSHOT//')
endif

ifeq ($(NEXT_VERSION),)
    NEXT_VERSION  := $(shell echo $(RELEASE_VERSION) | perl -pe 's{^(([0-9]\.)+)?([0-9]+)$$}{$$1 . ($$3 + 1)}e')
endif

ifneq (,$(findstring -SNAPSHOT,$(RELEASE_VERSION)))
	RELEASE_VERSION_NSNP = $(shell echo $(RELEASE_VERSION) | perl -pe 's/-SNAPSHOT//')
else
	RELEASE_VERSION_NSNP = $(RELEASE_VERSION)
endif

ifeq (,$(findstring -SNAPSHOT,$(NEXT_VERSION)))
	NEXT_VERSION_SNP = $(NEXT_VERSION)-SNAPSHOT
else
	 b = $(NEXT_VERSION)
endif
######################## BUILD TARGETS ###########################

.PHONY: all package command compile check install test package migrate mybatis clean help

all:
	@ $(MVN) $(MVNFLAGS) package

build:
	@ $(MVN) $(MVNFLAGS) package -Dcheckstyle.skip -Dpmd.skip -T 1C -B -Dmaven.compile.fork=true -Dmaven.test.skip=true

clean:
	@ $(MVN) $(MVNFLAGS) clean

compile:
	@ $(MVN) $(MVNFLAGS) compile

command:
	@ $(MVN) -pl iot-command spring-boot:run

dev:
	@echo run application
	@ $(MVN) -pl $(module) spring-boot:run -Dspring-boot.run.profiles=dev

test:
	@ $(MVN) $(MVNFLAGS) test

install:
	@ $(MVN) clean install -Dcheckstyle.skip -Dpmd.skip -T 1C -B -Dmaven.compile.fork=true -Dmaven.test.skip=true

lint:
	@ $(MVN) checkstyle:check & $(MVN) pmd:check

new:
	@ $(MVN) archetype:generate -DgroupId=org.shield -DartifactId=${module}

checkstyle:
	@ $(MVN) checkstyle:check

dropall:
	@echo drop all tables
	@ $(MVN) -pl ${module} liquibase:dropAll

pmd:
	@ $(MVN) pmd:check

package:
	@ $(MVN) $(MVNFLAGS) package

deploy-staging:
	@ $(MVN) clean deploy -Dcheckstyle.skip -Dpmd.skip -T 1C

migrate:
	@echo migrate database
	@ $(MVN) -pl ${module} liquibase:update

mybatis:
	@echo tk mybatis code generate
	@ $(MVN) -pl ${module} mybatis-generator:generate

rerun:
	@echo rerun application
	@ make install && $(MVN) -pl $(module) spring-boot:run -Dspring-boot.run.profiles=$(profile)

rollback:
	@echo rollback migrate database
	@ $(MVN) -pl ${module} liquibase:rollback -Dliquibase.rollbackCount=1

run:
	@echo run application
	@ $(MVN) -pl $(module) spring-boot:run -Dspring-boot.run.profiles=$(profile)

version:
	@echo new version
	@ $(MVN) versions:set -DnewVersion=$(version)

help:
	@ echo "Usage   :  make <target>"
	@ echo "Targets :"
	@ echo "   all ........... 构建所有项目"
	@ echo "   build ........... 构建所有项目"
	@ echo "   clean ......... 清理"
	@ echo "   command ....... 进入命令行操作"
	@ echo "   compile ....... 编译"
	@ echo "   dropall ....... 删除所有数据表: make dropall module={module}"
	@ echo "   dev ........... 开发环境运行应用: make dev module={module}"
	@ echo "   lint .......... 代码格式检查"
	@ echo "   new  .......... 创建新的子模块: make new module={module}"
	@ echo "   rollback .....  数据库回滚: make rollback module={module}"
	@ echo "   run ..........  运行应用: make run module={module} profile={profile}"
	@ echo "   test .......... 运行测试"
	@ echo "   install ....... 安装依赖"
	@ echo "   migrate ....... 迁移数据表: make migrate module={module}"
	@ echo "   mybatis ....... Nybatis 代码生成: make mybatis module={module}"
	@ echo "   deploy-staging .Deploys snapshot to staging"
	@ echo "   version ........设置新版本"
	@ echo "   help .......... 打印帮助"
