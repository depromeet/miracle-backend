module "vpc" {
    source = "../module"

    name = "depromeet-mimo"
    avail_zones = ["ap-northeast-2a"]
    vpc_cidr = "10.0.0.0/16"
    public_subnet_cidr = ["10.0.0.0/24"]
}
