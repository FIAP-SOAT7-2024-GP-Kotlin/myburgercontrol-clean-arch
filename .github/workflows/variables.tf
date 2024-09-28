variable "do_token" {
  description = "Token de autenticação para acessar a DigitalOcean"
  type        = string
  default     = "default"
}

variable "vpc_id" {
  type    = string
  default = "a2d6c783-0d5c-47f8-95a4-a75146ad7e9c"
}
