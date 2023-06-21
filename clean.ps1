$paths = @(
    "./classes/compute.jar",
    "./compute/Compute.class",
    "./compute/Task.class",
    "./runner/Runner.class",
    "./server/Server.class",
    "./server/stats/DataStats.class",
    "./server/network/ProfileRenderer.class",
    "./server/hashing/Hash.class",
    "./server/hashing/HashFunction.class",
    "./server/hashing/VerifyHash.class"
)

foreach ($path in $paths) {
    if(Test-Path $path) {
        Remove-Item $path -verbose
    }
}