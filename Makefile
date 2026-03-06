build:
	docker build -t ghcr.io/byronmoreno/university-backend:latest .

deploy:
	docker stack deploy --with-registry-auth -c stack.yml university

rm:
	docker stack rm university

