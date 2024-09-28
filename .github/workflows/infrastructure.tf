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

# resource "digitalocean_droplet" "myburger" {
#   image  = "docker-20-04"  # Escolha uma imagem com Docker instalado
#   name   = "myburger-droplet"
#   region = "nyc1"          # Você pode escolher a região
#   size   = "s-1vcpu-2gb"   # Escolha o tamanho do droplet
#   ssh_keys = [var.ssh_fingerprint]
# }

# Cria um cluster Kubernetes na DigitalOcean

resource "digitalocean_kubernetes_cluster" "my_k8s_cluster" {
  name   = "myburger-cluster"
  region = "nyc1"  # Você pode escolher a região de sua preferência
  version = "1.26.0-do.0"  # Especifique a versão do Kubernetes

  node_pool {
    name       = "default-pool"
    size       = "s-1vcpu-2gb"  # Escolha o tipo do nó
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
