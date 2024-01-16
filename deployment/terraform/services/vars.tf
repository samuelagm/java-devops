variable "do_token" {}

variable "cluster_name" {
  type    = string
  default = "main"
}

variable "rabbitmq_username" {
  type    = string
  default = "guest"
}

variable "rabbitmq_password" {
  type    = string
  default = "guest"
  sensitive = true
}

