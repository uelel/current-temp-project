#!/bin/bash

mvn verify -Dwebdriver.remote.driver=chrome &
mvn verify -Dwebdriver.remote.driver=firefox &
mvn verify -Dwebdriver.remote.driver=edge &
