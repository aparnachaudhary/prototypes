#
# WildFly Domain Dockerfile
#
#

# Pull base image.
FROM jboss/wildfly

MAINTAINER Aparna Chaudhary

# Create User.
RUN /opt/jboss/wildfly/bin/add-user.sh demo demo --silent

CMD ["/opt/jboss/wildfly/bin/domain.sh", "-b", "0.0.0.0", "-bmanagement", "0.0.0.0", "--host-config", "host-master.xml"]
