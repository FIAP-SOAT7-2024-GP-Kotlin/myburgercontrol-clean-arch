provider "digitalocean" {
  token = var.digitalocean_token
}

terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"  # Especifique a versão adequada (por exemplo, 2.0 ou outra)
    }
  }
}

resource "digitalocean_droplet" "my_burger" {
  image    = "docker-20-04"
  name     = "my-burger-api-gtw"
  region   = "nyc1"
  size     = "s-1vcpu-1gb"
  vpc_uuid = var.vpc_id
  ssh_keys = [43452957, 43405929]
}

# Cria um cluster Kubernetes na DigitalOcean

resource "digitalocean_kubernetes_cluster" "my_k8s_cluster" {
  name     = "my-burger-k8s"
  region   = "nyc1"
  version  = "1.31.1-do.0"
  vpc_uuid = var.vpc_id

  node_pool {
    name       = "my-burger-node-pool"
    size       = "s-2vcpu-2gb"  # Escolha o tipo do nó
    node_count = 2  # Número de nós no cluster

    # A chave SSH será usada para acessar os nós do Kubernetes, se necessário
    ssh_ids = [var.ssh_fingerprint]
  }
}
# Output do endpoint do cluster

output "kubernetes_endpoint" {
  value = digitalocean_kubernetes_cluster.my_k8s_cluster.endpoint
}
# Output do token do cluster

output "kubeconfig" {
  value = digitalocean_kubernetes_cluster.my_k8s_cluster.kube_config
}

output "droplet_ip" {
  value = digitalocean_kubernetes_cluster.my_k8s_cluster.ipv4_address
}
