MAKEFILE_DIR = $(abspath $(lastword $(MAKEFILE_LIST)))

JFLAGS = -g
JC = javac
SRC = ${MAKEFILE_DIR}/src
BUILD = ${MAKEFILE_DIR}/build

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFLAGS) $*.java -d ${BUILD}

CLASSES = \
	${SRC}/ExpertSystem.java

default: classes

classes: $(CLASSES:.java=.class)

clean:
	$(RM) -r ${BUILD}/*