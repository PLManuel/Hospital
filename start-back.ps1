$microservicios = @(
  "eureka-server",
  "config-server",
  "auth-service",
  "employee-service",
  "specialty-service",
  "doctor-service",
  "patient-service",
  "office-service",
  "schedule-service",
  "appointment-service",
  "medical-history-service",
  "receipt-service",
  "medical-attention-service",
  # ----> Añadir aquí todos los microservicios
  "gateway-service"
)

$paths = $microservicios | ForEach-Object {
  Join-Path -Path $PSScriptRoot -ChildPath $_
}

foreach ($index in 0..($paths.Length - 1)) {
  $path = $paths[$index]
  $microservicio = $microservicios[$index]

  if (-not (Test-Path $path)) {
    Write-Warning "La carpeta '$path' no existe. Se omite."
    continue
  }

  Start-Process wt.exe -ArgumentList @(
    "-w", "backend-hospital-window",
    "new-tab",
    "--title", $microservicio,
    "-d", $path,
    "powershell",
    "-NoExit",
    "-Command", "mvn spring-boot:run"
  )

  Start-Sleep -Seconds 7
}