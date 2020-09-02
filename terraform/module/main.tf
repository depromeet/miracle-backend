resource "aws_vpc" "vpc" {
    cidr_block = var.vpc_cidr

    tags = {
        Name = "${var.name}-vpc"
        Group = var.name
    }
}

resource "aws_subnet" "public_subnet" {
    count = length(var.public_subnet_cidr)

    cidr_block = var.public_subnet_cidr[count.index]
    vpc_id = aws_vpc.vpc.id
    availability_zone = var.avail_zones[count.index]

    tags = {
        Name = "${var.name}-public-subnet-${count.index}"
        Group = var.name
    }
}

resource "aws_route_table" "route_table" {
    vpc_id = aws_vpc.vpc.id

    route {
        cidr_block = "0.0.0.0/0"
        gateway_id = aws_internet_gateway.igw.id
    }

    tags = {
        Name = "${var.name}-route-table"
        Group = var.name
    }
}

resource "aws_route_table_association" "rta" {
    count = length(var.public_subnet_cidr)

    route_table_id = aws_route_table.route_table.id
    subnet_id = aws_subnet.public_subnet[count.index].id
}

resource "aws_internet_gateway" "igw" {
    vpc_id = aws_vpc.vpc.id

    tags = {
        Name = "${var.name}-igw"
        Group = var.name
    }
}

data "aws_ami" "amazon_linux_2" {
    most_recent = true
    filter {
        name = "name"
        values = ["amzn2-ami-hvm-*"]
    }
    owners = ["137112412989"]
}
