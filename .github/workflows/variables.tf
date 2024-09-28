variable "digitalocean_token" {
  description = "Token de autenticação para acessar a DigitalOcean"
  type        = string
}

variable "ssh_fingerprint" {
  description = "Fingerprint da chave SSH para acessar o droplet"
  type        = string
}
