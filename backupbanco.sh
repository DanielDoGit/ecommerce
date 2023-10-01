#!/bin/bash
export PGPASSWORD=LSJ6PGFB2000
pg_dump --file "./ecommerce.sql" --host "dev.limasoftware.com.br" --port "5432" --username "postgres" --verbose --format=p --no-owner --no-privileges --no-owner --no-privileges --no-tablespaces --no-unlogged-table-data --inserts "ecommerce"
