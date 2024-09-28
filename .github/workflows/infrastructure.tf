provider "digitalocean" {
  token = var.digitalocean_token
}

resource "digitalocean_droplet" "myburger" {
  image  = "docker-20-04"  # Escolha uma imagem com Docker instalado
  name   = "myburgercontrol-droplet"
  region = "nyc1"          # Você pode escolher a região
  size   = "s-1vcpu-1gb"   # Escolha o tamanho do droplet
  ssh_keys = [var.ssh_fingerprint]
}

output "droplet_ip" {
  value = digitalocean_droplet.myburger.ipv4_address
}
