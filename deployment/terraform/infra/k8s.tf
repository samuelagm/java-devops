provider "digitalocean" {
  token = var.do_token
}


resource "digitalocean_kubernetes_cluster" "ecabs_cluster" {
  name     = "main"
  region   = "ams3"
  version  = "1.28.2-do.0"

  node_pool {
    name       = "autoscale-worker-pool"
    size       = "s-2vcpu-2gb"
    node_count = 1
  }
}
