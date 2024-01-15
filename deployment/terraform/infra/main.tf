terraform {
  required_providers {
    digitalocean = { # Name of the provider
      source = "digitalocean/digitalocean" # Source of the provider
      version = "~> 2.0" # Version of the provider
    }
  }
}