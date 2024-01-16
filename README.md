# eCabs DevOps challenge

- The CI/CD pipeline is managed through GitHub Actions, as defined in the provided [file](.github/workflows/cd.yaml).
- Terraform handles the setup of Kubernetes (K8s) and RabbitMQ.
- The booking-producer service is publicly accessible via a nodePort.
- The live application can be accessed at http://159.65.204.52:30000/swagger-ui/index.html.
- Application properties are dynamically configured using environment variables, which are managed by GitHub's variables and secrets feature.


##### Missing
- Ingress Controller
- Liveness and Readiness Probes Inadequate for book-producer service
