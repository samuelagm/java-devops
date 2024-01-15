provider "digitalocean" {
  token = var.do_token
}

resource "digitalocean_kubernetes_cluster" "ecabs_cluster" {
  name    = var.cluster_name
  region  = var.cluster_region
  version = var.cluster_version

  node_pool {
    name       = "autoscale-worker-pool"
    size       = var.cluster_node_size
    node_count = var.cluster_node_count
  }
}
