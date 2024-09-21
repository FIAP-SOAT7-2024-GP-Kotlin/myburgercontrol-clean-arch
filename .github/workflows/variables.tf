variable "digitalocean_token" {
  description = "Token de acesso da DigitalOcean"
  type        = string
}

variable "ssh_key_id" {
  description = "ID da chave SSH para acesso ao droplet"
  type        = string
}

variable "region" {
  description = "Região onde o droplet será criado"
  type        = string
  default     = "nyc1"
}

variable "image" {
  description = "Imagem do sistema operacional do droplet"
  type        = string
  default     = "ubuntu-20-04-x64"
}

variable "size" {
  description = "Tamanho do droplet"
  type        = string
  default     = "s-1vcpu-1gb"
}
