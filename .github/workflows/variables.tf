variable "do_token" {
  description = "Token de autenticação para acessar a DigitalOcean"
  type        = string
  default     = ""
}

variable "vpc_id" {
  type    = string
  default = "ba5f81a6-5df2-4149-ad14-10cb12440d95"
}
