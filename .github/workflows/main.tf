terraform {
  required_providers {
    digitalocean = {
      source  = "digitalocean/digitalocean"
      version = "~> 2.0"
    }
  }
}

provider "digitalocean" {
  token = var.do_token
}

variable "do_token" {
  type = string
}

resource "digitalocean_droplet" "myburguercontrol_droplet" {
  name   = "myburguercontrol-droplet"
  region = "nyc1"
  size   = "s-1vcpu-1gb"
  image  = "ubuntu-22-04-x64"

  ssh_keys = [var.do_ssh_fingerprint]
}

variable "do_ssh_fingerprint" {
  type = string
}
