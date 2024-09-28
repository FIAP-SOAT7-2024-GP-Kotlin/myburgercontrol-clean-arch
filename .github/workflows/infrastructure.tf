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

resource "digitalocean_droplet" "myburger" {
  image  = "docker-20-04"  # Escolha uma imagem com Docker instalado
  name   = "myburger-droplet"
  region = "nyc1"          # Você pode escolher a região
  size   = "s-1vcpu-2gb"   # Escolha o tamanho do droplet
  ssh_keys = [var.ssh_fingerprint]
}

output "droplet_ip" {
  value = digitalocean_droplet.myburger.ipv4_address
}
