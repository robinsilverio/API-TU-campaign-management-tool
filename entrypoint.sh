#!/bin/sh
exec java -Djasypt.encryptor.password="${JASYPT_ENCRYPTOR_PASSWORD}" -Dtu.app.jwtSecret="${TU_APP_JWTSECRET}" -jar /TU_campaign_management_tool_API-0.0.1.jar "$@"
