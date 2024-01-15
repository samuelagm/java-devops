# Configure the DigitalOcean Provider
provider "digitalocean" {
  token = var.do_token
}

# Create a VPC with name "terraform-k8s-network"
resource "digitalocean_vpc" "terraform-k8s-vpc" {
  name     = "terraform-k8s-network" # Name of the VPC
  region   = "ams3"
  ip_range = "172.16.0.0/16" # IP range of the VPC
}


# Create a Kubernetes cluster in the VPC
resource "digitalocean_kubernetes_cluster" "ecabs_cluster" {
  name     = "main"
  region   = "ams3"
  version  = "1.28.2-do.0"
  vpc_uuid = digitalocean_vpc.terraform-k8s-vpc.id

  node_pool {
    name       = "autoscale-worker-pool"
    size       = "s-2vcpu-2gb"
    node_count = 1
  }
}

# Output the kubeconfig of the created cluster
output "kubernetes_cluster_output" {
  value       = digitalocean_kubernetes_cluster.ecabs_cluster.kube_config.0.raw_config # Output the raw kubeconfig
  description = "The raw kubeconfig of the created cluster"
  sensitive   = true
}
