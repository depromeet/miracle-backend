variable "name" {
    description = "This is Prefix Name"
    type        = string
}
variable "avail_zones" {
    description = "Seoul Region"
    type        = list
}
variable "vpc_cidr" {
    description = "Set VPC CIDR "
    type        = string
}
variable "public_subnet_cidr" {
    description = "Set Public Subnet CIDR"
    type        = list
}
