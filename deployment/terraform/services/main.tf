terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "helm" {
  kubernetes {
    host  = data.digitalocean_kubernetes_cluster.ecabs_cluster.endpoint
    token = data.digitalocean_kubernetes_cluster.ecabs_cluster.kube_config[0].token
    cluster_ca_certificate = base64decode(
      data.digitalocean_kubernetes_cluster.ecabs_cluster.kube_config[0].cluster_ca_certificate
    )
  }
}

provider "digitalocean" {
  token = var.do_token
}

data "digitalocean_kubernetes_cluster" "ecabs_cluster" {
  name = var.cluster_name
}

resource "helm_release" "rabbitmq" {
  name             = "rabbitmq"
  repository       = "oci://registry-1.docker.io/bitnamicharts"
  chart            = "rabbitmq"
  create_namespace = true
  namespace        = "broker"

  
  set {
    name  = "auth.username"
    value = var.rabbitmq_username
  }

  set {
    name  = "auth.password"
    value = var.rabbitmq_password
  }
}
