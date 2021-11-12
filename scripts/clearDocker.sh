#!/bin/bash
docker system prune -y
docker rm -f "front"
docker rm -f "back"
docker rm -f "postgres"
docker network rm net