terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0" # Especifique a versão adequada (por exemplo, 2.0 ou outra)
    }
  }
}

provider "digitalocean" {
  token = var.do_token
}

# Cria um cluster Kubernetes na DigitalOcean

resource "digitalocean_kubernetes_cluster" "my_burger_kubernetes_cluster" {
  name     = "my-burger-k8s"
  region   = "nyc1"
  version  = "1.31.1-do.0"
  vpc_uuid = var.vpc_id

  node_pool {
    name       = "my-burger-node-pool"
    size       = "s-2vcpu-4gb"
    node_count = 1
    auto_scale = true
    min_nodes  = 1
    max_nodes  = 3
  }
}
