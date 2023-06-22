# cdp-tpfinal
Trabalho prático final de Computação Paralela e Distribuída.

## Compilação e execução

### Compilação

Compile o módulo compute, a ser usado pelo server e pelo executor

```
javac compute/Compute.java compute/Task.java
jar cvf classes/compute.jar compute/*.class
```

Compile o executor e os módulos do servidor

```
javac -cp "./classes/compute.jar" runner/Runner.java server/hashing/*.java server/network/ProfileRenderer.java server/stats/DataStats.java
```

Compile o servidor
```
javac -cp "./classes/compute.jar;." server/Server.java server/ClientHandler.java
```

### Execução

Execute o executor. O único argumento necessário é a porta na qual o rmiregistry será criado.

```
java -cp ".;.\classes\compute.jar" '-Djava.security.policy=server-win.policy' runner.Runner 1099
```

Execute o servidor. Os argumentos são, respectivamente: host do rmiregistry, porta do rmiregistry, porta na qual o servidor deve rodar.

```
java -cp ".;.\classes\compute.jar" '-Djava.security.policy=client-win.policy' server.Server localhost 1099 666
```

O cliente deve ser executado via IDE, preferencialmente IntelliJ IDEA.
Todavia, outra IDE com suporte à projetos Maven deve funcionar adequadamente.

O endereço e porta do servidor devem ser configurados no arquivo ``conn.properties`` para que o cliente conecte-se corretamente.

### Scripts de execução

Alternativamente, utilize os scripts de execução (Powershell apenas).

Limpe compilações anteriores, para evitar conflito por autorização.

```
./scripts/clean.ps1
```

Compile

```
./scripts/build.ps1
```

Executor
```
./scripts/run-runner.ps1
```

Servidor
```
./scripts/run-server.ps1
```