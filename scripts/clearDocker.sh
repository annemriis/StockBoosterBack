#!/bin/bash
docker system prune -f
docker rm -f "front"
docker rm -f "back"
docker rm -f "postgres"
