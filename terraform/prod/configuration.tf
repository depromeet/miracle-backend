terraform {
    required_version = "~> 0.12.6"
}

provider "aws" {
    version = "~> 2.0"
    region = "ap-northeast-2"

    access_key = var.aws_access_key
    secret_key = var.aws_secret_key
}

variable "aws_access_key" {
    type = string
    default = "dummy"
}

variable "aws_secret_key" {
    type = string
    default = "dummy"
}
