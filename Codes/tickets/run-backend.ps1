Set-StrictMode -Version Latest
$ErrorActionPreference = "Stop"

function Start-InNewTerminal([string]$WorkingDir, [string]$Title, [string]$Command) {
  $escaped = $Command.Replace('"', '\"')
  Start-Process powershell -ArgumentList @(
    "-NoExit",
    "-Command",
    "cd `"$WorkingDir`"; `$host.ui.RawUI.WindowTitle = `"$Title`"; $escaped"
  )
}

$repoRoot = Resolve-Path (Join-Path $PSScriptRoot "..\..")
$ticketsDir = Resolve-Path (Join-Path $PSScriptRoot ".")

$dockerDir = Join-Path $ticketsDir "docker"

Write-Host "Starting Postgres + pgAdmin (Docker Compose)..."
Push-Location $dockerDir
try {
  docker compose -f docker-compose-dev.yml up -d
} finally {
  Pop-Location
}

Write-Host "Starting Spring services in separate terminals..."

Start-InNewTerminal (Join-Path $ticketsDir "nameserver") "tickets-nameserver" "cmd /c mvnw.cmd spring-boot:run"
Start-InNewTerminal (Join-Path $ticketsDir "users")      "tickets-users"      "cmd /c mvnw.cmd spring-boot:run"
Start-InNewTerminal (Join-Path $ticketsDir "sales")      "tickets-sales"      "cmd /c mvnw.cmd spring-boot:run"
Start-InNewTerminal (Join-Path $ticketsDir "gateway")    "tickets-gateway"    "cmd /c mvnw.cmd spring-boot:run"

Write-Host ""
Write-Host "Done."
Write-Host "Eureka:  http://localhost:8761"
Write-Host "Gateway: http://localhost:8080"

