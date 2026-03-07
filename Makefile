build:
	docker build -t ghcr.io/byronmoreno/university-backend:latest .

network:
	@ docker network create --driver overlay --scope swarm desarrollo_net || true

deploy:
	docker stack deploy --with-registry-auth -c stack.yml university

rm:
	docker stack rm university
