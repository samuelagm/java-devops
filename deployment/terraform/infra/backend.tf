
terraform {
  backend "s3" {
    endpoint                    = "https://ams3.digitaloceanspaces.com" # specify the correct DO region
    region                      = "eu-west-1"                    # not used since it's a DigitalOcean spaces bucket
    key                         = "infra.tfstate"
    bucket                      = "ecabs" # The name of your Spaces
    skip_credentials_validation = true
    skip_metadata_api_check     = true
    skip_region_validation      = true
    skip_requesting_account_id  = true
  }
}
