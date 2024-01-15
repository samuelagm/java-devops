variable "do_token" {}

variable "cluster_name" {
  type    = string
  default = "main"
}

variable "cluster_region" {
  type    = string
  default = "ams3"
}

variable "cluster_version" {
  type    = string
  default = "1.28.2-do.0"
}

variable "cluster_node_size" {
  type    = string
  default = "s-2vcpu-4gb"
}

variable "cluster_node_count" {
  type    = number
  default = 1
}
